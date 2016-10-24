describe('prefixed autobind', function() {
  var rAFName;

  // quick sniff to find the local rAF prefixed name.
  var vendors = ['r', 'msR', 'mozR', 'webkitR', 'oR'];
  _.forEach(vendors, function(vendor) {
    rAFName = rAFName || (window[vendor + 'equestAnimationFrame'] && vendor + 'equestAnimationFrame');
  });

  if (rAFName) {
    // rAF returns a function
    it('Modernizr.prefixed("requestAnimationFrame", window) returns a function', function() {
      expect(Modernizr.prefixed('requestAnimationFrame', window)).to.be.a('function');
    });

    // unless we false it to a string
    it('Modernizr.prefixed("requestAnimationFrame", window, false) returns a string (the prop name)', function() {
      expect(Modernizr.prefixed('requestAnimationFrame', window, false)).to.be(rAFName);
    });
  }

  if (document.body.webkitMatchesSelector || document.body.mozMatchesSelector) {

    var fn = Modernizr.prefixed('matchesSelector', HTMLElement.prototype, document.body);

    //returns function
    it('Modernizr.prefixed("matchesSelector", HTMLElement.prototype, document.body) returns a function', function() {
      expect(fn).to.be.a('function');
    });

    // fn scoping
    it('Modernizr.prefixed("matchesSelector", HTMLElement.prototype, document.body) is scoped to the body', function() {
      expect(fn('body')).to.be(true);
    });
  }

  // Webkit only: are there other objects that are prefixed?
  if (window.webkitNotifications) {
    // should be an object.
    it('Modernizr.prefixed("Notifications") returns an object', function() {
      expect(Modernizr.prefixed('Notifications', window)).to.be.an('object');
    });
  }

  // Webkit only:
  if (!_.isUndefined(document.webkitIsFullScreen)) {
    // boolean
    it('Modernizr.prefixed("isFullScreen") returns a boolean', function() {
      expect(Modernizr.prefixed('isFullScreen', document)).to.be.a('boolean');
    });
  }



  // Moz only:
  if (!_.isUndefined(document.mozFullScreen)) {
    // boolean

    it('Modernizr.prefixed("isFullScreen") returns a boolean', function() {
      expect(Modernizr.prefixed('fullScreen', document)).to.be.a('boolean');
    });
  }


  // Webkit-only.. takes advantage of Webkit's mixed case of prefixes
  if (document.body.style.WebkitAnimation) {
    // string

    it('Modernizr.prefixed("animation", document.body.style) returns value of that, as a string', function() {
      expect(Modernizr.prefixed('animation', document.body.style)).to.be.a('string');
    });

    it('Modernizr.prefixed("animation", document.body.style, false) returns the name of the property: webkitAnimation', function() {
      expect(Modernizr.prefixed('animation', document.body.style, false)).to.equal('webkitAnimation');
    });
  }

  it('Modernizr.prefixed("doSomethingAmazing$#$", window) : Gobbledygook with prefixed(str,obj) returns false', function() {
    expect(Modernizr.prefixed('doSomethingAmazing$#$', window)).to.be(false);
  });

  it('Modernizr.prefixed("doSomethingAmazing$#$", window) : Gobbledygook with prefixed(str,obj, scope) returns false', function() {
    expect(Modernizr.prefixed('doSomethingAmazing$#$', window, document.body)).to.be(false);
  });

  it('Modernizr.prefixed("doSomethingAmazing$#$", window) : Gobbledygook with prefixed(str,obj, false) returns false', function() {
    expect(Modernizr.prefixed('doSomethingAmazing$#$', window, false)).to.be(false);
  });
});
