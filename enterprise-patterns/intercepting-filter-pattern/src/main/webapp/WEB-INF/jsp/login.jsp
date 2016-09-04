<!DOCTYPE html>
<html>
    <head>
        <title>Bookshelf: Login</title>
    </head>
    <body>
        <p>Please input a username:</p>
        <h2>Login</h2>
        <form action="/intercepting-filter/?command=Login" method="POST">
            <input type="text" name="username" placeholder="Username">
            <input type="hidden" name="redirect" value="${redirect}">
            <input type="submit" value="Proceed">
        </form>
    </body>
</html>
