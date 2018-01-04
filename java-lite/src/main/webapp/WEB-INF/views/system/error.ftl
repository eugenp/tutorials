Error:<br/>
<pre>
${message}
</pre>
<script type="text/javascript">
    function show() {
        document.getElementById("stack_trace").removeAttribute("style");
    }
</script>

<br/>
<a id="show_link" href="#" onclick='show();'>See Stack Trace</a>

<div id="stack_trace" style="display:none">
<pre>
${stack_trace}
</pre>
</div>