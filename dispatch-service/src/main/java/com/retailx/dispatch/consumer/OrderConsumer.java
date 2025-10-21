package com.retailx.dispatch.consumer;

import com.retailx.dispatch.dto.OrderEvent;
import com.retailx.dispatch.domain.DispatchEntity;
import io.quarkus.logging.Log;
import io.vertx.mutiny.redis.client.Command;
import io.vertx.mutiny.redis.client.Redis;
import io.vertx.mutiny.redis.client.Request;
import io.vertx.mutiny.redis.client.Response;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class OrderConsumer {

    @Inject
    Redis redis;

    private final Jsonb jsonb = JsonbBuilder.create();

    /**
     * Procesa los mensajes entrantes desde Kafka (topic "orders-in").
     *
     * @param message JSON del evento de orden
     */

    @Incoming("orders-in")
    @Transactional
    public void consume(String message) {
        try {
            // Parsear el JSON recibido a un objeto OrderEvent
            OrderEvent evt = jsonb.fromJson(message, OrderEvent.class);

            // Guardar en Redis
            String key = "order:" + evt.id;
            Response result = redis.send(Request.cmd(Command.SET).arg(key).arg(message))
                    .await().indefinitely();

            Log.infof("Redis set result for %s: %s", key, result);

            // Guardar también en la base de datos (PostgreSQL)
            DispatchEntity dispatch = new DispatchEntity();
            dispatch.orderId = evt.id;
            dispatch.status = "DISPATCHED";
            dispatch.persist();

            Log.infof("Order %s dispatched successfully", evt.id);

        } catch (Exception e) {
            Log.error("Error procesando mensaje de orden", e);
        }
    }
}
