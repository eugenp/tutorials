
	var myLayout;

	jQuery(document).ready(function ($) {

                myLayout = $('body').layout({
                //Defining panes    
                        west__paneSelector:         "#sidebar"
                ,       north__paneSelector:        "#header"
                ,       center__paneSelector:       "#content"
		
		//	some resizing/toggling settings
		,	north__resizable:			false	// OVERRIDE the pane-default of 'resizable=true'
                ,	north__spacing_open:                    0		// no resizer-bar when open (zero height)
		,	west__slideable:			false
                ,	west__spacing_closed:		6		
		,	west__spacing_open:		4		

		,
		//	some pane-size settings
			west__minSize:				280
		,       north__minSize:                          99	

		//	some pane animation settings
		,	west__animatePaneSizing:	false
		,	west__fxSpeed_size:			"normal"	
		,	west__fxSpeed_open:			10	
		,	west__fxSettings_open:		{easing: ""} 
		,	west__fxName_close:			"none"	

	
		,	stateManagement__enabled:	true // automatic cookie load & save enabled by default
                ,       stateManagement__cookie__name:    "sidebar_state" 
		});




 	});

