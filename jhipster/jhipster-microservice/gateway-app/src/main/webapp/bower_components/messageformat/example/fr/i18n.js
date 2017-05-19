(function(G) {
var pluralFuncs = {
  fr: function (n, ord) {
    if (ord) return (n == 1) ? 'one' : 'other';
    return (n >= 0 && n < 2) ? 'one' : 'other';
  }
};
var fmt = {};
var number = function (value, offset) {
  if (isNaN(value)) throw new Error("'" + value + "' isn't a number.");
  return value - (offset || 0);
};
var plural = function (value, offset, lcfunc, data, isOrdinal) {
  if ({}.hasOwnProperty.call(data, value)) return data[value]();
  if (offset) value -= offset;
  var key = lcfunc(value, isOrdinal);
  if (key in data) return data[key]();
  return data.other();
};
var select = function (value, data) {
  if ({}.hasOwnProperty.call(data, value)) return data[value]();
  return data.other()
};

G.i18n = {
  colors: {
    red: function(d) { return "rouge"; },
    blue: function(d) { return "bleu"; },
    green: function(d) { return "vert"; }
  },
  "sub/folder/plural": {
    test: function(d) { return plural(d.NUM, 0, pluralFuncs.fr, { one: function() { return "Votre message se trouve";}, other: function() { return "Vos " + number(d.NUM) + " messages se trouvent";} }) + " ici."; }
  }
}
})(this);
