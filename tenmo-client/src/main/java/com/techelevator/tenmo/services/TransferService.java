package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class TransferService {

    private final String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferService(String API_BASE_URL) {
        this.API_BASE_URL = API_BASE_URL;
    }

    public Transfer[] getAuthenticatedUserHistory(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange(
                    API_BASE_URL + "/transfers/history",
                    HttpMethod.GET,
                    entity,
                    Transfer[].class
            );
            return  response.getBody();
        } catch (RestClientException e) {
            System.out.println("Error occurred while retrieving account balance. Please try again.");
        }
        return null;
    }


}
