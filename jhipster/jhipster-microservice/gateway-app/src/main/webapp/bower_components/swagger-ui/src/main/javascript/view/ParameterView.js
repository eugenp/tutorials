'use strict';

SwaggerUi.Views.ParameterView = Backbone.View.extend({
  events: {
    'change [name=parameterContentType]' : 'toggleParameterSnippet'
  },

  initialize: function(){
    Handlebars.registerHelper('isArray', function(param, opts) {
      var paramType = param.type && param.type.toLowerCase();
      if (paramType === 'array' || param.allowMultiple) {
        return opts.fn(this);
      } else {
        return opts.inverse(this);
      }
    });
  },

  render: function() {
    var type = this.model.type || this.model.dataType;
    var modelType = this.model.modelSignature.type;
    var modelDefinitions = this.model.modelSignature.definitions;
    var schema = this.model.schema || {};
    var consumes = this.model.consumes || [];
    var sampleJSON, signatureView;


    if (typeof type === 'undefined') {
      if (schema.$ref) {
        var ref = schema.$ref;
        if (ref.indexOf('#/definitions/') === 0) {
          type = ref.substring('#/definitions/'.length);
        } else {
          type = ref;
        }
      }
    }

    this.model.type = type;
    this.model.paramType = this.model.in || this.model.paramType;
    this.model.isBody = this.model.paramType === 'body' || this.model.in === 'body';
    this.model.isFile = type && type.toLowerCase() === 'file';

    // Allow for default === false
    if(typeof this.model.default === 'undefined') {
      this.model.default = this.model.defaultValue;
    }

    this.model.hasDefault = (typeof this.model.default !== 'undefined');
    this.model.valueId = 'm' + this.model.name + Math.random();

    if (this.model.allowableValues) {
      this.model.isList = true;
    }

    var isXML = this.contains(consumes, 'xml');
    var isJSON = isXML ? this.contains(consumes, 'json') : true;
    sampleJSON = SwaggerUi.partials.signature.createParameterJSONSample(modelType, modelDefinitions);

    var template = this.template();
    $(this.el).html(template(this.model));

    var signatureModel = {
      sampleJSON: isJSON ? sampleJSON : false,
      sampleXML: sampleJSON && isXML ? SwaggerUi.partials.signature.createXMLSample('', schema, modelDefinitions, true) : false,
      isParam: true,
      signature: SwaggerUi.partials.signature.getParameterModelSignature(modelType, modelDefinitions),
      defaultRendering: this.model.defaultRendering
    };

    if (sampleJSON) {
      signatureView = new SwaggerUi.Views.SignatureView({model: signatureModel, tagName: 'div'});
      $('.model-signature', $(this.el)).append(signatureView.render().el);
    }
    else {
      $('.model-signature', $(this.el)).html(this.model.signature);
    }

    var isParam = false;

    if( this.options.swaggerOptions.jsonEditor && this.model.isBody && this.model.schema){
      var $self = $(this.el);
      this.model.jsonEditor =
        /* global JSONEditor */
        new JSONEditor($('.editor_holder', $self)[0],
                       {schema: this.model.schema, startval : this.model.default,
                        ajax:true,
                        disable_properties:true,
                        disable_edit_json:true,
                        iconlib: 'swagger' });
      // This is so that the signature can send back the sample to the json editor
      // TODO: SignatureView should expose an event "onSampleClicked" instead
      signatureModel.jsonEditor = this.model.jsonEditor;
      $('.body-textarea', $self).hide();
      $('.editor_holder', $self).show();
      $('.parameter-content-type', $self)
        .change(function(e){
            if(e.target.value === 'application/xml'){
              $('.body-textarea', $self).show();
              $('.editor_holder', $self).hide();
              this.model.jsonEditor.disable();
            }
            else {
              $('.body-textarea', $self).hide();
              $('.editor_holder', $self).show();
              this.model.jsonEditor.enable();
            }
        });
      }


    if (this.model.isBody) {
      isParam = true;
    }

    var contentTypeModel = {
      isParam: isParam
    };

    contentTypeModel.consumes = this.model.consumes;

    if (isParam) {
      var parameterContentTypeView = new SwaggerUi.Views.ParameterContentTypeView({model: contentTypeModel});
      $('.parameter-content-type', $(this.el)).append(parameterContentTypeView.render().el);
      this.toggleParameterSnippet();
    }

    else {
      var responseContentTypeView = new SwaggerUi.Views.ResponseContentTypeView({model: contentTypeModel});
      $('.response-content-type', $(this.el)).append(responseContentTypeView.render().el);
      this.toggleResponseSnippet();
    }

    return this;
  },

  contains: function (consumes, type) {
    return consumes.filter(function (val) {
      if (val.indexOf(type) > -1) {
        return true;
      }
    }).length;
  },

  toggleParameterSnippet: function () {
    var contentType = this.$('[name=parameterContentType]').val();

    this.toggleSnippet(contentType);
  },

  toggleResponseSnippet: function () {
    var contentEl = this.$('[name=responseContentType]');

    if (!contentEl.length) { return; }

    this.toggleSnippet(contentEl.val());
  },

  toggleSnippet: function (type) {
    type = type || '';
    if (type.indexOf('xml') > -1) {
      this.$('.snippet_xml').show();
      this.$('.snippet_json').hide();
    } else {
      this.$('.snippet_json').show();
      this.$('.snippet_xml').hide();
    }
  },

  // Return an appropriate template based on if the parameter is a list, readonly, required
  template: function(){
    if (this.model.isList) {
      return Handlebars.templates.param_list;
    } else {
      if (this.options.readOnly) {
        if (this.model.required) {
          return Handlebars.templates.param_readonly_required;
        } else {
          return Handlebars.templates.param_readonly;
        }
      } else {
        if (this.model.required) {
          return Handlebars.templates.param_required;
        } else {
          return Handlebars.templates.param;
        }
      }
    }
  }
});
