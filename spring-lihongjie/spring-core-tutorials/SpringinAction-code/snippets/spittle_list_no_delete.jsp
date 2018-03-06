<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
  <h2>Spittles for ${spitter.username}</h2> <!--<co id="co_display_username"/>--> 

  <table cellspacing="15">
    <c:forEach items="${spittleList}" var="spittle">  <!--<co id="co_list_spittles"/>--> 
    <tr>
      <td>
          <img src="<s:url value="/resources/images/spitter_avatar.png"/>" 
               width="48" height="48" /></td>
      <td>
          <a href="<s:url value="/spitters/${spittle.spitter.username}"/>">
                       ${spittle.spitter.username}</a> 
          <c:out value="${spittle.text}" /><br/>
          <c:out value="${spittle.when}" />
      </td>
    </tr>
    </c:forEach>
  </table>
</div>
