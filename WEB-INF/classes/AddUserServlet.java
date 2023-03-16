import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/add-user")
public class AddUserServlet extends HttpServlet {
private static final long serialVersionUID = 1L;

protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String email = request.getParameter("cart-email");
    System.out.println("Email value: " + email);
    String name = request.getParameter("cart-name");
    System.out.println("Name value: " + name);
    String phone = request.getParameter("cart-phone-no");
    String address = request.getParameter("cart-address");
    String message = "";

    try (Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/im2073-web?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
            "myuser", "xxxx")) {
        String selectSql = "SELECT * FROM user_details WHERE email=?";
        try (PreparedStatement selectPstmt = conn.prepareStatement(selectSql)) {
            selectPstmt.setString(1, email);
            try (ResultSet rs = selectPstmt.executeQuery()) {
                if (rs.next()) {
                    // User already exists in the table, update the existing row
                    String updateSql = "UPDATE user_details SET name=?, phone=?, address=?, create_at = NOW() WHERE email=?";
                    try (PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
                        updatePstmt.setString(1, name);
                        updatePstmt.setString(2, phone);
                        updatePstmt.setString(3, address);
                        updatePstmt.setString(4, email);
                        int rowsUpdated = updatePstmt.executeUpdate();
                        if (rowsUpdated > 0) {
                            message = "User details updated successfully. Please go back.";
                        } else {
                            message = "No rows were updated. Please go back.";
                        }
                    }
                } else {
                    // User does not exist in the table, insert a new row
                    String insertSql = "INSERT INTO user_details (email, name, phone, address, create_at) VALUES (?, ?, ?, ?, NOW())";
                    try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {
                        insertPstmt.setString(1, email);
                        insertPstmt.setString(2, name);
                        insertPstmt.setString(3, phone);
                        insertPstmt.setString(4, address);
                        int rowsInserted = insertPstmt.executeUpdate();
                        if (rowsInserted > 0) {
                            message = "New user added successfully. Please go back.";
                        } else {
                            message = "No rows were inserted. Please go back.";
                        }
                    }
                }
            }
        }
    } catch (SQLException ex) {
        message = "Error: " + ex.getMessage();
    }

    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    out.print(message);
    out.flush();
}


}
