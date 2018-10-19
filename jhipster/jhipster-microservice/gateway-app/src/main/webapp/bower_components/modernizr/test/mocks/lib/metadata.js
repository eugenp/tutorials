define([], [
  {
    'name': 'Box Sizing',
    'property': 'boxsizing',
    'caniuse': 'css3-boxsizing',
    'polyfills': [],
    'tags': [],
    'builderAliases': [ ],
    'notes': [{
      'name': 'MDN Docs',
      'href': 'http://developer.mozilla.org/en/CSS/box-sizing'
    }],
    'doc': '<p>Detects support for the ability to control the css box model</p>\n',
    'deps': [
      'testAllProps'
    ],
    'path': './feature-detects/css/boxsizing.js',
    'amdPath': 'test/css/boxsizing',
    'async': false,
    'warnings': [],
    'cssclass': 'boxsizing',
    'authors': [],
    'knownBugs': []
  }, {
    'name': 'createElement with Attributes',
    'property': [
      'createelementattrs',
      'createelement-attrs'
    ],
    'tags': [
      'dom'
    ],
    'builderAliases': [
      'dom_createElement_attrs'
    ],
    'authors': [
      'James A. Rosen'
    ],
    'notes': [
      {
        'name': 'Related Github Issue',
        'href': 'https://github.com/Modernizr/Modernizr/issues/258'
      }
    ],
    'doc': null,
    'deps': [
      'createElement'
    ],
    'path': './feature-detects/dom/createElement-attrs.js',
    'amdPath': 'test/dom/createElement-attrs',
    'polyfills': [],
    'async': false,
    'warnings': [],
    'caniuse': null,
    'cssclass': [
      'createelementattrs',
      'createelement-attrs'
    ],
    'knownBugs': []
  }
]);
