import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebListener
public class CartTableInitializerServlet implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/im2073-web?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", "myuser", "xxxx");
             Statement stmt = conn.createStatement()) {
            
            // Execute the SQL query to delete all rows from the "Cart" table
            String sql = "DELETE FROM cart";
            stmt.executeUpdate(sql);
            
        } catch (SQLException ex) {
            System.err.println("Failed to initialize Cart table: " + ex.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // No cleanup needed
    }
}
