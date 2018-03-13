<h2>Choose Type</h2>
<form method="POST" action="flow.htm">
  <input type="radio" value="BUILD" name="pizzaType" 
      checked>Build your own<br/>
  <input type="radio" value="THEWORKS" name="pizzaType">
      Omnivore (Supreme)<br/>
  <input type="radio" value="MEAT" name="pizzaType">
      Carnivore (All Meat)<br/>
  <input type="radio" value="VEGGIE" name="pizzaType">
      Herbivore (Veggie)<br/> 

  <input type="hidden" name="_flowExecutionKey" 
      value="${flowExecutionKey}">

  <input type="submit" class="button" 
      name="_eventId_submit" value="Continue">
  <input type="submit" class="button" 
      name="_eventId_cancel" value="Cancel">  
</form>
