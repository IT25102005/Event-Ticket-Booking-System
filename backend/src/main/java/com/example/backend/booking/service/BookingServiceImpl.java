package com.example.backend.booking.service;

import com.example.backend.booking.model.Booking;
import com.example.backend.booking.model.BookingStatus;
import com.example.backend.booking.model.CounterBooking;
import com.example.backend.booking.model.OnlineBooking;
import com.example.backend.booking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Booking updateBooking(Long id, Booking bookingDetails) {
        return bookingRepository.findById(id).map(existingBooking -> {
            existingBooking.setCustomerName(bookingDetails.getCustomerName());
            existingBooking.setEventCode(bookingDetails.getEventCode());
            existingBooking.setNumberOfTickets(bookingDetails.getNumberOfTickets());
            existingBooking.setTicketPrice(bookingDetails.getTicketPrice());
            
            if (bookingDetails.getStatus() != null) {
                existingBooking.setStatus(bookingDetails.getStatus());
            }
            
            if (existingBooking instanceof OnlineBooking && bookingDetails instanceof OnlineBooking) {
                ((OnlineBooking) existingBooking).setPaymentGateway(((OnlineBooking) bookingDetails).getPaymentGateway());
                ((OnlineBooking) existingBooking).setConvenienceFee(((OnlineBooking) bookingDetails).getConvenienceFee());
            } else if (existingBooking instanceof CounterBooking && bookingDetails instanceof CounterBooking) {
                ((CounterBooking) existingBooking).setCounterNumber(((CounterBooking) bookingDetails).getCounterNumber());
                ((CounterBooking) existingBooking).setHandlingAgent(((CounterBooking) bookingDetails).getHandlingAgent());
            }

            return bookingRepository.save(existingBooking);
        }).orElseThrow(() -> new RuntimeException("Booking not found with id " + id));
    }

    @Override
    public Booking cancelBooking(Long id) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setStatus(BookingStatus.CANCELLED);
            return bookingRepository.save(booking);
        }).orElseThrow(() -> new RuntimeException("Booking not found with id " + id));
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
