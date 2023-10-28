package se.ifmo.ru.soa_2023_lab2_service1.web.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/service")
public class CatalogController {
    @GET
    @Path("/flats/{id}")
    public Response getFlat(@PathParam("id") int id) {

        return Response
                .ok("Example")
                .build();
    }

    @GET
    @Path("/ping")
    public Response ping() {
        return Response.ok("PING").build();
    }


    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }

}
