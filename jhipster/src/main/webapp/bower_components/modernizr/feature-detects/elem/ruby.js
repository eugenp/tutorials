/*!
{
  "name": "ruby, rp, rt Elements",
  "caniuse": "ruby",
  "property": "ruby",
  "tags": ["elem"],
  "builderAliases": ["elem_ruby"],
  "authors": ["Cătălin Mariș"],
  "notes": [{
    "name": "WHATWG Specification",
    "href": "https://html.spec.whatwg.org/multipage/semantics.html#the-ruby-element"
  }]
}
!*/
define(['Modernizr', 'createElement', 'docElement'], function(Modernizr, createElement, docElement) {
  Modernizr.addTest('ruby', function() {

    var ruby = createElement('ruby');
    var rt = createElement('rt');
    var rp = createElement('rp');
    var displayStyleProperty = 'display';
    // 'fontSize' - because it`s only used for IE6 and IE7
    var fontSizeStyleProperty = 'fontSize';

    ruby.appendChild(rp);
    ruby.appendChild(rt);
    docElement.appendChild(ruby);

    // browsers that support <ruby> hide the <rp> via "display:none"
    if (getStyle(rp, displayStyleProperty) == 'none' ||                                                        // for non-IE browsers
         // but in IE browsers <rp> has "display:inline" so, the test needs other conditions:
         getStyle(ruby, displayStyleProperty) == 'ruby' && getStyle(rt, displayStyleProperty) == 'ruby-text' || // for IE8+
         getStyle(rp, fontSizeStyleProperty) == '6pt' && getStyle(rt, fontSizeStyleProperty) == '6pt') {       // for IE6 & IE7

      cleanUp();
      return true;

    } else {
      cleanUp();
      return false;
    }

    function getStyle(element, styleProperty) {
      var result;

      if (window.getComputedStyle) {     // for non-IE browsers
        result = document.defaultView.getComputedStyle(element, null).getPropertyValue(styleProperty);
      } else if (element.currentStyle) { // for IE
        result = element.currentStyle[styleProperty];
      }

      return result;
    }

    function cleanUp() {
      docElement.removeChild(ruby);
      // the removed child node still exists in memory, so ...
      ruby = null;
      rt = null;
      rp = null;
    }

  });

});
