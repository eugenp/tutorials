/*!
{
  "name": "Blob constructor",
  "property": "blobconstructor",
  "aliases": ["blob-constructor"],
  "builderAliases": ["blob_constructor"],
  "caniuse": "blobbuilder",
  "notes": [{
    "name": "W3C spec",
    "href": "https://w3c.github.io/FileAPI/#constructorBlob"
  }],
  "polyfills": ["blobjs"]
}
!*/
/* DOC
Detects support for the Blob constructor, for creating file-like objects of immutable, raw data.
*/
define(['Modernizr'], function(Modernizr) {
  Modernizr.addTest('blobconstructor', function() {
    try {
      return !!new Blob();
    } catch (e) {
      return false;
    }
  }, {
    aliases: ['blob-constructor']
  });
});
