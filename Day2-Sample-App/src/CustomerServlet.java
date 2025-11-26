import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    private static final List<Customer> customers = new ArrayList<>();

    // CREATE
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/javaeeapp",
                    "root","12345678");
            String query="INSERT INTO customer(id,name,address) VALUES (?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,address);
            int rowInserted=preparedStatement.executeUpdate();
            if (rowInserted>0){
                resp.getWriter().println("Customer saved successfully");
            }else {
                resp.getWriter().println("Customer not saved");
            }
            connection.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // READ
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String id = req.getParameter("id");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/javaeeapp",
                    "root","12345678");
            if (id==null){
                String query="SELECT * FROM customer";
                PreparedStatement preparedStatement=
                        connection.prepareStatement(query);
                ResultSet resultSet=preparedStatement.executeQuery();
                while (resultSet.next()){
                    String cusId=resultSet.getString("id");
                    String name=resultSet.getString("name");
                    String address=resultSet.getString("address");
                    resp.getWriter().println(cusId+","+name+","+address);
                }
            }else  {
                String query="SELECT * FROM customer WHERE id=?";
                PreparedStatement preparedStatement=connection.prepareStatement(query);
                preparedStatement.setString(1,id);
                ResultSet resultSet=preparedStatement.executeQuery();
                while (resultSet.next()){
                    String cusId=resultSet.getString("id");
                    String name=resultSet.getString("name");
                    String address=resultSet.getString("address");
                    resp.getWriter().println(cusId+","+name+","+address);
                }
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // UPDATE
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        for (Customer c : customers) {
            if (c.getId().equals(id)) {
                if (name != null) c.setName(name);
                if (address != null) c.setAddress(address);

                resp.getWriter().println("Customer updated successfully");
                return;
            }
        }

        resp.getWriter().println("Customer not found for update");
    }

    // DELETE
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        for (Customer c : customers) {
            if (c.getId().equals(id)) {
                customers.remove(c);
                resp.getWriter().println("Customer deleted successfully");
                return;
            }
        }

        resp.getWriter().println("Customer not found for deletion");
    }
}
