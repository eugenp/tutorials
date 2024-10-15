flot-axislabels: Axis Labels plugin for flot
============================================

Originally written by Xuan Luo.  Maintained by Mark Cote.

Contributions:

* Xuan Luo
* Mark Cote
* stdexcept
* Clemens Stolle
* Michael Haddon
* andig
* Alex Pinkney

[flot-axislabels](https://github.com/markrcote/flot-axislabels) provides
flot with the ability to label axes.  It supports any number of axes.   It
can render the labels with CSS transforms, in canvas, or with traditional
CSS positioning ("HTML" mode).  flot-axislabels attempts a graceful fallback
from CSS to canvas to HTML if some modes are not supported.  You can also
force a particular lesser mode (canvas or HTML).

In both CSS and canvas modes, the y-axis labels are rotated to face the
graph (90 degrees counter-clockwise for left-hand labels, and 90 degrees
clockwise for right-hand labels).  In HTML mode, y-axis labels are left
horizontal (warning: this takes up a lot of space).

In CSS and HTML modes, each axis label belongs to the classes "axisLabels"
and "[axisName]Label" (e.g. .xaxisLabel, .y2axisLabel, etc).  You can use
standard CSS properties to customize their appearance.

In canvas mode, you can set font size, family, and colour through flot
options (see below).


Example
-------

    $(function () {
        var options = {
            axisLabels: {
                show: true
            },
            xaxes: [{
                axisLabel: 'foo',
            }],
            yaxes: [{
                position: 'left',
                axisLabel: 'bar',
            }, {
                position: 'right',
                axisLabel: 'bleem'
            }]
        };

        $.plot($("#placeholder"),
               yourData,
               options);
        );
    });


Usage
-----

flot-axislabel adds an axisLabels object to the global options object.
It supports one option:

* show (bool): display all axis labels (default: true)

There are also several options added to the axis objects.  The two main ones
are

* axisLabel (string): the text you want displayed as the label
* axisLabelPadding (int): padding, in pixels, between the tick labels and the
  axis label (default: 2)

By default, if supported, flot-axislabels uses CSS transforms.  You can force
either canvas or HTML mode by setting axisLabelUseCanvas or axisLabelUseHtml,
respectively, to true.

Canvas mode supports several other options:

* axisLabelFontSizePixels (int): the size, in pixels, of the font (default: 14)
* axisLabelFontFamily (string): the font family of the font (default:
  sans-serif)
* axisLabelColour (string): the font colour (default: black)


Compatibility
-------------

flot-axislabels should work with recent versions of Firefox, Chrome, Opera,
and Safari.  It also works with IE 8 and 9.  The canvas option does *not*
seem to work with IE 8, even with excanvas.


License
-------

flot-axislabels is released under the terms of [the MIT License](http://www.opensource.org/licenses/MIT).
