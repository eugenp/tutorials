/* Flot plugin for stacking data sets rather than overlyaing them.

Copyright (c) 2007-2014 IOLA and Ole Laursen.
Licensed under the MIT license.

The plugin assumes the data is sorted on x (or y if stacking horizontally).
For line charts, it is assumed that if a line has an undefined gap (from a
null point), then the line above it should have the same gap - insert zeros
instead of "null" if you want another behaviour. This also holds for the start
and end of the chart. Note that stacking a mix of positive and negative values
in most instances doesn't make sense (so it looks weird).

Two or more series are stacked when their "stack" attribute is set to the same
key (which can be any number or string or just "true"). To specify the default
stack, you can set the stack option like this:

    series: {
        stack: null/false, true, or a key (number/string)
    }

You can also specify it for a single series, like this:

    $.plot( $("#placeholder"), [{
        data: [ ... ],
        stack: true
    }])

The stacking order is determined by the order of the data series in the array
(later series end up on top of the previous).

Internally, the plugin modifies the datapoints in each series, adding an
offset to the y value. For line series, extra data points are inserted through
interpolation. If there's a second y value, it's also adjusted (e.g for bar
charts or filled areas).

*/

/**
 * Patched version as per https://github.com/flot/flot/issues/326
 */
(function ($) {
    var options = {
        series: { stack: null } // or number/string
    };

    function init(plot) {

        // will be built up dynamically as a hash from x-value to
        var stackBases = {};

        function stackData(plot, s, datapoints) {
            if (s.stack == null || s.stack === false)
                return;

            var newPoints = [];
    
            for (var i=0; i <  datapoints.points.length; i += 3) {

                if (!stackBases[datapoints.points[i]]) {
                    stackBases[datapoints.points[i]] = 0;
                }

                // note that the values need to be turned into absolute y-values.
                // in other words, if you were to stack (x, y1), (x, y2), and (x, y3), (each from different series, which is where stackBases comes in),
                // you'd want the new points to be (x, y1, 0), (x, y1+y2, y1), (x,y1+y2+y3, y1+y2)
                // generally, (x, thisValue + (base up to this point), + (base up tothis point))
                newPoints[i] = datapoints.points[i];
                newPoints[i+1] = datapoints.points[i+1] + stackBases[datapoints.points[i]];
                newPoints[i+2] = stackBases[datapoints.points[i]];
                stackBases[datapoints.points[i]] += datapoints.points[i+1];
            }
            datapoints.points = newPoints;
        }
        plot.hooks.processDatapoints.push(stackData);
    }

    $.plot.plugins.push({
        init: init,
        options: options,
        name: 'stack',
        version: '1.2-patch-326'
    });
})(jQuery);
