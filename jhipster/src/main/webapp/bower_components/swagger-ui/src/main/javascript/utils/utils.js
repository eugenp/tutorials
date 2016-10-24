'use strict';

window.SwaggerUi.utils = {
    parseSecurityDefinitions: function (security) {
        var auths = Object.assign({}, window.swaggerUi.api.authSchemes || window.swaggerUi.api.securityDefinitions);
        var oauth2Arr = [];
        var authsArr = [];
        var scopes = [];
        var utils = window.SwaggerUi.utils;

        if (!Array.isArray(security)) { return null; }

        security.forEach(function (item) {
            var singleSecurity = {};
            var singleOauth2Security = {};

            for (var key in item) {
                if (Array.isArray(item[key])) {
                    if (!auths[key]) { continue; }
                    auths[key] = auths[key] || {};
                    if (auths[key].type === 'oauth2') {
                        singleOauth2Security[key] = Object.assign({}, auths[key]);
                        singleOauth2Security[key].scopes = Object.assign({}, auths[key].scopes);
                        for (var i in singleOauth2Security[key].scopes) {
                            if (item[key].indexOf(i) < 0) {
                                delete singleOauth2Security[key].scopes[i];
                            }
                        }
                        singleOauth2Security[key].scopes = utils.parseOauth2Scopes(singleOauth2Security[key].scopes);
                        scopes = _.merge(scopes, singleOauth2Security[key].scopes);
                    } else {
                        singleSecurity[key] = Object.assign({}, auths[key]);
                    }
                } else {
                    if (item[key].type === 'oauth2') {
                        singleOauth2Security[key] = Object.assign({}, item[key]);
                        singleOauth2Security[key].scopes = utils.parseOauth2Scopes(singleOauth2Security[key].scopes);
                        scopes = _.merge(scopes, singleOauth2Security[key].scopes);
                    } else {
                        singleSecurity[key] = item[key];
                    }
                }
            }

            if (!_.isEmpty(singleSecurity)) {
                authsArr.push(singleSecurity);
            }

            if (!_.isEmpty(singleOauth2Security)){
                oauth2Arr.push(singleOauth2Security);
            }
        });

        return {
            auths : authsArr,
            oauth2: oauth2Arr,
            scopes: scopes
        };
    },

    parseOauth2Scopes: function (data) {
        var scopes = Object.assign({}, data);
        var result = [];
        var key;

        for (key in scopes) {
            result.push({scope: key, description: scopes[key]});
        }

        return result;
    }
};