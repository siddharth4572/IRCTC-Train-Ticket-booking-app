package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserBookingService {

     private List<User> userList;
    private User user;
    private ObjectMapper objectMapper = new ObjectMapper();
    // NEW and correct line
    private static final String USER_PATH = "app/src/main/java/org/example/localDb/users.json";
    public UserBookingService() throws IOException {
        File users = new File(USER_PATH);
        if (users.exists()) {
            userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
        } else {
            // If the file doesn't exist, start with an empty list
            userList = new ArrayList<>();
        }
    }

    // Your existing constructor for when a user logs in.
    public UserBookingService(User user1) throws IOException {
        this(); // This calls the new constructor above to load the userList!
        this.user = user1;
    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }


    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        } catch (IOException ex){
            // ADD THIS LINE to see the detailed error message in your console
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }
    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USER_PATH);
        // Create parent directories if they don't exist
        File parentDir = usersFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        objectMapper.writeValue(usersFile, userList);
    }


    public void fetchBookings(){
        // Find the user in the master list 'userList' that matches the user this service belongs to ('this.user')
        Optional<User> userFetched = userList.stream().filter(userFromList -> {
            // Compare the name and password of the user from the list
            // with the user this service was initialized with.
            return userFromList.getName().equals(this.user.getName()) &&
                    UserServiceUtil.checkPassword(this.user.getPassword(), userFromList.getHashedPassword());
        }).findFirst();

        if(userFetched.isPresent()){
            // If found, print their tickets
            userFetched.get().printTickets();
        }
    }

    public Boolean cancelBooking(String ticketId){
        Scanner s= new Scanner(System.in);
        System.out.println("Enter the ticket id to cancel");
        ticketId=s.next();

        if (ticketId == null || ticketId.isEmpty()) {
            System.out.println("Ticket ID cannot be null or empty.");
            return Boolean.FALSE;
        }

        String finalTicketId1 = ticketId;  //Because strings are immutable
        boolean removed = user.getTicketsBooked().removeIf(ticket -> ticket.getTicketId().equals(finalTicketId1));

        String finalTicketId = ticketId;
        user.getTicketsBooked().removeIf(Ticket -> Ticket.getTicketId().equals(finalTicketId));
        if (removed) {
            System.out.println("Ticket with ID " + ticketId + " has been canceled.");
            return Boolean.TRUE;
        }else{
            System.out.println("No ticket found with ID " + ticketId);
            return Boolean.FALSE;
        }
    }


    public List<Train> getTrains(String source, String destination){
        try{
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination);
        }catch(IOException ex){
            return new ArrayList<>();
        }
    }

    public List<List<Integer>> fetchSeats(Train train){
        return train.getSeats();
    }

    public Boolean bookTrainSeat(Train train, int row, int seat) {
        try{
            TrainService trainService = new TrainService();
            List<List<Integer>> seats = train.getSeats();
            if (row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
                if (seats.get(row).get(seat) == 0) {
                    seats.get(row).set(seat, 1);
                    train.setSeats(seats);
                    trainService.addTrain(train);
                    return true; // Booking successful
                } else {
                    return false; // Seat is already booked
                }
            } else {
                return false; // Invalid row or seat index
            }
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }
}