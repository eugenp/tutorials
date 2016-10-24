'use strict';

/* global redirect_uri:true */
/* global clientId */
/* global scopeSeparator */
/* global additionalQueryStringParams */
/* global clientSecret */
/* global onOAuthComplete */
/* global realm */
/*jshint unused:false*/

SwaggerUi.Views.AuthView = Backbone.View.extend({
    events: {
        'click .auth_submit__button': 'authorizeClick',
        'click .auth_logout__button': 'logoutClick'
    },

    tpls: {
        main: Handlebars.templates.auth_view
    },

    selectors: {
        innerEl: '.auth_inner',
        authBtn: '.auth_submit__button'
    },

    initialize: function(opts) {
        this.options = opts || {};
        opts.data = opts.data || {};
        this.router = this.options.router;

        this.authsCollectionView = new SwaggerUi.Views.AuthsCollectionView({data: opts.data});

        this.$el.html(this.tpls.main({
            isLogout: this.authsCollectionView.collection.isAuthorized(),
            isAuthorized: this.authsCollectionView.collection.isPartiallyAuthorized()
        }));
        this.$innerEl = this.$(this.selectors.innerEl);
        this.isLogout = this.authsCollectionView.collection.isPartiallyAuthorized();
    },

    render: function () {
        this.$innerEl.html(this.authsCollectionView.render().el);

        return this;
    },

    authorizeClick: function (e) {
        e.preventDefault();
        e.stopPropagation();

        if (this.authsCollectionView.collection.isValid()) {
            this.authorize();
        } else {
            this.authsCollectionView.highlightInvalid();
        }
    },

    authorize: function () {
        this.authsCollectionView.collection.forEach(function (auth) {
            var keyAuth, basicAuth;
            var type = auth.get('type');

            if (type === 'apiKey') {
                keyAuth = new SwaggerClient.ApiKeyAuthorization(
                    auth.get('name'),
                    auth.get('value'),
                    auth.get('in')
                );

                this.router.api.clientAuthorizations.add(auth.get('title'), keyAuth);
            } else if (type === 'basic') {
                basicAuth = new SwaggerClient.PasswordAuthorization(auth.get('username'), auth.get('password'));
                this.router.api.clientAuthorizations.add(auth.get('title'), basicAuth);
            } else if (type === 'oauth2') {
                this.handleOauth2Login(auth);
            }
        }, this);

        this.router.load();
    },

    logoutClick: function (e) {
        e.preventDefault();

        this.authsCollectionView.collection.forEach(function (auth) {
            window.swaggerUi.api.clientAuthorizations.remove(auth.get('title'));
        });

        this.router.load();
    },

    // taken from lib/swagger-oauth.js
    handleOauth2Login: function (auth) {
        var host = window.location;
        var pathname = location.pathname.substring(0, location.pathname.lastIndexOf('/'));
        var defaultRedirectUrl = host.protocol + '//' + host.host + pathname + '/o2c.html';
        var redirectUrl = window.oAuthRedirectUrl || defaultRedirectUrl;
        var url = null;
        var scopes = _.map(auth.get('scopes'), function (scope) {
            return scope.scope;
        });
        var state, dets, ep;
        window.OAuthSchemeKey = auth.get('title');

        window.enabledScopes = scopes;
        var flow = auth.get('flow');

        if(auth.get('type') === 'oauth2' && flow && (flow === 'implicit' || flow === 'accessCode')) {
            dets = auth.attributes;
            url = dets.authorizationUrl + '?response_type=' + (flow === 'implicit' ? 'token' : 'code');
            window.swaggerUi.tokenName = dets.tokenName || 'access_token';
            window.swaggerUi.tokenUrl = (flow === 'accessCode' ? dets.tokenUrl : null);
            state = window.OAuthSchemeKey;
        }
        else if(auth.get('type') === 'oauth2' && flow && (flow === 'application')) {
            dets = auth.attributes;
            window.swaggerUi.tokenName = dets.tokenName || 'access_token';
            this.clientCredentialsFlow(scopes, dets.tokenUrl, window.OAuthSchemeKey);
            return;
        }
        else if(auth.get('grantTypes')) {
            // 1.2 support
            var o = auth.get('grantTypes');
            for(var t in o) {
                if(o.hasOwnProperty(t) && t === 'implicit') {
                    dets = o[t];
                    ep = dets.loginEndpoint.url;
                    url = dets.loginEndpoint.url + '?response_type=token';
                    window.swaggerUi.tokenName = dets.tokenName;
                }
                else if (o.hasOwnProperty(t) && t === 'accessCode') {
                    dets = o[t];
                    ep = dets.tokenRequestEndpoint.url;
                    url = dets.tokenRequestEndpoint.url + '?response_type=code';
                    window.swaggerUi.tokenName = dets.tokenName;
                }
            }
        }

        redirect_uri = redirectUrl;

        url += '&redirect_uri=' + encodeURIComponent(redirectUrl);
        url += '&realm=' + encodeURIComponent(realm);
        url += '&client_id=' + encodeURIComponent(clientId);
        url += '&scope=' + encodeURIComponent(scopes.join(scopeSeparator));
        url += '&state=' + encodeURIComponent(state);
        for (var key in additionalQueryStringParams) {
            url += '&' + key + '=' + encodeURIComponent(additionalQueryStringParams[key]);
        }

        window.open(url);
    },

    // taken from lib/swagger-oauth.js
    clientCredentialsFlow: function (scopes, tokenUrl, OAuthSchemeKey) {
        var params = {
            'client_id': clientId,
            'client_secret': clientSecret,
            'scope': scopes.join(' '),
            'grant_type': 'client_credentials'
        };
        $.ajax({
            url : tokenUrl,
            type: 'POST',
            data: params,
            success: function (data)
            {
                onOAuthComplete(data, OAuthSchemeKey);
            },
            error: function ()
            {
                onOAuthComplete('');
            }
        });
    }

});
