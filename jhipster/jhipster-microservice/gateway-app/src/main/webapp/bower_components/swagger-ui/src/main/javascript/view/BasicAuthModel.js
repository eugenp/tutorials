'use strict';

SwaggerUi.Models.BasicAuthModel = Backbone.Model.extend({
    defaults: {
        username: '',
        password: '',
        title: 'basic'
    },

    initialize: function () {
        this.on('change', this.validate);
    },

    validate: function () {
        var valid = !!this.get('password') && !!this.get('username');

        this.set('valid', valid);

        return valid;
    }
});