<%--
  Created by IntelliJ IDEA.
  User: Olga
  Date: 1/19/16
  Time: 3:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Home Page</title>
</head>
<body>
    <div>
        <div>
            <h4>Select any of the options below to send sample email:</h4>
            <form method="get" style="width: 200px;">
                <fieldset style="border: none; padding-left: 0px; padding-top: 0px">
                    <table>
                        <tr>
                            <td>
                                <input type="submit" formaction="mail/send" value="Send Simple Email">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="submit" formaction="mail/sendTemplate" value="Send Email Using Text Template">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="submit" formaction="mail/sendAttachment" value="Send Email With Attachment">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="submit" formaction="mail/sendHtml" value="Send HTML Email">
                            </td>
                        </tr>
                    </table>
                </fieldset>
            </form>
        </div>
    </div>
</body>
</html>