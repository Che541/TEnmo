package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class UserService {

    public final String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();

    public UserService(String API_BASE_URL) {
        this.API_BASE_URL = API_BASE_URL + "users/";
    }

    public User getUserById(int id, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<User> response = this.restTemplate.exchange(
                    API_BASE_URL + id,
                    HttpMethod.GET,
                    entity,
                    User.class);
            return response.getBody();
        } catch (ResourceAccessException | RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }


    public User[] getUsers(String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<User[]> response = this.restTemplate.exchange(
                    API_BASE_URL,
                    HttpMethod.GET,
                    entity,
                    User[].class);
            return response.getBody();
        } catch (ResourceAccessException | RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }



    public String getUsernameByAccountId(int accountId, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = this.restTemplate.exchange(
                    API_BASE_URL + "username/account/" + accountId,
                    HttpMethod.GET,
                    entity,
                    String.class);
            return response.getBody();
        } catch (ResourceAccessException | RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }






}
