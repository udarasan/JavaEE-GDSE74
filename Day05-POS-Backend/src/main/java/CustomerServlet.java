import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/api/v1/customer")
public class CustomerServlet  extends HttpServlet {
    BasicDataSource ds;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        ds = (BasicDataSource)servletContext
                .getAttribute("datasource");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            JsonObject customer = gson.fromJson(request.getReader(), JsonObject.class);
            String id=customer.get("cid").getAsString();
            String name=customer.get("cname").getAsString();
            String address=customer.get("caddress").getAsString();

            Connection connection=ds.getConnection();
            String query="Insert into Customer (id,name,address) values (?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,address);
            int rowInserted=preparedStatement.executeUpdate();
            if(rowInserted>0){
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("Customer saved successfully");
            }else {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("Customer not saved");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            JsonArray customerList = new JsonArray();
            Connection connection=ds.getConnection();
            String query="Select * from Customer";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                String id=resultSet.getString("id");
                String name=resultSet.getString("name");
                String address=resultSet.getString("address");
                JsonObject customer=new JsonObject();
                customer.addProperty("cid",id);
                customer.addProperty("cname",name);
                customer.addProperty("caddress",address);
                customerList.add(customer);
            }
            response.getWriter().println(customerList.toString());
            response.setContentType("application/json;charset=UTF-8");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Gson gson = new Gson();
            JsonObject customer = gson.fromJson(request.getReader(), JsonObject.class);
            String id=customer.get("cid").getAsString();
            String name=customer.get("cname").getAsString();
            String address=customer.get("caddress").getAsString();

            Connection connection=ds.getConnection();
            String query="update customer set name=?,address=? where id=?";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,address);
            preparedStatement.setString(3,id);
            int rowInserted=preparedStatement.executeUpdate();
            if(rowInserted>0){
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("Customer updated successfully");
            }else {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().println("Customer updated saved");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id=req.getParameter("cid");
        try {
            Connection connection=ds.getConnection();
            String query="delete from customer where id=?";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            int rowDeleted=preparedStatement.executeUpdate();
            if(rowDeleted>0){
                resp.setContentType("text/html;charset=UTF-8");
                resp.getWriter().println("Customer deleted successfully");
            }else {
                resp.setContentType("text/html;charset=UTF-8");
                resp.getWriter().println("Customer deleted Failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
