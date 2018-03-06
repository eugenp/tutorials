// jQuery REST Client - v1.0.2 - https://github.com/jpillora/jquery.rest
// Jaime Pillora <dev@jpillora.com> - MIT Copyright 2014
(function(window,$,undefined) {'use strict';
var Cache, Resource, Verb, defaultOpts, deleteWarning, encode64, error, inheritExtend, s, stringify, validateOpts, validateStr;

error = function(msg) {
  throw new Error("ERROR: jquery.rest: " + msg);
};

s = function(n) {
  var t;
  t = "";
  while (n-- > 0) {
    t += "  ";
  }
  return t;
};

encode64 = function(s) {
  if (!window.btoa) {
    error("You need a polyfill for 'btoa' to use basic auth.");
  }
  return window.btoa(s);
};

stringify = function(obj) {
  if (!window.JSON) {
    error("You need a polyfill for 'JSON' to use stringify.");
  }
  return window.JSON.stringify(obj);
};

inheritExtend = function(a, b) {
  var F;
  F = function() {};
  F.prototype = a;
  return $.extend(true, new F(), b);
};

validateOpts = function(options) {
  if (!(options && $.isPlainObject(options))) {
    return false;
  }
  $.each(options, function(name) {
    if (defaultOpts[name] === undefined) {
      return error("Unknown option: '" + name + "'");
    }
  });
  return null;
};

validateStr = function(name, str) {
  if ('string' !== $.type(str)) {
    return error("'" + name + "' must be a string");
  }
};

deleteWarning = function() {
  return alert('"delete()" has been deprecated. Please use "destroy()" or "del()" instead.');
};

defaultOpts = {
  url: '',
  cache: 0,
  request: function(resource, options) {
    return $.ajax(options);
  },
  isSingle: false,
  autoClearCache: true,
  cachableMethods: ['GET'],
  methodOverride: false,
  stringifyData: false,
  stripTrailingSlash: false,
  password: null,
  username: null,
  verbs: {
    'create': 'POST',
    'read': 'GET',
    'update': 'PUT',
    'delete': 'DELETE',
    'patch': 'PATCH'
  },
  ajax: {
    dataType: 'json'
  }
};

Cache = (function() {
  function Cache(parent) {
    this.parent = parent;
    this.c = {};
  }

  Cache.prototype.valid = function(date) {
    var diff;
    diff = new Date().getTime() - date.getTime();
    return diff <= this.parent.opts.cache * 1000;
  };

  Cache.prototype.key = function(obj) {
    var key,
      _this = this;
    key = "";
    $.each(obj, function(k, v) {
      return key += k + "=" + ($.isPlainObject(v) ? "{" + _this.key(v) + "}" : v) + "|";
    });
    return key;
  };

  Cache.prototype.get = function(key) {
    var result;
    result = this.c[key];
    if (!result) {
      return;
    }
    if (this.valid(result.created)) {
      return result.data;
    }
  };

  Cache.prototype.put = function(key, data) {
    return this.c[key] = {
      created: new Date(),
      data: data
    };
  };

  Cache.prototype.clear = function(regexp) {
    var _this = this;
    if (regexp) {
      return $.each(this.c, function(k) {
        if (k.match(regexp)) {
          return delete _this.c[k];
        }
      });
    } else {
      return this.c = {};
    }
  };

  return Cache;

})();

Verb = (function() {
  function Verb(name, method, options, parent) {
    this.name = name;
    this.method = method;
    if (options == null) {
      options = {};
    }
    this.parent = parent;
    validateStr('name', this.name);
    validateStr('method', this.method);
    validateOpts(options);
    if (this.parent[this.name]) {
      error("Cannot add Verb: '" + name + "' already exists");
    }
    this.method = method.toUpperCase();
    if (!options.url) {
      options.url = '';
    }
    this.opts = inheritExtend(this.parent.opts, options);
    this.root = this.parent.root;
    this.custom = !defaultOpts.verbs[this.name];
    this.call = $.proxy(this.call, this);
    this.call.instance = this;
  }

  Verb.prototype.call = function() {
    var data, url, _ref;
    _ref = this.parent.extractUrlData(this.method, arguments), url = _ref.url, data = _ref.data;
    if (this.custom) {
      url += this.opts.url || this.name;
    }
    return this.parent.ajax.call(this, this.method, url, data);
  };

  Verb.prototype.show = function(d) {
    return console.log(s(d) + this.name + ": " + this.method);
  };

  return Verb;

})();

Resource = (function() {
  function Resource(nameOrUrl, options, parent) {
    if (options == null) {
      options = {};
    }
    validateOpts(options);
    if (parent && parent instanceof Resource) {
      this.name = nameOrUrl;
      validateStr('name', this.name);
      this.constructChild(parent, options);
    } else {
      this.url = nameOrUrl || '';
      validateStr('url', this.url);
      this.constructRoot(options);
    }
  }

  Resource.prototype.constructRoot = function(options) {
    this.opts = inheritExtend(defaultOpts, options);
    this.root = this;
    this.expectedIds = 0;
    this.urlNoId = this.url;
    this.cache = new Cache(this);
    this.parent = null;
    return this.name = this.opts.name || 'ROOT';
  };

  Resource.prototype.constructChild = function(parent, options) {
    this.parent = parent;
    validateStr('name', this.name);
    if (!(this.parent instanceof Resource)) {
      this.error("Invalid parent");
    }
    if (this.parent[this.name]) {
      this.error("'" + name + "' already exists");
    }
    if (!options.url) {
      options.url = '';
    }
    this.opts = inheritExtend(this.parent.opts, options);
    this.opts.isSingle = 'isSingle' in options && options.isSingle;
    this.root = this.parent.root;
    this.urlNoId = this.parent.url + ("" + (this.opts.url || this.name) + "/");
    this.url = this.urlNoId;
    this.expectedIds = this.parent.expectedIds;
    if (!this.opts.isSingle) {
      this.expectedIds += 1;
      this.url += ":ID_" + this.expectedIds + "/";
    }
    $.each(this.opts.verbs, $.proxy(this.addVerb, this));
    if (this.destroy) {
      this.del = this.destroy;
      return this["delete"] = deleteWarning;
    }
  };

  Resource.prototype.error = function(msg) {
    return error("Cannot add Resource: " + msg);
  };

  Resource.prototype.add = function(name, options) {
    return this[name] = new Resource(name, options, this);
  };

  Resource.prototype.addVerb = function(name, method, options) {
    return this[name] = new Verb(name, method, options, this).call;
  };

  Resource.prototype.show = function(d) {
    if (d == null) {
      d = 0;
    }
    if (d > 25) {
      error("Plugin Bug! Recursion Fail");
    }
    if (this.name) {
      console.log(s(d) + this.name + ": " + this.url);
    }
    $.each(this, function(name, fn) {
      if ($.type(fn) === 'function' && fn.instance instanceof Verb && name !== 'del') {
        return fn.instance.show(d + 1);
      }
    });
    $.each(this, function(name, res) {
      if (name !== "parent" && name !== "root" && res instanceof Resource) {
        return res.show(d + 1);
      }
    });
    return null;
  };

  Resource.prototype.toString = function() {
    return this.name;
  };

  Resource.prototype.extractUrlData = function(name, args) {
    var arg, canUrl, canUrlNoId, data, i, id, ids, msg, params, providedIds, t, url, _i, _j, _len, _len1;
    ids = [];
    data = null;
    params = null;
    for (_i = 0, _len = args.length; _i < _len; _i++) {
      arg = args[_i];
      t = $.type(arg);
      if (t === 'string' || t === 'number') {
        ids.push(arg);
      } else if (t === 'object' && data === null) {
        data = arg;
      } else if (t === 'array' && data === null) {
        data = arg;
      } else if (t === 'object' && params === null) {
        params = arg;
      } else {
        error(("Invalid argument: " + arg + " (" + t + ").") + " Must be strings or ints (IDs) followed by one optional object and one optional query params object.");
      }
    }
    providedIds = ids.length;
    canUrl = name !== 'create';
    canUrlNoId = name !== 'update' && name !== 'delete';
    url = null;
    if (canUrl && providedIds === this.expectedIds) {
      url = this.url;
    }
    if (canUrlNoId && providedIds === this.expectedIds - 1) {
      url = this.urlNoId;
    }
    if (url === null) {
      if (canUrlNoId) {
        msg = this.expectedIds - 1;
      }
      if (canUrl) {
        msg = (msg ? msg + ' or ' : '') + this.expectedIds;
      }
      error("Invalid number of ID arguments, required " + msg + ", provided " + providedIds);
    }
    for (i = _j = 0, _len1 = ids.length; _j < _len1; i = ++_j) {
      id = ids[i];
      url = url.replace(new RegExp("\/:ID_" + (i + 1) + "\/"), "/" + id + "/");
    }
    if (params) {
      url += "?" + ($.param(params));
    }
    return {
      url: url,
      data: data
    };
  };

  Resource.prototype.ajax = function(method, url, data) {
    var ajaxOpts, encoded, escapedUrl, headers, key, req, useCache,
      _this = this;
    if (!method) {
      error("method missing");
    }
    if (!url) {
      error("url missing");
    }
    headers = {};
    if (this.opts.username && this.opts.password) {
      encoded = encode64(this.opts.username + ":" + this.opts.password);
      headers.Authorization = "Basic " + encoded;
    }
    if (data && this.opts.stringifyData && (method !== 'GET' && method !== 'HEAD')) {
      data = stringify(data);
      headers['Content-Type'] = "application/json";
    }
    if (this.opts.methodOverride && (method !== 'GET' && method !== 'HEAD' && method !== 'POST')) {
      headers['X-HTTP-Method-Override'] = method;
      method = 'POST';
    }
    if ($.cookie('X-Token')) {
        headers['X-Token'] = $.cookie('X-Token');
    }
    if (this.opts.stripTrailingSlash) {
      url = url.replace(/\/$/, "");
    }
    ajaxOpts = {
      url: url,
      type: method,
      headers: headers
    };
    if (data) {
      ajaxOpts.data = data;
    }
    ajaxOpts = $.extend(true, {}, this.opts.ajax, ajaxOpts);
    useCache = this.opts.cache && $.inArray(method, this.opts.cachableMethods) >= 0;
    if (useCache) {
      key = this.root.cache.key(ajaxOpts);
      req = this.root.cache.get(key);
      if (req) {
        return req;
      }
    }
    if (this.opts.cache && this.opts.autoClearCache && $.inArray(method, this.opts.cachableMethods) === -1) {
      escapedUrl = url.replace(/([.?*+^$[\]\\(){}|-])/g, "\\$1");
      this.root.cache.clear(new RegExp(escapedUrl));
    }
    req = this.opts.request(this.parent, ajaxOpts);
    if (useCache) {
      req.done(function() {
        return _this.root.cache.put(key, req);
      });
    }
    return req;
  };

  return Resource;

})();

Resource.defaults = defaultOpts;

$.RestClient = Resource;
}.call(this,window,jQuery));