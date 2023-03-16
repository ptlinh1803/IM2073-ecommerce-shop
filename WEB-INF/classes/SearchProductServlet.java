import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import org.json.JSONArray;
import org.json.JSONObject;
//import javax.servlet.*;            // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/search-product")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class SearchProductServlet extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/im2073-web?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", "myuser", "xxxx");
        //Statement stmt = conn.createStatement()
        )
       {
        //String sqlString = "SELECT * FROM products WHERE name LIKE '%" + request.getParameter("searchproduct") + "%' OR brand LIKE '%" + request.getParameter("searchproduct") + "%';";
        //ResultSet rs = stmt.executeQuery(sqlString);
        String sqlString = "SELECT * FROM products WHERE name LIKE ? OR brand LIKE ?";
        PreparedStatement pstmt = conn.prepareStatement(sqlString);
        pstmt.setString(1, "%" + request.getParameter("searchproduct") + "%");
        pstmt.setString(2, "%" + request.getParameter("searchproduct") + "%");
        ResultSet rs = pstmt.executeQuery();
         // Step 3: Convert the result set into a JSON array
         JSONArray jsonArray = new JSONArray();
         while (rs.next()) {
             JSONObject jsonObject = new JSONObject();
             jsonObject.put("id", rs.getString("id"));
             jsonObject.put("name", rs.getString("name"));
             jsonObject.put("category", rs.getString("category"));
             jsonObject.put("price", rs.getString("price"));
             jsonObject.put("brand", rs.getString("brand"));
             //jsonObject.put("added", rs.getString("added"));
             jsonArray.put(jsonObject);
         }

         // Step 4: Set the content type of the response and write the JSON array as the response
         response.setContentType("application/json");
         PrintWriter out = response.getWriter();
         out.print(jsonArray);
         
         // Step 5: Close the connection and statement
         rs.close();
         pstmt.close();
         conn.close();
         
     } catch (SQLException ex) {
         ex.printStackTrace();
     }
   }
}