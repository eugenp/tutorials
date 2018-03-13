/**
 * This jQuery plugin displays pagination links inside the selected elements.
 *
 * @author Gabriel Birke (birke *at* d-scribe *dot* de)
 * @version 1.1
 * @param {int} maxentries Number of entries to paginate
 * @param {Object} opts Several options (see README for documentation)
 * @return {Object} jQuery Object
 */
/*
 * http://www.cnblogs.com/knowledgesea/archive/2013/01/03/2841554.html
 */
/*function pageselectCallback(page_id, jq) {
        //alert(page_id); 
        //return false; ajax prevent-page-forward
        return true;
    }*/
/* «1...9 10 11 (current) 12...17»
		$("#listPagination").pagination(100, {
            callback: pageselectCallback,//PageCallback() 
            prev_text: "Previous",
            next_text: "Next",
            items_per_page: 6, //
            num_display_entries: 4, 
            current_page: 10,   
            num_edge_entries: 1, 
            link_to:"?page=__id__"
        }); */

jQuery.fn.pagination = function(maxentries, opts) {
    opts = jQuery.extend({
        items_per_page: 10,
        num_display_entries: 10,
        current_page: 1,
        num_edge_entries: 0,
        link_to: "#",
        prev_text: "Prev",
        next_text: "Next",
        ellipse_text: "...",
        prev_show_always: true,
        next_show_always: true,
        callback: function() { return false; }
    }, opts || {});

    return this.each(function() {
        /**
        * Calculate the maximum number of pages
        */
        function numPages() {
            return Math.ceil(maxentries / opts.items_per_page);
        }

        /**
        * Calculate start and end point of pagination links depending on 
        * current_page and num_display_entries.
        * @return {Array}
        */
        function getInterval() {
            var ne_half = Math.ceil(opts.num_display_entries / 2);
            var np = numPages();
            var upper_limit = np - opts.num_display_entries;
            var start = current_page > ne_half ? Math.max(Math.min(current_page - ne_half, upper_limit), 0) : 0;
            var end = current_page > ne_half ? Math.min(current_page + ne_half, np) : Math.min(opts.num_display_entries, np);
            return [start, end];
        }

        /**
        * This is the event handling function for the pagination links. 
        * @param {int} page_id The new page number
        */
        function pageSelected(page_id, evt) {
            current_page = page_id;
            drawLinks();
            var continuePropagation = opts.callback(page_id, panel);
            if (!continuePropagation) {
                if (evt.stopPropagation) {
                    evt.stopPropagation();
                }
                else {
                    evt.cancelBubble = true;
                }
            }
            return continuePropagation;
        }

        /**
        * This function inserts the pagination links into the container element
        */
        function drawLinks() {
            panel.empty();
            var interval = getInterval();
            var np = numPages();
            // This helper function returns a handler function that calls pageSelected with the right page_id
            var getClickHandler = function(page_id) {
                return function(evt) { return pageSelected(page_id, evt); }
            }
            // Helper function for generating a single link (or a span tag if it'S the current page)
            /*var appendItem = function(page_id, appendopts) {
                page_id = page_id < 0 ? 0 : (page_id < np ? page_id : np - 1); // Normalize page id to sane value
                appendopts = jQuery.extend({ text: page_id + 1, classes: "current" }, appendopts || {});
                if (page_id == current_page) {
                    var lnk = $("<span class='current'>" + (appendopts.text) + "</span>");
                }
                else {
                    var lnk = $("<a>" + (appendopts.text) + "</a>")
                        .bind("click", getClickHandler(page_id))
                        .attr('href', opts.link_to.replace(/__id__/, page_id));


                }
                if (appendopts.classes) { lnk.removeAttr('class'); lnk.addClass(appendopts.classes); }
                panel.append(lnk);
            }*/
            var appendItem = function(page_id, appendopts) {
                page_id = page_id < 0 ? 0 : (page_id < np ? page_id : np - 1); // Normalize page id to sane value
                appendopts = jQuery.extend({ text: page_id + 1, classes: "current" }, appendopts || {});
                if (page_id == current_page) {
                    //var lnk = $("<span class='current'>" + (appendopts.text) + "</span>");
                	var lnk = $('<li class="active"><a href="#">' + (appendopts.text) + '<span class="sr-only">(current)</span></a></li>');
                }
                else {
                    /*var lnk = $("<a>" + (appendopts.text) + "</a>")
                        .bind("click", getClickHandler(page_id))
                        .attr('href', opts.link_to.replace(/__id__/, page_id));*/
                	var lnk = $('<li><a href="#">' + (appendopts.text) + '</a></li>');
                	
                    $(lnk).find("a")
            		.bind("click", getClickHandler(page_id))
                    .attr('href', opts.link_to.replace(/__id__/, page_id));


                }
                //if (appendopts.classes) { lnk.removeAttr('class'); lnk.addClass(appendopts.classes); }
                panel.append(lnk);
            }
            // Generate "Previous"-Link
            /*if (opts.prev_text && (current_page > 0 || opts.prev_show_always)) {
                appendItem(current_page - 1, { text: opts.prev_text, classes: "disabled" });
            }*/
            if (opts.prev_text && (current_page > 0 || opts.prev_show_always)) {
            	if(current_page >= 1) {
            		var lnk = $('<li class=""><a href="#" aria-label="'+opts.prev_text+'"><span aria-hidden="true">&laquo;</span></a></li>');
            		$(lnk).find("a")
            		.bind("click", getClickHandler(current_page))
                    .attr('href', opts.link_to.replace(/__id__/, current_page - 1));
            		panel.append(lnk);
            	} else {
            		//current_page = 0
            		panel.append('<li class="disabled"><a href="#" aria-label="'+opts.prev_text+'"><span aria-hidden="true">&laquo;</span></a></li>');
            	}
            }
            // Generate starting points  
            if (interval[0] > 0 && opts.num_edge_entries > 0) {
                var end = Math.min(opts.num_edge_entries, interval[0]);
                for (var i = 0; i < end; i++) {
                    appendItem(i);
                }
                if (opts.num_edge_entries < interval[0] && opts.ellipse_text) {
                    //jQuery("<span>" + opts.ellipse_text + "</span>").appendTo(panel);
                	jQuery('<li class="disabled"><a href="#" aria-label="'+opts.ellipse_text+'">' + opts.ellipse_text + '</a></li>').appendTo(panel);
                }
            }
            // Generate interval links
            for (var i = interval[0]; i < interval[1]; i++) {
                appendItem(i);
            }
            // Generate ending points  
            if (interval[1] < np && opts.num_edge_entries > 0) {
                if (np - opts.num_edge_entries > interval[1] && opts.ellipse_text) {
                    //jQuery("<span>" + opts.ellipse_text + "</span>").appendTo(panel);
                    jQuery('<li class="disabled"><a href="#" aria-label="'+opts.ellipse_text+'">' + opts.ellipse_text + '</a></li>').appendTo(panel);
                }
                var begin = Math.max(np - opts.num_edge_entries, interval[1]);
                for (var i = begin; i < np; i++) {
                    appendItem(i);
                }

            }
            // Generate "Next"-Link
            /*if (opts.next_text && (current_page < np - 1 || opts.next_show_always)) {
                appendItem(current_page + 1, { text: opts.next_text, classes: "disabled" });
            }*/
            if (opts.next_text && (current_page < np - 1 || opts.next_show_always)) {
            	if(current_page < np - 1) {
            		var lnk = $('<li><a href="#" aria-label="'+opts.ellipse_text+'"><span aria-hidden="true">&raquo;</span></a></li>');
            		$(lnk).find("a")
            		.bind("click", getClickHandler(current_page))
                    .attr('href', opts.link_to.replace(/__id__/, current_page + 1));
            		panel.append(lnk);
            	} else {
            		//current_page = np - 1 , pageTotal size
            		panel.append('<li class="disabled"><a href="#" aria-label="'+opts.ellipse_text+'"><span aria-hidden="true">&raquo;</span></a></li>');
            	}
            }
        }

        // Extract current_page from options
        var current_page = opts.current_page;
        // Create a sane value for maxentries and items_per_page
        maxentries = (!maxentries || maxentries < 0) ? 1 : maxentries;
        opts.items_per_page = (!opts.items_per_page || opts.items_per_page < 0) ? 1 : opts.items_per_page;
        // Store DOM element for easy access from all inner functions
        var panel = jQuery(this);
        // Attach control functions to the DOM element 
        this.selectPage = function(page_id) { pageSelected(page_id); }
        this.prevPage = function() {
            if (current_page > 0) {
                pageSelected(current_page - 1);
                return true;
            }
            else {
                return false;
            }
        }
        this.nextPage = function() {
            if (current_page < numPages() - 1) {
                pageSelected(current_page + 1);
                return true;
            }
            else {
                return false;
            }
        }
        // When all initialisation is done, draw the links
        drawLinks();
    });
}