package com.example.backend.payment;

import com.example.backend.payment.model.*;
import com.example.backend.payment.repository.PaymentRepository;
import com.example.backend.payment.repository.RefundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class H2DataInitializer implements CommandLineRunner {
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private RefundRepository refundRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize sample payments
        if (paymentRepository.count() == 0) {
            Payment payment1 = new Payment();
            payment1.setPaymentId("PAY-DEMO0001");
            payment1.setEventId(1L);
            payment1.setUserId(1L);
            payment1.setAmount(99.99);
            payment1.setCurrency("USD");
            payment1.setPaymentType(PaymentType.CARD);
            payment1.setStatus(PaymentStatus.COMPLETED);
            payment1.setDescription("Concert Ticket - John Doe");
            payment1.setTransactionId("TXN-DEMO0001");
            payment1.setPaymentDate(LocalDateTime.now().minusDays(2));
            paymentRepository.save(payment1);
            
            Payment payment2 = new Payment();
            payment2.setPaymentId("PAY-DEMO0002");
            payment2.setEventId(2L);
            payment2.setUserId(2L);
            payment2.setAmount(149.99);
            payment2.setCurrency("USD");
            payment2.setPaymentType(PaymentType.BANK_TRANSFER);
            payment2.setStatus(PaymentStatus.COMPLETED);
            payment2.setDescription("Theater Tickets - Jane Smith");
            payment2.setTransactionId("TXN-DEMO0002");
            payment2.setPaymentDate(LocalDateTime.now().minusDays(1));
            paymentRepository.save(payment2);
            
            Payment payment3 = new Payment();
            payment3.setPaymentId("PAY-DEMO0003");
            payment3.setEventId(1L);
            payment3.setUserId(3L);
            payment3.setAmount(75.50);
            payment3.setCurrency("USD");
            payment3.setPaymentType(PaymentType.WALLET);
            payment3.setStatus(PaymentStatus.PENDING);
            payment3.setDescription("Concert Ticket - Bob Wilson");
            payment3.setTransactionId("TXN-DEMO0003");
            paymentRepository.save(payment3);
        }
        
        // Initialize sample refunds
        if (refundRepository.count() == 0) {
            Refund refund1 = new Refund();
            refund1.setRefundId("REF-DEMO0001");
            refund1.setPaymentId(1L);
            refund1.setAmount(99.99);
            refund1.setRefundType("FULL");
            refund1.setStatus(RefundStatus.COMPLETED);
            refund1.setReason("Event Cancelled");
            refund1.setApprovedBy("Admin");
            refund1.setApprovalDate(LocalDateTime.now().minusDays(1));
            refund1.setRefundDate(LocalDateTime.now());
            refundRepository.save(refund1);
            
            Refund refund2 = new Refund();
            refund2.setRefundId("REF-DEMO0002");
            refund2.setPaymentId(2L);
            refund2.setAmount(50.00);
            refund2.setRefundType("PARTIAL");
            refund2.setStatus(RefundStatus.APPROVED);
            refund2.setReason("Partial Refund - Ticket Transfer");
            refund2.setApprovedBy("Manager");
            refund2.setApprovalDate(LocalDateTime.now());
            refundRepository.save(refund2);
        }
    }
}
