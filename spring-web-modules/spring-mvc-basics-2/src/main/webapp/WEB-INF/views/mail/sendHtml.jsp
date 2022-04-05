<%--
  User: Benjamin CAURE
  Date: 4/14/2020
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Send HTML Email</title>
</head>
<body>
    <div>
        <h3>Send Email Using Text Template</h3>
        <form:form method="POST" modelAttribute="mailObject" >
            <fieldset>
                <div style="width: 100%;max-width: 1280px">
                    <table>
                        <tr>
                            <th><label for="input_to">Recipient email</label></th>
                            <td><form:input path="to" id="input_to" type="email"/>
                                <small>Enter email address</small><br/>
                                <form:errors path="to" cssStyle="color:red;font-size:small"/>
                            </td>
                        </tr>
                        <tr>
                            <th><label for="input_recipient_name">Recipient name</label></th>
                            <td><form:input path="recipientName" id="input_recipient_name"/>
                                <small>Enter the recipient name</small><br/>
                                <form:errors path="recipientName" cssStyle="color:red;font-size:small"/>
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
                            <th><label for="input_text">Text</label></th>
                            <td><form:textarea path="text"
                                             rows="5" cols="50"
                                             id="input_text"/>
                                <form:errors path="text" cssStyle="color:red;font-size:small"/>
                            </td>
                        </tr>
                        <tr>
                            <th><label for="input_sender_name">Sender name</label></th>
                            <td><form:input path="senderName" id="input_sender_name"/>
                                <small>Enter the sender name</small><br/>
                                <form:errors path="senderName" cssStyle="color:red;font-size:small"/>
                            </td>
                        </tr>
                        <tr>
                            <th><label for="input_template_engine">Template Engine</label></th>
                            <td><form:select path="templateEngine" id="input_template_engine" items="${templateEngines}"/>
                                <small>Select the template engine</small><br/>
                                <form:errors path="templateEngine" cssStyle="color:red;font-size:small"/>
                            </td>
                        </tr>
                        <tr>
                            <th></th>
                            <td>
                                <input type="submit" value="Send">
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
        </form:form>
    </div>
</body>
</html>
