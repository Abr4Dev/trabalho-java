package dev.matheus.mock.AuthorizationTransaction;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/v2/authorize")
@RegisterRestClient(configKey = "auth-api")
public interface AuthorizationTransaction {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    AuthorizationTransactionResponse authorize();
}
