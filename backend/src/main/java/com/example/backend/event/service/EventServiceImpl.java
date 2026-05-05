package com.example.backend.event.service;

import com.example.backend.event.dto.EventRequestDTO;
import com.example.backend.event.dto.EventResponseDTO;
import com.example.backend.event.exception.EventNotFoundException;
import com.example.backend.event.model.*;
import com.example.backend.event.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * =====================================================
 * OOP CONCEPT: ABSTRACTION — Implements EventService interface
 *
 * This class contains all the business logic:
 *   - Mapping DTO → Entity (and back)
 *   - Validating data (seats, price)
 *   - Creating correct subclass based on eventType
 *   - Calling repository for database operations
 *
 * OOP CONCEPT: POLYMORPHISM in action:
 *   - mapToResponseDTO() calls event.getEventSummary() and event.getDisplayCategory()
 *   - The SAME method call behaves differently for ConcertEvent, SportEvent, etc.
 * =====================================================
 */
@Service
public class EventServiceImpl implements EventService {

    // Repository is injected by Spring (Dependency Injection)
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // ==================== CREATE ====================
    @Override
    public EventResponseDTO createEvent(EventRequestDTO dto) {
        // Validate business rules
        validateEventRequest(dto);

        // Create the correct subclass based on eventType (Factory-style logic)
        Event event = buildEventFromDTO(dto);

        // Set common fields (ENCAPSULATION: using setters)
        setCommonFields(event, dto);

        // Save to database
        Event saved = eventRepository.save(event);

        // Return as DTO (POLYMORPHISM: getEventSummary differs per type)
        return mapToResponseDTO(saved);
    }

    // ==================== GET ALL ====================
    @Override
    public List<EventResponseDTO> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // ==================== GET BY ID ====================
    @Override
    public EventResponseDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return mapToResponseDTO(event);
    }

    // ==================== SEARCH ====================
    @Override
    public List<EventResponseDTO> searchEvents(String keyword, EventCategory category, EventStatus status) {
        return eventRepository.searchEvents(keyword, category, status)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // ==================== UPDATE ====================
    @Override
    public EventResponseDTO updateEvent(Long id, EventRequestDTO dto) {
        // Find the existing event or throw 404
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));

        // Validate the new data
        validateEventRequest(dto);

        // Update common fields
        setCommonFields(event, dto);

        // Update subclass-specific fields
        updateSubclassFields(event, dto);

        Event updated = eventRepository.save(event);
        return mapToResponseDTO(updated);
    }

    // ==================== DELETE ====================
    @Override
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException(id);
        }
        eventRepository.deleteById(id);
    }

    // ==================== CANCEL ====================
    @Override
    public EventResponseDTO cancelEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        event.setStatus(EventStatus.CANCELLED); // Change status to CANCELLED (soft delete)
        Event saved = eventRepository.save(event);
        return mapToResponseDTO(saved);
    }

    // ==================== PRIVATE HELPERS ====================

    /**
     * Creates the correct Event subclass based on eventType.
     * This is a simple Factory-style method — clean OOP design.
     */
    private Event buildEventFromDTO(EventRequestDTO dto) {
        String type = dto.getEventType() != null ? dto.getEventType().toUpperCase() : "";

        switch (type) {
            case "CONCERT":
                ConcertEvent concert = new ConcertEvent();
                concert.setArtistName(dto.getArtistName());
                concert.setMusicGenre(dto.getMusicGenre());
                return concert;

            case "SPORT":
                SportEvent sport = new SportEvent();
                sport.setTeamOne(dto.getTeamOne());
                sport.setTeamTwo(dto.getTeamTwo());
                sport.setSportType(dto.getSportType());
                return sport;

            case "DRAMA":
                DramaEvent drama = new DramaEvent();
                drama.setDirector(dto.getDirector());
                drama.setCast(dto.getCast());
                drama.setLanguage(dto.getLanguage());
                return drama;

            case "MOVIE":
                MovieEvent movie = new MovieEvent();
                movie.setDirector(dto.getDirector());
                movie.setCast(dto.getCast());
                movie.setGenre(dto.getGenre());
                movie.setRating(dto.getRating());
                return movie;

            case "FESTIVAL":
                FestivalEvent festival = new FestivalEvent();
                festival.setTheme(dto.getTheme());
                festival.setOrganizerName(dto.getOrganizerName());
                festival.setHighlights(dto.getHighlights());
                return festival;

            default:
                throw new IllegalArgumentException("Unknown event type: " + dto.getEventType() +
                        ". Valid types: CONCERT, SPORT, DRAMA, MOVIE, FESTIVAL");
        }
    }

    /**
     * Sets the common fields shared by all event types.
     */
    private void setCommonFields(Event event, EventRequestDTO dto) {
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setVenueName(dto.getVenueName());
        event.setEventDate(dto.getEventDate());
        event.setEventTime(dto.getEventTime());
        event.setImageUrl(dto.getImageUrl());
        event.setTotalSeats(dto.getTotalSeats());
        event.setAvailableSeats(dto.getAvailableSeats());
        event.setBasePrice(dto.getBasePrice());

        // Convert String to Enum safely
        if (dto.getCategory() != null && !dto.getCategory().isBlank()) {
            event.setCategory(EventCategory.valueOf(dto.getCategory().toUpperCase()));
        }
        if (dto.getStatus() != null && !dto.getStatus().isBlank()) {
            event.setStatus(EventStatus.valueOf(dto.getStatus().toUpperCase()));
        }
    }

    /**
     * Updates subclass-specific fields when editing an existing event.
     */
    private void updateSubclassFields(Event event, EventRequestDTO dto) {
        if (event instanceof ConcertEvent ce) {
            ce.setArtistName(dto.getArtistName());
            ce.setMusicGenre(dto.getMusicGenre());
        } else if (event instanceof SportEvent se) {
            se.setTeamOne(dto.getTeamOne());
            se.setTeamTwo(dto.getTeamTwo());
            se.setSportType(dto.getSportType());
        } else if (event instanceof DramaEvent de) {
            de.setDirector(dto.getDirector());
            de.setCast(dto.getCast());
            de.setLanguage(dto.getLanguage());
        } else if (event instanceof MovieEvent me) {
            me.setDirector(dto.getDirector());
            me.setCast(dto.getCast());
            me.setGenre(dto.getGenre());
            me.setRating(dto.getRating());
        } else if (event instanceof FestivalEvent fe) {
            fe.setTheme(dto.getTheme());
            fe.setOrganizerName(dto.getOrganizerName());
            fe.setHighlights(dto.getHighlights());
        }
    }

    /**
     * Validates business rules before saving.
     */
    private void validateEventRequest(EventRequestDTO dto) {
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("Event title cannot be empty.");
        }
        if (dto.getEventDate() == null) {
            throw new IllegalArgumentException("Event date is required.");
        }
        if (dto.getBasePrice() != null && dto.getBasePrice().doubleValue() < 0) {
            throw new IllegalArgumentException("Base price cannot be negative.");
        }
        if (dto.getTotalSeats() < 1) {
            throw new IllegalArgumentException("Total seats must be at least 1.");
        }
        if (dto.getAvailableSeats() < 0) {
            throw new IllegalArgumentException("Available seats cannot be negative.");
        }
        if (dto.getAvailableSeats() > dto.getTotalSeats()) {
            throw new IllegalArgumentException("Available seats cannot exceed total seats.");
        }
    }

    /**
     * Maps an Event entity (including subclass) to EventResponseDTO.
     *
     * OOP CONCEPT: POLYMORPHISM IN ACTION
     *   - event.getEventSummary() → different string for each subclass
     *   - event.getDisplayCategory() → different formatted string per subclass
     */
    private EventResponseDTO mapToResponseDTO(Event event) {
        EventResponseDTO dto = new EventResponseDTO();

        // Common fields
        dto.setEventId(event.getEventId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setCategory(event.getCategory() != null ? event.getCategory().name() : null);
        dto.setVenueName(event.getVenueName());
        dto.setEventDate(event.getEventDate());
        dto.setEventTime(event.getEventTime());
        dto.setImageUrl(event.getImageUrl());
        dto.setTotalSeats(event.getTotalSeats());
        dto.setAvailableSeats(event.getAvailableSeats());
        dto.setBasePrice(event.getBasePrice());
        dto.setStatus(event.getStatus() != null ? event.getStatus().name() : null);
        dto.setCreatedAt(event.getCreatedAt());
        dto.setUpdatedAt(event.getUpdatedAt());

        // POLYMORPHISM: same method call, different result
        dto.setEventSummary(event.getEventSummary());
        dto.setDisplayCategory(event.getDisplayCategory());

        // Subclass-specific fields and event type
        if (event instanceof ConcertEvent ce) {
            dto.setEventType("CONCERT");
            dto.setArtistName(ce.getArtistName());
            dto.setMusicGenre(ce.getMusicGenre());
        } else if (event instanceof SportEvent se) {
            dto.setEventType("SPORT");
            dto.setTeamOne(se.getTeamOne());
            dto.setTeamTwo(se.getTeamTwo());
            dto.setSportType(se.getSportType());
        } else if (event instanceof DramaEvent de) {
            dto.setEventType("DRAMA");
            dto.setDirector(de.getDirector());
            dto.setCast(de.getCast());
            dto.setLanguage(de.getLanguage());
        } else if (event instanceof MovieEvent me) {
            dto.setEventType("MOVIE");
            dto.setDirector(me.getDirector());
            dto.setCast(me.getCast());
            dto.setGenre(me.getGenre());
            dto.setRating(me.getRating());
        } else if (event instanceof FestivalEvent fe) {
            dto.setEventType("FESTIVAL");
            dto.setTheme(fe.getTheme());
            dto.setOrganizerName(fe.getOrganizerName());
            dto.setHighlights(fe.getHighlights());
        }

        return dto;
    }
}

