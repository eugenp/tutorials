<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>${error!}</title>
	<style>
		body {
			background-color: #f1f1f1;
			margin: 0;
			font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
		}

		.container { margin: 50px auto 40px auto; width: 80%; text-align: center; }

		a { color: #4183c4; text-decoration: none; }
		a:hover { text-decoration: underline; }

		h1 { width: 100%; position:relative; left: 0px; letter-spacing: -1px; line-height: 60px; font-size: 60px; font-weight: 100; margin: 0px 0 20px 0; text-shadow: 0 1px 0 #fff;text-align:center; }
		p { color: rgba(0, 0, 0, 0.5); margin: 20px 0; line-height: 1.6; }

		ul { list-style: none; margin: 25px 0; padding: 0; }
		li { display: table-cell; font-weight: bold; width: 1%; }

		.logo { display: inline-block; margin-top: 35px; }
		.logo-img-2x { display: none; }
		@media
		only screen and (-webkit-min-device-pixel-ratio: 2),
		only screen and (   min--moz-device-pixel-ratio: 2),
		only screen and (     -o-min-device-pixel-ratio: 2/1),
		only screen and (        min-device-pixel-ratio: 2),
		only screen and (                min-resolution: 192dpi),
		only screen and (                min-resolution: 2dppx) {
			.logo-img-1x { display: none; }
			.logo-img-2x { display: inline-block; }
		}

		#suggestions {
			margin-top: 35px;
			color: #ccc;
		}
		#suggestions a {
			color: #666666;
			font-weight: 200;
			font-size: 14px;
			margin: 0 10px;
		}
        .exception {
            color: #ccc;
            text-align: left;
        }
	</style>
</head>
<body>
<div class="container">
	<h1>${status!}</h1>
	<p><strong>${error!}</strong></p>

	<p>
		您的请求发生了错误, 请再次尝试.
	</p>

	<div class="exception">
      ${exception!}
	</div>

</body>
</html>