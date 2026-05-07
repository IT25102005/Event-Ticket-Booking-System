package com.example.backend.payment.service;

import com.example.backend.payment.dto.PaymentRequestDTO;
import com.example.backend.payment.dto.PaymentResponseDTO;
import com.example.backend.payment.model.Payment;
import com.example.backend.payment.model.PaymentStatus;
import com.example.backend.payment.model.PaymentType;
import com.example.backend.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Override
    public PaymentResponseDTO createPayment(PaymentRequestDTO request) {
        Payment payment = new Payment();
        payment.setPaymentId("PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        payment.setEventId(request.getEventId());
        payment.setUserId(request.getUserId());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency() != null ? request.getCurrency() : "USD");
        payment.setPaymentType(PaymentType.valueOf(request.getPaymentType().toUpperCase()));
        payment.setDescription(request.getDescription());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        
        Payment saved = paymentRepository.save(payment);
        return convertToDTO(saved);
    }
    
    @Override
    public PaymentResponseDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
        return convertToDTO(payment);
    }
    
    @Override
    public PaymentResponseDTO getPaymentByPaymentId(String paymentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
        return convertToDTO(payment);
    }
    
    @Override
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<PaymentResponseDTO> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<PaymentResponseDTO> getPaymentsByEventId(Long eventId) {
        return paymentRepository.findByEventId(eventId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<PaymentResponseDTO> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(PaymentStatus.valueOf(status.toUpperCase())).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    public PaymentResponseDTO updatePaymentStatus(Long id, String status) {
        Payment payment = paymentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus(PaymentStatus.valueOf(status.toUpperCase()));
        if (status.equalsIgnoreCase("COMPLETED")) {
            payment.setPaymentDate(LocalDateTime.now());
        }
        Payment updated = paymentRepository.save(payment);
        return convertToDTO(updated);
    }
    
    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
    
    private PaymentResponseDTO convertToDTO(Payment payment) {
        return new PaymentResponseDTO(
            payment.getId(),
            payment.getPaymentId(),
            payment.getEventId(),
            payment.getUserId(),
            payment.getAmount(),
            payment.getCurrency(),
            payment.getPaymentType().toString(),
            payment.getStatus().toString(),
            payment.getDescription(),
            payment.getTransactionId(),
            payment.getPaymentDate(),
            payment.getCreatedAt(),
            payment.getUpdatedAt()
        );
    }
}
