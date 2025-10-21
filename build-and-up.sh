#!/usr/bin/env bash
set -euo pipefail
echo "Building services with Maven (skip tests)..."
for svc in client-service order-service dispatch-service; do
  echo "  -> build $svc"
  (cd "$svc" && mvn -DskipTests package -Dquarkus.package.type=uber-jar)
done
echo "Starting docker-compose..."
docker-compose up -d --build
echo "Waiting 10s for Kafka..."
sleep 10
KAFKA_CONTAINER=$(docker-compose ps -q kafka)
if [ -n "$KAFKA_CONTAINER" ]; then
  docker exec -it "$KAFKA_CONTAINER" bash -c "kafka-topics --bootstrap-server kafka:9092 --create --if-not-exists --replication-factor 1 --partitions 1 --topic orders" || true
else
  echo "Kafka container not found; create topic manually if needed."
fi
echo "Done. Endpoints:"
echo " - client-service: http://localhost:8081/api/clients"
echo " - order-service: http://localhost:8082/api/orders"
echo " - dispatch-service: http://localhost:8083"
echo "Use X-API-KEY: super-secret-apikey"
