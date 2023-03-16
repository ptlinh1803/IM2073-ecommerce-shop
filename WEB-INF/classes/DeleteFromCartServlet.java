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

@WebServlet("/deleteFromCart")
public class DeleteFromCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productId = request.getParameter("productId");
        String message = "";
        System.out.println("doPost method called");

        try (Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/im2073-web?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC","myuser", "xxxx")) {
            String deleteSql = "DELETE FROM cart WHERE Id = ?";
            try (PreparedStatement deletePstmt = conn.prepareStatement(deleteSql)) {
                deletePstmt.setString(1, productId);
                deletePstmt.executeUpdate();
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
