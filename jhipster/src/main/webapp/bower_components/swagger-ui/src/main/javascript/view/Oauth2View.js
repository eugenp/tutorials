'use strict';

SwaggerUi.Views.Oauth2View = Backbone.View.extend({
    events: {
        'change .oauth-scope': 'scopeChange'
    },

    template: Handlebars.templates.oauth2,

    render: function () {
        this.$el.html(this.template(this.model.toJSON()));

        return this;
    },

    scopeChange: function (e) {
        var val = $(e.target).prop('checked');
        var scope = $(e.target).data('scope');

        this.model.setScopes(scope, val);
    }
});