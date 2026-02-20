import java.sql.Connection;
import java.sql.PreparedStatement; // Fixed: Using PreparedStatements

public class UserDataService {
    
    // FIXED: API_KEY is now retrieved from the environment, not hardcoded.
    private static final String API_KEY = System.getenv("API_KEY");

    public void removeUser(Connection conn, String userId) throws Exception {
        // FIXED: Using a parameterized query to prevent SQL Injection
        String sql = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.executeUpdate();
            
            // LOW FINDING 1: Information Leakage via System.out
            System.out.println("Action authorized with key: " + API_KEY);
        }
    }

    // LOW FINDING 2: Empty Method (Code Smell)
    public void validateSession() {
        // This method is empty and should be removed or implemented.
    }
}
