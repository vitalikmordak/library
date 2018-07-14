<%@ page import="beans.Book" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="enums.SearchType" %>
<%--
  Created by IntelliJ IDEA.
  User: VM
  Date: 12-Jul-18
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@include file="../WEB-INF/jspf/left_menu.jspf" %>
<jsp:useBean id="bookList" class="beans.BookList" scope="page"/>
<h3>${param.name}</h3>
<h5 style="font-size: 14px; display: inline-block; margin-left: 30px; padding: 0;">Found books:</h5>
<div class="book_list">

    <%
        ArrayList<Book> list = null;
        if (request.getParameter("genre_id") != null) {
            try {
                long genreId = Long.valueOf(request.getParameter("genre_id"));
                list = bookList.getBooksByGenre(genreId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (request.getParameter("letter") != null) {
            String letter = request.getParameter("letter");
            list = bookList.getBooksByLetter(letter);
        } else if (request.getParameter("search_str") != null) {
            searchStr = request.getParameter("search_str");
            SearchType type = SearchType.TITLE;
            if (request.getParameter("search_option").equals("Author")) type = SearchType.AUTHOR;
            if (searchStr != null && !searchStr.trim().equals(""))
                list = bookList.getBooksBySearch(searchStr, type);
        }
        session.setAttribute("currentBookList", list);
        for (Book book : list) {

    %>
    <div class="book_info">
        <div class="book_title">
            <p><%=book.getName()%>
            </p>
        </div>
        <div class="book_image">
            <img src="<%=request.getContextPath()%>/ShowImage?index=<%=list.indexOf(book)%>"/>
        </div>
        <div class="book_details">
            <br><strong>ISBN:</strong> <%=book.getIsbn()%>
            <br><strong>Publisher:</strong> <%=book.getPublisher()%>
            <br><strong>Number of pages:</strong> <%=book.getPageCount()%>
            <br><strong>The year of publishing:</strong> <%=book.getPublishDate()%>
            <br><strong>Author:</strong> <%=book.getAuthor()%>
            <p style="margin: 10px;"><a href="#">Read online</a></p>
        </div>
    </div>
    <%}%>


</div>