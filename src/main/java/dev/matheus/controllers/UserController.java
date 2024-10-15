package dev.matheus.controllers;

import dev.matheus.entitys.user.User;
import dev.matheus.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/users")
public class UserController {

    UserService userService;

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser (User user) {
        this.userService.saveUser(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }
}
