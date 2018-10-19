/*!
{
  "name": "CSS vh unit",
  "property": "cssvhunit",
  "caniuse": "viewport-units",
  "tags": ["css"],
  "builderAliases": ["css_vhunit"],
  "notes": [{
    "name": "Related Modernizr Issue",
    "href": "https://github.com/Modernizr/Modernizr/issues/572"
  },{
    "name": "Similar JSFiddle",
    "href": "https://jsfiddle.net/FWeinb/etnYC/"
  }]
}
!*/
define(['Modernizr', 'testStyles'], function(Modernizr, testStyles) {
  testStyles('#modernizr { height: 50vh; }', function(elem) {
    var height = parseInt(window.innerHeight / 2, 10);
    var compStyle = parseInt((window.getComputedStyle ?
                              getComputedStyle(elem, null) :
                              elem.currentStyle).height, 10);
    Modernizr.addTest('cssvhunit', compStyle == height);
  });
});
