describe('bools', function() {
  it('all properties are lower case', function() {
    _.every(Modernizr, function(result, name) {
      return expect(name).to.not.match(/[A-Z]/);
    });
  });

  describe('everythings ship shape', function() {
    _.chain(Modernizr)
      .keys()
      .filter()
      .sort()
      .forEach(function(name) {
        var result = Modernizr[name];
        if (name === 'input' || name === 'inputtypes') {
          return;
        }

        it(name + ' is a boolean value or Boolean object', function() {
          expect(
            result instanceof Boolean ||
            result === true ||
            result === false
          ).to.be(true);
        });
      })
      .value();
  });

});
