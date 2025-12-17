package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

@WebServlet(urlPatterns = "/data-formats")
@MultipartConfig
public class DataFormatsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        String contentType = request.getContentType();
        System.out.println("contentType: " + contentType);
        //application/x-www-form-urlencoded
/*        System.out.println("id value :"
                +request.getParameter("id"));
        System.out.println("name value :"
                +request.getParameter("name"));*/

        //multipart/form-data
        System.out.println("id value :"
                +request.getParameter("id"));
        System.out.println("name value :"
                +request.getParameter("name"));
        //1 - read the file
        Part filePart = request.getPart("image");
        System.out.println("filePart: "
                + filePart.getSubmittedFileName());
        //2 - create a directory
        File uploadDir=new File
                ("C:\\Lectures\\Batch\\GDSE74\\AAD\\JavaEE\\JavaEE-Tomacat\\Day4-Sample-App2\\src\\main\\resources\\images");
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        //3 - save the file
        String fullPath =
                uploadDir.getAbsolutePath()
                        + File.separator
                        +filePart.getSubmittedFileName();
        filePart.write(fullPath);

    }
}
