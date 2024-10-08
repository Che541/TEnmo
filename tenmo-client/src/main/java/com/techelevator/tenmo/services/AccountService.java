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
    public final String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();

    public AccountService(String apiBaseUrl) {
        API_BASE_URL = apiBaseUrl + "accounts/";
    }

    public Account getAccount(int id, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<Account> response = this.restTemplate.exchange(
                    API_BASE_URL + id,
                    HttpMethod.GET,
                    entity,
                    Account.class);
            return response.getBody();
        } catch (ResourceAccessException | RestClientResponseException var6) {
            RestClientException e = var6;
            BasicLogger.log(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUserId(int userId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<Account> response = this.restTemplate.exchange(
                    API_BASE_URL + "/users/" + userId,
                    HttpMethod.GET,
                    entity,
                    Account.class);
            return response.getBody();
        } catch (ResourceAccessException | RestClientResponseException var6) {
            RestClientException e = var6;
            BasicLogger.log(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }



    public double getBalance(int id, String authToken) {
        Account account = null;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<Void> entity = new HttpEntity<Void>(headers);
            ResponseEntity<Account> response = this.restTemplate.exchange(API_BASE_URL + id, HttpMethod.GET, entity, Account.class, new Object[0]);
            account = (Account)response.getBody();
        } catch (ResourceAccessException | RestClientResponseException var6) {
            RestClientException e = var6;
            BasicLogger.log(e.getMessage());
        }

        return account.getBalance();
    }
}
