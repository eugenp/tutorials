@import com.baeldung.jte.Article
@param Article article
<html>
    <body>
        <h1>${article.title()}</h1>
        <h2>${article.author()}</h2>
        <p>${article.content()}</p>
        <p>${article.views()}</p>
    </body>
</html>
