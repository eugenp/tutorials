describe('svg context', function() {
  var object;

  this.timeout(20000);

  if ('createElementNS' in document) {

    it('is able to be loaded in a SVG file', function(done) {
      object = document.createElement('object');

      object.data = '../test/img/integration.svg';
      object.type = 'image/svg+xml';
      object.id = 'svgContext';

      object.onerror = function() {
        var arg = Array.prototype.slice.call(arguments).join(' ');
        try {
          if (arg.length) {
            expect(arg).to.be(undefined);
          }
        } catch (e) {
          done(e);
        }
      };

      object.onsuccess = function(modernizrRef) {
        expect(modernizrRef).to.not.be(undefined);
        done();
      };

      document.body.appendChild(object);
    });

    afterEach(function() {
      object.parentNode.removeChild(object);
    });
  }

});
