<h2>Choose size</h2>
<form method="POST" action="flow.htm">
  <input type="radio" value="SMALL" name="size">
      Small (12-inch)<br/>
  <input type="radio" value="MEDIUM" name="size">
      Medium (14-inch)<br/> 
  <input type="radio" value="LARGE" name="size" checked>
      Large (16-inch)<br/>
  <input type="radio" value="GINORMOUS" name="size">
      Ginormous (20-inch)<br/>

  <input type="hidden" name="_flowExecutionKey" 
      value="${flowExecutionKey}">

  <input type="submit" class="button" 
      name="_eventId_submit" value="Continue">
  <input type="submit" class="button" 
      name="_eventId_cancel" value="Cancel">  
</form>
