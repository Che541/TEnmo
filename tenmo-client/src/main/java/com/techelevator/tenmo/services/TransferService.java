package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;

public class TransferService {
    public final String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferService(String apiBaseUrl) {
        API_BASE_URL = apiBaseUrl + "transfers/";
    }

    public Transfer getTransfer(int id, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<Transfer> response = this.restTemplate.exchange(
                    API_BASE_URL + id,
                    HttpMethod.GET,
                    entity,
                    Transfer.class);
            return response.getBody();
        } catch (ResourceAccessException | RestClientResponseException var6) {
            RestClientException e = var6;
            BasicLogger.log(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Transfer[] findHistory(String authToken) {
       Transfer[] history = null;
         try {
           ResponseEntity<Transfer[]> response = this.restTemplate.exchange(
                   API_BASE_URL + "history",
                   HttpMethod.GET,
                   this.makeAuthEntity(authToken),
                   Transfer[].class);
           history = (Transfer[])response.getBody();
        } catch (ResourceAccessException | RestClientResponseException var6) {
            RestClientException e = var6;
            BasicLogger.log(e.getMessage());
        }
        return history;
    } 

    public Transfer[] findAllPending(String authToken) {
        Transfer[] pending = null;
          try {
            ResponseEntity<Transfer[]> response = this.restTemplate.exchange(
                    API_BASE_URL + "pending",
                    HttpMethod.GET,
                    this.makeAuthEntity(authToken),
                    Transfer[].class);
            pending=(Transfer[])response.getBody();
         } catch (ResourceAccessException | RestClientResponseException var6) {
             RestClientException e = var6;
             BasicLogger.log(e.getMessage());
         }
         return pending;
     } 

     public Transfer request(Transfer newTransfer, String authToken) {
        HttpEntity<Transfer> entity = this.makeTransferEntity(newTransfer, authToken);
        Transfer request = null;
        try {
            return request = this.restTemplate.postForObject(API_BASE_URL + "request", entity, Transfer.class);
        } catch (ResourceAccessException | RestClientResponseException e) {
            BasicLogger.log(e.getMessage());
        }
        return request;
     }

     public Transfer send(Transfer newTransfer, String authToken) {
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_JSON);
         headers.setBearerAuth(authToken);
         HttpEntity<Transfer> entity = new HttpEntity<>(newTransfer, headers);
        //HttpEntity<Transfer> entity = this.makeTransferEntity(newTransfer, authToken);

        try {
            return this.restTemplate.postForObject(API_BASE_URL + "send", entity, Transfer.class);
        } catch (ResourceAccessException | RestClientResponseException var5) {
            RestClientException e = var5;
            BasicLogger.log(e.getMessage());
        }
         System.out.println("error.");
        return null;
     }

    public boolean approve(int id, String authToken) {
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(authToken);
            HttpEntity<Object> entity = new HttpEntity<>(headers);
            restTemplate.postForObject(API_BASE_URL + id + "/approve", entity, Transfer.class);
            return true;

        } catch (RestClientResponseException e){
            BasicLogger.log(e.getMessage() + " : " + e.getStatusText());
        } catch (ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return false;
    }

    public boolean reject(int id, String authToken) {
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(authToken);
            HttpEntity<Object> entity = new HttpEntity<>(headers);
            restTemplate.postForObject(API_BASE_URL + id + "/reject", entity, Transfer.class);
            return true;
        } catch (RestClientResponseException e){
            BasicLogger.log(e.getMessage() + " : " + e.getStatusText());
        } catch (ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return false;
    }





     //approve service

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<Transfer>(transfer, headers);
    }

    private HttpEntity<Void> makeAuthEntity(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<Void>(headers);
    }
}
