package dev.matheus.controllers;

import dev.matheus.entitys.transaction.Transaction;
import dev.matheus.services.TransactionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionController {

    @Inject
    TransactionService transactionService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTransaction(Transaction newTransaction) throws Exception {
        Transaction createTransaction = transactionService.createTransaction(newTransaction);
        return Response.status(Response.Status.CREATED).entity(createTransaction).build();
    }
}
