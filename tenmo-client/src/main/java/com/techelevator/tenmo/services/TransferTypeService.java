package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.TransferType;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferTypeService {

    public final String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();

    public TransferTypeService(String API_BASE_URL) {
        this.API_BASE_URL = API_BASE_URL + "transfertypes/";
    }

    public TransferType getTransferType(int id, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<TransferType> response = this.restTemplate.exchange(
                    API_BASE_URL + id,
                    HttpMethod.GET,
                    entity,
                    TransferType.class);
            return response.getBody();
        } catch (ResourceAccessException | RestClientResponseException var6) {
            RestClientException e = var6;
            BasicLogger.log(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }


}
