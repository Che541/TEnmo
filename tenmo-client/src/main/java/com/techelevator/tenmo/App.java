package com.techelevator.tenmo;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.*;

import java.util.Scanner;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService(API_BASE_URL);
    private final TransferService transferService = new TransferService(API_BASE_URL);
    private final UserService userService = new UserService(API_BASE_URL);
    private final TransferTypeService transferTypeService = new TransferTypeService(API_BASE_URL);
    private final TransferStatusService transferStatusService = new TransferStatusService(API_BASE_URL);
    private final Scanner scanner = new Scanner(System.in);

    private AuthenticatedUser currentUser;



    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        System.out.println("Hello,"+" "+ "\033[1m" +credentials.getUsername()+"\033[0m"+" You've logged in successfully.");
        System.out.println("Please choose an option from the list below:");
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private double viewCurrentBalance(){
        // TODO Auto-generated method stub
        // Get the current user's token from the 'currentUser' object
        double current=0.00;
        String token = currentUser.getToken();

        // Create an instance of RestTemplate for making HTTP requests
        RestTemplate restTemplate = new RestTemplate();

        // Set up the HTTP headers with the token for authentication
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            // Make a GET request to the "/accounts/balance" endpoint
            ResponseEntity<Double> response = restTemplate.exchange(
                    API_BASE_URL + "accounts/balance",
                    HttpMethod.GET,
                    entity,
                    Double.class
            );

            // Get the account balance from the response
            Double balance = response.getBody();

            // Display the account balance
            System.out.println("Your current account balance is: $" + balance);
            current=balance;
        } catch (RestClientException e) {
            // Handle any exceptions that occurred during the request
            System.out.println("Error occurred while retrieving account balance. Please try again.");
        }
        return current;
    }

	private void viewTransferHistory() {
        // TODO use printf to properly format spacing in strings
        System.out.println("-------------------------------------------");
        System.out.println("Transfers");
        System.out.println("        ID          From/To                 Amount");
        System.out.println("-------------------------------------------");
        Transfer[] transfers = transferService.findHistory(currentUser.getToken());
        for (Transfer transfer : transfers){
            System.out.print(transfer.getId() + " ");

            int currentUserAccountId = accountService.getAccountByUserId(currentUser.getUser().getId(), currentUser.getToken()).getId();
            if(currentUserAccountId == transfer.getAccountTo()){
                int otherAccountId = transfer.getAccountFrom();
                Account otherAccount = accountService.getAccount(otherAccountId, currentUser.getToken());
                User otherUser = userService.getUserById(otherAccount.getUserId(), currentUser.getToken());
                System.out.print("From: " +  otherUser.getUsername() +  " ");
            } else if (currentUserAccountId == transfer.getAccountFrom()){
                int otherAccountId = transfer.getAccountTo();
                Account otherAccount = accountService.getAccount(otherAccountId, currentUser.getToken());
                User otherUser = userService.getUserById(otherAccount.getUserId(), currentUser.getToken());
                System.out.print("To: " +  otherUser.getUsername() +  " ");
            }
            System.out.println("$ " + transfer.getAmount());
        }

        System.out.println("---------");


        int selection = consoleService.promptForInt("Please enter transfer ID to view details (0 to cancel): ");

        if (selection != 0){
            Transfer transfer = transferService.getTransfer(selection, currentUser.getToken());
            System.out.println("--------------------------------------------");
            System.out.println("Transfer Details");
            System.out.println("--------------------------------------------");
            System.out.println("ID: " + transfer.getId());
            System.out.println("From: " + userService.getUsernameByAccountId(transfer.getAccountFrom(), currentUser.getToken()));
            System.out.println("To: " + userService.getUsernameByAccountId(transfer.getAccountTo(), currentUser.getToken()));
            System.out.println("Type: " + transferTypeService.getTransferType(transfer.getTransferTypeId(), currentUser.getToken()).getTransferTypeDesc());
            System.out.println("Status: " + transferStatusService.getTransferStatus(transfer.getTransferStatusId(), currentUser.getToken()).getTransferStatusDesc());
            System.out.println("Amount: $" + transfer.getAmount());
        }


	}

	private void viewPendingRequests() {
        Transfer[] transfers = transferService.findAllPending(currentUser.getToken());
        System.out.println("-------------------------------------------");
        System.out.println("Pending Transfers");
        System.out.println("ID          To                     Amount");
        System.out.println("-------------------------------------------");
        for(Transfer transfer : transfers){
            int otherAccountId = transfer.getAccountTo();
            Account otherAccount = accountService.getAccount(otherAccountId, currentUser.getToken());
            User otherUser = userService.getUserById(otherAccount.getUserId(), currentUser.getToken());
            System.out.println(transfer.getId() + " " + otherUser.getUsername() + " $ " + transfer.getAmount());
        }
        System.out.println("---------");
        int transferId = consoleService.promptForInt("Please enter transfer ID to approve/reject (0 to cancel): ");
        try{
            if(transferId != 0){
                approveOrReject(transferId);
            }
        } catch (Exception e){
            consoleService.printErrorMessage();
        }
        consoleService.pause();
	}

    private void sendBucks() {
        viewUsers();
        int userToId = consoleService.promptForInt("Enter the id of the user you are transferring funds to: ");
        double amountToSend = consoleService.promptForDouble("Enter the amount to transfer: ");
        try {
            int accountFromId = accountService.getAccountByUserId(currentUser.getUser().getId(), currentUser.getToken()).getId();
            int accountToId = accountService.getAccountByUserId(userToId, currentUser.getToken()).getId();
            Transfer transfer = new Transfer(2,2,accountFromId, accountToId, amountToSend);
            transferService.send(transfer, currentUser.getToken());
        } catch (RestClientException e) {
            consoleService.printErrorMessage();
        }
	}

	private void requestBucks() {
        viewUsers();
        int userFromId = consoleService.promptForInt("Enter the id of the user you are requesting funds from: ");
        double amountToRequest = consoleService.promptForDouble("Enter the amount to request: ");
        int accountToId = accountService.getAccountByUserId(currentUser.getUser().getId(), currentUser.getToken()).getId();
        int accountFromId = accountService.getAccountByUserId(userFromId, currentUser.getToken()).getId();
        try {
            Transfer transfer = new Transfer(1,1,accountFromId, accountToId, amountToRequest);
            transferService.request(transfer, currentUser.getToken());
        } catch (RestClientException e) {
            consoleService.printErrorMessage();
        }
	}

    private void viewUsers(){
        User[] users = userService.getUsers(currentUser.getToken());
        System.out.println("-------------------------------------------");
        System.out.println("Users");
        System.out.println("ID          Name");
        System.out.println("-------------------------------------------");
        for (User user : users){
            System.out.println(user.getId() + " " + user.getUsername());
        }
        System.out.println("---------");
        System.out.println();
    }

    private void approveOrReject(int transferId){
        while(true) {
            try{
                consoleService.printApproveOrRejectMenu();
                int selection = consoleService.promptForMenuSelection("Please choose an option:");
                if (selection == 1) {
                    boolean result = transferService.approve(transferId, currentUser.getToken());
                    System.out.println(result ? "Transfer successfully approved." : "Transfer approval failed.");
                    return;
                } else if (selection == 2) {
                    boolean result = transferService.reject(transferId, currentUser.getToken());
                    System.out.println(result ? "Transfer successfully rejected." : "Transfer rejection failed.");
                    return;
                } else if (selection == 0) {
                    return;
                } else {
                    System.out.println("Please choose a valid option.");
                    consoleService.pause();
                }
            } catch (Exception e){
                consoleService.printErrorMessage();
                return;
            }
        }
    }
    public double getCurrentBalance() {
        return viewCurrentBalance();
    }

}
