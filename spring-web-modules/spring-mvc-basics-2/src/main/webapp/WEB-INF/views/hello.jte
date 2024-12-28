@import com.baeldung.spring.domain.Message
@import java.util.ArrayList

@param Message message

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Baeldung - Duke Page</title>
</head>
<body>
    <form action="/welcome" method="post">
        Name: <input type="text" name="name" value="${message.getName()}">
        Message: <input type="text" name="text" value="${message.getText()}">
        <input type="submit" value="Submit">
    </form>

</body>
</html>

