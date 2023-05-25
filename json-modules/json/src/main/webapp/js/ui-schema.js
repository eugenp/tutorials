'use strict';

var app = angular.module('jsonforms-intro');
app.value('UISchema',
    {
        "type": "HorizontalLayout",
        "elements": [
            {
                "type": "Control",
                "scope": { "$ref": "#/properties/id" }
            },
            {
                "type": "Control",
                "scope": { "$ref": "#/properties/name" }
            },
            {
                "type": "Control",
                "scope": { "$ref": "#/properties/price" }
            },
        ]
    }
);
