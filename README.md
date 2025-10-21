RetailX - Quarkus microservices sample (ready to run)

Services:
 - client-service (8081)
 - order-service (8082)
 - dispatch-service (8083)

1) Build & up:
   chmod +x build-and-up.sh
   ./build-and-up.sh

2) Test:
   curl -X POST http://localhost:8081/api/clients -H "Content-Type: application/json" -H "X-API-KEY: super-secret-apikey" -d '{"name":"Ana","email":"ana@example.com","document":"DNI-1"}'
   curl -X POST http://localhost:8082/api/orders -H "Content-Type: application/json" -H "X-API-KEY: super-secret-apikey" -d '{"clientId":1,"total":100.0}'

Notes:
 - API key is configured in docker-compose env as super-secret-apikey
 - Prometheus (9090), Grafana (3000), OTEL collector (4317)
