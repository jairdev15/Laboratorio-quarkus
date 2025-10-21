package com.retailx.dispatch.domain;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import java.time.Instant;

@Entity
public class DispatchEntity extends PanacheEntity {

    public Long orderId;
    public String status;
    public Instant processedAt = Instant.now();
}
