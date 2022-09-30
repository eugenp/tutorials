<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Authorization</title>
    <style>
        input[type=submit]{
            width: 25%;
            padding: 4px 0px;
            display: inline-block;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        .container {
            width: 75%;
            padding: 25px;
            border: 1px solid #ccc;
            background: beige;
        }

    </style>
</head>
<body>

<div class="container">
    <p><h3>Want to Authorize scopes for client : ${client.clientId} ?</h3></p>
    <hr>

    <form method="post" action="authorize">
        <table>
            <tr>
                <td valign="top">Scopes :</td>
                <td>
                    <c:forTokens items="${scopes}" delims=" " var="scope">
                        <input type="checkbox" name="scope" value="${scope}">${scope}</input><br/>
                    </c:forTokens>
                </td>
            </tr>

            <tr>
                <td colspan="2">
                    <input type="submit" name="approval_status" value="YES"/>
                    <input type="submit" name="approval_status" value="NO"/>
                </td>
            </tr>
        </table>
    </form>

</div>

</body>
</html>
