package dev.matheus.controllers;

import dev.matheus.entitys.user.User;
import dev.matheus.services.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    @POST
    public Response createUser (User user) {
        User createUser = userService.saveUser(user);
        return Response.status(Response.Status.CREATED).entity(createUser).build();
    }
}
