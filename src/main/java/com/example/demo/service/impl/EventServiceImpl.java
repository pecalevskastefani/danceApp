package com.example.demo.service.impl;

import com.example.demo.model.Event;
import com.example.demo.repository.EventRepository;
import com.example.demo.service.EventService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event create(LocalDateTime start, LocalDateTime end, String text) {
        Event event = new Event(start,end,text);
        return eventRepository.save(event);
    }

    @Override
    public Event changeDate(Long id,LocalDateTime start, LocalDateTime end) {
        Event event = eventRepository.findById(id).get();
        event.setStart(start);
        event.setEnd(end);
        return eventRepository.save(event);
    }

    @Override
    public Event putColor(Long id,String color) {
        Event event = eventRepository.findById(id).get();
        event.setColor(color);
        return eventRepository.save(event);
    }

    @Override
    public List<Event> findBetween(LocalDateTime start, LocalDateTime end) {
        return eventRepository.findBetween(start,end);
    }
}