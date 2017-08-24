'use strict';
/*jslint eqeq: true*/

Handlebars.registerHelper('sanitize', function(html) {
    // Strip the script tags from the html, and return it as a Handlebars.SafeString
    html = html.replace(/<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi, '');
    return new Handlebars.SafeString(html);
});

Handlebars.registerHelper('renderTextParam', function(param) {
    var result, type = 'text', idAtt = '';
    var paramType = param.type || param.schema.type || '';
    var isArray = paramType.toLowerCase() === 'array' || param.allowMultiple;
    var defaultValue = isArray && Array.isArray(param.default) ? param.default.join('\n') : param.default;

    var dataVendorExtensions = Object.keys(param).filter(function(property) {
        // filter X-data- properties
        return property.match(/^X-data-/i) !== null;
    }).reduce(function(result, property) {
        // remove X- from property name, so it results in html attributes like data-foo='bar'
        return result += ' ' + property.substring(2, property.length) + '=\'' + param[property] + '\'';
    }, '');

    if (typeof defaultValue === 'undefined') {
        defaultValue = '';
    }

    if(param.format && param.format === 'password') {
        type = 'password';
    }

    if(param.valueId) {
        idAtt = ' id=\'' + param.valueId + '\'';
    }

    if (typeof defaultValue === 'string' || defaultValue instanceof String) {
        defaultValue = defaultValue.replace(/'/g,'&apos;');
    }

    if(isArray) {
        result = '<textarea class=\'body-textarea' + (param.required ? ' required' : '') + '\' name=\'' + param.name + '\'' + idAtt + dataVendorExtensions;
        result += ' placeholder=\'Provide multiple values in new lines' + (param.required ? ' (at least one required).' : '.') + '\'>';
        result += defaultValue + '</textarea>';
    } else {
        var parameterClass = 'parameter';
        if(param.required) {
          parameterClass += ' required';
        }
        result = '<input class=\'' + parameterClass + '\' minlength=\'' + (param.required ? 1 : 0) + '\'';
        result += ' name=\'' + param.name +'\' placeholder=\'' + (param.required ? '(required)' : '') + '\'' + idAtt + dataVendorExtensions;
        result += ' type=\'' + type + '\' value=\'' + defaultValue + '\'/>';
    }
    return new Handlebars.SafeString(result);
});

Handlebars.registerHelper('ifCond', function (v1, operator, v2, options) {

    switch (operator) {
        case '==':
            return (v1 == v2) ? options.fn(this) : options.inverse(this);
        case '===':
            return (v1 === v2) ? options.fn(this) : options.inverse(this);
        case '<':
            return (v1 < v2) ? options.fn(this) : options.inverse(this);
        case '<=':
            return (v1 <= v2) ? options.fn(this) : options.inverse(this);
        case '>':
            return (v1 > v2) ? options.fn(this) : options.inverse(this);
        case '>=':
            return (v1 >= v2) ? options.fn(this) : options.inverse(this);
        case '&&':
            return (v1 && v2) ? options.fn(this) : options.inverse(this);
        case '||':
            return (v1 || v2) ? options.fn(this) : options.inverse(this);
        default:
            return options.inverse(this);
    }
});