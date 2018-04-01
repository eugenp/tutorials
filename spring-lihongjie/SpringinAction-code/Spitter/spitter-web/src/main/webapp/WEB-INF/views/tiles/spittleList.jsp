<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ol class="spittle-list">
     
  <c:forEach var="spittle" items="${spittles}">
       <s:url value="/spitter/{spitterName}"  var="spitter_url" >
         <s:param name="spitterName" 
                  value="${spittle.spitter.username}" />
       </s:url>
       <li>
         <span class="spittleListImage">
           <img src="${images_url}/spitter_me.jpg" 
                border="0" 
                align="middle" /></span>
         <span class="spittleListText">
             <a href="${spitter_url }">
                <c:out value="${spittle.spitter.username }" /></a>
        - <c:out value="${spittle.text }" /><br/>
         <small><c:out value="${spittle.when }" /></small></span>
       </li>
  </c:forEach>
</ol>