<form method="POST" action="flow.htm">
  <input type="hidden" name="_flowExecutionKey" value="${flowExecutionKey}">
  <input type="hidden" name="amount" value="${amount}" />
  
  <b>Credit card #:  </b><input type="text" name="creditCardNumber"><br/>
  <b>Expiration  :  </b>
      <select name="expirationMonth">
        <option value="01">Jan</option>
        <option value="02">Feb</option>
        <option value="03">Mar</option>
        <option value="04">Apr</option>
        <option value="05">May</option>
        <option value="06">Jun</option>
        <option value="07">Jul</option>
        <option value="08">Aug</option>
        <option value="09">Sep</option>
        <option value="10">Oct</option>
        <option value="11">Nov</option>
        <option value="12">Dec</option>
      </select> / 
      <select name="expirationYear">
        <option value="2007">2007</option>
        <option value="2008">2008</option>
        <option value="2009">2009</option>
        <option value="2010">2010</option>
        <option value="2011">2011</option>
        <option value="2012">2012</option>
      </select><br/>
    
  <input type="submit" class="button" 
      name="_eventId_submit" value="Submit">
  <input type="submit" class="button" 
      name="_eventId_cancel" value="Cancel">        
</form>
