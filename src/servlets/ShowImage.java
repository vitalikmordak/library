package servlets;

import controllers.BookController;
import entities.Book;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/ShowImage")
public class ShowImage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }
    @Inject BookController bookController;
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("image/jpg");
    // get Images from request collection instead of from DB
        try (OutputStream out = resp.getOutputStream()) {
            long id = Long.parseLong(req.getParameter("id"));
            Book book = bookController.getBookList().stream().filter(p -> p.getId() == id).findFirst().get();
            byte[] image = book.getImage();
            resp.setContentLength(image.length);
            out.write(image);
        }
    }
}
