package com.retailx.client.security;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class ApiKeyFilter implements ContainerRequestFilter {

    @ConfigProperty(name = "app.security.api-key", defaultValue = "")
    String apiKey;

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        if (apiKey == null || apiKey.isBlank()) return; // skip if not configured (useful for tests)
        String header = ctx.getHeaderString("X-API-KEY");
        if (header == null || !header.equals(apiKey)) {
            ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid API key").build());
        }
    }
}
