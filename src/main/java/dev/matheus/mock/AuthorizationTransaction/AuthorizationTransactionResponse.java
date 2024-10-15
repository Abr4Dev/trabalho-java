package dev.matheus.mock.AuthorizationTransaction;

public class AuthorizationTransactionResponse {
    public String status;
    public Data data;

    public static class Data {
        public boolean authorization;
    }
}
