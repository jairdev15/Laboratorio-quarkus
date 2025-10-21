package com.retailx.client.resource;
import com.retailx.client.dto.ClientDTO;
import com.retailx.client.service.ClientService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientResource {

    @Inject ClientService clientService;

    @GET
    public Response list() {
        List<ClientDTO> clients = clientService.listAll();
        return Response.ok(clients).build();
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") Long id) {
        ClientDTO dto = clientService.findById(id);
        if (dto == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(dto).build();
    }

    @POST
    public Response create(@Valid ClientDTO dto, @Context UriInfo uriInfo) {
        ClientDTO created = clientService.create(dto);
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getId())).build();
        return Response.created(uri).entity(created).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean removed = clientService.delete(id);
        if (!removed) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.noContent().build();
    }
}
