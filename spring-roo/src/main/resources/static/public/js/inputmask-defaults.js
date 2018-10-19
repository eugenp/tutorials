(function(jQuery) {
	jQuery(document).ready(function() {

		/*
		Inputmask.extendDefaults({
			'autoUnmask' : true
		});
		*/

		jQuery(".inputmask").each(function(index) {
			var $input = jQuery(this);
			var options = {
				removeMaskOnSubmit : true
			};

			var pattern = $input.attr("data-inputmask-mask");
			if (pattern) {
				options.mask = pattern;
			} else {
				var alias = $input.attr("data-inputmask-alias");
				if (alias) {
					options.alias = alias;
				} else {
					throw "missing input initialization value on (id='"+this.id+"' name='"+this.name+"')";
				}
			}
			$input.inputmask(options);
		});
	});
})(jQuery);