package com.wnca.events_microservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity(name="subscription")
@Table(name="subscription")
@Data
@EqualsAndHashCode(of="id")
public class Subscription {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   private Event event;

   private String participantEmail;

   public Subscription(Event event, String participantEmail) {
      this.event = event;
      this.participantEmail = participantEmail;
   }
}
