package servlets;

import beans.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

@WebServlet("/PdfContent")
public class PdfContent extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/pdf");

        try (OutputStream out = resp.getOutputStream()) {
            int index = Integer.valueOf(req.getParameter("index"));

            ArrayList<Book> list = (ArrayList<Book>) req.getSession(false).getAttribute("currentBookList");
            Book book = list.get(index);
            // Downloading pdf
            book.downloadPdf();
            resp.setContentLength(book.getContent().length);
            out.write(book.getContent());
        }


    }

}
