package com.eventbooking.ticketmanagement.exception;

public class DuplicateTicketTypeException extends RuntimeException {
    public DuplicateTicketTypeException(String message) {
        super(message);
    }
}
