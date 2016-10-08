<!DOCTYPE html>
<html>
    <head>
        <title>Bookshelf: Title found</title>
    </head>
    <body>
        <p>Our Bookshelf contains this title:</p>
        <h2>${book.getTitle()}</h2>
        <p>Author: ${book.getAuthor()}</p>
        <input type="submit" value="Buy it: ${book.getPrice()}$">
    </body>
</html>
