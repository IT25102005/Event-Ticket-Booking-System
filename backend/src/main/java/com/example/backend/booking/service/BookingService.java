package com.example.backend.booking.service;

import com.example.backend.booking.model.Booking;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking createBooking(Booking booking);
    List<Booking> getAllBookings();
    Optional<Booking> getBookingById(Long id);
    Booking updateBooking(Long id, Booking bookingDetails);
    Booking cancelBooking(Long id);
    void deleteBooking(Long id);
}
