<!--<start id="jsp_form_hiddenMethod"/>--> 
<form method="post">
    <input type="hidden" name="_method" value="delete"/>
    
    ...
    
</form>
<!--<end id="jsp_form_hiddenMethod"/>--> 


<!--<start id="jsp_formform_hiddenMethod"/>--> 
<sf:form method="delete" modelAttribute="spitter">
    ...
</sf:form>
<!--<end id="jsp_formform_hiddenMethod"/>--> 


<sf:form method="delete">
    ...
</sf:form>



<form id="command" method="post">
    <input type="hidden" name="_method" value="delete"/>

    ...

</form>