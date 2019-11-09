<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Spring MVC Async</title>
    <link href="<c:url value="/resources/styles/style.css"/>" rel="stylesheet">
</head>
<body>
<main>
    <h2>Spring MVC Async</h2>
    <div id="rbe"></div>
    <div id="sse"></div>
    <div id="srb"></div>
</main>
</body>
<script>

  /**
   * AJAX Helpers.
   */

  var xhr = function(url) {
    return new Promise(function(resolve, reject) {
      try {
        var xmhr = new XMLHttpRequest();

        //Listen for API Response
        xmhr.onreadystatechange = function() {
          if (xmhr.readyState == XMLHttpRequest.DONE && xmhr.status == 200) return resolve(xmhr.responseText);
        };

        //Open connection
        xmhr.open("GET", url, true);
        //Additional headers as needed
        //x.withCredentials = true;
        //x.setRequestHeader("Accept", "application/json");
        //x.setRequestHeader("Content-Type", "text/plain");

        //Perform the actual AJAX call
        xmhr.send();

      } catch (ex) {
        reject("Exception: Oh CORS's you've made a mistake!");
      }
    });
  };

  /**
   * RBE
   */

  xhr('http://localhost:8080/rbe').then(function(success){
    var el = document.getElementById('rbe');
    el.appendChild(document.createTextNode(success));
    el.appendChild(document.createElement('br'))
  });

  /**
   * SSE
   */

  var sse = new EventSource('http://localhost:8080/sse');
  sse.onmessage = function (evt) {
    var el = document.getElementById('sse');
    el.appendChild(document.createTextNode(evt.data));
    el.appendChild(document.createElement('br'))
  };

  /**
   * SRB
   */

  xhr('http://localhost:8080/srb').then(function(success){
    var el = document.getElementById('srb');
    el.appendChild(document.createTextNode(success));
    el.appendChild(document.createElement('br'))
  });

</script>
</html>
