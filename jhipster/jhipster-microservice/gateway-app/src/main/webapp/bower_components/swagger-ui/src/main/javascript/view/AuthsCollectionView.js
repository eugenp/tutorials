'use strict';

SwaggerUi.Views.AuthsCollectionView = Backbone.View.extend({

    initialize: function(opts) {
        this.options = opts || {};
        this.options.data = this.options.data || {};
        this.router = this.options.router;

        this.collection = new SwaggerUi.Collections.AuthsCollection(opts.data);

        this.$innerEl = $('<div>');
        this.authViews = [];
    },

    render: function () {
        this.collection.each(function (auth) {
            this.renderOneAuth(auth);
        }, this);

        this.$el.html(this.$innerEl.html() ? this.$innerEl : '');

        return this;
    },

    renderOneAuth: function (authModel) {
        var authViewEl, authView, authViewName;
        var type = authModel.get('type');

        if (type === 'apiKey') {
            authViewName = 'ApiKeyAuthView';
        } else if (type === 'basic' && this.$innerEl.find('.basic_auth_container').length === 0) {
            authViewName = 'BasicAuthView';
        } else if (type === 'oauth2') {
            authViewName = 'Oauth2View';
        }

        if (authViewName) {
            authView = new SwaggerUi.Views[authViewName]({model: authModel, router: this.router});
            authViewEl = authView.render().el;
            this.authViews.push(authView);
        }

        this.$innerEl.append(authViewEl);
    },

    highlightInvalid: function () {
        this.authViews.forEach(function (view) {
            view.highlightInvalid();
        }, this);
    }

});
