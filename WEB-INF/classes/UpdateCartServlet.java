import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

//import org.json.JSONArray;
//import org.json.JSONObject;
//import javax.servlet.*;            // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/addToCart")
public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productId = request.getParameter("productId");
        String message = "";
        System.out.println("doPost method called");

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/im2073-web?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "myuser", "xxxx")) {
            String selectSql = "SELECT * FROM products WHERE Id = ?";
            try (PreparedStatement selectPstmt = conn.prepareStatement(selectSql)) {
                selectPstmt.setString(1, productId);
                try (ResultSet rs = selectPstmt.executeQuery()) {
                    if (rs.next()) {
                        String name = rs.getString("Name");
                        String category = rs.getString("Category");
                        String price = rs.getString("Price");
                        String brand = rs.getString("Brand");

                        String insertSql = "INSERT IGNORE INTO cart (Id, Name, Category, Price, Brand) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {
                            insertPstmt.setString(1, productId);
                            insertPstmt.setString(2, name);
                            insertPstmt.setString(3, category);
                            insertPstmt.setString(4, price);
                            insertPstmt.setString(5, brand);
                            int rowsInserted = insertPstmt.executeUpdate();
                            if (rowsInserted > 0) {
                                message = "Product added to cart.";
                            } else {
                                message = "Product already in cart.";
                            }
                        }
                    } else {
                        message = "Product not found.";
                    }
                }
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            message = "Product already in cart.";
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
