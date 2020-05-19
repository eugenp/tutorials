/**
 * Miscellaneous js functions for WebHelp
 * Kasun Gajasinghe, http://kasunbg.blogspot.com
 * David Cramer, http://www.thingbag.net
 *
 */

//Turn ON and OFF the animations for Show/Hide Sidebar. Extend this to other anime as well if any.
var noAnimations=false;

$(document).ready(function() {
	// When you click on a link to an anchor, scroll down 
	// 105 px to cope with the fact that the banner
	// hides the top 95px or so of the page.
	// This code deals with the problem when 
	// you click on a link within a page.
	$('a[href*=#]').click(function() {
		if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'')
		    && location.hostname == this.hostname) {
		    var $target = $(this.hash);
		    $target = $target.length && $target
			|| $('[name=' + this.hash.slice(1) +']');
		if (!(this.hash == "#searchDiv" || this.hash == "#treeDiv"  || this.hash == "") && $target.length) {
			var targetOffset = $target.offset().top - 120;
			$('html,body')
			    .animate({scrollTop: targetOffset}, 200);
			return false;
		    }
		}
	    });

    //  $("#showHideHighlight").button(); //add jquery button styling to 'Go' button
    //Generate tabs in nav-pane with JQuery
    $(function() {
        $("#tabs").tabs({
            cookie: {
                expires: 2 // store cookie for 2 days.
            }
        });
    });

    //Generate the tree
    $("#ulTreeDiv").attr("style", "");
    $("#tree").treeview({
        collapsed: true,
        animated: "medium",
        control: "#sidetreecontrol",
        persist: "cookie"
    });

    //after toc fully styled, display it. Until loading, a 'loading' image will be displayed
    $("#tocLoading").attr("style", "display:none;");
    //    $("#ulTreeDiv").attr("style","display:block;");

    //.searchButton is the css class applied to 'Go' button 
    $(function() {
        $("button", ".searchButton").button();

        $("button", ".searchButton").click(function() {
            return false;
        });
    });

    //'ui-tabs-1' is the cookie name which is used for the persistence of the tabs.(Content/Search tab)
    if ($.cookie('ui-tabs-1') === '1') {    //search tab is active
        if ($.cookie('textToSearch') != undefined && $.cookie('textToSearch').length > 0) {
            document.getElementById('textToSearch').value = $.cookie('textToSearch');
            Verifie('searchForm');
            searchHighlight($.cookie('textToSearch'));
            $("#showHideHighlight").css("display", "block");
        }
    }

    syncToc(); //Synchronize the toc tree with the content pane, when loading the page.
    //$("#doSearch").button(); //add jquery button styling to 'Go' button

    // When you click on a link to an anchor, scroll down 
    // 120 px to cope with the fact that the banner
    // hides the top 95px or so of the page.
    // This code deals with the problem when 
    // you click on a link from another page. 
    var hash = window.location.hash;
    if(hash){ 
	var targetOffset = $(hash).offset().top - 120;
	$('html,body').animate({scrollTop: targetOffset}, 200);
	return false;
    }
});


/**
 * If an user moved to another page by clicking on a toc link, and then clicked on #searchDiv,
 * search should be performed if the cookie textToSearch is not empty.
 */
function doSearch() {
//'ui-tabs-1' is the cookie name which is used for the persistence of the tabs.(Content/Search tab)
    if ($.cookie('textToSearch') != undefined && $.cookie('textToSearch').length > 0) {
        document.getElementById('textToSearch').value = $.cookie('textToSearch');
        Verifie('searchForm');
    }
}

/**
 * Synchronize with the tableOfContents
 */
function syncToc() {
    var a = document.getElementById("webhelp-currentid");
    if (a != undefined) {
        //Expanding the child sections of the selected node.
        var nodeClass = a.getAttribute("class");
        if (nodeClass != null && !nodeClass.match(/collapsable/)) {
            a.setAttribute("class", "collapsable");
            //remove display:none; css style from <ul> block in the selected node.
            var ulNode = a.getElementsByTagName("ul")[0];
            if (ulNode != undefined) {
                if (ulNode.hasAttribute("style")) {
                    ulNode.setAttribute("style", "display: block; background-color: #D8D8D8 !important;");
                } else {
                    var ulStyle = document.createAttribute("style");
                    ulStyle.nodeValue = "display: block; background-color: #D8D8D8 !important;";
                    ulNode.setAttributeNode(ulStyle);
            }   }
            //adjust tree's + sign to -
            var divNode = a.getElementsByTagName("div")[0];
            if (divNode != undefined) {
                if (divNode.hasAttribute("class")) {
                    divNode.setAttribute("class", "hitarea collapsable-hitarea");
                } else {
                    var divClass = document.createAttribute("class");
                    divClass.nodeValue = "hitarea collapsable-hitarea";
                    divNode.setAttributeNode(divClass);
            }   }
            //set persistence cookie when a node is auto expanded
            //     setCookieForExpandedNode("webhelp-currentid");
        }
        var b = a.getElementsByTagName("a")[0];

        if (b != undefined) {
            //Setting the background for selected node.
            var style = a.getAttribute("style", 2);
            if (style != null && !style.match(/background-color: Background;/)) {
                a.setAttribute("style", "background-color: #D8D8D8;  " + style);
                b.setAttribute("style", "color: black;");
            } else if (style != null) {
                a.setAttribute("style", "background-color: #D8D8D8;  " + style);
                b.setAttribute("style", "color: black;");
            } else {
                a.setAttribute("style", "background-color: #D8D8D8;  ");
                b.setAttribute("style", "color: black;");
            }
        }

        //shows the node related to current content.
        //goes a recursive call from current node to ancestor nodes, displaying all of them.
        while (a.parentNode && a.parentNode.nodeName) {
            var parentNode = a.parentNode;
            var nodeName = parentNode.nodeName;

            if (nodeName.toLowerCase() == "ul") {
                parentNode.setAttribute("style", "display: block;");
            } else if (nodeName.toLocaleLowerCase() == "li") {
                parentNode.setAttribute("class", "collapsable");
                parentNode.firstChild.setAttribute("class", "hitarea collapsable-hitarea ");
            }
            a = parentNode;
}   }  }
/*
 function setCookieForExpandedNode(nodeName) {
 var tocDiv = document.getElementById("tree"); //get table of contents Div
 var divs = tocDiv.getElementsByTagName("div");
 var matchedDivNumber;
 var i;
 for (i = 0; i < divs.length; i++) {        //1101001
 var div = divs[i];
 var liNode = div.parentNode;
 }
//create a new cookie if a treeview does not exist
 if ($.cookie(treeCookieId) == null || $.cookie(treeCookieId) == "") {
 var branches = $("#tree").find("li");//.prepareBranches(treesettings);
 var data = [];
 branches.each(function(i, e) {
 data[i] = $(e).is(":has(>ul:visible)") ? 1 : 0;
 });
 $.cookie(treeCookieId, data.join(""));

 }

 if (i < divs.length) {
 var treeviewCookie = $.cookie(treeCookieId);
 var tvCookie1 = treeviewCookie.substring(0, i);
 var tvCookie2 = treeviewCookie.substring(i + 1);
 var newTVCookie = tvCookie1 + "1" + tvCookie2;
 $.cookie(treeCookieId, newTVCookie);
 }
 }       */

/**
 * Code for Show/Hide TOC
 *
 */
function showHideToc() {
    var showHideButton = $("#showHideButton");
    var leftNavigation = $("#sidebar"); //hide the parent div of leftnavigation, ie sidebar
    var content = $("#content");
    var animeTime=75

    if (showHideButton != undefined && showHideButton.hasClass("pointLeft")) {
        //Hide TOC
        showHideButton.removeClass('pointLeft').addClass('pointRight');
	
        if(noAnimations) {
            leftNavigation.css("display", "none");
            content.css("margin", "125px 0 0 0");
        } else {
            leftNavigation.hide(animeTime);
            content.animate( { "margin-left": 0 }, animeTime);
        }
        showHideButton.attr("title", "Show Sidebar");
    } else {
        //Show the TOC
        showHideButton.removeClass('pointRight').addClass('pointLeft');
        if(noAnimations) {
            content.css("margin", "125px 0 0 280px");
            leftNavigation.css("display", "block");
        } else {
            content.animate( { "margin-left": '280px' }, animeTime);
            leftNavigation.show(animeTime);
        }
        showHideButton.attr("title", "Hide Sidebar");
    }
}

/**
 * Code for search highlighting
 */
var highlightOn = true;
function searchHighlight(searchText) {
    highlightOn = true;
    if (searchText != undefined) {
        var wList;
        var sList = new Array();    //stem list 
        //Highlight the search terms
        searchText = searchText.toLowerCase().replace(/<\//g, "_st_").replace(/\$_/g, "_di_").replace(/\.|%2C|%3B|%21|%3A|@|\/|\*/g, " ").replace(/(%20)+/g, " ").replace(/_st_/g, "</").replace(/_di_/g, "%24_")
        searchText = searchText.replace(/  +/g, " ");
        searchText = searchText.replace(/ $/, "").replace(/^ /, "");

        wList = searchText.split(" ");
        $("#content").highlight(wList); //Highlight the search input

        if (typeof stemmer != "undefined") {
            //Highlight the stems
            for (var i = 0; i < wList.length; i++) {
                var stemW = stemmer(wList[i]);
                sList.push(stemW);
            }
        } else {
            sList = wList;
        }
        $("#content").highlight(sList); //Highlight the search input's all stems
    }
}

function searchUnhighlight() {
    highlightOn = false;
    //unhighlight the search input's all stems
    $("#content").unhighlight();
    $("#content").unhighlight();
}

function toggleHighlight() {
    if (highlightOn) {
        searchUnhighlight();
    } else {
        searchHighlight($.cookie('textToSearch'));
    }
}