<%--
  Created by IntelliJ IDEA.
  User: Olga
  Date: 7/20/2016
  Time: 1:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Send Email</title>
</head>
<body>
    <div>
        <h3>Create Email</h3>
        <form:form method="POST" modelAttribute="mailObject">
            <fieldset>
                <table cellspacing="0">
                    <tr>
                        <th><label for="input_to">To</label></th>
                        <td><form:input path="to" id="input_to" type="email"/>
                            <small>Enter email address</small><br/>
                            <form:errors path="to" cssStyle="color:red;font-size:small"/>
                        </td>
                    </tr>
                    <tr>
                        <th><label for="input_subject">Subject</label></th>
                        <td><form:input path="subject" id="input_subject"/>
                            <small>Enter the subject</small><br/>
                            <form:errors path="subject" cssStyle="color:red;font-size:small"/>
                        </td>
                    </tr>
                    <tr>
                        <th><label for="input_text">Message:</label></th>
                        <td><form:textarea path="text"
                                         rows="5" cols="50"
                                         id="input_text"/>
                            <small>Enter message text</small><br/>
                            <form:errors path="text" cssStyle="color:red;font-size:small"/>
                        </td>
                    </tr>
                    <tr>
                        <th></th>
                        <td>
                            <input type="submit" value="Send">
                        </td>
                    </tr>
                </table>
            </fieldset>
        </form:form>
    </div>
</body>
</html>
