import org.example.services.UserBookingService;
import org.example.services.TrainService;

public class TestApp {
    public static void main(String[] args) {
        try {
            System.out.println("Testing UserBookingService...");
            UserBookingService userService = new UserBookingService();
            System.out.println("UserBookingService initialized successfully!");
            
            System.out.println("Testing TrainService...");
            TrainService trainService = new TrainService();
            System.out.println("TrainService initialized successfully!");
            
            System.out.println("All services initialized correctly. JSON files are accessible.");
            
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
