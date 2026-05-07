package com.example.backend.payment.dto;

public class PaymentRequestDTO {
    private Long eventId;
    private Long userId;
    private Double amount;
    private String currency;
    private String paymentType;
    private String description;
    
    public PaymentRequestDTO() {}
    
    public PaymentRequestDTO(Long eventId, Long userId, Double amount, String paymentType) {
        this.eventId = eventId;
        this.userId = userId;
        this.amount = amount;
        this.paymentType = paymentType;
        this.currency = "USD";
    }
    
    // Getters and Setters
    public Long getEventId() {
        return eventId;
    }
    
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public String getPaymentType() {
        return paymentType;
    }
    
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}
