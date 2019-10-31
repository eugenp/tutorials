/*!
{
  "name": "CSS Display table",
  "property": "displaytable",
  "caniuse": "css-table",
  "authors": ["scottjehl"],
  "tags": ["css"],
  "builderAliases": ["css_displaytable"],
  "notes": [{
    "name": "Detects for all additional table display values",
    "href": "http://pastebin.com/Gk9PeVaQ"
  }]
}
!*/
/* DOC
`display: table` and `table-cell` test. (both are tested under one name `table-cell` )
*/
define(['Modernizr', 'testStyles'], function(Modernizr, testStyles) {
  // If a document is in rtl mode this test will fail so we force ltr mode on the injeced
  // element https://github.com/Modernizr/Modernizr/issues/716
  testStyles('#modernizr{display: table; direction: ltr}#modernizr div{display: table-cell; padding: 10px}', function(elem) {
    var ret;
    var child = elem.childNodes;
    ret = child[0].offsetLeft < child[1].offsetLeft;
    Modernizr.addTest('displaytable', ret, {aliases: ['display-table']});
  }, 2);
});
