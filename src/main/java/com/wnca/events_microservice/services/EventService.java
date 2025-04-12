package com.wnca.events_microservice.services;

import com.wnca.events_microservice.domain.Event;
import com.wnca.events_microservice.domain.Subscription;
import com.wnca.events_microservice.dtos.EmailRequestDTO;
import com.wnca.events_microservice.dtos.EventRequestDTO;
import com.wnca.events_microservice.exception.EventFullException;
import com.wnca.events_microservice.exception.EventNotFoundException;
import com.wnca.events_microservice.feign.EmailServiceClient;
import com.wnca.events_microservice.repositories.EventRepository;
import com.wnca.events_microservice.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class EventService {

   @Autowired
   private EventRepository eventRepository;

   @Autowired
   private SubscriptionRepository subscriptionRepository;

   @Autowired
   private EmailServiceClient emailServiceClient;

   public List<Event> getAllEvents() {
      return eventRepository.findAll();
   }

   public List<Event> getUpcomingEvents() {
      List<Event> upcomingEvents = eventRepository.findUpcomingEvents(LocalDateTime.now());
      return upcomingEvents.isEmpty() ? Collections.emptyList() : upcomingEvents;
   }

   public Event createEvent(EventRequestDTO eventRequest) {
      Event event = new Event(eventRequest);
      return eventRepository.save(event);
   }

   private Boolean isEventFull(Event event){
      return event.getRegisteredParticipants() >= event.getMaxParticipants();
   }

   public void registerParticipant(String eventId, String participantEmail) throws EventNotFoundException, EventFullException {
      Event event = eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);

      if (isEventFull(event)) {
         throw new EventFullException();
      }

      if (event.getRegisteredParticipants() < event.getMaxParticipants()) {
         Subscription subscription = new Subscription(event, participantEmail);
         subscriptionRepository.save(subscription);

         event.setRegisteredParticipants(event.getRegisteredParticipants() + 1);

         EmailRequestDTO emailRequest = new EmailRequestDTO(
               participantEmail,
               "Confirmação de Inscrição",
               "Você foi inscrito no evento com sucesso!")
               ;

         emailServiceClient.sendEmail(emailRequest);
      } else {
         throw new EventFullException();
      }
   }
}
