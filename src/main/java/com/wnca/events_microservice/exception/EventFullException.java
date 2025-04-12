package com.wnca.events_microservice.exception;

public class EventFullException extends RuntimeException {

      public EventFullException() {
         super("Evento cheio");
      }

      public EventFullException(String message) {
         super(message);
      }
}
