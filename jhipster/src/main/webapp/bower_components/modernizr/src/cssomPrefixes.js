define(['ModernizrProto', 'omPrefixes'], function(ModernizrProto, omPrefixes) {
  var cssomPrefixes = (ModernizrProto._config.usePrefixes ? omPrefixes.split(' ') : []);
  ModernizrProto._cssomPrefixes = cssomPrefixes;
  return cssomPrefixes;
});
