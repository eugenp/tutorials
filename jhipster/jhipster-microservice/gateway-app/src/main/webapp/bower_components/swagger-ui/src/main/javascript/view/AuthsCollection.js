'use strict';

SwaggerUi.Collections.AuthsCollection = Backbone.Collection.extend({
    constructor: function() {
        var args = Array.prototype.slice.call(arguments);

        args[0] = this.parse(args[0]);

        Backbone.Collection.apply(this, args);
    },

    add: function (model) {
        var args = Array.prototype.slice.call(arguments);

        if (Array.isArray(model)) {
            args[0] = _.map(model, function(val) {
                return this.handleOne(val);
            }, this);
        } else {
            args[0] = this.handleOne(model);
        }

        Backbone.Collection.prototype.add.apply(this, args);
    },

    handleOne: function (model) {
        var result = model;

        if (! (model instanceof Backbone.Model) ) {
            switch (model.type) {
                case 'oauth2':
                    result = new SwaggerUi.Models.Oauth2Model(model);
                    break;
                case 'basic':
                    result = new SwaggerUi.Models.BasicAuthModel(model);
                    break;
                case 'apiKey':
                    result = new SwaggerUi.Models.ApiKeyAuthModel(model);
                    break;
                default:
                    result = new Backbone.Model(model);
            }
        }

        return result;
    },

    isValid: function () {
        var valid = true;

        this.models.forEach(function(model) {
            if (!model.validate()) {
                valid = false;
            }
        });

        return valid;
    },

    isAuthorized: function () {
        return this.length === this.where({ isLogout: true }).length;
    },

    isPartiallyAuthorized: function () {
        return this.where({ isLogout: true }).length > 0;
    },

    parse: function (data) {
        var authz = Object.assign({}, window.swaggerUi.api.clientAuthorizations.authz);

        return _.map(data, function (auth, name) {
            var isBasic = authz[name] && auth.type === 'basic' && authz[name].username && authz[name].password;

            _.extend(auth, {
                title: name
            });

            if (authz[name] || isBasic) {
                _.extend(auth, {
                    isLogout: true,
                    value: isBasic ? undefined : authz[name].value,
                    username: isBasic ? authz[name].username : undefined,
                    password: isBasic ? authz[name].password : undefined,
                    valid: true
                });
            }

            return auth;
        });
    }
});