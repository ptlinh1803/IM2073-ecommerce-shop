import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


@WebServlet("/update-order-details")
public class UpdateOrderDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Retrieve the values from the request parameters
            String[] quantities = request.getParameterValues("quantities[]");
            String[] subtotals = request.getParameterValues("subtotals[]");

            String quantities0 = quantities[0];
            String subtotals0 = subtotals[0];
            quantities0 = quantities0.substring(1, quantities0.length() - 1);
            subtotals0 = subtotals0.substring(1, subtotals0.length() - 1);
            String[] quantitiesUSE = quantities0.split(",");
            String[] subtotalsUSE = subtotals0.split(",");
            String userEmail = getUserEmail();
            System.out.println("email =" + getUserEmail());
            // Retrieve the product IDs from the cart
            String[] productIds = getCartProductIds();
            System.out.println("quantities = " + Arrays.toString(quantities));
            System.out.println("subtotals = " + Arrays.toString(subtotals));
            System.out.println("productIds = " + Arrays.toString(productIds));
            System.out.println("quantities0 = " + quantities0);
            System.out.println("subtotals0 = " + subtotals0);
            System.out.println("quantitiesUSE = " + Arrays.toString(quantitiesUSE));
            System.out.println("subtotalsUSE = " + Arrays.toString(subtotalsUSE));
            // Insert the order details into the order_details table
            insertOrderDetails(productIds, quantitiesUSE, subtotalsUSE, userEmail, response);

            // Send a success response
            //response.getWriter().write("Order added successfully");
            // Send a success response
        String message = "Order added successfully. Back to HOME.";
        String redirectUrl = "/index.html";
        String script = "alert('" + message + "'); window.location.href = '" + redirectUrl + "';";
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("<html><head><script>" + script + "</script></head><body></body></html>");
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Send an error response
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error adding order: " + e.getMessage());
        }
    }

    private String[] getCartProductIds() throws SQLException {
        // Connect to the database
        String url = "jdbc:mysql://localhost:3306/im2073-web?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
        String user = "myuser";
        String password = "xxxx";
        String sql = "SELECT ID FROM cart";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Execute the query and retrieve the product IDs
            List<String> productIds = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                productIds.add(rs.getString("ID"));
            }

            // Return the product IDs as an array
            return productIds.toArray(new String[0]);
        }
    }

    private String getUserEmail() throws SQLException {
        // Connect to the database
        String url = "jdbc:mysql://localhost:3306/im2073-web?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
        String user = "myuser";
        String password = "xxxx";
        String sql = "SELECT email FROM user_details ORDER BY create_at DESC LIMIT 1";
    
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            // Execute the query and retrieve the email
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            } else {
                throw new SQLException("No user found");
            }
        }
    }
    

    private void insertOrderDetails(String[] productIds, String[] quantities, String[] subtotals, String userEmail, HttpServletResponse response)
            throws SQLException {
        // Connect to the database
        String url = "jdbc:mysql://localhost:3306/im2073-web?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
        String user = "myuser";
        String password = "xxxx";
        String sql = "INSERT INTO order_details (product_id, quantity, subtotal, user_email, order_date) VALUES (?, ?, ?, ?, NOW())";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            // Set the parameter values for each row to be inserted
            //Timestamp orderDate = new Timestamp(System.currentTimeMillis());
            for (int i = 0; i < productIds.length; i++) {
                statement.setString(1, productIds[i]);
                statement.setString(2, quantities[i]);
                statement.setString(3, subtotals[i]);
                statement.setString(4, userEmail);
                //statement.setTimestamp(5, orderDate);
                statement.addBatch();
            }

            // Execute the batch insert
            statement.executeBatch();
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            try {
                PrintWriter out = response.getWriter();
                out.flush();
            } catch (IOException e) {
                // Handle the exception here
            }
            
        }
    }
}
