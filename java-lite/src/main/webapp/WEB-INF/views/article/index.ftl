<@content for="title">Articles</@content>

<table>
    <tr>
        <td>Title</td>
        <td>Author</td>
        <td>Words #</td>
        <td>Date Published</td>
    </tr>
<#list articles as article>
    <tr>
        <td>${article.title}</td>
        <td>${article.author}</td>
        <td>${article.words}</td>
        <td>${article.date}</td>
    </tr>
</#list>
</table>