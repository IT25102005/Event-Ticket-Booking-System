package com.eventbooking.ticketmanagement.service;

import com.eventbooking.ticketmanagement.dto.TicketAvailabilityDTO;
import com.eventbooking.ticketmanagement.dto.TicketRequestDTO;
import com.eventbooking.ticketmanagement.dto.TicketResponseDTO;
import com.eventbooking.ticketmanagement.model.TicketCategory;
import com.eventbooking.ticketmanagement.model.TicketStatus;
import java.util.List;

public interface TicketService {
    TicketResponseDTO createTicket(TicketRequestDTO request);
    List<TicketResponseDTO> getAllTickets();
    TicketResponseDTO getTicketById(Long id);
    List<TicketResponseDTO> getTicketsByEventId(Long eventId);
    List<TicketResponseDTO> searchTickets(String keyword, Long eventId, TicketCategory category, TicketStatus status);
    TicketResponseDTO updateTicket(Long id, TicketRequestDTO request);
    TicketResponseDTO updatePrice(Long id, Double newBasePrice);
    TicketResponseDTO updateQuantity(Long id, Integer additionalQuantity);
    TicketResponseDTO activateTicket(Long id);
    TicketResponseDTO deactivateTicket(Long id);
    void deleteTicket(Long id);
    TicketAvailabilityDTO getTicketAvailability(Long id);
}
