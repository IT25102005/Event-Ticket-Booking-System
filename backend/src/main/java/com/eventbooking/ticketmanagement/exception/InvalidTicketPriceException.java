package com.eventbooking.ticketmanagement.exception;

public class InvalidTicketPriceException extends RuntimeException {
    public InvalidTicketPriceException(String message) {
        super(message);
    }
}
