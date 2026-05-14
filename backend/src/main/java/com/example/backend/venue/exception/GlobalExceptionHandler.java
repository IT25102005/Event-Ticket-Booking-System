package com.example.backend.venue.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * =====================================================
 * GlobalExceptionHandler.java
 * =====================================================
 * Centralized error handling for the entire application.
 * Instead of writing try-catch in every controller,
 * this class intercepts exceptions and returns clean
 * JSON error responses.
 *
 * @RestControllerAdvice = applies to all @RestController classes
 * =====================================================
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ---- Helper method to build a consistent error response ----
    private Map<String, Object> buildErrorResponse(HttpStatus status, String error, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        return body;
    }

    // ---- Handle: Venue not found (404) ----
    @ExceptionHandler(VenueNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleVenueNotFound(VenueNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildErrorResponse(HttpStatus.NOT_FOUND, "Venue Not Found", ex.getMessage()));
    }

    // ---- Handle: Seat not found (404) ----
    @ExceptionHandler(SeatNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleSeatNotFound(SeatNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildErrorResponse(HttpStatus.NOT_FOUND, "Seat Not Found", ex.getMessage()));
    }

    // ---- Handle: Duplicate seat (409 Conflict) ----
    @ExceptionHandler(DuplicateSeatException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateSeat(DuplicateSeatException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(buildErrorResponse(HttpStatus.CONFLICT, "Duplicate Seat", ex.getMessage()));
    }

    // ---- Handle: Validation errors from @Valid DTOs (400) ----
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        // Collect all field errors into a map: { "fieldName": "error message" }
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation Failed");
        body.put("message", "One or more fields have validation errors");
        body.put("fieldErrors", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // ---- Handle: Illegal argument errors (400) ----
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage()));
    }

    // ---- Handle: All other unexpected errors (500) ----
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Internal Server Error",
                        "An unexpected error occurred: " + ex.getMessage()
                ));
    }
}
