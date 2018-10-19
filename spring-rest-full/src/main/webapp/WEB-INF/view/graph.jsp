<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Metric Graph</title>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
	google.load("visualization", "1", {
		packages : [ "corechart" ]
	});

	function drawChart() {
		$.get("<c:url value="/metric-graph-data"/>",
				function(mydata) {

					var data = google.visualization.arrayToDataTable(mydata);
					var options = {
						title : 'Website Metric',
						hAxis : {
							title : 'Time',
							titleTextStyle : {
								color : '#333'
							}
						},
						vAxis : {
							minValue : 0
						}
					};

					var chart = new google.visualization.AreaChart(document
							.getElementById('chart_div'));
					chart.draw(data, options);

				});

	}
</script>
</head>
<body onload="drawChart()">
	<div id="chart_div" style="width: 900px; height: 500px;"></div>
</body>
</html>