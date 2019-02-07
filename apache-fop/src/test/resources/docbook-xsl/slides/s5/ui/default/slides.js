// S5 v1.2a1 slides.js -- released into the Public Domain
//
// Please see http://www.meyerweb.com/eric/tools/s5/credits.html for information 
// about all the wonderful and talented contributors to this code!

var undef;
var slideCSS = '';
var snum = 0;
var smax = 1;
var incpos = 0;
var number = undef;
var s5mode = true;
var defaultView = 'slideshow';
var controlVis = 'visible';

var s5NotesWindow;
var s5NotesWindowLoaded = false;
var previousSlide = 0;
var presentationStart = new Date();
var slideStart = new Date();

var countdown = {
	timer: 0,
	state: 'pause',
	start: new Date(),
	end: 0,
	remaining: 0
};


var isIE = navigator.appName == 'Microsoft Internet Explorer' && navigator.userAgent.indexOf('Opera') < 1 ? 1 : 0;
var isOp = navigator.userAgent.indexOf('Opera') > -1 ? 1 : 0;
var isGe = navigator.userAgent.indexOf('Gecko') > -1 && navigator.userAgent.indexOf('Safari') < 1 ? 1 : 0;

function hasClass(object, className) {
	if (!object.className) return false;
	return (object.className.search('(^|\\s)' + className + '(\\s|$)') != -1);
}

function hasValue(object, value) {
	if (!object) return false;
	return (object.search('(^|\\s)' + value + '(\\s|$)') != -1);
}

function removeClass(object,className) {
	if (!object || !hasClass(object,className)) return;
	object.className = object.className.replace(new RegExp('(^|\\s)'+className+'(\\s|$)'), RegExp.$1+RegExp.$2);
}

function addClass(object,className) {
	if (!object || hasClass(object, className)) return;
	if (object.className) {
		object.className += ' '+className;
	} else {
		object.className = className;
	}
}

function GetElementsWithClassName(elementName,className) {
	var allElements = document.getElementsByTagName(elementName);
	var elemColl = new Array();
	for (var i = 0; i< allElements.length; i++) {
		if (hasClass(allElements[i], className)) {
			elemColl[elemColl.length] = allElements[i];
		}
	}
	return elemColl;
}

function isParentOrSelf(element, id) {
	if (element == null || element.nodeName=='BODY') return false;
	else if (element.id == id) return true;
	else return isParentOrSelf(element.parentNode, id);
}

function nodeValue(node) {
	var result = "";
	if (node.nodeType == 1) {
		var children = node.childNodes;
		for (var i = 0; i < children.length; ++i) {
			result += nodeValue(children[i]);
		}		
	}
	else if (node.nodeType == 3) {
		result = node.nodeValue;
	}
	return(result);
}

function slideLabel() {
	var slideColl = GetElementsWithClassName('*','slide');
	var list = document.getElementById('jumplist');
	smax = slideColl.length;
	for (var n = 0; n < smax; n++) {
		var obj = slideColl[n];

		var did = 'slide' + n.toString();
		obj.setAttribute('id',did);

//		if (isOp) continue;   // Opera fix (hallvord)

		var otext = '';
		var menu = obj.firstChild;
		if (!menu) continue; // to cope with empty slides
		while (menu && menu.nodeType == 3) {
			menu = menu.nextSibling;
		}
	 	if (!menu) continue; // to cope with slides with only text nodes

		var menunodes = menu.childNodes;
		for (var o = 0; o < menunodes.length; o++) {
			otext += nodeValue(menunodes[o]);
		}
		list.options[list.length] = new Option(n + ' : '  + otext, n);
	}
}

function currentSlide() {
	var cs;
	if (document.getElementById) {
		cs = document.getElementById('currentSlide');
	} else {
		cs = document.currentSlide;
	}
	cs.innerHTML = '<a id="plink" href="">' + 
		'<span id="csHere">' + snum + '<\/span> ' + 
		'<span id="csSep">\/<\/span> ' + 
		'<span id="csTotal">' + (smax-1) + '<\/span>' +
		'<\/a>'
		;
	if (snum == 0) {
		cs.style.visibility = 'hidden';
	} else {
		cs.style.visibility = 'visible';
	}
}

function go(step) {
	if (document.getElementById('slideProj').disabled || step == 0) return;
	var jl = document.getElementById('jumplist');
	var cid = 'slide' + snum;
	var ce = document.getElementById(cid);
	if (incrementals[snum].length > 0) {
		for (var i = 0; i < incrementals[snum].length; i++) {
			removeClass(incrementals[snum][i], 'current');
			removeClass(incrementals[snum][i], 'incremental');
		}
	}
	if (step != 'j') {
		snum += step;
		lmax = smax - 1;
		if (snum > lmax) snum = lmax;
		if (snum < 0) snum = 0;
	} else
		snum = parseInt(jl.value);
	var nid = 'slide' + snum;
	var ne = document.getElementById(nid);
	if (!ne) {
		ne = document.getElementById('slide0');
		snum = 0;
	}
	if (step < 0) {incpos = incrementals[snum].length} else {incpos = 0;}
	if (incrementals[snum].length > 0 && incpos == 0) {
		for (var i = 0; i < incrementals[snum].length; i++) {
			if (hasClass(incrementals[snum][i], 'current'))
				incpos = i + 1;
			else
				addClass(incrementals[snum][i], 'incremental');
		}
	}
	if (incrementals[snum].length > 0 && incpos > 0)
		addClass(incrementals[snum][incpos - 1], 'current');
	if (isOp) { //hallvord
		location.hash = nid;
	} else {
		ce.style.visibility = 'hidden'; 
		ne.style.visibility = 'visible';
	} // /hallvord
	jl.selectedIndex = snum;
	currentSlide();
	loadNote();
	permaLink();
	number = undef;
}

function goTo(target) {
	if (target >= smax || target == snum) return;
	go(target - snum);
}

function subgo(step) {
	if (step > 0) {
		removeClass(incrementals[snum][incpos - 1],'current');
		removeClass(incrementals[snum][incpos], 'incremental');
		addClass(incrementals[snum][incpos],'current');
		incpos++;
	} else {
		incpos--;
		removeClass(incrementals[snum][incpos],'current');
		addClass(incrementals[snum][incpos], 'incremental');
		addClass(incrementals[snum][incpos - 1],'current');
	}
	loadNote();
}

function toggle() {
	var slideColl = GetElementsWithClassName('*','slide');
	var slides = document.getElementById('slideProj');
	var outline = document.getElementById('outlineStyle');
	if (!slides.disabled) {
		slides.disabled = true;
		outline.disabled = false;
		s5mode = false;
		fontSize('1em');
		for (var n = 0; n < smax; n++) {
			var slide = slideColl[n];
			slide.style.visibility = 'visible';
		}
	} else {
		slides.disabled = false;
		outline.disabled = true;
		s5mode = true;
		fontScale();
		for (var n = 0; n < smax; n++) {
			var slide = slideColl[n];
			slide.style.visibility = 'hidden';
		}
		slideColl[snum].style.visibility = 'visible';
	}
}

function showHide(action) {
	var obj = GetElementsWithClassName('*','hideme')[0];
	switch (action) {
	case 's': obj.style.visibility = 'visible'; break;
	case 'h': obj.style.visibility = 'hidden'; break;
	case 'k':
		if (obj.style.visibility != 'visible') {
			obj.style.visibility = 'visible';
		} else {
			obj.style.visibility = 'hidden';
		}
	break;
	}
}

// 'keys' code adapted from MozPoint (http://mozpoint.mozdev.org/)
function keys(key) {
	if (!key) {
		key = event;
		key.which = key.keyCode;
	}
	if (key.which == 84) {
		toggle();
		return;
	}
	if (s5mode) {
		switch (key.which) {
			case 10: // return
			case 13: // enter
				if (window.event && isParentOrSelf(window.event.srcElement, 'controls')) return;
				if (key.target && isParentOrSelf(key.target, 'controls')) return;
				if(number != undef) {
					goTo(number);
					break;
				}
			case 32: // spacebar
			case 34: // page down
			case 39: // rightkey
			case 40: // downkey
				if(number != undef) {
					go(number);
				} else if (!incrementals[snum] || incpos >= incrementals[snum].length) {
					go(1);
				} else {
					subgo(1);
				}
				break;
			case 33: // page up
			case 37: // leftkey
			case 38: // upkey
				if(number != undef) {
					go(-1 * number);
				} else if (!incrementals[snum] || incpos <= 0) {
					go(-1);
				} else {
					subgo(-1);
				}
				break;
			case 36: // home
				goTo(0);
				break;
			case 35: // end
				goTo(smax-1);
				break;
			case 67: // c
				showHide('k');
				break;
			case 78: // n
				createNotesWindow();
				break;
		}
		if (key.which < 48 || key.which > 57) {
			number = undef;
		} else {
			if (window.event && isParentOrSelf(window.event.srcElement, 'controls')) return;
			if (key.target && isParentOrSelf(key.target, 'controls')) return;
			number = (((number != undef) ? number : 0) * 10) + (key.which - 48);
		}
	}
	return false;
}

function clicker(e) {
	number = undef;
	var target;
	if (window.event) {
		target = window.event.srcElement;
		e = window.event;
	} else target = e.target;
	if (target.href != null || hasValue(target.rel, 'external') || isParentOrSelf(target, 'controls') || isParentOrSelf(target,'embed') || isParentOrSelf(target,'object')) return true;
	if (!e.which || e.which == 1) {
		if (!incrementals[snum] || incpos >= incrementals[snum].length) {
			go(1);
		} else {
			subgo(1);
		}
	}
}

function findSlide(hash) {
	var target = null;
	var slides = GetElementsWithClassName('*','slide');
	for (var i = 0; i < slides.length; i++) {
		var targetSlide = slides[i];
		if ( (targetSlide.name && targetSlide.name == hash)
		 || (targetSlide.id && targetSlide.id == hash) ) {
			target = targetSlide;
			break;
		}
	}
	while(target != null && target.nodeName != 'BODY') {
		if (hasClass(target, 'slide')) {
			return parseInt(target.id.slice(5));
		}
		target = target.parentNode;
	}
	return null;
}

function slideJump() {
	if (window.location.hash == null) return;
	var sregex = /^#slide(\d+)$/;
	var matches = sregex.exec(window.location.hash);
	var dest = null;
	if (matches != null) {
		dest = parseInt(matches[1]);
	} else {
		dest = findSlide(window.location.hash.slice(1));
	}
	if (dest != null)
		go(dest - snum);
}

function fixLinks() {
	var thisUri = window.location.href;
	thisUri = thisUri.slice(0, thisUri.length - window.location.hash.length);
	var aelements = document.getElementsByTagName('A');
	for (var i = 0; i < aelements.length; i++) {
		var a = aelements[i].href;
		var slideID = a.match('\#slide[0-9]{1,2}');
		if ((slideID) && (slideID[0].slice(0,1) == '#')) {
			var dest = findSlide(slideID[0].slice(1));
			if (dest != null) {
				if (aelements[i].addEventListener) {
					aelements[i].addEventListener("click", new Function("e",
						"if (document.getElementById('slideProj').disabled) return;" +
						"go("+dest+" - snum); " +
						"if (e.preventDefault) e.preventDefault();"), true);
				} else if (aelements[i].attachEvent) {
					aelements[i].attachEvent("onclick", new Function("",
						"if (document.getElementById('slideProj').disabled) return;" +
						"go("+dest+" - snum); " +
						"event.returnValue = false;"));
				}
			}
		}
	}
}

function externalLinks() {
	if (!document.getElementsByTagName) return;
	var anchors = document.getElementsByTagName('a');
	for (var i=0; i<anchors.length; i++) {
		var anchor = anchors[i];
		if (anchor.getAttribute('href') && hasValue(anchor.rel, 'external')) {
			anchor.target = '_blank';
			addClass(anchor,'external');
		}
	}
}

function permaLink() {
	document.getElementById('plink').href = window.location.pathname + '#slide' + snum;
}

function createControls() {
	var controlsDiv = document.getElementById("controls");
	if (!controlsDiv) return;
	var hider = ' onmouseover="showHide(\'s\');" onmouseout="showHide(\'h\');"';
	var hideDiv, hideList = '';
	if (controlVis == 'hidden') {
		hideDiv = hider;
	} else {
		hideList = hider;
	}
	controlsDiv.innerHTML = '<form action="#" id="controlForm"' + hideDiv + '>' +
	'<div id="navLinks">' +
	'<a accesskey="n" id="show-notes" href="javascript:createNotesWindow();" title="Show Notes">&equiv;<\/a>' +
	'<a accesskey="t" id="toggle" href="javascript:toggle();">&#216;<\/a>' +
	'<a accesskey="z" id="prev" href="javascript:go(-1);">&laquo;<\/a>' +
	'<a accesskey="x" id="next" href="javascript:go(1);">&raquo;<\/a>' +
	'<div id="navList"' + hideList + '><select id="jumplist" onchange="go(\'j\');"><\/select><\/div>' +
	'<\/div><\/form>';
	if (controlVis == 'hidden') {
		var hidden = document.getElementById('navLinks');
	} else {
		var hidden = document.getElementById('jumplist');
	}
	addClass(hidden,'hideme');
}

function fontScale() {  // causes layout problems in FireFox that get fixed if browser's Reload is used; same may be true of other Gecko-based browsers
	if (!s5mode) return false;
	var vScale = 48;  // both yield 16 (the usual browser default) at 1024x768
	var hScale = 64;  // perhaps should auto-calculate based on theme's declared value?
	if (window.innerHeight) {
		var vSize = window.innerHeight;
		var hSize = window.innerWidth;
	} else if (document.documentElement.clientHeight) {
		var vSize = document.documentElement.clientHeight;
		var hSize = document.documentElement.clientWidth;
	} else if (document.body.clientHeight) {
		var vSize = document.body.clientHeight;
		var hSize = document.body.clientWidth;
	} else {
		var vSize = 700;  // assuming 1024x768, minus chrome and such
		var hSize = 1024; // these do not account for kiosk mode or Opera Show
	}
	var newSize = Math.min(Math.round(vSize/vScale),Math.round(hSize/hScale));
	fontSize(newSize + 'px');
	if (isGe) {  // hack to counter incremental reflow bugs
		var obj = document.getElementsByTagName('body')[0];
		obj.style.display = 'none';
		obj.style.display = 'block';
	}
}

function fontSize(value) {
	if (!(s5ss = document.getElementById('s5ss'))) {
		if (!document.createStyleSheet) {
			document.getElementsByTagName('head')[0].appendChild(s5ss = document.createElement('style'));
			s5ss.setAttribute('media','screen, projection');
			s5ss.setAttribute('id','s5ss');
		} else {
			document.createStyleSheet();
			document.s5ss = document.styleSheets[document.styleSheets.length - 1];
		}
	}
	if (!(document.s5ss && document.s5ss.addRule)) {
		while (s5ss.lastChild) s5ss.removeChild(s5ss.lastChild);
		s5ss.appendChild(document.createTextNode('html {font-size: ' + value + ' !important;}'));
	} else {
		document.s5ss.addRule('html','font-size: ' + value + ' !important;');
	}
}

function notOperaFix() {
	slideCSS = document.getElementById('slideProj').href;
	var slides = document.getElementById('slideProj');
	var outline = document.getElementById('outlineStyle');
	slides.setAttribute('media','screen');
	outline.disabled = true;
	if (isGe) {
		slides.setAttribute('href','null');   // Gecko fix
		slides.setAttribute('href',slideCSS); // Gecko fix
	}
	if (isIE && document.styleSheets && document.styleSheets[0]) {
		document.styleSheets[0].addRule('img', 'behavior: url(ui/default/iepngfix.htc)');
		document.styleSheets[0].addRule('div', 'behavior: url(ui/default/iepngfix.htc)');
		document.styleSheets[0].addRule('.slide', 'behavior: url(ui/default/iepngfix.htc)');
	}
}

function getIncrementals(obj) {
	var incrementals = new Array();
	if (!obj) 
		return incrementals;
	var children = obj.childNodes;
	for (var i = 0; i < children.length; i++) {
		var child = children[i];
		if (hasClass(child, 'incremental')) {
			if (child.nodeName == 'OL' || child.nodeName == 'UL') {
				removeClass(child, 'incremental');
				for (var j = 0; j < child.childNodes.length; j++) {
					if (child.childNodes[j].nodeType == 1) {
						addClass(child.childNodes[j], 'incremental');
					}
				}
			} else {
				incrementals[incrementals.length] = child;
				removeClass(child,'incremental');
			}
		}
		if (hasClass(child, 'show-first')) {
			if (child.nodeName == 'OL' || child.nodeName == 'UL') {
				removeClass(child, 'show-first');
				if (child.childNodes[isGe].nodeType == 1) {
					removeClass(child.childNodes[isGe], 'incremental');
				}
			} else {
				incrementals[incrementals.length] = child;
			}
		}
		incrementals = incrementals.concat(getIncrementals(child));
	}
	return incrementals;
}

function createIncrementals() {
	var incrementals = new Array();
	for (var i = 0; i < smax; i++) {
		incrementals[i] = getIncrementals(document.getElementById('slide'+i));
	}
	return incrementals;
}

function defaultCheck() {
	var allMetas = document.getElementsByTagName('meta');
	for (var i = 0; i< allMetas.length; i++) {
		if (allMetas[i].name == 'defaultView') {
			defaultView = allMetas[i].content;
		}
		if (allMetas[i].name == 'controlVis') {
			controlVis = allMetas[i].content;
		}
	}
}

// Key trap fix, new function body for trap()
function trap(e) {
	if (!e) {
		e = event;
		e.which = e.keyCode;
	}
	try {
		modifierKey = e.ctrlKey || e.altKey || e.metaKey;
	}
	catch(e) {
		modifierKey = false;
	}
	return modifierKey || e.which == 0;
}

function noteLabel() { // Gives notes id's to match parent slides
	var notes = GetElementsWithClassName('div','notes');
	for (var i = 0; i < notes.length; i++) {
		var note = notes[i];
		var id = 'note' + note.parentNode.id.substring(5);
		note.setAttribute('id',id);
	}
	resetElapsedSlide();
	resetRemainingTime();
	window.setInterval('updateElaspedTime()', 1000);
}

function createNotesWindow() { // creates a window for our notes
	if (!s5NotesWindow || s5NotesWindow.closed) { // Create the window if it doesn't exist
		s5NotesWindowLoaded = false;
		// Note: Safari has a tendency to ignore window options preferring to default to the settings of the parent window, grr.
		s5NotesWindow = window.open('ui/s5-notes.html', 's5NotesWindow', 'top=0,left=0');
	}
	if (s5NotesWindowLoaded) { // Load the current note if the Note HTML has loaded
		loadNote();
	} else { // Keep trying...
		window.setTimeout('createNotesWindow()', 50);
	}
}

function loadNote() {
// Loads a note into the note window
	var notes = nextNotes = '<em class="disclaimer">There are no notes for this slide.</em>';
	if (document.getElementById('note' + snum)) {
		notes = document.getElementById('note' + snum).innerHTML;
	}
	if (document.getElementById('note' + (snum + 1))) {
		nextNotes = document.getElementById('note' + (snum + 1)).innerHTML;
	}
	
	var jl = document.getElementById('jumplist');
	var slideTitle = jl.options[jl.selectedIndex].text.replace(/^\d+\s+:\s+/, '') + ((jl.selectedIndex) ? ' (' + jl.selectedIndex + '/' + (smax - 1) + ')' : '');
	if (incrementals[snum].length > 0) {
//		alert('howdy');
		slideTitle += ' <small>[' + incpos + '/' + incrementals[snum].length + ']</small>';
	}
	if (jl.selectedIndex < smax - 1) {
		var nextTitle = jl.options[jl.selectedIndex + 1].text.replace(/^\d+\s+:\s+/, '') + ((jl.selectedIndex + 1) ? ' (' + (jl.selectedIndex + 1) + '/' + (smax - 1) + ')' : '');
	} else {
		var nextTitle = '[end of slide show]';
	}
	
	if (s5NotesWindow && !s5NotesWindow.closed && s5NotesWindow.document) {
		s5NotesWindow.document.getElementById('slide').innerHTML = slideTitle;
		s5NotesWindow.document.getElementById('notes').innerHTML = notes;
		s5NotesWindow.document.getElementById('next').innerHTML = nextTitle;
		s5NotesWindow.document.getElementById('nextnotes').innerHTML = nextNotes;
	}
	resetElapsedSlide();
}

function minimizeTimer(id) {
	var obj = s5NotesWindow.document.getElementById(id);
	if (hasClass(obj,'collapsed')) {
		removeClass(obj,'collapsed');
	} else {
		addClass(obj,'collapsed');
	}
}

function resetElapsedTime() {
	presentationStart = new Date();
	slideStart = new Date();
	updateElaspedTime();
}

function resetElapsedSlide() {
	if (snum != previousSlide) {
		slideStart = new Date();
		previousSlide = snum;
		updateElaspedTime();
	}
}

function updateElaspedTime() {
	if (!s5NotesWindowLoaded || !s5NotesWindow || s5NotesWindow.closed) return;
	var now = new Date();
	var ep = s5NotesWindow.document.getElementById('elapsed-presentation');
	var es = s5NotesWindow.document.getElementById('elapsed-slide');
	ep.innerHTML = formatTime(now.valueOf() - presentationStart.valueOf());
	es.innerHTML = formatTime(now.valueOf() - slideStart.valueOf());
}

function resetRemainingTime() {
	if (!s5NotesWindowLoaded || !s5NotesWindow || s5NotesWindow.closed) return;
	var startField = s5NotesWindow.document.getElementById('startFrom');
	startFrom = readTime(startField.value);
	countdown.remaining = startFrom * 60000;  // convert to msecs
	countdown.start = new Date().valueOf();
	countdown.end = countdown.start + countdown.remaining;
	var tl = s5NotesWindow.document.getElementById('timeLeft');
	var timeLeft = formatTime(countdown.remaining);
	tl.innerHTML = timeLeft;
}

function updateRemainingTime() {
	if (!s5NotesWindowLoaded || !s5NotesWindow || s5NotesWindow.closed) return;
	var tl = s5NotesWindow.document.getElementById('timeLeft');
	var now = new Date();
	if (countdown.state == 'run') {
		countdown.remaining = countdown.end - now;
	}
	tl.style.color = '';
	tl.style.backgroundColor = '';
	if (countdown.remaining >= 0) {
		var timeLeft = formatTime(countdown.remaining);
		removeClass(tl,'overtime');
		if (countdown.remaining < 300000) {
			tl.style.color = 'rgb(' + (255-Math.round(countdown.remaining/2000)) + ',0,0)';
			tl.style.backgroundColor = 'rgb(255,255,' + (Math.round(countdown.remaining/2000)) + ')';
		}
	} else {
		var timeLeft = '-' + formatTime(-countdown.remaining);
		addClass(tl,'overtime');
	}
	tl.innerHTML = timeLeft;
}

function toggleRemainingTime() {
	if (countdown.state == 'pause') countdown.state = 'run'; else countdown.state = 'pause';
	if (countdown.state == 'pause') {
		window.clearInterval(countdown.timer);
	}
	if (countdown.state == 'run') {
		countdown.start = new Date().valueOf();
		countdown.end = countdown.start + countdown.remaining;
		countdown.timer = window.setInterval('updateRemainingTime()', 1000);
	}
}

function alterRemainingTime(amt) {
	var change = amt * 60000;  // convert to msecs
	countdown.end += change;
	countdown.remaining += change;
	updateRemainingTime();
}

function formatTime(msecs)  {
	var time = new Date(msecs);
	
	var hrs = time.getUTCHours() + ((time.getUTCDate() -1) * 24); // I doubt anyone will spend more than 24 hours on a presentation or single slide but just in case...
	hrs = (hrs < 10) ? '0'+hrs : hrs;
	if (hrs == 'NaN' || isNaN(hrs)) hrs = '--';
	
	var min = time.getUTCMinutes();
	min = (min < 10) ? '0'+min : min;
	if (min == 'NaN' || isNaN(min)) min = '--';
	
	var sec = time.getUTCSeconds();
	sec = (sec < 10) ? '0'+sec : sec;
	if (sec == 'NaN' || isNaN(sec)) sec = '--';

	return hrs + ':' + min + ':' + sec;
}

function readTime(val) {
	var sregex = /:/;
	var matches = sregex.exec(val);
	if (matches == null) {
		return val;
	} else {
		var times = val.split(':');
		var hours = parseInt(times[0]);
		var mins = parseInt(times[1]);
		var total = (hours * 60) + mins;
		return total;
	}
}

function windowChange() {
	fontScale();
}

function startup() {
	defaultCheck();
	createControls();  // hallvord
	slideLabel();
	incrementals = createIncrementals();
	noteLabel(); // [SI:060104] must follow slideLabel()
	loadNote();
	fixLinks();
	externalLinks();
	fontScale();
	if (!isOp) notOperaFix();
	slideJump();
	if (defaultView == 'outline') {
		toggle();
	}
	document.onkeyup = keys;
	document.onkeypress = trap;
	document.onclick = clicker;
}

window.onload = startup;
window.onresize = function(){setTimeout('windowChange()',5);}