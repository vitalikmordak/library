package servlets;

import entities.Book;
import controllers.BookController;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

@WebServlet("/PdfContent")
public class PdfContent extends HttpServlet {
    @Inject
    BookController bookController;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/pdf");

        try (OutputStream out = resp.getOutputStream()) {
            int id = Integer.parseInt(req.getParameter("id"));

            ArrayList<Book> list = bookController.getBookList();
            Book book = new Book();
            for (Book b : list) {
                if (b.getId() == id) {
                    book = b;
                }
            }
            // Downloading pdf
//            book.downloadPdf();
            resp.setContentLength(book.getContent().length);
            out.write(book.getContent());
        }


    }

}
