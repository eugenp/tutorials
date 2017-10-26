(function(jQuery) {
  jQuery(document)
      .ready(
          function() {

            function getLanguage() {
              // usamos el locale de momentjs
              moment.locale();
            }

            jQuery.validator.setDefaults({
              ignoreTitle : true
            });

            /**
             * Initialize jQuery Validator methods
             */
            /**
             * Date/time validation with format
             *
             * @name jQuery.validator.methods.number
             * @type Boolean
             */
            jQuery.validator.addMethod("dateformat", function(value, element, params) {
              if (this.optional(element)) {
                return true;
              }
              if (params === "DEFAULT") {
                return moment(value, moment.javaToMomentDateFormat()).isValid();
              } else {
                return moment(value, params, true).isValid();
              }
            }, "Please enter a correct date/time");

            /**
            * Replaces the standard number validation to support number with comma.
            *
            * @name jQuery.validator.methods.number
            * @type Boolean
            */
            jQuery.validator.methods.number = function(value, element) {
              return this.optional(element) || Inputmask.isValid(value, {
                alias : "numeric"
              });
            };

            /**
             * Replaces the standar min validation to support number with comma.
             *
             * @name jQuery.validator.methods.number
             * @type Boolean
             */
            jQuery.validator.methods.min = function(value, element, params) {
              var localizedValue = Inputmask.unmask(value, {
                alias : "numeric",
                unmaskAsNumber : true
              });
              return this.optional(element) || localizedValue >= params;
            };

            /**
             * Replaces the standar max validation to support number with comma.
             *
             * @name jQuery.validator.methods.number
             * @type Boolean
             */
            jQuery.validator.methods.max = function(value, element, params) {
              var localizedValue = Inputmask.unmask(value, {
                alias : "numeric",
                unmaskAsNumber : true
              });
              return this.optional(element) || localizedValue <= params;
            };

            /**
             * Replaces the standard range validation to support number with comma.
             *
             * @name jQuery.validator.methods.number
             * @type Boolean
             */
            jQuery.validator.methods.range = function(value, element, params) {
              var localizedValue = Inputmask.unmask(value, {
                alias : "numeric",
                unmaskAsNumber : true
              });
              return this.optional(element)
                  || (localizedValue >= params[0] && localizedValue <= params[1]);
            };

            /**
            * Return true if the field value matches the given RegExp
            *
            * The difference between `pattern` method is than this one
            * use parameter as expression literally (`pattern` includes
            * prefix and suffix).
            */
            $.validator.addMethod("regexp", function(value, element, param) {
              if (this.optional(element)) {
                return true;
              }
              if (typeof param === "string") {
                param = new RegExp(param);
              }
              return param.test(value);
            }, "Invalid format.");

            /**
             * jquery.inputmask rule: delegates on inputmask control
             */
            jQuery.validator.addMethod("inputmask", function(value, element, params) {
              if (this.optional(element)) {
                return true;
              }
              var $inputmask = jQuery(element);
              return $inputmask.inputmask && $inputmask.inputmask("isComplete");
            }, "Please enter a valid value.");

            // Form validation init
            jQuery("form.validate")
                .each(
                    function(index) {
                      var $form = $(this);

                      // see options at https://jqueryvalidation.org/documentation/
                      $form
                          .validate({
                            highlight : function(element) {
                              var $element = $(element);
                              // añadir marca error
                              $element.closest('.form-group').addClass('has-error has-feedback');
                              // añadir span con icono
                              var iconSpan = $element.parent().find('span.form-control-feedback');
                              if (!iconSpan.length) {
                                $element
                                    .after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
                              }
                            },
                            unhighlight : function(element) {
                              var $element = $(element);
                              // añadir marca error
                              $element.closest('.form-group').removeClass('has-error has-feedback');
                              // quitar span con icono
                              $element.parent().find('span.form-control-feedback.glyphicon-remove')
                                  .remove();
                              // limpiar errores
                              if ($element.parent('.input-group').length) {
                                $element.parent('.input-group').parent().find(
                                    'span.help-block[id=' + $element.attr('id') + '-error]')
                                    .remove();

                              } else {
                                $element.parent().find(
                                    'span.help-block[id=' + $element.attr('id') + '-error]')
                                    .remove();
                              }
                            },
                            errorElement : 'span',
                            errorClass : 'help-block',
                            errorPlacement : function(error, element) {
                              var $element = $(element);

                              var $previousErrors;
                              if ($element.parent('.input-group').length) {
                                 $previousErrors = $element.parent('.input-group').parent()
                                    .find('span.help-block[id=' + $element.attr('id') + '-error]');
                              } else {
                                 $previousErrors = $element.parent().find(
                                    'span.help-block[id=' + $element.attr('id') + '-error]');
                              }

                              if ($previousErrors.length === 1) {
                                $previousErrors.replaceWith(error);
                              } else {
                                if ($previousErrors.length > 1) {
                                  // mas de un error limpiamos
                                  $previousErrors.remove();
                                }
                                //  insertar error
                                if ($element.parent('.input-group').length) {
                                  error.insertAfter($element.parent());
                                } else {
                                  $element.parent().append(error);
                                }
                              }

                            }
                          });

                      // Iterate form inputs to set validation rules and messages
                      $form.find("input,textarea,select").each(function(index) {
                        var $input = $(this);
                        var data = $input.data();

                        // this input validation rules
                        var rules = {
                          required : data.required,
                          messages : {}
                        };
                        if (isNotNull(data.missing)) {
                          rules.messages.required = data.missing
                        }
                        if (isNotNull(data.invalid)) {
                          rules.messages.remote = data.invalid
                        }

                        // inputmaks
                        if ($input.hasClass("inputmask")) {
                          rules["inputmask"] = true;
                          if (isNotNull(data.invalid)) {
                            rules.messages.inputmask = data.invalid
                          }
                        }
                        // datetimepicker
                        if (isNotEmpty(data.dateformat)) {
                          rules["dateformat"] = moment.javaToMomentDateFormat(data.dateformat);
                          if (isNotNull(data.invalid)) {
                            rules.messages.dateformat = data.invalid
                          }
                          // datetimepicker without format
                        } else if ($input.hasClass("datetimepicker")) {
                          rules["dateformat"] = "DEFAULT";
                          if (isNotNull(data.invalid)) {
                            rules.messages.dateformat = data.invalid
                          }
                        }

                        $input.rules("add", rules);
                      });
                    });
          });
})(jQuery);


