Package.describe({
  name: 'gsklee:ngstorage',
  version: '0.3.10',
  summary: 'ngStorage package for Meteor',
  git: 'https://github.com/gsklee/ngStorage',
  documentation: 'README.md'
});

Package.onUse(function(api) {
  api.versionsFrom('METEOR@0.9.0.1');
  api.use('urigo:angular@0.8.4', 'client');
  api.addFiles('ngStorage.js', 'client');
});
