<!-- 
  Form to prompt the user for their phone number. 
  
  From Listing 15.2
 -->
<h2>Customer Lookup</h2>
<form method="post" action="flow.htm">
  <input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}">

  <b>Phone number: </b> 
      <input type="text" name="phoneNumber"><br>
  <input type="submit" class="button" 
      name="_eventId_submit" value="Submit">
</form>
