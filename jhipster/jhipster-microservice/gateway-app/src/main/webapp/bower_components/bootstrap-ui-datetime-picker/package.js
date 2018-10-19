Package.describe({
  name: 'hexencoded:bootstrap-ui-datetime-picker',
  version: '2.0.42',
  // Brief, one-line summary of the package.
  summary: 'AngularJs directive to use a date and/or time picker as a dropdown from an input',
  // URL to the Git repository containing the source code for this package.
  git: 'https://github.com/hexencoded/bootstrap-ui-datetime-picker.git',
  // By default, Meteor will default to using README.md for documentation.
  // To avoid submitting documentation, set this field to null.
  documentation: 'README.md'
});

Package.onUse(function(api) {
  api.versionsFrom('1.2.1');
  // api.use('ecmascript');
  // api.addFiles('bootstrap-ui-datetime-picker.js');

  // Dependencies
  api.use('angular:angular@1.4.0', 'client');
  api.use('angularui:angular-ui-bootstrap@1.1.0', 'client');

  api.addFiles('dist/datetime-picker.js', 'client'); // Files in use
});

Package.onTest(function(api) {
  api.use('ecmascript');
  api.use('tinytest');
  api.use('hexencoded:bootstrap-ui-datetime-picker');
  api.addFiles('bootstrap-ui-datetime-picker-tests.js');
});
