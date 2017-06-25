'use strict';

SwaggerUi.Views.ContentTypeView = Backbone.View.extend({
  initialize: function() {},

  render: function(){
  	this.model.contentTypeId = 'ct' + Math.random();
    $(this.el).html(Handlebars.templates.content_type(this.model));
    return this;
  }
});