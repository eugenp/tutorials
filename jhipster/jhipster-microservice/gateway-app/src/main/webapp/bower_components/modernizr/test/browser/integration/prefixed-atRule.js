describe('prefixed @rule', function() {
  it('(Almost) Everyone supports import', function() {
    expect(['@import', undefined]).to.contain(Modernizr.prefixed('@import'));
  });

  it('Nobody supports @penguin', function() {
    expect(!!Modernizr.prefixed('@penguin')).to.equal(false);
  });
});
