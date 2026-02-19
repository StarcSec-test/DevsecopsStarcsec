import java.sql.*;
import java.io.*;
import javax.servlet.http.HttpServletRequest;

public class VulnerableApp {

    // VULNERABILITY 1: Hardcoded Secrets (Static Analysis will catch this)
    private static final String DB_USER = "admin";
    private static final String DB_PASS = "StarcSec_P@ssw0rd_2026"; 
    private static final String DB_URL = "jdbc:mysql://35.154.173.124:3306/demo";

    public void processUserRequest(HttpServletRequest request) {
        try {
            // VULNERABILITY 2: SQL Injection (Input is not sanitized)
            String userId = request.getParameter("id");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = conn.createStatement();
            
            // DANGEROUS: Concatenating strings in SQL allows attackers to bypass login
            String query = "SELECT * FROM users WHERE id = '" + userId + "'";
            ResultSet rs = stmt.executeQuery(query);

            // VULNERABILITY 3: Insecure Deserialization (Remote Code Execution)
            // DANGEROUS: Directly reading objects from user input
            InputStream is = request.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            Object obj = ois.readObject(); 
            
            System.out.println("Processed object: " + obj.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
