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
@WebServlet("/ShowImage")
public class ShowImage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/jpg");
        OutputStream out= resp.getOutputStream();

        try {
            int index = Integer.valueOf(req.getParameter("index"));

            ArrayList<Book> list = (ArrayList<Book>) req.getSession(false).getAttribute("currentBookList");
            Book book = list.get(index);
            resp.setContentLength(book.getImage().length);
            out.write(book.getImage());
        } finally {
            out.close();
        }
    }

}
