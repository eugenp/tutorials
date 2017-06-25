<% Integer counter = (Integer) request.getAttribute("counter"); %>
<% if (counter != null && counter > 0) { %>
    <hr/>
    <p>You are visitor #<%= counter %>. <a href="/?command=Logout">Logout</a></p>
<% } %>
