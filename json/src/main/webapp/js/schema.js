'use strict';

var app = angular.module('jsonforms-intro');
app.value('Schema',
	{
	    "$schema": "http://json-schema.org/draft-04/schema#",
	    "title": "Product",
	    "description": "A product from the catalog",
	    "type": "object",
	    "properties": {
	        "id": {
	            "description": "The unique identifier for a product",
	            "type": "integer"
	        },
	        "name": {
	            "description": "Name of the product",
	            "type": "string"
	        },
	        "price": {
	            "type": "number",
	            "minimum": 0,
	            "exclusiveMinimum": true
	        }
	    },
	    "required": ["id", "name", "price"]
	}
);
