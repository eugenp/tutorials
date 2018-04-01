# usage:
# $.app.params.set('page', 4).build()
# $.app.params.get('page')

$.app = $.app or {}

class RequestParams
	constructor: ->
		@params = {}

		query = window.location.search.slice 1
		return if query is ''

		for each in query.split '&'
			q = each.split '='
			@params[q[0]] = decodeURIComponent q[1]

	has: (key) -> key of @params && @params[key].length > 0

	get: (key) -> decodeURIComponent @params[key]

	set: (key, value) ->
		@params[key] = value
		this

	remove: (keys...) ->
		delete @params[key] for key in keys
		this

	clear: ->
		@params = {}
		this

	build: ->
		$.param(@params)

	go: ->
		window.location.search = @build()

$.app.params = new RequestParams()

menu = ->
	$("#menu-toggle").click (e) ->
		e.preventDefault()
		$('#wrapper').toggleClass('toggled')
		$('#menu-toggle').toggleClass('folded')
		if $("#menu-toggle").hasClass('folded')
			$("#menu-toggle>i").removeClass('fa-angle-double-left')
			$("#menu-toggle>i").addClass('fa-angle-double-right')
		else
			$("#menu-toggle>i").addClass('fa-angle-double-left')
			$("#menu-toggle>i").removeClass('fa-angle-double-right')

sidebar = ->
	p = $(".sidebar-nav").find("li a")

	items = {}
	['console', 'users', 'create_user', 'tasks', 'create_task', 'tokens', 'backup'].forEach (x, i) -> items[x] = p[i]
	$(items[$.app.page]).addClass('act')

window.page = (index) -> $.app.params.set('page', index).go()

$(document).on "keypress", "form", (event) -> return event.keyCode != 13

$ ->
	menu()
	sidebar()

