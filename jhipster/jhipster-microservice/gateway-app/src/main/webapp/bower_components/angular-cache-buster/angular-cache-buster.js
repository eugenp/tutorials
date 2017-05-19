angular.module('ngCacheBuster', [])
  .config(['$httpProvider', function($httpProvider) {
    return $httpProvider.interceptors.push('httpRequestInterceptorCacheBuster');
  }])
    .provider('httpRequestInterceptorCacheBuster', function() {
	
	this.matchlist = [/.*partials.*/, /.*views.*/ ];
	this.logRequests = false;
	
	//Default to whitelist (i.e. block all except matches)
	this.black=false; 
	
	//Select blacklist or whitelist, default to whitelist
	this.setMatchlist = function(list,black) {
	    this.black = typeof black != 'undefined' ? black : false
	    this.matchlist = list;
	};
	

	this.setLogRequests = function(logRequests) {
	    this.logRequests = logRequests;
	};
	
	this.$get = ['$q', '$log', function($q, $log) {
	    var matchlist = this.matchlist;
	    var logRequests = this.logRequests;
	    var black = this.black;
        if (logRequests) {
            $log.log("Blacklist? ",black);
        }
	    return {
		'request': function(config) {
		    //Blacklist by default, match with whitelist
		    var busted= !black; 
		    
		    for(var i=0; i< matchlist.length; i++){
			if(config.url.match(matchlist[i])) {
			    busted=black; break;
			}
		    }
		    
		    //Bust if the URL was on blacklist or not on whitelist
		    if (busted) {
			var d = new Date();
			config.url = config.url.replace(/[?|&]cacheBuster=\d+/,'');
			//Some url's allready have '?' attached
			config.url+=config.url.indexOf('?') === -1 ? '?' : '&'
			config.url += 'cacheBuster=' + d.getTime();
		    }
		    
		    if (logRequests) {
			var log='request.url =' + config.url
			busted ? $log.warn(log) : $log.info(log)
		    }

		    return config || $q.when(config);
		}
	    }
	}];
    });


