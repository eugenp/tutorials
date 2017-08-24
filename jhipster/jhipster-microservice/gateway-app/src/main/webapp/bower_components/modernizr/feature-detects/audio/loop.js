/*!
{
  "name": "Audio Loop Attribute",
  "property": "audioloop",
  "tags": ["audio", "media"]
}
!*/
/* DOC
Detects if an audio element can automatically restart, once it has finished
*/
define(['Modernizr', 'createElement'], function(Modernizr, createElement) {
  Modernizr.addTest('audioloop', 'loop' in createElement('audio'));
});
