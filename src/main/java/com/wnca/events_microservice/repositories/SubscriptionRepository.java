package com.wnca.events_microservice.repositories;

import com.wnca.events_microservice.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
