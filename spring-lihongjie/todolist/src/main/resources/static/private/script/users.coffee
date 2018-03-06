$ ->
	updateOpPanelState = ->
		$("#op-panel").toggleClass 'hidden', $(".data-table td .item-selected:checked").length == 0

	$(".data-table th .item-selected").change ->
		value = $(".data-table th .item-selected").is ':checked'
		$(".data-table td .item-selected").prop 'checked', value
		updateOpPanelState()

	$(".data-table td .item-selected").change ->
		updateOpPanelState()


	p = {}
	[p.all, p.user, p.admin] = ($(each) for each in $('#role-panel button'))
	role = if $.app.params.has 'role' then $.app.params.get('role') else 'all'
	p[role].addClass 'active'
	p.all.click -> $.app.params.remove('page', 'role', 'q').go()
	p.user.click -> $.app.params.remove('page', 'q').set('role', 'user').go()
	p.admin.click -> $.app.params.remove('page', 'q').set('role', 'admin').go()

	q = {}
	[q.id, q.name, q.createdAt] = ($(each) for each in $('#sort-panel button'))
	sort = if $.app.params.has 'sort' then $.app.params.get('sort') else 'id'
	q[sort.replace(/,.*$/, "")].addClass 'active'
	q.id.click -> $.app.params.remove('page').remove('sort').go()
	q.name.click -> $.app.params.remove('page').set('sort', 'name').go()
	q.createdAt.click -> $.app.params.remove('page').set('sort', 'createdAt').go()

	$('#search-panel input').on 'input propertychange', ->
		$("#search-panel .form-control-clear").toggleClass 'hidden', $('#search-panel input').val().length is 0


	$('#search-panel .form-control-clear').click ->
		$('#search-panel input').val('')
		$.app.params.clear().go()


	if $.app.params.has('q')
		$("#search-panel input").val $.app.params.get('q').replace(/\+/g, ' ')
		$("#search-panel .form-control-clear").removeClass 'hidden'

	$("#search-panel button").click ->
		text = $("#search-panel input").get(0).value
		$.app.params.clear().set('q', text).go()
