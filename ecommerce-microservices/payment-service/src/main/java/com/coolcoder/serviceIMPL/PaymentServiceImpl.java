package com.coolcoder.serviceIMPL;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.coolcoder.dto.PaymentDTO;
import com.coolcoder.dto.ProductDTO;
import com.coolcoder.dto.PurchaseDTO;
import com.coolcoder.exception.ResourceNotFoundException;
import com.coolcoder.feignClients.ProductClient;
import com.coolcoder.feignClients.PurchaseClient;
import com.coolcoder.model.Payment;
import com.coolcoder.repository.PaymentRepository;
import com.coolcoder.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
	private final PaymentRepository repo;
	private final RazorpayClient razorpay;
	private final PurchaseClient purchaseClient;
	private final ProductClient productClient;

	@Value("${razorpay.key-secret}")
	private String razorpaySecret;

	@Override
	@Transactional
	public PaymentDTO initiate(Long purchaseId, Double amount) {
		// 1) Fetch and validate the purchase
		PurchaseDTO purchase = purchaseClient.getById(purchaseId);
		if (purchase == null) {
			throw new ResourceNotFoundException("Purchase not found: " + purchaseId);
		}

		// 2) Fetch the product to validate its unit price
		ProductDTO product = productClient.getById(purchase.getProductId());
		if (product == null) {
			throw new ResourceNotFoundException("Product not found: " + purchase.getProductId());
		}

		// 3) Compute and validate expected amount = unit price * quantity
		double expectedAmount = product.getPrice() * purchase.getQuantity();
		if (!Objects.equals(amount, expectedAmount)) {
			throw new IllegalArgumentException("Invalid amount. Expected: " + expectedAmount);
		}

		try {
			// 4) Create Razorpay order
			JSONObject options = new JSONObject();
			options.put("amount", (int) (amount * 100)); // convert to paise
			options.put("currency", "INR");
			options.put("receipt", purchaseId.toString());

			Order order = razorpay.Orders.create(options);

			// 5) Persist Payment entity
			Payment payment = Payment.builder().purchaseId(purchaseId).razorpayOrderId(order.get("id"))
					.status("CREATED").amount(amount).createdAt(LocalDateTime.now()).build();
			repo.save(payment);

			// 6) Return DTO
			return PaymentDTO.builder().id(payment.getId()).purchaseId(purchaseId).razorpayOrderId(order.get("id"))
					.status(payment.getStatus()).amount(amount).createdAt(payment.getCreatedAt()).build();

		} catch (RazorpayException e) {
			throw new RuntimeException("Razorpay error: " + e.getMessage(), e);
		}
	}

	@Override
	public PaymentDTO getById(Long id) {
		Payment p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payment not found: " + id));
		return map(p);
	}

	@Override
	public List<PaymentDTO> getAll() {
		List<PaymentDTO> list = new ArrayList<>();
		repo.findAll().forEach(p -> list.add(map(p)));
		return list;
	}

	@Override
	@Transactional
	public String handleWebhook(String payload, String sigHeader) {
		try {
			// Use the injected secret instead of razorpay.getAuth()
			com.razorpay.Utils.verifyWebhookSignature(payload, sigHeader, razorpaySecret);

			JSONObject event = new JSONObject(payload);
			String eventType = event.getString("event");
			JSONObject paymentEntity = event.getJSONObject("payload").getJSONObject("payment").getJSONObject("entity");

			String razorpayPaymentId = paymentEntity.getString("id");
			String orderId = paymentEntity.getString("order_id");

			Payment payment = repo.findByRazorpayOrderId(orderId)
					.orElseThrow(() -> new ResourceNotFoundException("Payment not found for order: " + orderId));

			payment.setRazorpayPaymentId(razorpayPaymentId);
			payment.setStatus(eventType.equals("payment.captured") ? "PAID" : "FAILED");
			repo.save(payment);
			return "OK";
		} catch (Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

	private PaymentDTO map(Payment p) {
		return PaymentDTO.builder().id(p.getId()).purchaseId(p.getPurchaseId()).razorpayOrderId(p.getRazorpayOrderId())
				.razorpayPaymentId(p.getRazorpayPaymentId()).status(p.getStatus()).amount(p.getAmount())
				.createdAt(p.getCreatedAt()).build();
	}
}
