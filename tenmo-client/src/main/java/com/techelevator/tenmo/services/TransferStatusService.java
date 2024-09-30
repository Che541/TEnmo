package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferStatusService {
    public final String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferStatusService(String API_BASE_URL) {
        this.API_BASE_URL = API_BASE_URL + "transferstatuses/";
    }

    public TransferStatus getTransferStatus(int id, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<TransferStatus> response = this.restTemplate.exchange(
                    API_BASE_URL + id,
                    HttpMethod.GET,
                    entity,
                    TransferStatus.class);
            return response.getBody();
        } catch (ResourceAccessException | RestClientResponseException var6) {
            RestClientException e = var6;
            BasicLogger.log(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }
}
