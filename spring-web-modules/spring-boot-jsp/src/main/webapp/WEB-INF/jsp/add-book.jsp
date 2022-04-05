<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Add Book</title>
    </head>
    <body>
        <c:if test="${addBookSuccess}">
            <div>Successfully added Book with ISBN: ${savedBook.isbn}</div>
        </c:if>

        <c:url var="add_book_url" value="/book/addBook"/>
        <form:form action="${add_book_url}" method="post" modelAttribute="book">
            <form:label path="isbn">ISBN: </form:label> <form:input type="text" path="isbn"/>
            <form:label path="name">Book Name: </form:label> <form:input type="text" path="name"/>
            <form:label path="author">Author Name: </form:label> <form:input path="author"/>
            <input type="submit" value="submit"/>
        </form:form>
    </body>
</html>