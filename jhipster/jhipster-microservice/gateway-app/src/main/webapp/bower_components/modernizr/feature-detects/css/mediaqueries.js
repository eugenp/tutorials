/*!
{
  "name": "CSS Media Queries",
  "caniuse": "css-mediaqueries",
  "property": "mediaqueries",
  "tags": ["css"],
  "builderAliases": ["css_mediaqueries"]
}
!*/
define(['Modernizr', 'mq'], function(Modernizr, mq) {
  Modernizr.addTest('mediaqueries', mq('only all'));
});
