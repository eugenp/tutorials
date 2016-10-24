'use strict';

SwaggerUi.Views.PopupView = Backbone.View.extend({
    events: {
        'click .api-popup-cancel': 'cancelClick'
    },

    template: Handlebars.templates.popup,
    className: 'api-popup-dialog',

    selectors: {
        content: '.api-popup-content',
        main   : '#swagger-ui-container'
    },

    initialize: function(){
        this.$el.html(this.template(this.model));
    },

    render: function () {
        this.$(this.selectors.content).append(this.model.content);
        $(this.selectors.main).first().append(this.el);
        this.showPopup();

        return this;
    },

    showPopup: function () {
        this.$el.show();
    },

    cancelClick: function () {
        this.remove();
    }

});
