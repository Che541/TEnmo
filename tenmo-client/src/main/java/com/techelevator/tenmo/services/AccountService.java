package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.model.Account;
import com.techelevator.util.BasicLogger;

public class AccountService {
    public static final String API_BASE_URL = "http://localhost:8080/accounts";
    private RestTemplate restTemplate = new RestTemplate();

    public AccountService() {}

    public double getAuthenticatedUserBalance(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<Double> response = restTemplate.exchange(
                    API_BASE_URL + "/balance",
                    HttpMethod.GET,
                    entity,
                    Double.class
            );
            return response.getBody();
        } catch (RestClientException e) {
            System.out.println("Error occurred while retrieving account balance. Please try again.");
        }
        return -1;
    }



/*
    public Account getAccount(int id) {
        Account account = null;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(this.authToken);
            HttpEntity<Void> entity = new HttpEntity(headers);
            ResponseEntity<Account> response = this.restTemplate.exchange("http://localhost:8080/account/" + id, HttpMethod.GET, entity, Account.class, new Object[0]);
            account = (Account)response.getBody();
        } catch (ResourceAccessException | RestClientResponseException var6) {
            RestClientException e = var6;
            BasicLogger.log(e.getMessage());
        }

        return account;
    }

 */

    /*
    public double getBalance(int id) {
        Account account = null;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(this.authToken);
            HttpEntity<Void> entity = new HttpEntity(headers);
            ResponseEntity<Account> response = this.restTemplate.exchange("http://localhost:8080/account/" + id, HttpMethod.GET, entity, Account.class, new Object[0]);
            account = (Account)response.getBody();
        } catch (ResourceAccessException | RestClientResponseException var6) {
            RestClientException e = var6;
            BasicLogger.log(e.getMessage());
        }

        return account.getBalance();
    }
     */
}
