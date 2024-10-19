/* The MIT License

 Copyright (c) 2011 by Michael Zinsmaier and nergal.dev
 Copyright (c) 2012 by Thomas Ritou

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
*/

/*
____________________________________________________

 what it is:
 ____________________________________________________

 curvedLines is a plugin for flot, that tries to display lines in a smoother way.
 This is achieved through adding of more data points. The plugin is a data processor and can thus be used
 in combination with standard line / point rendering options.

 => 1) with large data sets you may get trouble
 => 2) if you want to display the points too, you have to plot them as 2nd data series over the lines
 => 3) consecutive x data points are not allowed to have the same value

 Feel free to further improve the code

 ____________________________________________________

 how to use it:
 ____________________________________________________

 var d1 = [[5,5],[7,3],[9,12]];

 var options = { series: { curvedLines: {  active: true }}};

 $.plot($("#placeholder"), [{data: d1, lines: { show: true}, curvedLines: {apply: true}}], options);

 _____________________________________________________

 options:
 _____________________________________________________

 active:           bool true => plugin can be used
 apply:            bool true => series will be drawn as curved line
 monotonicFit:	   bool true => uses monotone cubic interpolation (preserve monotonicity)
 tension:          int          defines the tension parameter of the hermite spline interpolation (no effect if monotonicFit is set)
 nrSplinePoints:   int 			defines the number of sample points (of the spline) in between two consecutive points

 deprecated options from flot prior to 1.0.0:
 ------------------------------------------------
 legacyOverride	   bool true => use old default
    OR
 legacyOverride    optionArray
 {
 	fit: 	             bool true => forces the max,mins of the curve to be on the datapoints
 	curvePointFactor	 int  		  defines how many "virtual" points are used per "real" data point to
 									  emulate the curvedLines (points total = real points * curvePointFactor)
 	fitPointDist: 	     int  		  defines the x axis distance of the additional two points that are used
 }						   		   	  to enforce the min max condition.
 */

/*
 *  v0.1   initial commit
 *  v0.15  negative values should work now (outcommented a negative -> 0 hook hope it does no harm)
 *  v0.2   added fill option (thanks to monemihir) and multi axis support (thanks to soewono effendi)
 *  v0.3   improved saddle handling and added basic handling of Dates
 *  v0.4   rewritten fill option (thomas ritou) mostly from original flot code (now fill between points rather than to graph bottom), corrected fill Opacity bug
 *  v0.5   rewritten instead of implementing a own draw function CurvedLines is now based on the processDatapoints flot hook (credits go to thomas ritou).
 * 		   This change breakes existing code however CurvedLines are now just many tiny straight lines to flot and therefore all flot lines options (like gradient fill,
 * 	       shadow) are now supported out of the box
 *  v0.6   flot 0.8 compatibility and some bug fixes
 *  v0.6.x changed versioning schema
 *
 *  v1.0.0 API Break marked existing implementation/options as deprecated
 *  v1.1.0 added the new curved line calculations based on hermite splines
 *  v1.1.1 added a rough parameter check to make sure the new options are used
 */

(function($) {

	var options = {
		series : {
			curvedLines : {
				active : false,
				apply : false,
				monotonicFit : false,
				tension : 0.5,
				nrSplinePoints : 20,
				legacyOverride : undefined
			}
		}
	};

	function init(plot) {

		plot.hooks.processOptions.push(processOptions);

		//if the plugin is active register processDatapoints method
		function processOptions(plot, options) {
			if (options.series.curvedLines.active) {
				plot.hooks.processDatapoints.unshift(processDatapoints);
			}
		}

		//only if the plugin is active
		function processDatapoints(plot, series, datapoints) {
			var nrPoints = datapoints.points.length / datapoints.pointsize;
			var EPSILON = 0.005;

			//detects missplaced legacy parameters (prior v1.x.x) in the options object
			//this can happen if somebody upgrades to v1.x.x without adjusting the parameters or uses old examples
            var invalidLegacyOptions = hasInvalidParameters(series.curvedLines);

			if (!invalidLegacyOptions && series.curvedLines.apply == true && series.originSeries === undefined && nrPoints > (1 + EPSILON)) {
				if (series.lines.fill) {

					var pointsTop = calculateCurvePoints(datapoints, series.curvedLines, 1);
					var pointsBottom = calculateCurvePoints(datapoints, series.curvedLines, 2);
					//flot makes sure for us that we've got a second y point if fill is true !

					//Merge top and bottom curve
					datapoints.pointsize = 3;
					datapoints.points = [];
					var j = 0;
					var k = 0;
					var i = 0;
					var ps = 2;
					while (i < pointsTop.length || j < pointsBottom.length) {
						if (pointsTop[i] == pointsBottom[j]) {
							datapoints.points[k] = pointsTop[i];
							datapoints.points[k + 1] = pointsTop[i + 1];
							datapoints.points[k + 2] = pointsBottom[j + 1];
							j += ps;
							i += ps;

						} else if (pointsTop[i] < pointsBottom[j]) {
							datapoints.points[k] = pointsTop[i];
							datapoints.points[k + 1] = pointsTop[i + 1];
							datapoints.points[k + 2] = k > 0 ? datapoints.points[k - 1] : null;
							i += ps;
						} else {
							datapoints.points[k] = pointsBottom[j];
							datapoints.points[k + 1] = k > 1 ? datapoints.points[k - 2] : null;
							datapoints.points[k + 2] = pointsBottom[j + 1];
							j += ps;
						}
						k += 3;
					}
				} else if (series.lines.lineWidth > 0) {
					datapoints.points = calculateCurvePoints(datapoints, series.curvedLines, 1);
					datapoints.pointsize = 2;
				}
			}
		}

		function calculateCurvePoints(datapoints, curvedLinesOptions, yPos) {
			if ( typeof curvedLinesOptions.legacyOverride != 'undefined' && curvedLinesOptions.legacyOverride != false) {
				var defaultOptions = {
					fit : false,
					curvePointFactor : 20,
					fitPointDist : undefined
				};
				var legacyOptions = jQuery.extend(defaultOptions, curvedLinesOptions.legacyOverride);
				return calculateLegacyCurvePoints(datapoints, legacyOptions, yPos);
			}

			return calculateSplineCurvePoints(datapoints, curvedLinesOptions, yPos);
		}

		function calculateSplineCurvePoints(datapoints, curvedLinesOptions, yPos) {
			var points = datapoints.points;
			var ps = datapoints.pointsize;
			
			//create interpolant fuction
			var splines = createHermiteSplines(datapoints, curvedLinesOptions, yPos);
			var result = [];

			//sample the function
			// (the result is intependent from the input data =>
			//	it is ok to alter the input after this method)
			var j = 0;
			for (var i = 0; i < points.length - ps; i += ps) {
				var curX = i;
				var curY = i + yPos;	
				
				var xStart = points[curX];
				var xEnd = points[curX + ps];
				var xStep = (xEnd - xStart) / Number(curvedLinesOptions.nrSplinePoints);

				//add point
				result.push(points[curX]);
				result.push(points[curY]);

				//add curve point
				for (var x = (xStart += xStep); x < xEnd; x += xStep) {
					result.push(x);
					result.push(splines[j](x));
				}
				
				j++;
			}

			//add last point
			result.push(points[points.length - ps]);
			result.push(points[points.length - ps + yPos]);

			return result;
		}



		// Creates an array of splines, one for each segment of the original curve. Algorithm based on the wikipedia articles: 
		//
		// http://de.wikipedia.org/w/index.php?title=Kubisch_Hermitescher_Spline&oldid=130168003 and 
		// http://en.wikipedia.org/w/index.php?title=Monotone_cubic_interpolation&oldid=622341725 and the description of Fritsch-Carlson from
		// http://math.stackexchange.com/questions/45218/implementation-of-monotone-cubic-interpolation
		// for a detailed description see https://github.com/MichaelZinsmaier/CurvedLines/docu
		function createHermiteSplines(datapoints, curvedLinesOptions, yPos) {
			var points = datapoints.points;
			var ps = datapoints.pointsize;
			
			// preparation get length (x_{k+1} - x_k) and slope s=(p_{k+1} - p_k) / (x_{k+1} - x_k) of the segments
			var segmentLengths = [];
			var segmentSlopes = [];

			for (var i = 0; i < points.length - ps; i += ps) {
				var curX = i;
				var curY = i + yPos;			
				var dx = points[curX + ps] - points[curX];
				var dy = points[curY + ps] - points[curY];
							
				segmentLengths.push(dx);
				segmentSlopes.push(dy / dx);
			}

			//get the values for the desired gradients  m_k for all points k
			//depending on the used method the formula is different
			var gradients = [segmentSlopes[0]];	
			if (curvedLinesOptions.monotonicFit) {
				// Fritsch Carlson
				for (var i = 1; i < segmentLengths.length; i++) {
					var slope = segmentSlopes[i];
					var prev_slope = segmentSlopes[i - 1];
					if (slope * prev_slope <= 0) { // sign(prev_slope) != sign(slpe)
						gradients.push(0);
					} else {
						var length = segmentLengths[i];
						var prev_length = segmentLengths[i - 1];
						var common = length + prev_length;
						//m = 3 (prev_length + length) / ((2 length + prev_length) / prev_slope + (length + 2 prev_length) / slope)
						gradients.push(3 * common / ((common + length) / prev_slope + (common + prev_length) / slope));
					}
				}
			} else {
				// Cardinal spline with t € [0,1]
				// Catmull-Rom for t = 0
				for (var i = ps; i < points.length - ps; i += ps) {
					var curX = i;
					var curY = i + yPos;	
					gradients.push(Number(curvedLinesOptions.tension) * (points[curY + ps] - points[curY - ps]) / (points[curX + ps] - points[curX - ps]));
				}
			}
			gradients.push(segmentSlopes[segmentSlopes.length - 1]);

			//get the two major coefficients (c'_{oef1} and c'_{oef2}) for each segment spline
			var coefs1 = [];
			var coefs2 = [];
			for (i = 0; i < segmentLengths.length; i++) {
				var m_k = gradients[i];
				var m_k_plus = gradients[i + 1];
				var slope = segmentSlopes[i];
				var invLength = 1 / segmentLengths[i];
				var common = m_k + m_k_plus - slope - slope;
				
				coefs1.push(common * invLength * invLength);
				coefs2.push((slope - common - m_k) * invLength);
			}

			//create functions with from the coefficients and capture the parameters
			var ret = [];
			for (var i = 0; i < segmentLengths.length; i ++) {
				var spline = function (x_k, coef1, coef2, coef3, coef4) {
					// spline for a segment
					return function (x) {									
						var diff = x - x_k;
						var diffSq = diff * diff;
						return coef1 * diff * diffSq + coef2 * diffSq + coef3 * diff + coef4;
					};
				};			
		
				ret.push(spline(points[i * ps], coefs1[i], coefs2[i], gradients[i], points[i * ps + yPos]));
			}
			
			return ret;
		};

		//no real idea whats going on here code mainly from https://code.google.com/p/flot/issues/detail?id=226
		//if fit option is selected additional datapoints get inserted before the curve calculations in nergal.dev s code.
		function calculateLegacyCurvePoints(datapoints, curvedLinesOptions, yPos) {

			var points = datapoints.points;
			var ps = datapoints.pointsize;
			var num = Number(curvedLinesOptions.curvePointFactor) * (points.length / ps);

			var xdata = new Array;
			var ydata = new Array;

			var curX = -1;
			var curY = -1;
			var j = 0;

			if (curvedLinesOptions.fit) {
				//insert a point before and after the "real" data point to force the line
				//to have a max,min at the data point.

				var fpDist;
				if ( typeof curvedLinesOptions.fitPointDist == 'undefined') {
					//estimate it
					var minX = points[0];
					var maxX = points[points.length - ps];
					fpDist = (maxX - minX) / (500 * 100);
					//x range / (estimated pixel length of placeholder * factor)
				} else {
					//use user defined value
					fpDist = Number(curvedLinesOptions.fitPointDist);
				}

				for (var i = 0; i < points.length; i += ps) {

					var frontX;
					var backX;
					curX = i;
					curY = i + yPos;

					//add point X s
					frontX = points[curX] - fpDist;
					backX = points[curX] + fpDist;

					var factor = 2;
					while (frontX == points[curX] || backX == points[curX]) {
						//inside the ulp
						frontX = points[curX] - (fpDist * factor);
						backX = points[curX] + (fpDist * factor);
						factor++;
					}

					//add curve points
					xdata[j] = frontX;
					ydata[j] = points[curY];
					j++;

					xdata[j] = points[curX];
					ydata[j] = points[curY];
					j++;

					xdata[j] = backX;
					ydata[j] = points[curY];
					j++;
				}
			} else {
				//just use the datapoints
				for (var i = 0; i < points.length; i += ps) {
					curX = i;
					curY = i + yPos;

					xdata[j] = points[curX];
					ydata[j] = points[curY];
					j++;
				}
			}

			var n = xdata.length;

			var y2 = new Array();
			var delta = new Array();
			y2[0] = 0;
			y2[n - 1] = 0;
			delta[0] = 0;

			for (var i = 1; i < n - 1; ++i) {
				var d = (xdata[i + 1] - xdata[i - 1]);
				if (d == 0) {
					//point before current point and after current point need some space in between
					return [];
				}

				var s = (xdata[i] - xdata[i - 1]) / d;
				var p = s * y2[i - 1] + 2;
				y2[i] = (s - 1) / p;
				delta[i] = (ydata[i + 1] - ydata[i]) / (xdata[i + 1] - xdata[i]) - (ydata[i] - ydata[i - 1]) / (xdata[i] - xdata[i - 1]);
				delta[i] = (6 * delta[i] / (xdata[i + 1] - xdata[i - 1]) - s * delta[i - 1]) / p;
			}

			for (var j = n - 2; j >= 0; --j) {
				y2[j] = y2[j] * y2[j + 1] + delta[j];
			}

			//   xmax  - xmin  / #points
			var step = (xdata[n - 1] - xdata[0]) / (num - 1);

			var xnew = new Array;
			var ynew = new Array;
			var result = new Array;

			xnew[0] = xdata[0];
			ynew[0] = ydata[0];

			result.push(xnew[0]);
			result.push(ynew[0]);

			for ( j = 1; j < num; ++j) {
				//new x point (sampling point for the created curve)
				xnew[j] = xnew[0] + j * step;

				var max = n - 1;
				var min = 0;

				while (max - min > 1) {
					var k = Math.round((max + min) / 2);
					if (xdata[k] > xnew[j]) {
						max = k;
					} else {
						min = k;
					}
				}

				//found point one to the left and one to the right of generated new point
				var h = (xdata[max] - xdata[min]);

				if (h == 0) {
					//similar to above two points from original x data need some space between them
					return [];
				}

				var a = (xdata[max] - xnew[j]) / h;
				var b = (xnew[j] - xdata[min]) / h;

				ynew[j] = a * ydata[min] + b * ydata[max] + ((a * a * a - a) * y2[min] + (b * b * b - b) * y2[max]) * (h * h) / 6;

				result.push(xnew[j]);
				result.push(ynew[j]);
			}

			return result;
		}
		
		function hasInvalidParameters(curvedLinesOptions) {
			if (typeof curvedLinesOptions.fit != 'undefined' ||
			    typeof curvedLinesOptions.curvePointFactor != 'undefined' ||
			    typeof curvedLinesOptions.fitPointDist != 'undefined') {
			    	throw new Error("CurvedLines detected illegal parameters. The CurvedLines API changed with version 1.0.0 please check the options object.");
			    	return true;
			    }
			return false;
		}
		

	}//end init


	$.plot.plugins.push({
		init : init,
		options : options,
		name : 'curvedLines',
		version : '1.1.1'
	});

})(jQuery);

