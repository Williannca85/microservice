package com.wnca.events_microservice.exception;

public class EventNotFoundException extends RuntimeException {

   public EventNotFoundException() {
      super("Evento não encontrado");
   }

   public EventNotFoundException(String message) {
      super(message);
   }
}
