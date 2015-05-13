<html>
<head>

<title>Schedule to Reddit</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
<link rel="stylesheet" th:href="@{/resources/autocomplete.css}"/>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
<script th:src="@{/resources/validator.js}"></script>
<style type="text/css">
.btn.disabled{
background-color: #ddd;
border-color: #ddd;
}

.btn.disabled:hover{
background-color: #ddd;
border-color: #ddd;
}
</style>
</head>
<body>
<div th:include="header"/>

<div class="container">
<h1>Post to Reddit</h1>
<form action="submit" method="post" role="form" data-toggle="validator">
<div class="row">
<div class="form-group">
    <label class="col-sm-3">Title</label>
    <span class="col-sm-9"><input name="title" placeholder="title" class="form-control" data-minlength="3" required="required"/></span>
</div>
<br/><br/>
<div class="form-group">
    <label class="col-sm-3">Url</label>
    <span class="col-sm-9"><input name="url" type="url" placeholder="url" class="form-control" data-minlength="3" required="required"/></span>
</div>
<br/><br/>  
<div class="form-group">
    <label class="col-sm-3">Subreddit</label>
    <span class="col-sm-9"><input id="sr" name="sr" placeholder="Subreddit (e.g. kitten)" class="form-control" data-minlength="3" required="required"/></span>
</div>
<br/><br/>
<div>
<label class="col-sm-3">Send replies to my inbox</label> 
<span class="col-sm-9"><input type="checkbox" name="sendreplies" value="true"/></span> 
</div>
<br/>
<br/>
<div>
<span class="col-sm-2"><a href="#" class="btn btn-default" onclick="checkIfAlreadySubmitted()">Check if already submitted</a></span>
<span class="col-sm-1"></span>
<span class=" col-sm-9"><span id="checkResult" class="alert alert-info" style="display:none"></span></span>
</div>
<br/><br/>
 
    <div th:if="${iden != null}">
    <input type="hidden" name="iden" value="${iden}"/>
    
	<div class="form-group">   
	    <label class="col-sm-3">Captcha</label>
	    <span class="col-sm-9"><input name="captcha" placeholder="captcha" class="form-control"/></span>
	</div>
	<br/><br/>
    <img src="http://www.reddit.com/captcha/${iden}" alt="captcha" width="200"/>
    </div>
    <br/><br/>
    <span class="col-sm-3"><button id="submitbtn" type="submit" class="btn btn-primary">Post</button></span>
   </div>
</form>
<div>
<div th:if="${session.PREDICTION_FEATURE.isActive()}">
<button id="checkbtn" class="btn btn-default disabled" onclick="predicateResponse()">Predicate Response</button>
<span id="prediction"></span>
<script type="text/javascript">
/*<![CDATA[*/
$("input").change(function() {
    if($("#submitbtn").hasClass("disabled")){
    	if(! $("#checkbtn").hasClass("disabled")){
    		$("#checkbtn").addClass("disabled");
    	}
    }else{
    	$("#checkbtn").removeClass("disabled");
    }
});
function predicateResponse(){
	var title = $('input[name="title"]').val();
	var domain = $('input[name="url"]').val();
	domain =  $('<a>').prop('href', domain).prop('hostname');
	console.log(domain);
	$.post("predicatePostResponse",{title: title, domain: domain} ,function(data){
        $("#prediction").addClass("alert alert-info").html(data.replace('{','').replace('}',''));
    });
}
/*]]>*/
</script>
</div>
</div>
</div>
<script>
  $(function() {
    $( "#sr" ).autocomplete({
      source: "subredditAutoComplete"
    });
    
    $("input[name='url'],input[name='sr']").focus(function (){
    	$("#checkResult").hide();
    });
    
  });
</script>
  
<script>
/*<![CDATA[*/
function checkIfAlreadySubmitted(){
    var url = $("input[name='url']").val();
    var sr = $("input[name='sr']").val();
    console.log(url);
    if(url.length >3 && sr.length > 3){
        $.post("checkIfAlreadySubmitted",{url: url, sr: sr}, function(data){
        	var result = JSON.parse(data);
        	if(result.length == 0){
        		$("#checkResult").show().html("Not submitted before");
        	}else{
                $("#checkResult").show().html('Already submitted <b><a target="_blank" href="http://www.reddit.com'+result[0].data.permalink+'">here</a></b>');
        	}
        });
    }
    else{
        $("#checkResult").show().html("Too short url and/or subreddit");
    }
}           
/*]]>*/          
</script>
</body>
</html>