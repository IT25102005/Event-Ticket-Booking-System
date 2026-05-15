package com.eventbooking.ticketmanagement.service;

import com.eventbooking.ticketmanagement.dto.PriceCalculationDTO;

public interface PricingService {
    PriceCalculationDTO calculateLivePrice(PriceCalculationDTO calculationDTO);
    PriceCalculationDTO getTicketPricingDetails(Long ticketTypeId);
}
