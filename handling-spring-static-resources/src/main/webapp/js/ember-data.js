/*!
 * @overview  Ember Data
 * @copyright Copyright 2011-2014 Tilde Inc. and contributors.
 *            Portions Copyright 2011 LivingSocial Inc.
 * @license   Licensed under MIT license (see license.js)
 * @version   1.0.0-beta.5
 */


(function() {
var define, requireModule;

(function() {
  var registry = {}, seen = {};

  define = function(name, deps, callback) {
    registry[name] = { deps: deps, callback: callback };
  };

  requireModule = function(name) {
    if (seen[name]) { return seen[name]; }
    seen[name] = {};

    var mod, deps, callback, reified , exports;

    mod = registry[name];

    if (!mod) {
      throw new Error("Module '" + name + "' not found.");
    }

    deps = mod.deps;
    callback = mod.callback;
    reified = [];
    exports;

    for (var i=0, l=deps.length; i<l; i++) {
      if (deps[i] === 'exports') {
        reified.push(exports = {});
      } else {
        reified.push(requireModule(deps[i]));
      }
    }

    var value = callback.apply(this, reified);
    return seen[name] = exports || value;
  };
})();
(function() {
/**
  @module ember-data
*/

/**
  All Ember Data methods and functions are defined inside of this namespace.

  @class DS
  @static
*/
var DS;
if ('undefined' === typeof DS) {
  /**
    @property VERSION
    @type String
    @default '1.0.0-beta.5'
    @static
  */
  DS = Ember.Namespace.create({
    VERSION: '1.0.0-beta.5'
  });

  if ('undefined' !== typeof window) {
    window.DS = DS;
  }

  if (Ember.libraries) {
    Ember.libraries.registerCoreLibrary('Ember Data', DS.VERSION);
  }
}

})();



(function() {
var get = Ember.get, set = Ember.set, isNone = Ember.isNone;

// Simple dispatcher to support overriding the aliased
// method in subclasses.
function aliasMethod(methodName) {
  return function() {
    return this[methodName].apply(this, arguments);
  };
}

/**
  In Ember Data a Serializer is used to serialize and deserialize
  records when they are transfered in and out of an external source.
  This process involves normalizing property names, transforming
  attribute values and serializeing relationships.

  For maximum performance Ember Data recomends you use the
  [RESTSerializer](DS.RESTSerializer.html) or one of its subclasses.

  `JSONSerializer` is useful for simpler or legacy backends that may
  not support the http://jsonapi.org/ spec.

  @class JSONSerializer
  @namespace DS
*/
DS.JSONSerializer = Ember.Object.extend({
  /**
    The primaryKey is used when serializing and deserializing
    data. Ember Data always uses the `id` propery to store the id of
    the record. The external source may not always follow this
    convention. In these cases it is usesful to override the
    primaryKey property to match the primaryKey of your external
    store.

    Example

    ```javascript
    App.ApplicationSerializer = DS.JSONSerializer.extend({
      primaryKey: '_id'
    });
    ```

    @property primaryKey
    @type {String}
    @default 'id'
  */
  primaryKey: 'id',

  /**
   Given a subclass of `DS.Model` and a JSON object this method will
   iterate through each attribute of the `DS.Model` and invoke the
   `DS.Transform#deserialize` method on the matching property of the
   JSON object.  This method is typically called after the
   serializer's `normalize` method.

   @method applyTransforms
   @private
   @param {subclass of DS.Model} type
   @param {Object} data The data to transform
   @return {Object} data The transformed data object
  */
  applyTransforms: function(type, data) {
    type.eachTransformedAttribute(function(key, type) {
      var transform = this.transformFor(type);
      data[key] = transform.deserialize(data[key]);
    }, this);

    return data;
  },

  /**
    Normalizes a part of the JSON payload returned by
    the server. You should override this method, munge the hash
    and call super if you have generic normalization to do.

    It takes the type of the record that is being normalized
    (as a DS.Model class), the property where the hash was
    originally found, and the hash to normalize.

    You can use this method, for example, to normalize underscored keys to camelized
    or other general-purpose normalizations.

    Example

    ```javascript
    App.ApplicationSerializer = DS.JSONSerializer.extend({
      normalize: function(type, hash) {
        var fields = Ember.get(type, 'fields');
        fields.forEach(function(field) {
          var payloadField = Ember.String.underscore(field);
          if (field === payloadField) { return; }

          hash[field] = hash[payloadField];
          delete hash[payloadField];
        });
        return this._super.apply(this, arguments);
      }
    });
    ```

    @method normalize
    @param {subclass of DS.Model} type
    @param {Object} hash
    @return {Object}
  */
  normalize: function(type, hash) {
    if (!hash) { return hash; }

    this.applyTransforms(type, hash);
    return hash;
  },

  // SERIALIZE
  /**
    Called when a record is saved in order to convert the
    record into JSON.

    By default, it creates a JSON object with a key for
    each attribute and belongsTo relationship.

    For example, consider this model:

    ```javascript
    App.Comment = DS.Model.extend({
      title: DS.attr(),
      body: DS.attr(),

      author: DS.belongsTo('user')
    });
    ```

    The default serialization would create a JSON object like:

    ```javascript
    {
      "title": "Rails is unagi",
      "body": "Rails? Omakase? O_O",
      "author": 12
    }
    ```

    By default, attributes are passed through as-is, unless
    you specified an attribute type (`DS.attr('date')`). If
    you specify a transform, the JavaScript value will be
    serialized when inserted into the JSON hash.

    By default, belongs-to relationships are converted into
    IDs when inserted into the JSON hash.

    ## IDs

    `serialize` takes an options hash with a single option:
    `includeId`. If this option is `true`, `serialize` will,
    by default include the ID in the JSON object it builds.

    The adapter passes in `includeId: true` when serializing
    a record for `createRecord`, but not for `updateRecord`.

    ## Customization

    Your server may expect a different JSON format than the
    built-in serialization format.

    In that case, you can implement `serialize` yourself and
    return a JSON hash of your choosing.

    ```javascript
    App.PostSerializer = DS.JSONSerializer.extend({
      serialize: function(post, options) {
        var json = {
          POST_TTL: post.get('title'),
          POST_BDY: post.get('body'),
          POST_CMS: post.get('comments').mapProperty('id')
        }

        if (options.includeId) {
          json.POST_ID_ = post.get('id');
        }

        return json;
      }
    });
    ```

    ## Customizing an App-Wide Serializer

    If you want to define a serializer for your entire
    application, you'll probably want to use `eachAttribute`
    and `eachRelationship` on the record.

    ```javascript
    App.ApplicationSerializer = DS.JSONSerializer.extend({
      serialize: function(record, options) {
        var json = {};

        record.eachAttribute(function(name) {
          json[serverAttributeName(name)] = record.get(name);
        })

        record.eachRelationship(function(name, relationship) {
          if (relationship.kind === 'hasMany') {
            json[serverHasManyName(name)] = record.get(name).mapBy('id');
          }
        });

        if (options.includeId) {
          json.ID_ = record.get('id');
        }

        return json;
      }
    });

    function serverAttributeName(attribute) {
      return attribute.underscore().toUpperCase();
    }

    function serverHasManyName(name) {
      return serverAttributeName(name.singularize()) + "_IDS";
    }
    ```

    This serializer will generate JSON that looks like this:

    ```javascript
    {
      "TITLE": "Rails is omakase",
      "BODY": "Yep. Omakase.",
      "COMMENT_IDS": [ 1, 2, 3 ]
    }
    ```

    ## Tweaking the Default JSON

    If you just want to do some small tweaks on the default JSON,
    you can call super first and make the tweaks on the returned
    JSON.

    ```javascript
    App.PostSerializer = DS.JSONSerializer.extend({
      serialize: function(record, options) {
        var json = this._super.apply(this, arguments);

        json.subject = json.title;
        delete json.title;

        return json;
      }
    });
    ```

    @method serialize
    @param {subclass of DS.Model} record
    @param {Object} options
    @return {Object} json
  */
  serialize: function(record, options) {
    var json = {};

    if (options && options.includeId) {
      var id = get(record, 'id');

      if (id) {
        json[get(this, 'primaryKey')] = id;
      }
    }

    record.eachAttribute(function(key, attribute) {
      this.serializeAttribute(record, json, key, attribute);
    }, this);

    record.eachRelationship(function(key, relationship) {
      if (relationship.kind === 'belongsTo') {
        this.serializeBelongsTo(record, json, relationship);
      } else if (relationship.kind === 'hasMany') {
        this.serializeHasMany(record, json, relationship);
      }
    }, this);

    return json;
  },

  /**
   `serializeAttribute` can be used to customize how `DS.attr`
   properties are serialized

   For example if you wanted to ensure all you attributes were always
   serialized as properties on an `attributes` object you could
   write:

   ```javascript
   App.ApplicationSerializer = DS.JSONSerializer.extend({
     serializeAttribute: function(record, json, key, attributes) {
       json.attributes = json.attributes || {};
       this._super(record, json.attributes, key, attributes);
     }
   });
   ```

   @method serializeAttribute
   @param {DS.Model} record
   @param {Object} json
   @param {String} key
   @param {Object} attribute
  */
  serializeAttribute: function(record, json, key, attribute) {
    var attrs = get(this, 'attrs');
    var value = get(record, key), type = attribute.type;

    if (type) {
      var transform = this.transformFor(type);
      value = transform.serialize(value);
    }

    // if provided, use the mapping provided by `attrs` in
    // the serializer
    key = attrs && attrs[key] || (this.keyForAttribute ? this.keyForAttribute(key) : key);

    json[key] = value;
  },

  /**
   `serializeBelongsTo` can be used to customize how `DS.belongsTo`
   properties are serialized.

   Example

   ```javascript
   App.PostSerializer = DS.JSONSerializer.extend({
     serializeBelongsTo: function(record, json, relationship) {
       var key = relationship.key;

       var belongsTo = get(record, key);

       key = this.keyForRelationship ? this.keyForRelationship(key, "belongsTo") : key;

       json[key] = Ember.isNone(belongsTo) ? belongsTo : belongsTo.toJSON();
     }
   });
   ```

   @method serializeBelongsTo
   @param {DS.Model} record
   @param {Object} json
   @param {Object} relationship
  */
  serializeBelongsTo: function(record, json, relationship) {
    var key = relationship.key;

    var belongsTo = get(record, key);

    key = this.keyForRelationship ? this.keyForRelationship(key, "belongsTo") : key;

    if (isNone(belongsTo)) {
      json[key] = belongsTo;
    } else {
      json[key] = get(belongsTo, 'id');
    }

    if (relationship.options.polymorphic) {
      this.serializePolymorphicType(record, json, relationship);
    }
  },

  /**
   `serializeHasMany` can be used to customize how `DS.hasMany`
   properties are serialized.

   Example

   ```javascript
   App.PostSerializer = DS.JSONSerializer.extend({
     serializeHasMany: function(record, json, relationship) {
       var key = relationship.key;
       if (key === 'comments') {
         return;
       } else {
         this._super.apply(this, arguments);
       }
     }
   });
   ```

   @method serializeHasMany
   @param {DS.Model} record
   @param {Object} json
   @param {Object} relationship
  */
  serializeHasMany: function(record, json, relationship) {
    var key = relationship.key;

    var relationshipType = DS.RelationshipChange.determineRelationshipType(record.constructor, relationship);

    if (relationshipType === 'manyToNone' || relationshipType === 'manyToMany') {
      json[key] = get(record, key).mapBy('id');
      // TODO support for polymorphic manyToNone and manyToMany relationships
    }
  },

  /**
    You can use this method to customize how polymorphic objects are
    serialized. Objects are considered to be polymorphic if
    `{polymorphic: true}` is pass as the second argument to the
    `DS.belongsTo` function.

    Example

    ```javascript
    App.CommentSerializer = DS.JSONSerializer.extend({
      serializePolymorphicType: function(record, json, relationship) {
        var key = relationship.key,
            belongsTo = get(record, key);
        key = this.keyForAttribute ? this.keyForAttribute(key) : key;
        json[key + "_type"] = belongsTo.constructor.typeKey;
      }
    });
   ```

    @method serializePolymorphicType
    @param {DS.Model} record
    @param {Object} json
    @param {Object} relationship
  */
  serializePolymorphicType: Ember.K,

  // EXTRACT

  /**
    The `extract` method is used to deserialize payload data from the
    server. By default the `JSONSerializer` does not push the records
    into the store. However records that subclass `JSONSerializer`
    such as the `RESTSerializer` may push records into the store as
    part of the extract call.

    This method deletegates to a more specific extract method based on
    the `requestType`.

    Example

    ```javascript
    var get = Ember.get;
    socket.on('message', function(message) {
      var modelName = message.model;
      var data = message.data;
      var type = store.modelFor(modelName);
      var serializer = store.serializerFor(type.typeKey);
      var record = serializer.extract(store, type, data, get(data, 'id'), 'single');
      store.push(modelName, record);
    });
    ```

    @method extract
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} payload
    @param {String or Number} id
    @param {String} requestType
    @return {Object} json The deserialized payload
  */
  extract: function(store, type, payload, id, requestType) {
    this.extractMeta(store, type, payload);

    var specificExtract = "extract" + requestType.charAt(0).toUpperCase() + requestType.substr(1);
    return this[specificExtract](store, type, payload, id, requestType);
  },

  /**
    `extractFindAll` is a hook into the extract method used when a
    call is made to `DS.Store#findAll`. By default this method is an
    alias for [extractArray](#method_extractArray).

    @method extractFindAll
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} payload
    @return {Array} array An array of deserialized objects
  */
  extractFindAll: aliasMethod('extractArray'),
  /**
    `extractFindQuery` is a hook into the extract method used when a
    call is made to `DS.Store#findQuery`. By default this method is an
    alias for [extractArray](#method_extractArray).

    @method extractFindQuery
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} payload
    @return {Array} array An array of deserialized objects
  */
  extractFindQuery: aliasMethod('extractArray'),
  /**
    `extractFindMany` is a hook into the extract method used when a
    call is made to `DS.Store#findMany`. By default this method is
    alias for [extractArray](#method_extractArray).

    @method extractFindMany
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} payload
    @return {Array} array An array of deserialized objects
  */
  extractFindMany: aliasMethod('extractArray'),
  /**
    `extractFindHasMany` is a hook into the extract method used when a
    call is made to `DS.Store#findHasMany`. By default this method is
    alias for [extractArray](#method_extractArray).

    @method extractFindHasMany
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} payload
    @return {Array} array An array of deserialized objects
  */
  extractFindHasMany: aliasMethod('extractArray'),

  /**
    `extractCreateRecord` is a hook into the extract method used when a
    call is made to `DS.Store#createRecord`. By default this method is
    alias for [extractSave](#method_extractSave).

    @method extractCreateRecord
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} payload
    @return {Object} json The deserialized payload
  */
  extractCreateRecord: aliasMethod('extractSave'),
  /**
    `extractUpdateRecord` is a hook into the extract method used when
    a call is made to `DS.Store#update`. By default this method is alias
    for [extractSave](#method_extractSave).

    @method extractUpdateRecord
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} payload
    @return {Object} json The deserialized payload
  */
  extractUpdateRecord: aliasMethod('extractSave'),
  /**
    `extractDeleteRecord` is a hook into the extract method used when
    a call is made to `DS.Store#deleteRecord`. By default this method is
    alias for [extractSave](#method_extractSave).

    @method extractDeleteRecord
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} payload
    @return {Object} json The deserialized payload
  */
  extractDeleteRecord: aliasMethod('extractSave'),

  /**
    `extractFind` is a hook into the extract method used when
    a call is made to `DS.Store#find`. By default this method is
    alias for [extractSingle](#method_extractSingle).

    @method extractFind
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} payload
    @return {Object} json The deserialized payload
  */
  extractFind: aliasMethod('extractSingle'),
  /**
    `extractFindBelongsTo` is a hook into the extract method used when
    a call is made to `DS.Store#findBelongsTo`. By default this method is
    alias for [extractSingle](#method_extractSingle).

    @method extractFindBelongsTo
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} payload
    @return {Object} json The deserialized payload
  */
  extractFindBelongsTo: aliasMethod('extractSingle'),
  /**
    `extractSave` is a hook into the extract method used when a call
    is made to `DS.Model#save`. By default this method is alias
    for [extractSingle](#method_extractSingle).

    @method extractSave
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} payload
    @return {Object} json The deserialized payload
  */
  extractSave: aliasMethod('extractSingle'),

  /**
    `extractSingle` is used to deserialize a single record returned
    from the adapter.

    Example

    ```javascript
    App.PostSerializer = DS.JSONSerializer.extend({
      extractSingle: function(store, type, payload) {
        payload.comments = payload._embedded.comment;
        delete payload._embedded;

        return this._super(store, type, payload);
      },
    });
    ```

    @method extractSingle
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} payload
    @return {Object} json The deserialized payload
  */
  extractSingle: function(store, type, payload) {
    return this.normalize(type, payload);
  },

  /**
    `extractArray` is used to deserialize an array of records
    returned from the adapter.

    Example

    ```javascript
    App.PostSerializer = DS.JSONSerializer.extend({
      extractArray: function(store, type, payload) {
        return payload.map(function(json) {
          return this.extractSingle(json);
        }, this);
      }
    });
    ```

    @method extractArray
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} payload
    @return {Array} array An array of deserialized objects
  */
  extractArray: function(store, type, payload) {
    return this.normalize(type, payload);
  },

  /**
    `extractMeta` is used to deserialize any meta information in the
    adapter payload. By default Ember Data expects meta information to
    be located on the `meta` property of the payload object.

    Example

    ```javascript
    App.PostSerializer = DS.JSONSerializer.extend({
      extractMeta: function(store, type, payload) {
        if (payload && payload._pagination) {
          store.metaForType(type, payload._pagination);
          delete payload._pagination;
        }
      }
    });
    ```

    @method extractMeta
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} payload
  */
  extractMeta: function(store, type, payload) {
    if (payload && payload.meta) {
      store.metaForType(type, payload.meta);
      delete payload.meta;
    }
  },

  /**
   `keyForAttribute` can be used to define rules for how to convert an
   attribute name in your model to a key in your JSON.

   Example

   ```javascript
   App.ApplicationSerializer = DS.RESTSerializer.extend({
     keyForAttribute: function(attr) {
       return Ember.String.underscore(attr).toUpperCase();
     }
   });
   ```

   @method keyForAttribute
   @param {String} key
   @return {String} normalized key
  */


  /**
   `keyForRelationship` can be used to define a custom key when
   serializeing relationship properties. By default `JSONSerializer`
   does not provide an implementation of this method.

   Example

    ```javascript
    App.PostSerializer = DS.JSONSerializer.extend({
      keyForRelationship: function(key, relationship) {
         return 'rel_' + Ember.String.underscore(key);
      }
    });
    ```

   @method keyForRelationship
   @param {String} key
   @param {String} relationship type
   @return {String} normalized key
  */

  // HELPERS

  /**
   @method transformFor
   @private
   @param {String} attributeType
   @param {Boolean} skipAssertion
   @return {DS.Transform} transform
  */
  transformFor: function(attributeType, skipAssertion) {
    var transform = this.container.lookup('transform:' + attributeType);
    Ember.assert("Unable to find transform for '" + attributeType + "'", skipAssertion || !!transform);
    return transform;
  }
});

})();



(function() {
/**
  @module ember-data
*/
var get = Ember.get, capitalize = Ember.String.capitalize, underscore = Ember.String.underscore, DS = window.DS ;

/**
  Extend `Ember.DataAdapter` with ED specific code.

  @class DebugAdapter
  @namespace DS
  @extends Ember.DataAdapter
  @private
*/
DS.DebugAdapter = Ember.DataAdapter.extend({
  getFilters: function() {
    return [
      { name: 'isNew', desc: 'New' },
      { name: 'isModified', desc: 'Modified' },
      { name: 'isClean', desc: 'Clean' }
    ];
  },

  detect: function(klass) {
    return klass !== DS.Model && DS.Model.detect(klass);
  },

  columnsForType: function(type) {
    var columns = [{ name: 'id', desc: 'Id' }], count = 0, self = this;
    get(type, 'attributes').forEach(function(name, meta) {
        if (count++ > self.attributeLimit) { return false; }
        var desc = capitalize(underscore(name).replace('_', ' '));
        columns.push({ name: name, desc: desc });
    });
    return columns;
  },

  getRecords: function(type) {
    return this.get('store').all(type);
  },

  getRecordColumnValues: function(record) {
    var self = this, count = 0,
        columnValues = { id: get(record, 'id') };

    record.eachAttribute(function(key) {
      if (count++ > self.attributeLimit) {
        return false;
      }
      var value = get(record, key);
      columnValues[key] = value;
    });
    return columnValues;
  },

  getRecordKeywords: function(record) {
    var keywords = [], keys = Ember.A(['id']);
    record.eachAttribute(function(key) {
      keys.push(key);
    });
    keys.forEach(function(key) {
      keywords.push(get(record, key));
    });
    return keywords;
  },

  getRecordFilterValues: function(record) {
    return {
      isNew: record.get('isNew'),
      isModified: record.get('isDirty') && !record.get('isNew'),
      isClean: !record.get('isDirty')
    };
  },

  getRecordColor: function(record) {
    var color = 'black';
    if (record.get('isNew')) {
      color = 'green';
    } else if (record.get('isDirty')) {
      color = 'blue';
    }
    return color;
  },

  observeRecord: function(record, recordUpdated) {
    var releaseMethods = Ember.A(), self = this,
        keysToObserve = Ember.A(['id', 'isNew', 'isDirty']);

    record.eachAttribute(function(key) {
      keysToObserve.push(key);
    });

    keysToObserve.forEach(function(key) {
      var handler = function() {
        recordUpdated(self.wrapRecord(record));
      };
      Ember.addObserver(record, key, handler);
      releaseMethods.push(function() {
        Ember.removeObserver(record, key, handler);
      });
    });

    var release = function() {
      releaseMethods.forEach(function(fn) { fn(); } );
    };

    return release;
  }

});

})();



(function() {
/**
  The `DS.Transform` class is used to serialize and deserialize model
  attributes when they are saved or loaded from an
  adapter. Subclassing `DS.Transform` is useful for creating custom
  attributes. All subclasses of `DS.Transform` must implement a
  `serialize` and a `deserialize` method.

  Example

  ```javascript
  App.RawTransform = DS.Transform.extend({
    deserialize: function(serialized) {
      return serialized;
    },
    serialize: function(deserialized) {
      return deserialized;
    }
  });
  ```

  Usage

  ```javascript
  var attr = DS.attr;
  App.Requirement = DS.Model.extend({
    name: attr('string'),
    optionsArray: attr('raw')
  });
  ```

  @class Transform
  @namespace DS
 */
DS.Transform = Ember.Object.extend({
  /**
    When given a deserialized value from a record attribute this
    method must return the serialized value.

    Example

    ```javascript
    serialize: function(deserialized) {
      return Ember.isEmpty(deserialized) ? null : Number(deserialized);
    }
    ```

    @method serialize
    @param deserialized The deserialized value
    @return The serialized value
  */
  serialize: Ember.required(),

  /**
    When given a serialize value from a JSON object this method must
    return the deserialized value for the record attribute.

    Example

    ```javascript
    deserialize: function(serialized) {
      return empty(serialized) ? null : Number(serialized);
    }
    ```

    @method deserialize
    @param serialized The serialized value
    @return The deserialized value
  */
  deserialize: Ember.required()

});

})();



(function() {

/**
  The `DS.BooleanTransform` class is used to serialize and deserialize
  boolean attributes on Ember Data record objects. This transform is
  used when `boolean` is passed as the type parameter to the
  [DS.attr](../../data#method_attr) function.

  Usage

  ```javascript
  var attr = DS.attr;
  App.User = DS.Model.extend({
    isAdmin: attr('boolean'),
    name: attr('string'),
    email: attr('string')
  });
  ```

  @class BooleanTransform
  @extends DS.Transform
  @namespace DS
 */
DS.BooleanTransform = DS.Transform.extend({
  deserialize: function(serialized) {
    var type = typeof serialized;

    if (type === "boolean") {
      return serialized;
    } else if (type === "string") {
      return serialized.match(/^true$|^t$|^1$/i) !== null;
    } else if (type === "number") {
      return serialized === 1;
    } else {
      return false;
    }
  },

  serialize: function(deserialized) {
    return Boolean(deserialized);
  }
});

})();



(function() {
/**
  The `DS.DateTransform` class is used to serialize and deserialize
  date attributes on Ember Data record objects. This transform is used
  when `date` is passed as the type parameter to the
  [DS.attr](../../data#method_attr) function.

  ```javascript
  var attr = DS.attr;
  App.Score = DS.Model.extend({
    value: attr('number'),
    player: DS.belongsTo('player'),
    date: attr('date')
  });
  ```

  @class DateTransform
  @extends DS.Transform
  @namespace DS
 */
DS.DateTransform = DS.Transform.extend({

  deserialize: function(serialized) {
    var type = typeof serialized;

    if (type === "string") {
      return new Date(Ember.Date.parse(serialized));
    } else if (type === "number") {
      return new Date(serialized);
    } else if (serialized === null || serialized === undefined) {
      // if the value is not present in the data,
      // return undefined, not null.
      return serialized;
    } else {
      return null;
    }
  },

  serialize: function(date) {
    if (date instanceof Date) {
      var days = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
      var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

      var pad = function(num) {
        return num < 10 ? "0"+num : ""+num;
      };

      var utcYear = date.getUTCFullYear(),
          utcMonth = date.getUTCMonth(),
          utcDayOfMonth = date.getUTCDate(),
          utcDay = date.getUTCDay(),
          utcHours = date.getUTCHours(),
          utcMinutes = date.getUTCMinutes(),
          utcSeconds = date.getUTCSeconds();


      var dayOfWeek = days[utcDay];
      var dayOfMonth = pad(utcDayOfMonth);
      var month = months[utcMonth];

      return dayOfWeek + ", " + dayOfMonth + " " + month + " " + utcYear + " " +
             pad(utcHours) + ":" + pad(utcMinutes) + ":" + pad(utcSeconds) + " GMT";
    } else {
      return null;
    }
  } 

});

})();



(function() {
var empty = Ember.isEmpty;
/**
  The `DS.NumberTransform` class is used to serialize and deserialize
  numeric attributes on Ember Data record objects. This transform is
  used when `number` is passed as the type parameter to the
  [DS.attr](../../data#method_attr) function.

  Usage

  ```javascript
  var attr = DS.attr;
  App.Score = DS.Model.extend({
    value: attr('number'),
    player: DS.belongsTo('player'),
    date: attr('date')
  });
  ```

  @class NumberTransform
  @extends DS.Transform
  @namespace DS
 */
DS.NumberTransform = DS.Transform.extend({

  deserialize: function(serialized) {
    return empty(serialized) ? null : Number(serialized);
  },

  serialize: function(deserialized) {
    return empty(deserialized) ? null : Number(deserialized);
  }
});

})();



(function() {
var none = Ember.isNone;

/**
  The `DS.StringTransform` class is used to serialize and deserialize
  string attributes on Ember Data record objects. This transform is
  used when `string` is passed as the type parameter to the
  [DS.attr](../../data#method_attr) function.

  Usage

  ```javascript
  var attr = DS.attr;
  App.User = DS.Model.extend({
    isAdmin: attr('boolean'),
    name: attr('string'),
    email: attr('string')
  });
  ```

  @class StringTransform
  @extends DS.Transform
  @namespace DS
 */
DS.StringTransform = DS.Transform.extend({

  deserialize: function(serialized) {
    return none(serialized) ? null : String(serialized);
  },

  serialize: function(deserialized) {
    return none(deserialized) ? null : String(deserialized);
  }

});

})();



(function() {

})();



(function() {
/**
  @module ember-data
*/

var set = Ember.set;

/*
  This code registers an injection for Ember.Application.

  If an Ember.js developer defines a subclass of DS.Store on their application,
  this code will automatically instantiate it and make it available on the
  router.

  Additionally, after an application's controllers have been injected, they will
  each have the store made available to them.

  For example, imagine an Ember.js application with the following classes:

  App.Store = DS.Store.extend({
    adapter: 'custom'
  });

  App.PostsController = Ember.ArrayController.extend({
    // ...
  });

  When the application is initialized, `App.Store` will automatically be
  instantiated, and the instance of `App.PostsController` will have its `store`
  property set to that instance.

  Note that this code will only be run if the `ember-application` package is
  loaded. If Ember Data is being used in an environment other than a
  typical application (e.g., node.js where only `ember-runtime` is available),
  this code will be ignored.
*/

Ember.onLoad('Ember.Application', function(Application) {
  Application.initializer({
    name: "store",

    initialize: function(container, application) {
      application.register('store:main', application.Store || DS.Store);
      application.register('serializer:_default', DS.JSONSerializer);
      application.register('serializer:_rest', DS.RESTSerializer);
      application.register('adapter:_rest', DS.RESTAdapter);

      // Eagerly generate the store so defaultStore is populated.
      // TODO: Do this in a finisher hook
      container.lookup('store:main');
    }
  });

  Application.initializer({
    name: "transforms",
    before: "store",

    initialize: function(container, application) {
      application.register('transform:boolean', DS.BooleanTransform);
      application.register('transform:date', DS.DateTransform);
      application.register('transform:number', DS.NumberTransform);
      application.register('transform:string', DS.StringTransform);
    }
  });

  Application.initializer({
    name: "dataAdapter",
    before: "store",

    initialize: function(container, application) {
      application.register('dataAdapter:main', DS.DebugAdapter);
    }
  });

  Application.initializer({
    name: "injectStore",
    before: "store",

    initialize: function(container, application) {
      application.inject('controller', 'store', 'store:main');
      application.inject('route', 'store', 'store:main');
      application.inject('serializer', 'store', 'store:main');
      application.inject('dataAdapter', 'store', 'store:main');
    }
  });

});

})();



(function() {
/**
  @module ember-data
*/

/**
  Date.parse with progressive enhancement for ISO 8601 <https://github.com/csnover/js-iso8601>

  © 2011 Colin Snover <http://zetafleet.com>

  Released under MIT license.

  @class Date
  @namespace Ember
  @static
*/
Ember.Date = Ember.Date || {};

var origParse = Date.parse, numericKeys = [ 1, 4, 5, 6, 7, 10, 11 ];

/**
  @method parse
  @param date
*/
Ember.Date.parse = function (date) {
    var timestamp, struct, minutesOffset = 0;

    // ES5 §15.9.4.2 states that the string should attempt to be parsed as a Date Time String Format string
    // before falling back to any implementation-specific date parsing, so that’s what we do, even if native
    // implementations could be faster
    //              1 YYYY                2 MM       3 DD           4 HH    5 mm       6 ss        7 msec        8 Z 9 ±    10 tzHH    11 tzmm
    if ((struct = /^(\d{4}|[+\-]\d{6})(?:-(\d{2})(?:-(\d{2}))?)?(?:T(\d{2}):(\d{2})(?::(\d{2})(?:\.(\d{3}))?)?(?:(Z)|([+\-])(\d{2})(?::(\d{2}))?)?)?$/.exec(date))) {
        // avoid NaN timestamps caused by “undefined” values being passed to Date.UTC
        for (var i = 0, k; (k = numericKeys[i]); ++i) {
            struct[k] = +struct[k] || 0;
        }

        // allow undefined days and months
        struct[2] = (+struct[2] || 1) - 1;
        struct[3] = +struct[3] || 1;

        if (struct[8] !== 'Z' && struct[9] !== undefined) {
            minutesOffset = struct[10] * 60 + struct[11];

            if (struct[9] === '+') {
                minutesOffset = 0 - minutesOffset;
            }
        }

        timestamp = Date.UTC(struct[1], struct[2], struct[3], struct[4], struct[5] + minutesOffset, struct[6], struct[7]);
    }
    else {
        timestamp = origParse ? origParse(date) : NaN;
    }

    return timestamp;
};

if (Ember.EXTEND_PROTOTYPES === true || Ember.EXTEND_PROTOTYPES.Date) {
  Date.parse = Ember.Date.parse;
}

})();



(function() {

})();



(function() {
/**
  @module ember-data
*/

var get = Ember.get, set = Ember.set;

/**
  A record array is an array that contains records of a certain type. The record
  array materializes records as needed when they are retrieved for the first
  time. You should not create record arrays yourself. Instead, an instance of
  `DS.RecordArray` or its subclasses will be returned by your application's store
  in response to queries.

  @class RecordArray
  @namespace DS
  @extends Ember.ArrayProxy
  @uses Ember.Evented
*/

DS.RecordArray = Ember.ArrayProxy.extend(Ember.Evented, {
  /**
    The model type contained by this record array.

    @property type
    @type DS.Model
  */
  type: null,

  /**
    The array of client ids backing the record array. When a
    record is requested from the record array, the record
    for the client id at the same index is materialized, if
    necessary, by the store.

    @property content
    @private
    @type Ember.Array
  */
  content: null,

  /**
    The flag to signal a `RecordArray` is currently loading data.

    Example

    ```javascript
    var people = store.all(App.Person);
    people.get('isLoaded'); // true
    ```

    @property isLoaded
    @type Boolean
  */
  isLoaded: false,
  /**
    The flag to signal a `RecordArray` is currently loading data.

    Example

    ```javascript
    var people = store.all(App.Person);
    people.get('isUpdating'); // false
    people.update();
    people.get('isUpdating'); // true
    ```

    @property isUpdating
    @type Boolean
  */
  isUpdating: false,

  /**
    The store that created this record array.

    @property store
    @private
    @type DS.Store
  */
  store: null,

  /**
    Retrieves an object from the content by index.

    @method objectAtContent
    @private
    @param {Number} index
    @return {DS.Model} record
  */
  objectAtContent: function(index) {
    var content = get(this, 'content');

    return content.objectAt(index);
  },

  /**
    Used to get the latest version of all of the records in this array
    from the adapter.

    Example

    ```javascript
    var people = store.all(App.Person);
    people.get('isUpdating'); // false
    people.update();
    people.get('isUpdating'); // true
    ```

    @method update
  */
  update: function() {
    if (get(this, 'isUpdating')) { return; }

    var store = get(this, 'store'),
        type = get(this, 'type');

    store.fetchAll(type, this);
  },

  /**
    Adds a record to the `RecordArray`.

    @method addRecord
    @private
    @param {DS.Model} record
  */
  addRecord: function(record) {
    get(this, 'content').addObject(record);
  },

  /**
    Removes a record to the `RecordArray`.

    @method removeRecord
    @private
    @param {DS.Model} record
  */
  removeRecord: function(record) {
    get(this, 'content').removeObject(record);
  },

  /**
    Saves all of the records in the `RecordArray`.

    Example

    ```javascript
    var messages = store.all(App.Message);
    messages.forEach(function(message) {
      message.set('hasBeenSeen', true);
    });
    messages.save();
    ```

    @method save
    @return {DS.PromiseArray} promise
  */
  save: function() {
    var promiseLabel = "DS: RecordArray#save " + get(this, 'type');
    var promise = Ember.RSVP.all(this.invoke("save"), promiseLabel).then(function(array) {
      return Ember.A(array);
    }, null, "DS: RecordArray#save apply Ember.NativeArray");

    return DS.PromiseArray.create({ promise: promise });
  }
});

})();



(function() {
/**
  @module ember-data
*/

var get = Ember.get;

/**
  Represents a list of records whose membership is determined by the
  store. As records are created, loaded, or modified, the store
  evaluates them to determine if they should be part of the record
  array.

  @class FilteredRecordArray
  @namespace DS
  @extends DS.RecordArray
*/
DS.FilteredRecordArray = DS.RecordArray.extend({
  /**
    The filterFunction is a function used to test records from the store to
    determine if they should be part of the record array.

    Example

    ```javascript
    var allPeople = store.all('person');
    allPeople.mapBy('name'); // ["Tom Dale", "Yehuda Katz", "Trek Glowacki"]

    var people = store.filter('person', function(person) {
      if (person.get('name').match(/Katz$/)) { return true; }
    });
    people.mapBy('name'); // ["Yehuda Katz"]

    var notKatzFilter = function(person) {
      return !person.get('name').match(/Katz$/);
    };
    people.set('filterFunction', notKatzFilter);
    people.mapBy('name'); // ["Tom Dale", "Trek Glowacki"]
    ```

    @method filterFunction
    @param {DS.Model} record
    @return {Boolean} `true` if the record should be in the array
  */
  filterFunction: null,
  isLoaded: true,

  replace: function() {
    var type = get(this, 'type').toString();
    throw new Error("The result of a client-side filter (on " + type + ") is immutable.");
  },

  /**
    @method updateFilter
    @private
  */
  updateFilter: Ember.observer(function() {
    var manager = get(this, 'manager');
    manager.updateFilter(this, get(this, 'type'), get(this, 'filterFunction'));
  }, 'filterFunction')
});

})();



(function() {
/**
  @module ember-data
*/

var get = Ember.get, set = Ember.set;

/**
  Represents an ordered list of records whose order and membership is
  determined by the adapter. For example, a query sent to the adapter
  may trigger a search on the server, whose results would be loaded
  into an instance of the `AdapterPopulatedRecordArray`.

  @class AdapterPopulatedRecordArray
  @namespace DS
  @extends DS.RecordArray
*/
DS.AdapterPopulatedRecordArray = DS.RecordArray.extend({
  query: null,

  replace: function() {
    var type = get(this, 'type').toString();
    throw new Error("The result of a server query (on " + type + ") is immutable.");
  },

  /**
    @method load
    @private
    @param {Array} data
  */
  load: function(data) {
    var store = get(this, 'store'),
        type = get(this, 'type'),
        records = store.pushMany(type, data),
        meta = store.metadataFor(type);

    this.setProperties({
      content: Ember.A(records),
      isLoaded: true,
      meta: meta
    });

    // TODO: does triggering didLoad event should be the last action of the runLoop?
    Ember.run.once(this, 'trigger', 'didLoad');
  }
});

})();



(function() {
/**
  @module ember-data
*/

var get = Ember.get, set = Ember.set;
var map = Ember.EnumerableUtils.map;

/**
  A `ManyArray` is a `RecordArray` that represents the contents of a has-many
  relationship.

  The `ManyArray` is instantiated lazily the first time the relationship is
  requested.

  ### Inverses

  Often, the relationships in Ember Data applications will have
  an inverse. For example, imagine the following models are
  defined:

  ```javascript
  App.Post = DS.Model.extend({
    comments: DS.hasMany('comment')
  });

  App.Comment = DS.Model.extend({
    post: DS.belongsTo('post')
  });
  ```

  If you created a new instance of `App.Post` and added
  a `App.Comment` record to its `comments` has-many
  relationship, you would expect the comment's `post`
  property to be set to the post that contained
  the has-many.

  We call the record to which a relationship belongs the
  relationship's _owner_.

  @class ManyArray
  @namespace DS
  @extends DS.RecordArray
*/
DS.ManyArray = DS.RecordArray.extend({
  init: function() {
    this._super.apply(this, arguments);
    this._changesToSync = Ember.OrderedSet.create();
  },

  /**
    The property name of the relationship

    @property {String} name
    @private
  */
  name: null,

  /**
    The record to which this relationship belongs.

    @property {DS.Model} owner
    @private
  */
  owner: null,

  /**
    `true` if the relationship is polymorphic, `false` otherwise.

    @property {Boolean} isPolymorphic
    @private
  */
  isPolymorphic: false,

  // LOADING STATE

  isLoaded: false,

  /**
    Used for async `hasMany` arrays
    to keep track of when they will resolve.

    @property {Ember.RSVP.Promise} promise
    @private
  */
  promise: null,

  /**
    @method loadingRecordsCount
    @param {Number} count
    @private
  */
  loadingRecordsCount: function(count) {
    this.loadingRecordsCount = count;
  },

  /**
    @method loadedRecord
    @private
  */
  loadedRecord: function() {
    this.loadingRecordsCount--;
    if (this.loadingRecordsCount === 0) {
      set(this, 'isLoaded', true);
      this.trigger('didLoad');
    }
  },

  /**
    @method fetch
    @private
  */
  fetch: function() {
    var records = get(this, 'content'),
        store = get(this, 'store'),
        owner = get(this, 'owner'),
        resolver = Ember.RSVP.defer("DS: ManyArray#fetch " + get(this, 'type'));

    var unloadedRecords = records.filterProperty('isEmpty', true);
    store.fetchMany(unloadedRecords, owner, resolver);
  },

  // Overrides Ember.Array's replace method to implement
  replaceContent: function(index, removed, added) {
    // Map the array of record objects into an array of  client ids.
    added = map(added, function(record) {
      Ember.assert("You cannot add '" + record.constructor.typeKey + "' records to this relationship (only '" + this.type.typeKey + "' allowed)", !this.type || record instanceof this.type);
      return record;
    }, this);

    this._super(index, removed, added);
  },

  arrangedContentDidChange: function() {
    Ember.run.once(this, 'fetch');
  },

  arrayContentWillChange: function(index, removed, added) {
    var owner = get(this, 'owner'),
        name = get(this, 'name');

    if (!owner._suspendedRelationships) {
      // This code is the first half of code that continues inside
      // of arrayContentDidChange. It gets or creates a change from
      // the child object, adds the current owner as the old
      // parent if this is the first time the object was removed
      // from a ManyArray, and sets `newParent` to null.
      //
      // Later, if the object is added to another ManyArray,
      // the `arrayContentDidChange` will set `newParent` on
      // the change.
      for (var i=index; i<index+removed; i++) {
        var record = get(this, 'content').objectAt(i);

        var change = DS.RelationshipChange.createChange(owner, record, get(this, 'store'), {
          parentType: owner.constructor,
          changeType: "remove",
          kind: "hasMany",
          key: name
        });

        this._changesToSync.add(change);
      }
    }

    return this._super.apply(this, arguments);
  },

  arrayContentDidChange: function(index, removed, added) {
    this._super.apply(this, arguments);

    var owner = get(this, 'owner'),
        name = get(this, 'name'),
        store = get(this, 'store');

    if (!owner._suspendedRelationships) {
      // This code is the second half of code that started in
      // `arrayContentWillChange`. It gets or creates a change
      // from the child object, and adds the current owner as
      // the new parent.
      for (var i=index; i<index+added; i++) {
        var record = get(this, 'content').objectAt(i);

        var change = DS.RelationshipChange.createChange(owner, record, store, {
          parentType: owner.constructor,
          changeType: "add",
          kind:"hasMany",
          key: name
        });
        change.hasManyName = name;

        this._changesToSync.add(change);
      }

      // We wait until the array has finished being
      // mutated before syncing the OneToManyChanges created
      // in arrayContentWillChange, so that the array
      // membership test in the sync() logic operates
      // on the final results.
      this._changesToSync.forEach(function(change) {
        change.sync();
      });

      this._changesToSync.clear();
    }
  },

  /**
    Create a child record within the owner

    @method createRecord
    @private
    @param {Object} hash
    @return {DS.Model} record
  */
  createRecord: function(hash) {
    var owner = get(this, 'owner'),
        store = get(owner, 'store'),
        type = get(this, 'type'),
        record;

    Ember.assert("You cannot add '" + type.typeKey + "' records to this polymorphic relationship.", !get(this, 'isPolymorphic'));

    record = store.createRecord.call(store, type, hash);
    this.pushObject(record);

    return record;
  }

});

})();



(function() {
/**
  @module ember-data
*/

})();



(function() {
/*globals Ember*/
/*jshint eqnull:true*/
/**
  @module ember-data
*/

var get = Ember.get, set = Ember.set;
var once = Ember.run.once;
var isNone = Ember.isNone;
var forEach = Ember.EnumerableUtils.forEach;
var indexOf = Ember.EnumerableUtils.indexOf;
var map = Ember.EnumerableUtils.map;
var resolve = Ember.RSVP.resolve;
var copy = Ember.copy;

// Implementors Note:
//
//   The variables in this file are consistently named according to the following
//   scheme:
//
//   * +id+ means an identifier managed by an external source, provided inside
//     the data provided by that source. These are always coerced to be strings
//     before being used internally.
//   * +clientId+ means a transient numerical identifier generated at runtime by
//     the data store. It is important primarily because newly created objects may
//     not yet have an externally generated id.
//   * +reference+ means a record reference object, which holds metadata about a
//     record, even if it has not yet been fully materialized.
//   * +type+ means a subclass of DS.Model.

// Used by the store to normalize IDs entering the store.  Despite the fact
// that developers may provide IDs as numbers (e.g., `store.find(Person, 1)`),
// it is important that internally we use strings, since IDs may be serialized
// and lose type information.  For example, Ember's router may put a record's
// ID into the URL, and if we later try to deserialize that URL and find the
// corresponding record, we will not know if it is a string or a number.
var coerceId = function(id) {
  return id == null ? null : id+'';
};

/**
  The store contains all of the data for records loaded from the server.
  It is also responsible for creating instances of `DS.Model` that wrap
  the individual data for a record, so that they can be bound to in your
  Handlebars templates.

  Define your application's store like this:

  ```javascript
  MyApp.Store = DS.Store.extend();
  ```

  Most Ember.js applications will only have a single `DS.Store` that is
  automatically created by their `Ember.Application`.

  You can retrieve models from the store in several ways. To retrieve a record
  for a specific id, use `DS.Store`'s `find()` method:

  ```javascript
  var person = store.find('person', 123);
  ```

  If your application has multiple `DS.Store` instances (an unusual case), you can
  specify which store should be used:

  ```javascript
  var person = store.find(App.Person, 123);
  ```

  By default, the store will talk to your backend using a standard
  REST mechanism. You can customize how the store talks to your
  backend by specifying a custom adapter:

  ```javascript
   MyApp.store = DS.Store.create({
     adapter: 'MyApp.CustomAdapter'
   });
   ```

  You can learn more about writing a custom adapter by reading the `DS.Adapter`
  documentation.

  @class Store
  @namespace DS
  @extends Ember.Object
*/
DS.Store = Ember.Object.extend({

  /**
    @method init
    @private
  */
  init: function() {
    // internal bookkeeping; not observable
    this.typeMaps = {};
    this.recordArrayManager = DS.RecordArrayManager.create({
      store: this
    });
    this._relationshipChanges = {};
    this._pendingSave = [];
  },

  /**
    The adapter to use to communicate to a backend server or other persistence layer.

    This can be specified as an instance, class, or string.

    If you want to specify `App.CustomAdapter` as a string, do:

    ```js
    adapter: 'custom'
    ```

    @property adapter
    @default DS.RESTAdapter
    @type {DS.Adapter|String}
  */
  adapter: '_rest',

  /**
    Returns a JSON representation of the record using a custom
    type-specific serializer, if one exists.

    The available options are:

    * `includeId`: `true` if the record's ID should be included in
      the JSON representation

    @method serialize
    @private
    @param {DS.Model} record the record to serialize
    @param {Object} options an options hash
  */
  serialize: function(record, options) {
    return this.serializerFor(record.constructor.typeKey).serialize(record, options);
  },

  /**
    This property returns the adapter, after resolving a possible
    string key.

    If the supplied `adapter` was a class, or a String property
    path resolved to a class, this property will instantiate the
    class.

    This property is cacheable, so the same instance of a specified
    adapter class should be used for the lifetime of the store.

    @property defaultAdapter
    @private
    @returns DS.Adapter
  */
  defaultAdapter: Ember.computed('adapter', function() {
    var adapter = get(this, 'adapter');

    Ember.assert('You tried to set `adapter` property to an instance of `DS.Adapter`, where it should be a name or a factory', !(adapter instanceof DS.Adapter));

    if (typeof adapter === 'string') {
      adapter = this.container.lookup('adapter:' + adapter) || this.container.lookup('adapter:application') || this.container.lookup('adapter:_rest');
    }

    if (DS.Adapter.detect(adapter)) {
      adapter = adapter.create({ container: this.container });
    }

    return adapter;
  }),

  // .....................
  // . CREATE NEW RECORD .
  // .....................

  /**
    Create a new record in the current store. The properties passed
    to this method are set on the newly created record.

    To create a new instance of `App.Post`:

    ```js
    store.createRecord('post', {
      title: "Rails is omakase"
    });
    ```

    @method createRecord
    @param {String} type
    @param {Object} properties a hash of properties to set on the
      newly created record.
    @returns {DS.Model} record
  */
  createRecord: function(type, properties) {
    type = this.modelFor(type);

    properties = copy(properties) || {};

    // If the passed properties do not include a primary key,
    // give the adapter an opportunity to generate one. Typically,
    // client-side ID generators will use something like uuid.js
    // to avoid conflicts.

    if (isNone(properties.id)) {
      properties.id = this._generateId(type);
    }

    // Coerce ID to a string
    properties.id = coerceId(properties.id);

    var record = this.buildRecord(type, properties.id);

    // Move the record out of its initial `empty` state into
    // the `loaded` state.
    record.loadedData();

    // Set the properties specified on the record.
    record.setProperties(properties);

    return record;
  },

  /**
    If possible, this method asks the adapter to generate an ID for
    a newly created record.

    @method _generateId
    @private
    @param {String} type
    @returns {String} if the adapter can generate one, an ID
  */
  _generateId: function(type) {
    var adapter = this.adapterFor(type);

    if (adapter && adapter.generateIdForRecord) {
      return adapter.generateIdForRecord(this);
    }

    return null;
  },

  // .................
  // . DELETE RECORD .
  // .................

  /**
    For symmetry, a record can be deleted via the store.

    Example

    ```javascript
    var post = store.createRecord('post', {
      title: "Rails is omakase"
    });

    store.deletedRecord(post);
    ```

    @method deleteRecord
    @param {DS.Model} record
  */
  deleteRecord: function(record) {
    record.deleteRecord();
  },

  /**
    For symmetry, a record can be unloaded via the store. Only
    non-dirty records can be unloaded.

    Example

    ```javascript
    store.find('post', 1).then(function(post) {
      store.unloadRecord(post);
    });
    ```

    @method unloadRecord
    @param {DS.Model} record
  */
  unloadRecord: function(record) {
    record.unloadRecord();
  },

  // ................
  // . FIND RECORDS .
  // ................

  /**
    This is the main entry point into finding records. The first parameter to
    this method is the model's name as a string.

    ---

    To find a record by ID, pass the `id` as the second parameter:

    ```javascript
    store.find('person', 1);
    ```

    The `find` method will always return a **promise** that will be resolved
    with the record. If the record was already in the store, the promise will
    be resolved immediately. Otherwise, the store will ask the adapter's `find`
    method to find the necessary data.

    The `find` method will always resolve its promise with the same object for
    a given type and `id`.

    ---

    To find all records for a type, call `find` with no additional parameters:

    ```javascript
    store.find('person');
    ```

    This will ask the adapter's `findAll` method to find the records for the
    given type, and return a promise that will be resolved once the server
    returns the values.

    ---

    To find a record by a query, call `find` with a hash as the second
    parameter:

    ```javascript
    store.find(App.Person, { page: 1 });
    ```

    This will ask the adapter's `findQuery` method to find the records for
    the query, and return a promise that will be resolved once the server
    responds.

    @method find
    @param {String or subclass of DS.Model} type
    @param {Object|String|Integer|null} id
    @return {Promise} promise
  */
  find: function(type, id) {
    if (id === undefined) {
      return this.findAll(type);
    }

    // We are passed a query instead of an id.
    if (Ember.typeOf(id) === 'object') {
      return this.findQuery(type, id);
    }

    return this.findById(type, coerceId(id));
  },

  /**
    This method returns a record for a given type and id combination.

    @method findById
    @private
    @param {String or subclass of DS.Model} type
    @param {String|Integer} id
    @return {Promise} promise
  */
  findById: function(type, id) {
    type = this.modelFor(type);

    var record = this.recordForId(type, id);

    var promise = this.fetchRecord(record) || resolve(record, "DS: Store#findById " + type + " with id: " + id);
    return promiseObject(promise);
  },

  /**
    This method makes a series of requests to the adapter's `find` method
    and returns a promise that resolves once they are all loaded.

    @private
    @method findByIds
    @param {String} type
    @param {Array} ids
    @returns {Promise} promise
  */
  findByIds: function(type, ids) {
    var store = this;
    var promiseLabel = "DS: Store#findByIds " + type;
    return promiseArray(Ember.RSVP.all(map(ids, function(id) {
      return store.findById(type, id);
    })).then(Ember.A, null, "DS: Store#findByIds of " + type + " complete"));
  },

  /**
    This method is called by `findById` if it discovers that a particular
    type/id pair hasn't been loaded yet to kick off a request to the
    adapter.

    @method fetchRecord
    @private
    @param {DS.Model} record
    @returns {Promise} promise
  */
  fetchRecord: function(record) {
    if (isNone(record)) { return null; }
    if (record._loadingPromise) { return record._loadingPromise; }
    if (!get(record, 'isEmpty')) { return null; }

    var type = record.constructor,
        id = get(record, 'id');

    var adapter = this.adapterFor(type);

    Ember.assert("You tried to find a record but you have no adapter (for " + type + ")", adapter);
    Ember.assert("You tried to find a record but your adapter (for " + type + ") does not implement 'find'", adapter.find);

    var promise = _find(adapter, this, type, id);
    record.loadingData(promise);
    return promise;
  },

  /**
    Get a record by a given type and ID without triggering a fetch.

    This method will synchronously return the record if it's available.
    Otherwise, it will return null.

    ```js
    var post = store.getById('post', 1);
    ```

    @method getById
    @param {String or subclass of DS.Model} type
    @param {String|Integer} id
    @param {DS.Model} record
  */
  getById: function(type, id) {
    if (this.hasRecordForId(type, id)) {
      return this.recordForId(type, id);
    } else {
      return null;
    }
  },

  /**
    This method is called by the record's `reload` method.

    This method calls the adapter's `find` method, which returns a promise. When
    **that** promise resolves, `reloadRecord` will resolve the promise returned
    by the record's `reload`.

    @method reloadRecord
    @private
    @param {DS.Model} record
    @return {Promise} promise
  */
  reloadRecord: function(record) {
    var type = record.constructor,
        adapter = this.adapterFor(type),
        id = get(record, 'id');

    Ember.assert("You cannot reload a record without an ID", id);
    Ember.assert("You tried to reload a record but you have no adapter (for " + type + ")", adapter);
    Ember.assert("You tried to reload a record but your adapter does not implement `find`", adapter.find);

    return _find(adapter, this, type, id);
  },

  /**
    This method takes a list of records, groups the records by type,
    converts the records into IDs, and then invokes the adapter's `findMany`
    method.

    The records are grouped by type to invoke `findMany` on adapters
    for each unique type in records.

    It is used both by a brand new relationship (via the `findMany`
    method) or when the data underlying an existing relationship
    changes.

    @method fetchMany
    @private
    @param {Array} records
    @param {DS.Model} owner
    @param {Resolver} resolver
  */
  fetchMany: function(records, owner, resolver) {
    if (!records.length) { return; }

    // Group By Type
    var recordsByTypeMap = Ember.MapWithDefault.create({
      defaultValue: function() { return Ember.A(); }
    });

    forEach(records, function(record) {
      recordsByTypeMap.get(record.constructor).push(record);
    });

    forEach(recordsByTypeMap, function(type, records) {
      var ids = records.mapProperty('id'),
          adapter = this.adapterFor(type);

      Ember.assert("You tried to load many records but you have no adapter (for " + type + ")", adapter);
      Ember.assert("You tried to load many records but your adapter does not implement `findMany`", adapter.findMany);

      resolver.resolve(_findMany(adapter, this, type, ids, owner));
    }, this);
  },

  /**
    Returns true if a record for a given type and ID is already loaded.

    @method hasRecordForId
    @param {String or subclass of DS.Model} type
    @param {String|Integer} id
    @returns {Boolean}
  */
  hasRecordForId: function(type, id) {
    id = coerceId(id);
    type = this.modelFor(type);
    return !!this.typeMapFor(type).idToRecord[id];
  },

  /**
    Returns id record for a given type and ID. If one isn't already loaded,
    it builds a new record and leaves it in the `empty` state.

    @method recordForId
    @private
    @param {String or subclass of DS.Model} type
    @param {String|Integer} id
    @returns {DS.Model} record
  */
  recordForId: function(type, id) {
    type = this.modelFor(type);

    id = coerceId(id);

    var record = this.typeMapFor(type).idToRecord[id];

    if (!record) {
      record = this.buildRecord(type, id);
    }

    return record;
  },

  /**
    @method findMany
    @private
    @param {DS.Model} owner
    @param {Array} records
    @param {String or subclass of DS.Model} type
    @param {Resolver} resolver
    @return {DS.ManyArray} records
  */
  findMany: function(owner, records, type, resolver) {
    type = this.modelFor(type);

    records = Ember.A(records);

    var unloadedRecords = records.filterProperty('isEmpty', true),
        manyArray = this.recordArrayManager.createManyArray(type, records);

    forEach(unloadedRecords, function(record) {
      record.loadingData();
    });

    manyArray.loadingRecordsCount = unloadedRecords.length;

    if (unloadedRecords.length) {
      forEach(unloadedRecords, function(record) {
        this.recordArrayManager.registerWaitingRecordArray(record, manyArray);
      }, this);

      this.fetchMany(unloadedRecords, owner, resolver);
    } else {
      if (resolver) { resolver.resolve(); }
      manyArray.set('isLoaded', true);
      Ember.run.once(manyArray, 'trigger', 'didLoad');
    }

    return manyArray;
  },

  /**
    If a relationship was originally populated by the adapter as a link
    (as opposed to a list of IDs), this method is called when the
    relationship is fetched.

    The link (which is usually a URL) is passed through unchanged, so the
    adapter can make whatever request it wants.

    The usual use-case is for the server to register a URL as a link, and
    then use that URL in the future to make a request for the relationship.

    @method findHasMany
    @private
    @param {DS.Model} owner
    @param {any} link
    @param {String or subclass of DS.Model} type
    @param {Resolver} resolver
    @return {DS.ManyArray}
  */
  findHasMany: function(owner, link, relationship, resolver) {
    var adapter = this.adapterFor(owner.constructor);

    Ember.assert("You tried to load a hasMany relationship but you have no adapter (for " + owner.constructor + ")", adapter);
    Ember.assert("You tried to load a hasMany relationship from a specified `link` in the original payload but your adapter does not implement `findHasMany`", adapter.findHasMany);

    var records = this.recordArrayManager.createManyArray(relationship.type, Ember.A([]));
    resolver.resolve(_findHasMany(adapter, this, owner, link, relationship));
    return records;
  },

  /**
    @method findBelongsTo
    @private
    @param {DS.Model} owner
    @param {any} link
    @param {Relationship} relationship
    @param {Resolver} resolver
  */
  findBelongsTo: function(owner, link, relationship, resolver) {
    var adapter = this.adapterFor(owner.constructor);

    Ember.assert("You tried to load a belongsTo relationship but you have no adapter (for " + owner.constructor + ")", adapter);
    Ember.assert("You tried to load a belongsTo relationship from a specified `link` in the original payload but your adapter does not implement `findBelongsTo`", adapter.findBelongsTo);

    resolver.resolve(_findBelongsTo(adapter, this, owner, link, relationship));
  },

  /**
    This method delegates a query to the adapter. This is the one place where
    adapter-level semantics are exposed to the application.

    Exposing queries this way seems preferable to creating an abstract query
    language for all server-side queries, and then require all adapters to
    implement them.

    This method returns a promise, which is resolved with a `RecordArray`
    once the server returns.

    @method findQuery
    @private
    @param {String or subclass of DS.Model} type
    @param {any} query an opaque query to be used by the adapter
    @return {Promise} promise
  */
  findQuery: function(type, query) {
    type = this.modelFor(type);

    var array = this.recordArrayManager
      .createAdapterPopulatedRecordArray(type, query);

    var adapter = this.adapterFor(type),
        promiseLabel = "DS: Store#findQuery " + type,
        resolver = Ember.RSVP.defer(promiseLabel);

    Ember.assert("You tried to load a query but you have no adapter (for " + type + ")", adapter);
    Ember.assert("You tried to load a query but your adapter does not implement `findQuery`", adapter.findQuery);

    resolver.resolve(_findQuery(adapter, this, type, query, array));

    return promiseArray(resolver.promise);
  },

  /**
    This method returns an array of all records adapter can find.
    It triggers the adapter's `findAll` method to give it an opportunity to populate
    the array with records of that type.

    @method findAll
    @private
    @param {String or subclass of DS.Model} type
    @return {DS.AdapterPopulatedRecordArray}
  */
  findAll: function(type) {
    type = this.modelFor(type);

    return this.fetchAll(type, this.all(type));
  },

  /**
    @method fetchAll
    @private
    @param {DS.Model} type
    @param {DS.RecordArray} array
    @returns {Promise} promise
  */
  fetchAll: function(type, array) {
    var adapter = this.adapterFor(type),
        sinceToken = this.typeMapFor(type).metadata.since;

    set(array, 'isUpdating', true);

    Ember.assert("You tried to load all records but you have no adapter (for " + type + ")", adapter);
    Ember.assert("You tried to load all records but your adapter does not implement `findAll`", adapter.findAll);

    return promiseArray(_findAll(adapter, this, type, sinceToken));
  },

  /**
    @method didUpdateAll
    @param {DS.Model} type
  */
  didUpdateAll: function(type) {
    var findAllCache = this.typeMapFor(type).findAllCache;
    set(findAllCache, 'isUpdating', false);
  },

  /**
    This method returns a filtered array that contains all of the known records
    for a given type.

    Note that because it's just a filter, it will have any locally
    created records of the type.

    Also note that multiple calls to `all` for a given type will always
    return the same RecordArray.

    Example

    ```javascript
    var local_posts = store.all(App.Post);
    ```

    @method all
    @param {String or subclass of DS.Model} type
    @return {DS.RecordArray}
  */
  all: function(type) {
    type = this.modelFor(type);

    var typeMap = this.typeMapFor(type),
        findAllCache = typeMap.findAllCache;

    if (findAllCache) { return findAllCache; }

    var array = this.recordArrayManager.createRecordArray(type);

    typeMap.findAllCache = array;
    return array;
  },


  /**
    This method unloads all of the known records for a given type.

    ```javascript
    store.unloadAll(App.Post);
    ```

    @method unloadAll
    @param {String or subclass of DS.Model} type
  */
  unloadAll: function(type) {
    type = this.modelFor(type);

    var typeMap = this.typeMapFor(type),
        records = typeMap.records, record;

    while(record = records.pop()) {
      record.unloadRecord();
    }

    typeMap.findAllCache = null;
  },

  /**
    Takes a type and filter function, and returns a live RecordArray that
    remains up to date as new records are loaded into the store or created
    locally.

    The callback function takes a materialized record, and returns true
    if the record should be included in the filter and false if it should
    not.

    The filter function is called once on all records for the type when
    it is created, and then once on each newly loaded or created record.

    If any of a record's properties change, or if it changes state, the
    filter function will be invoked again to determine whether it should
    still be in the array.

    Optionally you can pass a query which will be triggered at first. The
    results returned by the server could then appear in the filter if they
    match the filter function.

    Example

    ```javascript
    store.filter(App.Post, {unread: true}, function(post) {
      return post.get('unread');
    }).then(function(unreadPosts) {
      unreadPosts.get('length'); // 5
      var unreadPost = unreadPosts.objectAt(0);
      unreadPosts.set('unread', false);
      unreadPosts.get('length'); // 4
    });
    ```

    @method filter
    @param {String or subclass of DS.Model} type
    @param {Object} query optional query
    @param {Function} filter
    @return {DS.PromiseArray}
  */
  filter: function(type, query, filter) {
    var promise;

    // allow an optional server query
    if (arguments.length === 3) {
      promise = this.findQuery(type, query);
    } else if (arguments.length === 2) {
      filter = query;
    }

    type = this.modelFor(type);

    var array = this.recordArrayManager
      .createFilteredRecordArray(type, filter);
    promise = promise || resolve(array);

    return promiseArray(promise.then(function() {
      return array;
    }, null, "DS: Store#filter of " + type));
  },

  /**
    This method returns if a certain record is already loaded
    in the store. Use this function to know beforehand if a find()
    will result in a request or that it will be a cache hit.

     Example

    ```javascript
    store.recordIsLoaded(App.Post, 1); // false
    store.find(App.Post, 1).then(function() {
      store.recordIsLoaded(App.Post, 1); // true
    });
    ```

    @method recordIsLoaded
    @param {String or subclass of DS.Model} type
    @param {string} id
    @return {boolean}
  */
  recordIsLoaded: function(type, id) {
    if (!this.hasRecordForId(type, id)) { return false; }
    return !get(this.recordForId(type, id), 'isEmpty');
  },

  /**
    This method returns the metadata for a specific type.

    @method metadataFor
    @param {String or subclass of DS.Model} type
    @return {object}
  */
  metadataFor: function(type) {
    type = this.modelFor(type);
    return this.typeMapFor(type).metadata;
  },

  // ............
  // . UPDATING .
  // ............

  /**
    If the adapter updates attributes or acknowledges creation
    or deletion, the record will notify the store to update its
    membership in any filters.
    To avoid thrashing, this method is invoked only once per

    run loop per record.

    @method dataWasUpdated
    @private
    @param {Class} type
    @param {DS.Model} record
  */
  dataWasUpdated: function(type, record) {
    this.recordArrayManager.recordDidChange(record);
  },

  // ..............
  // . PERSISTING .
  // ..............

  /**
    This method is called by `record.save`, and gets passed a
    resolver for the promise that `record.save` returns.

    It schedules saving to happen at the end of the run loop.

    @method scheduleSave
    @private
    @param {DS.Model} record
    @param {Resolver} resolver
  */
  scheduleSave: function(record, resolver) {
    record.adapterWillCommit();
    this._pendingSave.push([record, resolver]);
    once(this, 'flushPendingSave');
  },

  /**
    This method is called at the end of the run loop, and
    flushes any records passed into `scheduleSave`

    @method flushPendingSave
    @private
  */
  flushPendingSave: function() {
    var pending = this._pendingSave.slice();
    this._pendingSave = [];

    forEach(pending, function(tuple) {
      var record = tuple[0], resolver = tuple[1],
          adapter = this.adapterFor(record.constructor),
          operation;

      if (get(record, 'isNew')) {
        operation = 'createRecord';
      } else if (get(record, 'isDeleted')) {
        operation = 'deleteRecord';
      } else {
        operation = 'updateRecord';
      }

      resolver.resolve(_commit(adapter, this, operation, record));
    }, this);
  },

  /**
    This method is called once the promise returned by an
    adapter's `createRecord`, `updateRecord` or `deleteRecord`
    is resolved.

    If the data provides a server-generated ID, it will
    update the record and the store's indexes.

    @method didSaveRecord
    @private
    @param {DS.Model} record the in-flight record
    @param {Object} data optional data (see above)
  */
  didSaveRecord: function(record, data) {
    if (data) {
      // normalize relationship IDs into records
      data = normalizeRelationships(this, record.constructor, data, record);

      this.updateId(record, data);
    }

    record.adapterDidCommit(data);
  },

  /**
    This method is called once the promise returned by an
    adapter's `createRecord`, `updateRecord` or `deleteRecord`
    is rejected with a `DS.InvalidError`.

    @method recordWasInvalid
    @private
    @param {DS.Model} record
    @param {Object} errors
  */
  recordWasInvalid: function(record, errors) {
    record.adapterDidInvalidate(errors);
  },

  /**
    This method is called once the promise returned by an
    adapter's `createRecord`, `updateRecord` or `deleteRecord`
    is rejected (with anything other than a `DS.InvalidError`).

    @method recordWasError
    @private
    @param {DS.Model} record
  */
  recordWasError: function(record) {
    record.adapterDidError();
  },

  /**
    When an adapter's `createRecord`, `updateRecord` or `deleteRecord`
    resolves with data, this method extracts the ID from the supplied
    data.

    @method updateId
    @private
    @param {DS.Model} record
    @param {Object} data
  */
  updateId: function(record, data) {
    var oldId = get(record, 'id'),
        id = coerceId(data.id);

    Ember.assert("An adapter cannot assign a new id to a record that already has an id. " + record + " had id: " + oldId + " and you tried to update it with " + id + ". This likely happened because your server returned data in response to a find or update that had a different id than the one you sent.", oldId === null || id === oldId);

    this.typeMapFor(record.constructor).idToRecord[id] = record;

    set(record, 'id', id);
  },

  /**
    Returns a map of IDs to client IDs for a given type.

    @method typeMapFor
    @private
    @param type
    @return {Object} typeMap
  */
  typeMapFor: function(type) {
    var typeMaps = get(this, 'typeMaps'),
        guid = Ember.guidFor(type),
        typeMap;

    typeMap = typeMaps[guid];

    if (typeMap) { return typeMap; }

    typeMap = {
      idToRecord: {},
      records: [],
      metadata: {}
    };

    typeMaps[guid] = typeMap;

    return typeMap;
  },

  // ................
  // . LOADING DATA .
  // ................

  /**
    This internal method is used by `push`.

    @method _load
    @private
    @param {String or subclass of DS.Model} type
    @param {Object} data
    @param {Boolean} partial the data should be merged into
      the existing data, not replace it.
  */
  _load: function(type, data, partial) {
    var id = coerceId(data.id),
        record = this.recordForId(type, id);

    record.setupData(data, partial);
    this.recordArrayManager.recordDidChange(record);

    return record;
  },

  /**
    Returns a model class for a particular key. Used by
    methods that take a type key (like `find`, `createRecord`,
    etc.)

    @method modelFor
    @param {String or subclass of DS.Model} key
    @returns {subclass of DS.Model}
  */
  modelFor: function(key) {
    var factory;


    if (typeof key === 'string') {
      var normalizedKey = this.container.normalize('model:' + key);

      factory = this.container.lookupFactory(normalizedKey);
      if (!factory) { throw new Ember.Error("No model was found for '" + key + "'"); }
      factory.typeKey = normalizedKey.split(':', 2)[1];
    } else {
      // A factory already supplied.
      factory = key;
    }

    factory.store = this;
    return factory;
  },

  /**
    Push some data for a given type into the store.

    This method expects normalized data:

    * The ID is a key named `id` (an ID is mandatory)
    * The names of attributes are the ones you used in
      your model's `DS.attr`s.
    * Your relationships must be:
      * represented as IDs or Arrays of IDs
      * represented as model instances
      * represented as URLs, under the `links` key

    For this model:

    ```js
    App.Person = DS.Model.extend({
      firstName: DS.attr(),
      lastName: DS.attr(),

      children: DS.hasMany('person')
    });
    ```

    To represent the children as IDs:

    ```js
    {
      id: 1,
      firstName: "Tom",
      lastName: "Dale",
      children: [1, 2, 3]
    }
    ```

    To represent the children relationship as a URL:

    ```js
    {
      id: 1,
      firstName: "Tom",
      lastName: "Dale",
      links: {
        children: "/people/1/children"
      }
    }
    ```

    If you're streaming data or implementing an adapter,
    make sure that you have converted the incoming data
    into this form.

    This method can be used both to push in brand new
    records, as well as to update existing records.

    @method push
    @param {String or subclass of DS.Model} type
    @param {Object} data
    @returns {DS.Model} the record that was created or
      updated.
  */
  push: function(type, data, _partial) {
    // _partial is an internal param used by `update`.
    // If passed, it means that the data should be
    // merged into the existing data, not replace it.

    Ember.assert("You must include an `id` in a hash passed to `push`", data.id != null);

    type = this.modelFor(type);

    // normalize relationship IDs into records
    data = normalizeRelationships(this, type, data);

    this._load(type, data, _partial);

    return this.recordForId(type, data.id);
  },

  /**
    Push some raw data into the store.

    The data will be automatically deserialized using the
    serializer for the `type` param.

    This method can be used both to push in brand new
    records, as well as to update existing records.

    You can push in more than one type of object at once.
    All objects should be in the format expected by the
    serializer.

    ```js
    App.ApplicationSerializer = DS.ActiveModelSerializer;

    var pushData = {
      posts: [
        {id: 1, post_title: "Great post", comment_ids: [2]}
      ],
      comments: [
        {id: 2, comment_body: "Insightful comment"}
      ]
    }

    store.pushPayload('post', pushData);
    ```

    @method pushPayload
    @param {String} type
    @param {Object} payload
    @return {DS.Model} the record that was created or updated.
  */
  pushPayload: function (type, payload) {
    var serializer;
    if (!payload) {
      payload = type;
      serializer = defaultSerializer(this.container);
      Ember.assert("You cannot use `store#pushPayload` without a type unless your default serializer defines `pushPayload`", serializer.pushPayload);
    } else {
      serializer = this.serializerFor(type);
    }
    serializer.pushPayload(this, payload);
  },

  update: function(type, data) {
    Ember.assert("You must include an `id` in a hash passed to `update`", data.id != null);

    return this.push(type, data, true);
  },

  /**
    If you have an Array of normalized data to push,
    you can call `pushMany` with the Array, and it will
    call `push` repeatedly for you.

    @method pushMany
    @param {String or subclass of DS.Model} type
    @param {Array} datas
    @return {Array}
  */
  pushMany: function(type, datas) {
    return map(datas, function(data) {
      return this.push(type, data);
    }, this);
  },

  /**
    If you have some metadata to set for a type
    you can call `metaForType`.

    @method metaForType
    @param {String or subclass of DS.Model} type
    @param {Object} metadata
  */
  metaForType: function(type, metadata) {
    type = this.modelFor(type);

    Ember.merge(this.typeMapFor(type).metadata, metadata);
  },

  /**
    Build a brand new record for a given type, ID, and
    initial data.

    @method buildRecord
    @private
    @param {subclass of DS.Model} type
    @param {String} id
    @param {Object} data
    @returns {DS.Model} record
  */
  buildRecord: function(type, id, data) {
    var typeMap = this.typeMapFor(type),
        idToRecord = typeMap.idToRecord;

    Ember.assert('The id ' + id + ' has already been used with another record of type ' + type.toString() + '.', !id || !idToRecord[id]);

    // lookupFactory should really return an object that creates
    // instances with the injections applied
    var record = type._create({
      id: id,
      store: this,
      container: this.container
    });

    if (data) {
      record.setupData(data);
    }

    // if we're creating an item, this process will be done
    // later, once the object has been persisted.
    if (id) {
      idToRecord[id] = record;
    }

    typeMap.records.push(record);

    return record;
  },

  // ...............
  // . DESTRUCTION .
  // ...............

  /**
    When a record is destroyed, this un-indexes it and
    removes it from any record arrays so it can be GCed.

    @method dematerializeRecord
    @private
    @param {DS.Model} record
  */
  dematerializeRecord: function(record) {
    var type = record.constructor,
        typeMap = this.typeMapFor(type),
        id = get(record, 'id');

    record.updateRecordArrays();

    if (id) {
      delete typeMap.idToRecord[id];
    }

    var loc = indexOf(typeMap.records, record);
    typeMap.records.splice(loc, 1);
  },

  // ........................
  // . RELATIONSHIP CHANGES .
  // ........................

  addRelationshipChangeFor: function(childRecord, childKey, parentRecord, parentKey, change) {
    var clientId = childRecord.clientId,
        parentClientId = parentRecord ? parentRecord : parentRecord;
    var key = childKey + parentKey;
    var changes = this._relationshipChanges;
    if (!(clientId in changes)) {
      changes[clientId] = {};
    }
    if (!(parentClientId in changes[clientId])) {
      changes[clientId][parentClientId] = {};
    }
    if (!(key in changes[clientId][parentClientId])) {
      changes[clientId][parentClientId][key] = {};
    }
    changes[clientId][parentClientId][key][change.changeType] = change;
  },

  removeRelationshipChangeFor: function(clientRecord, childKey, parentRecord, parentKey, type) {
    var clientId = clientRecord.clientId,
        parentClientId = parentRecord ? parentRecord.clientId : parentRecord;
    var changes = this._relationshipChanges;
    var key = childKey + parentKey;
    if (!(clientId in changes) || !(parentClientId in changes[clientId]) || !(key in changes[clientId][parentClientId])){
      return;
    }
    delete changes[clientId][parentClientId][key][type];
  },

  relationshipChangePairsFor: function(record){
    var toReturn = [];

    if( !record ) { return toReturn; }

    //TODO(Igor) What about the other side
    var changesObject = this._relationshipChanges[record.clientId];
    for (var objKey in changesObject){
      if(changesObject.hasOwnProperty(objKey)){
        for (var changeKey in changesObject[objKey]){
          if(changesObject[objKey].hasOwnProperty(changeKey)){
            toReturn.push(changesObject[objKey][changeKey]);
          }
        }
      }
    }
    return toReturn;
  },

  // ......................
  // . PER-TYPE ADAPTERS
  // ......................

  /**
    Returns the adapter for a given type.

    @method adapterFor
    @private
    @param {subclass of DS.Model} type
    @returns DS.Adapter
  */
  adapterFor: function(type) {
    var container = this.container, adapter;

    if (container) {
      adapter = container.lookup('adapter:' + type.typeKey) || container.lookup('adapter:application');
    }

    return adapter || get(this, 'defaultAdapter');
  },

  // ..............................
  // . RECORD CHANGE NOTIFICATION .
  // ..............................

  /**
    Returns an instance of the serializer for a given type. For
    example, `serializerFor('person')` will return an instance of
    `App.PersonSerializer`.

    If no `App.PersonSerializer` is found, this method will look
    for an `App.ApplicationSerializer` (the default serializer for
    your entire application).

    If no `App.ApplicationSerializer` is found, it will fall back
    to an instance of `DS.JSONSerializer`.

    @method serializerFor
    @private
    @param {String} type the record to serialize
    @return {DS.Serializer}
  */
  serializerFor: function(type) {
    type = this.modelFor(type);
    var adapter = this.adapterFor(type);

    return serializerFor(this.container, type.typeKey, adapter && adapter.defaultSerializer);
  }
});

function normalizeRelationships(store, type, data, record) {
  type.eachRelationship(function(key, relationship) {
    // A link (usually a URL) was already provided in
    // normalized form
    if (data.links && data.links[key]) {
      if (record && relationship.options.async) { record._relationships[key] = null; }
      return;
    }

    var kind = relationship.kind,
        value = data[key];

    if (value == null) { return; }

    if (kind === 'belongsTo') {
      deserializeRecordId(store, data, key, relationship, value);
    } else if (kind === 'hasMany') {
      deserializeRecordIds(store, data, key, relationship, value);
      addUnsavedRecords(record, key, value);
    }
  });

  return data;
}

function deserializeRecordId(store, data, key, relationship, id) {
  if (isNone(id) || id instanceof DS.Model) {
    return;
  }

  var type;

  if (typeof id === 'number' || typeof id === 'string') {
    type = typeFor(relationship, key, data);
    data[key] = store.recordForId(type, id);
  } else if (typeof id === 'object') {
    // polymorphic
    data[key] = store.recordForId(id.type, id.id);
  }
}

function typeFor(relationship, key, data) {
  if (relationship.options.polymorphic) {
    return data[key + "Type"];
  } else {
    return relationship.type;
  }
}

function deserializeRecordIds(store, data, key, relationship, ids) {
  for (var i=0, l=ids.length; i<l; i++) {
    deserializeRecordId(store, ids, i, relationship, ids[i]);
  }
}

// If there are any unsaved records that are in a hasMany they won't be
// in the payload, so add them back in manually.
function addUnsavedRecords(record, key, data) {
  if(record) {
    data.pushObjects(record.get(key).filterBy('isNew'));
  }
}

// Delegation to the adapter and promise management
/**
  A `PromiseArray` is an object that acts like both an `Ember.Array`
  and a promise. When the promise is resolved the the resulting value
  will be set to the `PromiseArray`'s `content` property. This makes
  it easy to create data bindings with the `PromiseArray` that will be
  updated when the promise resolves.

  For more information see the [Ember.PromiseProxyMixin
  documentation](/api/classes/Ember.PromiseProxyMixin.html).

  Example

  ```javascript
  var promiseArray = DS.PromiseArray.create({
    promise: $.getJSON('/some/remote/data.json')
  });

  promiseArray.get('length'); // 0

  promiseArray.then(function() {
    promiseArray.get('length'); // 100
  });
  ```

  @class PromiseArray
  @namespace DS
  @extends Ember.ArrayProxy
  @uses Ember.PromiseProxyMixin
*/
DS.PromiseArray = Ember.ArrayProxy.extend(Ember.PromiseProxyMixin);
/**
  A `PromiseObject` is an object that acts like both an `Ember.Object`
  and a promise. When the promise is resolved the the resulting value
  will be set to the `PromiseObject`'s `content` property. This makes
  it easy to create data bindings with the `PromiseObject` that will
  be updated when the promise resolves.

  For more information see the [Ember.PromiseProxyMixin
  documentation](/api/classes/Ember.PromiseProxyMixin.html).

  Example

  ```javascript
  var promiseObject = DS.PromiseObject.create({
    promise: $.getJSON('/some/remote/data.json')
  });

  promiseObject.get('name'); // null

  promiseObject.then(function() {
    promiseObject.get('name'); // 'Tomster'
  });
  ```

  @class PromiseObject
  @namespace DS
  @extends Ember.ObjectProxy
  @uses Ember.PromiseProxyMixin
*/
DS.PromiseObject = Ember.ObjectProxy.extend(Ember.PromiseProxyMixin);

function promiseObject(promise) {
  return DS.PromiseObject.create({ promise: promise });
}

function promiseArray(promise) {
  return DS.PromiseArray.create({ promise: promise });
}

function isThenable(object) {
  return object && typeof object.then === 'function';
}

function serializerFor(container, type, defaultSerializer) {
  return container.lookup('serializer:'+type) ||
                 container.lookup('serializer:application') ||
                 container.lookup('serializer:' + defaultSerializer) ||
                 container.lookup('serializer:_default');
}

function defaultSerializer(container) {
  return container.lookup('serializer:application') ||
         container.lookup('serializer:_default');
}

function serializerForAdapter(adapter, type) {
  var serializer = adapter.serializer,
      defaultSerializer = adapter.defaultSerializer,
      container = adapter.container;

  if (container && serializer === undefined) {
    serializer = serializerFor(container, type.typeKey, defaultSerializer);
  }

  if (serializer === null || serializer === undefined) {
    serializer = {
      extract: function(store, type, payload) { return payload; }
    };
  }

  return serializer;
}

function _find(adapter, store, type, id) {
  var promise = adapter.find(store, type, id),
      serializer = serializerForAdapter(adapter, type);

  return resolve(promise, "DS: Handle Adapter#find of " + type + " with id: " + id).then(function(payload) {
    Ember.assert("You made a request for a " + type.typeKey + " with id " + id + ", but the adapter's response did not have any data", payload);
    payload = serializer.extract(store, type, payload, id, 'find');

    return store.push(type, payload);
  }, function(error) {
    var record = store.getById(type, id);
    record.notFound();
    throw error;
  }, "DS: Extract payload of '" + type + "'");
}

function _findMany(adapter, store, type, ids, owner) {
  var promise = adapter.findMany(store, type, ids, owner),
      serializer = serializerForAdapter(adapter, type);

  return resolve(promise, "DS: Handle Adapter#findMany of " + type).then(function(payload) {
    payload = serializer.extract(store, type, payload, null, 'findMany');

    Ember.assert("The response from a findMany must be an Array, not " + Ember.inspect(payload), Ember.typeOf(payload) === 'array');

    store.pushMany(type, payload);
  }, null, "DS: Extract payload of " + type);
}

function _findHasMany(adapter, store, record, link, relationship) {
  var promise = adapter.findHasMany(store, record, link, relationship),
      serializer = serializerForAdapter(adapter, relationship.type);

  return resolve(promise, "DS: Handle Adapter#findHasMany of " + record + " : " + relationship.type).then(function(payload) {
    payload = serializer.extract(store, relationship.type, payload, null, 'findHasMany');

    Ember.assert("The response from a findHasMany must be an Array, not " + Ember.inspect(payload), Ember.typeOf(payload) === 'array');

    var records = store.pushMany(relationship.type, payload);
    record.updateHasMany(relationship.key, records);
  }, null, "DS: Extract payload of " + record + " : hasMany " + relationship.type);
}

function _findBelongsTo(adapter, store, record, link, relationship) {
  var promise = adapter.findBelongsTo(store, record, link, relationship),
      serializer = serializerForAdapter(adapter, relationship.type);

  return resolve(promise, "DS: Handle Adapter#findBelongsTo of " + record + " : " + relationship.type).then(function(payload) {
    payload = serializer.extract(store, relationship.type, payload, null, 'findBelongsTo');

    var record = store.push(relationship.type, payload);
    record.updateBelongsTo(relationship.key, record);
    return record;
  }, null, "DS: Extract payload of " + record + " : " + relationship.type);
}

function _findAll(adapter, store, type, sinceToken) {
  var promise = adapter.findAll(store, type, sinceToken),
      serializer = serializerForAdapter(adapter, type);

  return resolve(promise, "DS: Handle Adapter#findAll of " + type).then(function(payload) {
    payload = serializer.extract(store, type, payload, null, 'findAll');

    Ember.assert("The response from a findAll must be an Array, not " + Ember.inspect(payload), Ember.typeOf(payload) === 'array');

    store.pushMany(type, payload);
    store.didUpdateAll(type);
    return store.all(type);
  }, null, "DS: Extract payload of findAll " + type);
}

function _findQuery(adapter, store, type, query, recordArray) {
  var promise = adapter.findQuery(store, type, query, recordArray),
      serializer = serializerForAdapter(adapter, type);

  return resolve(promise, "DS: Handle Adapter#findQuery of " + type).then(function(payload) {
    payload = serializer.extract(store, type, payload, null, 'findQuery');

    Ember.assert("The response from a findQuery must be an Array, not " + Ember.inspect(payload), Ember.typeOf(payload) === 'array');

    recordArray.load(payload);
    return recordArray;
  }, null, "DS: Extract payload of findQuery " + type);
}

function _commit(adapter, store, operation, record) {
  var type = record.constructor,
      promise = adapter[operation](store, type, record),
      serializer = serializerForAdapter(adapter, type);

  Ember.assert("Your adapter's '" + operation + "' method must return a promise, but it returned " + promise, isThenable(promise));

  return promise.then(function(payload) {
    if (payload) { payload = serializer.extract(store, type, payload, get(record, 'id'), operation); }
    store.didSaveRecord(record, payload);
    return record;
  }, function(reason) {
    if (reason instanceof DS.InvalidError) {
      store.recordWasInvalid(record, reason.errors);
    } else {
      store.recordWasError(record, reason);
    }

    throw reason;
  }, "DS: Extract and notify about " + operation + " completion of " + record);
}

})();



(function() {
/**
  @module ember-data
*/

var get = Ember.get, set = Ember.set;
/*
  This file encapsulates the various states that a record can transition
  through during its lifecycle.
*/
/**
  ### State

  Each record has a `currentState` property that explicitly tracks what
  state a record is in at any given time. For instance, if a record is
  newly created and has not yet been sent to the adapter to be saved,
  it would be in the `root.loaded.created.uncommitted` state.  If a
  record has had local modifications made to it that are in the
  process of being saved, the record would be in the
  `root.loaded.updated.inFlight` state. (These state paths will be
  explained in more detail below.)

  Events are sent by the record or its store to the record's
  `currentState` property. How the state reacts to these events is
  dependent on which state it is in. In some states, certain events
  will be invalid and will cause an exception to be raised.

  States are hierarchical and every state is a substate of the
  `RootState`. For example, a record can be in the
  `root.deleted.uncommitted` state, then transition into the
  `root.deleted.inFlight` state. If a child state does not implement
  an event handler, the state manager will attempt to invoke the event
  on all parent states until the root state is reached. The state
  hierarchy of a record is described in terms of a path string. You
  can determine a record's current state by getting the state's
  `stateName` property:

  ```javascript
  record.get('currentState.stateName');
  //=> "root.created.uncommitted"
   ```

  The hierarchy of valid states that ship with ember data looks like
  this:

  ```text
  * root
    * deleted
      * saved
      * uncommitted
      * inFlight
    * empty
    * loaded
      * created
        * uncommitted
        * inFlight
      * saved
      * updated
        * uncommitted
        * inFlight
    * loading
  ```

  The `DS.Model` states are themselves stateless. What we mean is
  that, the hierarchical states that each of *those* points to is a
  shared data structure. For performance reasons, instead of each
  record getting its own copy of the hierarchy of states, each record
  points to this global, immutable shared instance. How does a state
  know which record it should be acting on? We pass the record
  instance into the state's event handlers as the first argument.

  The record passed as the first parameter is where you should stash
  state about the record if needed; you should never store data on the state
  object itself.

  ### Events and Flags

  A state may implement zero or more events and flags.

  #### Events

  Events are named functions that are invoked when sent to a record. The
  record will first look for a method with the given name on the
  current state. If no method is found, it will search the current
  state's parent, and then its grandparent, and so on until reaching
  the top of the hierarchy. If the root is reached without an event
  handler being found, an exception will be raised. This can be very
  helpful when debugging new features.

  Here's an example implementation of a state with a `myEvent` event handler:

  ```javascript
  aState: DS.State.create({
    myEvent: function(manager, param) {
      console.log("Received myEvent with", param);
    }
  })
  ```

  To trigger this event:

  ```javascript
  record.send('myEvent', 'foo');
  //=> "Received myEvent with foo"
  ```

  Note that an optional parameter can be sent to a record's `send()` method,
  which will be passed as the second parameter to the event handler.

  Events should transition to a different state if appropriate. This can be
  done by calling the record's `transitionTo()` method with a path to the
  desired state. The state manager will attempt to resolve the state path
  relative to the current state. If no state is found at that path, it will
  attempt to resolve it relative to the current state's parent, and then its
  parent, and so on until the root is reached. For example, imagine a hierarchy
  like this:

      * created
        * uncommitted <-- currentState
        * inFlight
      * updated
        * inFlight

  If we are currently in the `uncommitted` state, calling
  `transitionTo('inFlight')` would transition to the `created.inFlight` state,
  while calling `transitionTo('updated.inFlight')` would transition to
  the `updated.inFlight` state.

  Remember that *only events* should ever cause a state transition. You should
  never call `transitionTo()` from outside a state's event handler. If you are
  tempted to do so, create a new event and send that to the state manager.

  #### Flags

  Flags are Boolean values that can be used to introspect a record's current
  state in a more user-friendly way than examining its state path. For example,
  instead of doing this:

  ```javascript
  var statePath = record.get('stateManager.currentPath');
  if (statePath === 'created.inFlight') {
    doSomething();
  }
  ```

  You can say:

  ```javascript
  if (record.get('isNew') && record.get('isSaving')) {
    doSomething();
  }
  ```

  If your state does not set a value for a given flag, the value will
  be inherited from its parent (or the first place in the state hierarchy
  where it is defined).

  The current set of flags are defined below. If you want to add a new flag,
  in addition to the area below, you will also need to declare it in the
  `DS.Model` class.


   * [isEmpty](DS.Model.html#property_isEmpty)
   * [isLoading](DS.Model.html#property_isLoading)
   * [isLoaded](DS.Model.html#property_isLoaded)
   * [isDirty](DS.Model.html#property_isDirty)
   * [isSaving](DS.Model.html#property_isSaving)
   * [isDeleted](DS.Model.html#property_isDeleted)
   * [isNew](DS.Model.html#property_isNew)
   * [isValid](DS.Model.html#property_isValid)

  @namespace DS
  @class RootState
*/

var hasDefinedProperties = function(object) {
  // Ignore internal property defined by simulated `Ember.create`.
  var names = Ember.keys(object);
  var i, l, name;
  for (i = 0, l = names.length; i < l; i++ ) {
    name = names[i];
    if (object.hasOwnProperty(name) && object[name]) { return true; }
  }

  return false;
};

var didSetProperty = function(record, context) {
  if (context.value === context.originalValue) {
    delete record._attributes[context.name];
    record.send('propertyWasReset', context.name);
  } else if (context.value !== context.oldValue) {
    record.send('becomeDirty');
  }

  record.updateRecordArraysLater();
};

// Implementation notes:
//
// Each state has a boolean value for all of the following flags:
//
// * isLoaded: The record has a populated `data` property. When a
//   record is loaded via `store.find`, `isLoaded` is false
//   until the adapter sets it. When a record is created locally,
//   its `isLoaded` property is always true.
// * isDirty: The record has local changes that have not yet been
//   saved by the adapter. This includes records that have been
//   created (but not yet saved) or deleted.
// * isSaving: The record has been committed, but
//   the adapter has not yet acknowledged that the changes have
//   been persisted to the backend.
// * isDeleted: The record was marked for deletion. When `isDeleted`
//   is true and `isDirty` is true, the record is deleted locally
//   but the deletion was not yet persisted. When `isSaving` is
//   true, the change is in-flight. When both `isDirty` and
//   `isSaving` are false, the change has persisted.
// * isError: The adapter reported that it was unable to save
//   local changes to the backend. This may also result in the
//   record having its `isValid` property become false if the
//   adapter reported that server-side validations failed.
// * isNew: The record was created on the client and the adapter
//   did not yet report that it was successfully saved.
// * isValid: No client-side validations have failed and the
//   adapter did not report any server-side validation failures.

// The dirty state is a abstract state whose functionality is
// shared between the `created` and `updated` states.
//
// The deleted state shares the `isDirty` flag with the
// subclasses of `DirtyState`, but with a very different
// implementation.
//
// Dirty states have three child states:
//
// `uncommitted`: the store has not yet handed off the record
//   to be saved.
// `inFlight`: the store has handed off the record to be saved,
//   but the adapter has not yet acknowledged success.
// `invalid`: the record has invalid information and cannot be
//   send to the adapter yet.
var DirtyState = {
  initialState: 'uncommitted',

  // FLAGS
  isDirty: true,

  // SUBSTATES

  // When a record first becomes dirty, it is `uncommitted`.
  // This means that there are local pending changes, but they
  // have not yet begun to be saved, and are not invalid.
  uncommitted: {
    // EVENTS
    didSetProperty: didSetProperty,

    propertyWasReset: function(record, name) {
      var stillDirty = false;

      for (var prop in record._attributes) {
        stillDirty = true;
        break;
      }

      if (!stillDirty) { record.send('rolledBack'); }
    },

    pushedData: Ember.K,

    becomeDirty: Ember.K,

    willCommit: function(record) {
      record.transitionTo('inFlight');
    },

    reloadRecord: function(record, resolve) {
      resolve(get(record, 'store').reloadRecord(record));
    },

    rolledBack: function(record) {
      record.transitionTo('loaded.saved');
    },

    becameInvalid: function(record) {
      record.transitionTo('invalid');
    },

    rollback: function(record) {
      record.rollback();
    }
  },

  // Once a record has been handed off to the adapter to be
  // saved, it is in the 'in flight' state. Changes to the
  // record cannot be made during this window.
  inFlight: {
    // FLAGS
    isSaving: true,

    // EVENTS
    didSetProperty: didSetProperty,
    becomeDirty: Ember.K,
    pushedData: Ember.K,

    // TODO: More robust semantics around save-while-in-flight
    willCommit: Ember.K,

    didCommit: function(record) {
      var dirtyType = get(this, 'dirtyType');

      record.transitionTo('saved');
      record.send('invokeLifecycleCallbacks', dirtyType);
    },

    becameInvalid: function(record) {
      record.transitionTo('invalid');
      record.send('invokeLifecycleCallbacks');
    },

    becameError: function(record) {
      record.transitionTo('uncommitted');
      record.triggerLater('becameError', record);
    }
  },

  // A record is in the `invalid` state when its client-side
  // invalidations have failed, or if the adapter has indicated
  // the the record failed server-side invalidations.
  invalid: {
    // FLAGS
    isValid: false,

    // EVENTS
    deleteRecord: function(record) {
      record.transitionTo('deleted.uncommitted');
      record.clearRelationships();
    },

    didSetProperty: function(record, context) {
      get(record, 'errors').remove(context.name);

      didSetProperty(record, context);
    },

    becomeDirty: Ember.K,

    rolledBack: function(record) {
      get(record, 'errors').clear();
    },

    becameValid: function(record) {
      record.transitionTo('uncommitted');
    },

    invokeLifecycleCallbacks: function(record) {
      record.triggerLater('becameInvalid', record);
    }
  }
};

// The created and updated states are created outside the state
// chart so we can reopen their substates and add mixins as
// necessary.

function deepClone(object) {
  var clone = {}, value;

  for (var prop in object) {
    value = object[prop];
    if (value && typeof value === 'object') {
      clone[prop] = deepClone(value);
    } else {
      clone[prop] = value;
    }
  }

  return clone;
}

function mixin(original, hash) {
  for (var prop in hash) {
    original[prop] = hash[prop];
  }

  return original;
}

function dirtyState(options) {
  var newState = deepClone(DirtyState);
  return mixin(newState, options);
}

var createdState = dirtyState({
  dirtyType: 'created',

  // FLAGS
  isNew: true
});

createdState.uncommitted.rolledBack = function(record) {
  record.transitionTo('deleted.saved');
};

var updatedState = dirtyState({
  dirtyType: 'updated'
});

createdState.uncommitted.deleteRecord = function(record) {
  record.clearRelationships();
  record.transitionTo('deleted.saved');
};

createdState.uncommitted.rollback = function(record) {
  DirtyState.uncommitted.rollback.apply(this, arguments);
  record.transitionTo('deleted.saved');
};

updatedState.uncommitted.deleteRecord = function(record) {
  record.transitionTo('deleted.uncommitted');
  record.clearRelationships();
};

var RootState = {
  // FLAGS
  isEmpty: false,
  isLoading: false,
  isLoaded: false,
  isDirty: false,
  isSaving: false,
  isDeleted: false,
  isNew: false,
  isValid: true,

  // DEFAULT EVENTS

  // Trying to roll back if you're not in the dirty state
  // doesn't change your state. For example, if you're in the
  // in-flight state, rolling back the record doesn't move
  // you out of the in-flight state.
  rolledBack: Ember.K,

  propertyWasReset: Ember.K,

  // SUBSTATES

  // A record begins its lifecycle in the `empty` state.
  // If its data will come from the adapter, it will
  // transition into the `loading` state. Otherwise, if
  // the record is being created on the client, it will
  // transition into the `created` state.
  empty: {
    isEmpty: true,

    // EVENTS
    loadingData: function(record, promise) {
      record._loadingPromise = promise;
      record.transitionTo('loading');
    },

    loadedData: function(record) {
      record.transitionTo('loaded.created.uncommitted');

      record.suspendRelationshipObservers(function() {
        record.notifyPropertyChange('data');
      });
    },

    pushedData: function(record) {
      record.transitionTo('loaded.saved');
      record.triggerLater('didLoad');
    }
  },

  // A record enters this state when the store askes
  // the adapter for its data. It remains in this state
  // until the adapter provides the requested data.
  //
  // Usually, this process is asynchronous, using an
  // XHR to retrieve the data.
  loading: {
    // FLAGS
    isLoading: true,

    exit: function(record) {
      record._loadingPromise = null;
    },

    // EVENTS
    pushedData: function(record) {
      record.transitionTo('loaded.saved');
      record.triggerLater('didLoad');
      set(record, 'isError', false);
    },

    becameError: function(record) {
      record.triggerLater('becameError', record);
    },

    notFound: function(record) {
      record.transitionTo('empty');
    }
  },

  // A record enters this state when its data is populated.
  // Most of a record's lifecycle is spent inside substates
  // of the `loaded` state.
  loaded: {
    initialState: 'saved',

    // FLAGS
    isLoaded: true,

    // SUBSTATES

    // If there are no local changes to a record, it remains
    // in the `saved` state.
    saved: {
      setup: function(record) {
        var attrs = record._attributes,
            isDirty = false;

        for (var prop in attrs) {
          if (attrs.hasOwnProperty(prop)) {
            isDirty = true;
            break;
          }
        }

        if (isDirty) {
          record.adapterDidDirty();
        }
      },

      // EVENTS
      didSetProperty: didSetProperty,

      pushedData: Ember.K,

      becomeDirty: function(record) {
        record.transitionTo('updated.uncommitted');
      },

      willCommit: function(record) {
        record.transitionTo('updated.inFlight');
      },

      reloadRecord: function(record, resolve) {
        resolve(get(record, 'store').reloadRecord(record));
      },

      deleteRecord: function(record) {
        record.transitionTo('deleted.uncommitted');
        record.clearRelationships();
      },

      unloadRecord: function(record) {
        // clear relationships before moving to deleted state
        // otherwise it fails
        record.clearRelationships();
        record.transitionTo('deleted.saved');
      },

      didCommit: function(record) {
        record.send('invokeLifecycleCallbacks', get(record, 'lastDirtyType'));
      },

      // loaded.saved.notFound would be triggered by a failed
      // `reload()` on an unchanged record
      notFound: Ember.K

    },

    // A record is in this state after it has been locally
    // created but before the adapter has indicated that
    // it has been saved.
    created: createdState,

    // A record is in this state if it has already been
    // saved to the server, but there are new local changes
    // that have not yet been saved.
    updated: updatedState
  },

  // A record is in this state if it was deleted from the store.
  deleted: {
    initialState: 'uncommitted',
    dirtyType: 'deleted',

    // FLAGS
    isDeleted: true,
    isLoaded: true,
    isDirty: true,

    // TRANSITIONS
    setup: function(record) {
      record.updateRecordArrays();
    },

    // SUBSTATES

    // When a record is deleted, it enters the `start`
    // state. It will exit this state when the record
    // starts to commit.
    uncommitted: {

      // EVENTS

      willCommit: function(record) {
        record.transitionTo('inFlight');
      },

      rollback: function(record) {
        record.rollback();
      },

      becomeDirty: Ember.K,
      deleteRecord: Ember.K,

      rolledBack: function(record) {
        record.transitionTo('loaded.saved');
      }
    },

    // After a record starts committing, but
    // before the adapter indicates that the deletion
    // has saved to the server, a record is in the
    // `inFlight` substate of `deleted`.
    inFlight: {
      // FLAGS
      isSaving: true,

      // EVENTS

      // TODO: More robust semantics around save-while-in-flight
      willCommit: Ember.K,
      didCommit: function(record) {
        record.transitionTo('saved');

        record.send('invokeLifecycleCallbacks');
      },

      becameError: function(record) {
        record.transitionTo('uncommitted');
        record.triggerLater('becameError', record);
      }
    },

    // Once the adapter indicates that the deletion has
    // been saved, the record enters the `saved` substate
    // of `deleted`.
    saved: {
      // FLAGS
      isDirty: false,

      setup: function(record) {
        var store = get(record, 'store');
        store.dematerializeRecord(record);
      },

      invokeLifecycleCallbacks: function(record) {
        record.triggerLater('didDelete', record);
        record.triggerLater('didCommit', record);
      }
    }
  },

  invokeLifecycleCallbacks: function(record, dirtyType) {
    if (dirtyType === 'created') {
      record.triggerLater('didCreate', record);
    } else {
      record.triggerLater('didUpdate', record);
    }

    record.triggerLater('didCommit', record);
  }
};

function wireState(object, parent, name) {
  /*jshint proto:true*/
  // TODO: Use Object.create and copy instead
  object = mixin(parent ? Ember.create(parent) : {}, object);
  object.parentState = parent;
  object.stateName = name;

  for (var prop in object) {
    if (!object.hasOwnProperty(prop) || prop === 'parentState' || prop === 'stateName') { continue; }
    if (typeof object[prop] === 'object') {
      object[prop] = wireState(object[prop], object, name + "." + prop);
    }
  }

  return object;
}

RootState = wireState(RootState, null, "root");

DS.RootState = RootState;

})();



(function() {
var get = Ember.get, isEmpty = Ember.isEmpty;

/**
@module ember-data
*/

/**
  Holds validation errors for a given record organized by attribute names.

  @class Errors
  @namespace DS
  @extends Ember.Object
  @uses Ember.Enumerable
  @uses Ember.Evented
 */
DS.Errors = Ember.Object.extend(Ember.Enumerable, Ember.Evented, {
  /**
    Register with target handler

    @method registerHandlers
    @param {Object} target
    @param {Function} becameInvalid
    @param {Function} becameValid
  */
  registerHandlers: function(target, becameInvalid, becameValid) {
    this.on('becameInvalid', target, becameInvalid);
    this.on('becameValid', target, becameValid);
  },

  /**
    @property errorsByAttributeName
    @type {Ember.MapWithDefault}
    @private
  */
  errorsByAttributeName: Ember.reduceComputed("content", {
    initialValue: function() {
      return Ember.MapWithDefault.create({
        defaultValue: function() {
          return Ember.A();
        }
      });
    },

    addedItem: function(errors, error) {
      errors.get(error.attribute).pushObject(error);

      return errors;
    },

    removedItem: function(errors, error) {
      errors.get(error.attribute).removeObject(error);

      return errors;
    }
  }),

  /**
    Returns errors for a given attribute

    @method errorsFor
    @param {String} attribute
    @returns {Array}
  */
  errorsFor: function(attribute) {
    return get(this, 'errorsByAttributeName').get(attribute);
  },

  /**
  */
  messages: Ember.computed.mapBy('content', 'message'),

  /**
    @property content
    @type {Array}
    @private
  */
  content: Ember.computed(function() {
    return Ember.A();
  }),

  /**
    @method unknownProperty
    @private
  */
  unknownProperty: function(attribute) {
    var errors = this.errorsFor(attribute);
    if (isEmpty(errors)) { return null; }
    return errors;
  },

  /**
    @method nextObject
    @private
  */
  nextObject: function(index, previousObject, context) {
    return get(this, 'content').objectAt(index);
  },

  /**
    Total number of errors.

    @property length
    @type {Number}
    @readOnly
  */
  length: Ember.computed.oneWay('content.length').readOnly(),

  /**
    @property isEmpty
    @type {Boolean}
    @readOnly
  */
  isEmpty: Ember.computed.not('length').readOnly(),

  /**
    Adds error messages to a given attribute and sends
    `becameInvalid` event to the record.

    @method add
    @param {String} attribute
    @param {Array|String} messages
  */
  add: function(attribute, messages) {
    var wasEmpty = get(this, 'isEmpty');

    messages = this._findOrCreateMessages(attribute, messages);
    get(this, 'content').addObjects(messages);

    this.notifyPropertyChange(attribute);
    this.enumerableContentDidChange();

    if (wasEmpty && !get(this, 'isEmpty')) {
      this.trigger('becameInvalid');
    }
  },

  /**
    @method _findOrCreateMessages
    @private
  */
  _findOrCreateMessages: function(attribute, messages) {
    var errors = this.errorsFor(attribute);

    return Ember.makeArray(messages).map(function(message) {
      return errors.findBy('message', message) || {
        attribute: attribute,
        message: message
      };
    });
  },

  /**
    Removes all error messages from the given attribute and sends
    `becameValid` event to the record if there no more errors left.

    @method remove
    @param {String} attribute
  */
  remove: function(attribute) {
    if (get(this, 'isEmpty')) { return; }

    var content = get(this, 'content').rejectBy('attribute', attribute);
    get(this, 'content').setObjects(content);

    this.notifyPropertyChange(attribute);
    this.enumerableContentDidChange();

    if (get(this, 'isEmpty')) {
      this.trigger('becameValid');
    }
  },

  /**
    Removes all error messages and sends `becameValid` event
    to the record.

    @method clear
  */
  clear: function() {
    if (get(this, 'isEmpty')) { return; }

    get(this, 'content').clear();
    this.enumerableContentDidChange();

    this.trigger('becameValid');
  },

  /**
    Checks if there is error messages for the given attribute.

    @method has
    @param {String} attribute
    @returns {Boolean} true if there some errors on given attribute
  */
  has: function(attribute) {
    return !isEmpty(this.errorsFor(attribute));
  }
});

})();



(function() {
/**
  @module ember-data
*/

var get = Ember.get, set = Ember.set,
    merge = Ember.merge, once = Ember.run.once;

var retrieveFromCurrentState = Ember.computed('currentState', function(key, value) {
  return get(get(this, 'currentState'), key);
}).readOnly();

/**

  The model class that all Ember Data records descend from.

  @class Model
  @namespace DS
  @extends Ember.Object
  @uses Ember.Evented
*/
DS.Model = Ember.Object.extend(Ember.Evented, {
  /**
    If this property is `true` the record is in the `empty`
    state. Empty is the first state all records enter after they have
    been created. Most records created by the store will quickly
    transition to the `loading` state if data needs to be fetched from
    the server or the `created` state if the record is created on the
    client. A record can also enter the empty state if the adapter is
    unable to locate the record.

    @property isEmpty
    @type {Boolean}
    @readOnly
  */
  isEmpty: retrieveFromCurrentState,
  /**
    If this property is `true` the record is in the `loading` state. A
    record enters this state when the store askes the adapter for its
    data. It remains in this state until the adapter provides the
    requested data.

    @property isLoading
    @type {Boolean}
    @readOnly
  */
  isLoading: retrieveFromCurrentState,
  /**
    If this property is `true` the record is in the `loaded` state. A
    record enters this state when its data is populated. Most of a
    record's lifecycle is spent inside substates of the `loaded`
    state.

    Example

    ```javascript
    var record = store.createRecord(App.Model);
    record.get('isLoaded'); // true

    store.find('model', 1).then(function(model) {
      model.get('isLoaded'); // true
    });
    ```

    @property isLoaded
    @type {Boolean}
    @readOnly
  */
  isLoaded: retrieveFromCurrentState,
  /**
    If this property is `true` the record is in the `dirty` state. The
    record has local changes that have not yet been saved by the
    adapter. This includes records that have been created (but not yet
    saved) or deleted.

    Example

    ```javascript
    var record = store.createRecord(App.Model);
    record.get('isDirty'); // true

    store.find('model', 1).then(function(model) {
      model.get('isDirty'); // false
      model.set('foo', 'some value');
      model.set('isDirty'); // true
    });
    ```

    @property isDirty
    @type {Boolean}
    @readOnly
  */
  isDirty: retrieveFromCurrentState,
  /**
    If this property is `true` the record is in the `saving` state. A
    record enters the saving state when `save` is called, but the
    adapter has not yet acknowledged that the changes have been
    persisted to the backend.

    Example

    ```javascript
    var record = store.createRecord(App.Model);
    record.get('isSaving'); // false
    var promise = record.save();
    record.get('isSaving'); // true
    promise.then(function() {
      record.get('isSaving'); // false
    });
    ```

    @property isSaving
    @type {Boolean}
    @readOnly
  */
  isSaving: retrieveFromCurrentState,
  /**
    If this property is `true` the record is in the `deleted` state
    and has been marked for deletion. When `isDeleted` is true and
    `isDirty` is true, the record is deleted locally but the deletion
    was not yet persisted. When `isSaving` is true, the change is
    in-flight. When both `isDirty` and `isSaving` are false, the
    change has persisted.

    Example

    ```javascript
    var record = store.createRecord(App.Model);
    record.get('isDeleted'); // false
    record.deleteRecord();
    record.get('isDeleted'); // true
    ```

    @property isDeleted
    @type {Boolean}
    @readOnly
  */
  isDeleted: retrieveFromCurrentState,
  /**
    If this property is `true` the record is in the `new` state. A
    record will be in the `new` state when it has been created on the
    client and the adapter has not yet report that it was successfully
    saved.

    Example

    ```javascript
    var record = store.createRecord(App.Model);
    record.get('isNew'); // true

    store.find('model', 1).then(function(model) {
      model.get('isNew'); // false
    });
    ```

    @property isNew
    @type {Boolean}
    @readOnly
  */
  isNew: retrieveFromCurrentState,
  /**
    If this property is `true` the record is in the `valid` state. A
    record will be in the `valid` state when no client-side
    validations have failed and the adapter did not report any
    server-side validation failures.

    @property isValid
    @type {Boolean}
    @readOnly
  */
  isValid: retrieveFromCurrentState,
  /**
    If the record is in the dirty state this property will report what
    kind of change has caused it to move into the dirty
    state. Possible values are:

    - `created` The record has been created by the client and not yet saved to the adapter.
    - `updated` The record has been updated by the client and not yet saved to the adapter.
    - `deleted` The record has been deleted by the client and not yet saved to the adapter.

    Example

    ```javascript
    var record = store.createRecord(App.Model);
    record.get('dirtyType'); // 'created'
    ```

    @property dirtyType
    @type {String}
    @readOnly
  */
  dirtyType: retrieveFromCurrentState,

  /**
    If `true` the adapter reported that it was unable to save local
    changes to the backend. This may also result in the record having
    its `isValid` property become false if the adapter reported that
    server-side validations failed.

    Example

    ```javascript
    record.get('isError'); // false
    record.set('foo', 'invalid value');
    record.save().then(null, function() {
      record.get('isError'); // true
    });
    ```

    @property isError
    @type {Boolean}
    @readOnly
  */
  isError: false,
  /**
    If `true` the store is attempting to reload the record form the adapter.

    Example

    ```javascript
    record.get('isReloading'); // false
    record.reload();
    record.get('isReloading'); // true
    ```

    @property isReloading
    @type {Boolean}
    @readOnly
  */
  isReloading: false,

  /**
    The `clientId` property is a transient numerical identifier
    generated at runtime by the data store. It is important
    primarily because newly created objects may not yet have an
    externally generated id.

    @property clientId
    @private
    @type {Number|String}
  */
  clientId: null,
  /**
    All ember models have an id property. This is an identifier
    managed by an external source. These are always coerced to be
    strings before being used internally. Note when declaring the
    attributes for a model it is an error to declare an id
    attribute.

    ```javascript
    var record = store.createRecord(App.Model);
    record.get('id'); // null

    store.find('model', 1).then(function(model) {
      model.get('id'); // '1'
    });
    ```

    @property id
    @type {String}
  */
  id: null,
  transaction: null,
  /**
    @property currentState
    @private
    @type {Object}
  */
  currentState: null,
  /**
    When the record is in the `invalid` state this object will contain
    any errors returned by the adapter. When present the errors hash
    typically contains keys coresponding to the invalid property names
    and values which are an array of error messages.

    ```javascript
    record.get('errors.length'); // 0
    record.set('foo', 'invalid value');
    record.save().then(null, function() {
      record.get('errors').get('foo'); // ['foo should be a number.']
    });
    ```

    @property errors
    @type {Object}
  */
  errors: null,

  /**
    Create a JSON representation of the record, using the serialization
    strategy of the store's adapter.

   `serialize` takes an optional hash as a parameter, currently
    supported options are:

   - `includeId`: `true` if the record's ID should be included in the
      JSON representation.

    @method serialize
    @param {Object} options
    @returns {Object} an object whose values are primitive JSON values only
  */
  serialize: function(options) {
    var store = get(this, 'store');
    return store.serialize(this, options);
  },

  /**
    Use [DS.JSONSerializer](DS.JSONSerializer.html) to
    get the JSON representation of a record.

    `toJSON` takes an optional hash as a parameter, currently
    supported options are:

    - `includeId`: `true` if the record's ID should be included in the
      JSON representation.

    @method toJSON
    @param {Object} options
    @returns {Object} A JSON representation of the object.
  */
  toJSON: function(options) {
    // container is for lazy transform lookups
    var serializer = DS.JSONSerializer.create({ container: this.container });
    return serializer.serialize(this, options);
  },

  /**
    Fired when the record is loaded from the server.

    @event didLoad
  */
  didLoad: Ember.K,

  /**
    Fired when the record is updated.

    @event didUpdate
  */
  didUpdate: Ember.K,

  /**
    Fired when the record is created.

    @event didCreate
  */
  didCreate: Ember.K,

  /**
    Fired when the record is deleted.

    @event didDelete
  */
  didDelete: Ember.K,

  /**
    Fired when the record becomes invalid.

    @event becameInvalid
  */
  becameInvalid: Ember.K,

  /**
    Fired when the record enters the error state.

    @event becameError
  */
  becameError: Ember.K,

  /**
    @property data
    @private
    @type {Object}
  */
  data: Ember.computed(function() {
    this._data = this._data || {};
    return this._data;
  }).property(),

  _data: null,

  init: function() {
    set(this, 'currentState', DS.RootState.empty);
    var errors = DS.Errors.create();
    errors.registerHandlers(this, function() {
      this.send('becameInvalid');
    }, function() {
      this.send('becameValid');
    });
    set(this, 'errors', errors);
    this._super();
    this._setup();
  },

  _setup: function() {
    this._changesToSync = {};
    this._deferredTriggers = [];
    this._data = {};
    this._attributes = {};
    this._inFlightAttributes = {};
    this._relationships = {};
  },

  /**
    @method send
    @private
    @param {String} name
    @param {Object} context
  */
  send: function(name, context) {
    var currentState = get(this, 'currentState');

    if (!currentState[name]) {
      this._unhandledEvent(currentState, name, context);
    }

    return currentState[name](this, context);
  },

  /**
    @method transitionTo
    @private
    @param {String} name
  */
  transitionTo: function(name) {
    // POSSIBLE TODO: Remove this code and replace with
    // always having direct references to state objects

    var pivotName = name.split(".", 1),
        currentState = get(this, 'currentState'),
        state = currentState;

    do {
      if (state.exit) { state.exit(this); }
      state = state.parentState;
    } while (!state.hasOwnProperty(pivotName));

    var path = name.split(".");

    var setups = [], enters = [], i, l;

    for (i=0, l=path.length; i<l; i++) {
      state = state[path[i]];

      if (state.enter) { enters.push(state); }
      if (state.setup) { setups.push(state); }
    }

    for (i=0, l=enters.length; i<l; i++) {
      enters[i].enter(this);
    }

    set(this, 'currentState', state);

    for (i=0, l=setups.length; i<l; i++) {
      setups[i].setup(this);
    }

    this.updateRecordArraysLater();
  },

  _unhandledEvent: function(state, name, context) {
    var errorMessage = "Attempted to handle event `" + name + "` ";
    errorMessage    += "on " + String(this) + " while in state ";
    errorMessage    += state.stateName + ". ";

    if (context !== undefined) {
      errorMessage  += "Called with " + Ember.inspect(context) + ".";
    }

    throw new Ember.Error(errorMessage);
  },

  withTransaction: function(fn) {
    var transaction = get(this, 'transaction');
    if (transaction) { fn(transaction); }
  },

  /**
    @method loadingData
    @private
    @param {Promise} promise
  */
  loadingData: function(promise) {
    this.send('loadingData', promise);
  },

  /**
    @method loadedData
    @private
  */
  loadedData: function() {
    this.send('loadedData');
  },

  /**
    @method notFound
    @private
  */
  notFound: function() {
    this.send('notFound');
  },

  /**
    @method pushedData
    @private
  */
  pushedData: function() {
    this.send('pushedData');
  },

  /**
    Marks the record as deleted but does not save it. You must call
    `save` afterwards if you want to persist it. You might use this
    method if you want to allow the user to still `rollback()` a
    delete after it was made.

    Example

    ```javascript
    App.ModelDeleteRoute = Ember.Route.extend({
      actions: {
        softDelete: function() {
          this.get('model').deleteRecord();
        },
        confirm: function() {
          this.get('model').save();
        },
        undo: function() {
          this.get('model').rollback();
        }
      }
    });
    ```

    @method deleteRecord
  */
  deleteRecord: function() {
    this.send('deleteRecord');
  },

  /**
    Same as `deleteRecord`, but saves the record immediately.

    Example

    ```javascript
    App.ModelDeleteRoute = Ember.Route.extend({
      actions: {
        delete: function() {
          var controller = this.controller;
          this.get('model').destroyRecord().then(function() {
            controller.transitionToRoute('model.index');
          });
        }
      }
    });
    ```

    @method destroyRecord
    @return {Promise} a promise that will be resolved when the adapter returns
    successfully or rejected if the adapter returns with an error.
  */
  destroyRecord: function() {
    this.deleteRecord();
    return this.save();
  },

  /**
    @method unloadRecord
    @private
  */
  unloadRecord: function() {
    Ember.assert("You can only unload a loaded, non-dirty record.", !get(this, 'isDirty'));

    this.send('unloadRecord');
  },

  /**
    @method clearRelationships
    @private
  */
  clearRelationships: function() {
    this.eachRelationship(function(name, relationship) {
      if (relationship.kind === 'belongsTo') {
        set(this, name, null);
      } else if (relationship.kind === 'hasMany') {
        var hasMany = this._relationships[relationship.name];
        if (hasMany) { hasMany.clear(); }
      }
    }, this);
  },

  /**
    @method updateRecordArrays
    @private
  */
  updateRecordArrays: function() {
    get(this, 'store').dataWasUpdated(this.constructor, this);
  },

  /**
    Returns an object, whose keys are changed properties, and value is
    an [oldProp, newProp] array.

    Example

    ```javascript
    App.Mascot = DS.Model.extend({
      name: attr('string')
    });

    var person = store.createRecord('person');
    person.changedAttributes(); // {}
    person.set('name', 'Tomster');
    person.changedAttributes(); // {name: [undefined, 'Tomster']}
    ```

    @method changedAttributes
    @return {Object} an object, whose keys are changed properties,
      and value is an [oldProp, newProp] array.
  */
  changedAttributes: function() {
    var oldData = get(this, '_data'),
        newData = get(this, '_attributes'),
        diffData = {},
        prop;

    for (prop in newData) {
      diffData[prop] = [oldData[prop], newData[prop]];
    }

    return diffData;
  },

  /**
    @method adapterWillCommit
    @private
  */
  adapterWillCommit: function() {
    this.send('willCommit');
  },

  /**
    If the adapter did not return a hash in response to a commit,
    merge the changed attributes and relationships into the existing
    saved data.

    @method adapterDidCommit
  */
  adapterDidCommit: function(data) {
    set(this, 'isError', false);

    if (data) {
      this._data = data;
    } else {
      Ember.mixin(this._data, this._inFlightAttributes);
    }

    this._inFlightAttributes = {};

    this.send('didCommit');
    this.updateRecordArraysLater();

    if (!data) { return; }

    this.suspendRelationshipObservers(function() {
      this.notifyPropertyChange('data');
    });
  },

  /**
    @method adapterDidDirty
    @private
  */
  adapterDidDirty: function() {
    this.send('becomeDirty');
    this.updateRecordArraysLater();
  },

  dataDidChange: Ember.observer(function() {
    this.reloadHasManys();
  }, 'data'),

  reloadHasManys: function() {
    var relationships = get(this.constructor, 'relationshipsByName');
    this.updateRecordArraysLater();
    relationships.forEach(function(name, relationship) {
      if (this._data.links && this._data.links[name]) { return; }
      if (relationship.kind === 'hasMany') {
        this.hasManyDidChange(relationship.key);
      }
    }, this);
  },

  hasManyDidChange: function(key) {
    var hasMany = this._relationships[key];

    if (hasMany) {
      var records = this._data[key] || [];

      set(hasMany, 'content', Ember.A(records));
      set(hasMany, 'isLoaded', true);
      hasMany.trigger('didLoad');
    }
  },

  /**
    @method updateRecordArraysLater
    @private
  */
  updateRecordArraysLater: function() {
    Ember.run.once(this, this.updateRecordArrays);
  },

  /**
    @method setupData
    @private
    @param {Object} data
    @param {Boolean} partial the data should be merged into
      the existing data, not replace it.
  */
  setupData: function(data, partial) {
    if (partial) {
      Ember.merge(this._data, data);
    } else {
      this._data = data;
    }

    var relationships = this._relationships;

    this.eachRelationship(function(name, rel) {
      if (data.links && data.links[name]) { return; }
      if (rel.options.async) { relationships[name] = null; }
    });

    if (data) { this.pushedData(); }

    this.suspendRelationshipObservers(function() {
      this.notifyPropertyChange('data');
    });
  },

  materializeId: function(id) {
    set(this, 'id', id);
  },

  materializeAttributes: function(attributes) {
    Ember.assert("Must pass a hash of attributes to materializeAttributes", !!attributes);
    merge(this._data, attributes);
  },

  materializeAttribute: function(name, value) {
    this._data[name] = value;
  },

  /**
    @method updateHasMany
    @private
    @param {String} name
    @param {Array} records
  */
  updateHasMany: function(name, records) {
    this._data[name] = records;
    this.hasManyDidChange(name);
  },

  /**
    @method updateBelongsTo
    @private
    @param {String} name
    @param {DS.Model} record
  */
  updateBelongsTo: function(name, record) {
    this._data[name] = record;
  },

  /**
    If the model `isDirty` this function will which discard any unsaved
    changes

    Example

    ```javascript
    record.get('name'); // 'Untitled Document'
    record.set('name', 'Doc 1');
    record.get('name'); // 'Doc 1'
    record.rollback();
    record.get('name'); // 'Untitled Document'
    ```

    @method rollback
  */
  rollback: function() {
    this._attributes = {};

    if (get(this, 'isError')) {
      this._inFlightAttributes = {};
      set(this, 'isError', false);
    }

    if (!get(this, 'isValid')) {
      this._inFlightAttributes = {};
    }

    this.send('rolledBack');

    this.suspendRelationshipObservers(function() {
      this.notifyPropertyChange('data');
    });
  },

  toStringExtension: function() {
    return get(this, 'id');
  },

  /**
    The goal of this method is to temporarily disable specific observers
    that take action in response to application changes.

    This allows the system to make changes (such as materialization and
    rollback) that should not trigger secondary behavior (such as setting an
    inverse relationship or marking records as dirty).

    The specific implementation will likely change as Ember proper provides
    better infrastructure for suspending groups of observers, and if Array
    observation becomes more unified with regular observers.

    @method suspendRelationshipObservers
    @private
    @param callback
    @param binding
  */
  suspendRelationshipObservers: function(callback, binding) {
    var observers = get(this.constructor, 'relationshipNames').belongsTo;
    var self = this;

    try {
      this._suspendedRelationships = true;
      Ember._suspendObservers(self, observers, null, 'belongsToDidChange', function() {
        Ember._suspendBeforeObservers(self, observers, null, 'belongsToWillChange', function() {
          callback.call(binding || self);
        });
      });
    } finally {
      this._suspendedRelationships = false;
    }
  },

  /**
    Save the record and persist any changes to the record to an
    extenal source via the adapter.

    Example

    ```javascript
    record.set('name', 'Tomster');
    record.save().then(function(){
      // Success callback
    }, function() {
      // Error callback
    });
    ```
    @method save
    @return {Promise} a promise that will be resolved when the adapter returns
    successfully or rejected if the adapter returns with an error.
  */
  save: function() {
    var promiseLabel = "DS: Model#save " + this;
    var resolver = Ember.RSVP.defer(promiseLabel);

    this.get('store').scheduleSave(this, resolver);
    this._inFlightAttributes = this._attributes;
    this._attributes = {};

    return DS.PromiseObject.create({ promise: resolver.promise });
  },

  /**
    Reload the record from the adapter.

    This will only work if the record has already finished loading
    and has not yet been modified (`isLoaded` but not `isDirty`,
    or `isSaving`).

    Example

    ```javascript
    App.ModelViewRoute = Ember.Route.extend({
      actions: {
        reload: function() {
          this.get('model').reload();
        }
      }
    });
    ```

    @method reload
    @return {Promise} a promise that will be resolved with the record when the
    adapter returns successfully or rejected if the adapter returns
    with an error.
  */
  reload: function() {
    set(this, 'isReloading', true);

    var  record = this;

    var promiseLabel = "DS: Model#reload of " + this;
    var promise = new Ember.RSVP.Promise(function(resolve){
       record.send('reloadRecord', resolve);
    }, promiseLabel).then(function() {
      record.set('isReloading', false);
      record.set('isError', false);
      return record;
    }, function(reason) {
      record.set('isError', true);
      throw reason;
    }, "DS: Model#reload complete, update flags");

    return DS.PromiseObject.create({ promise: promise });
  },

  // FOR USE DURING COMMIT PROCESS

  adapterDidUpdateAttribute: function(attributeName, value) {

    // If a value is passed in, update the internal attributes and clear
    // the attribute cache so it picks up the new value. Otherwise,
    // collapse the current value into the internal attributes because
    // the adapter has acknowledged it.
    if (value !== undefined) {
      this._data[attributeName] = value;
      this.notifyPropertyChange(attributeName);
    } else {
      this._data[attributeName] = this._inFlightAttributes[attributeName];
    }

    this.updateRecordArraysLater();
  },

  /**
    @method adapterDidInvalidate
    @private
  */
  adapterDidInvalidate: function(errors) {
    var recordErrors = get(this, 'errors');
    function addError(name) {
      if (errors[name]) {
        recordErrors.add(name, errors[name]);
      }
    }

    this.eachAttribute(addError);
    this.eachRelationship(addError);
  },

  /**
    @method adapterDidError
    @private
  */
  adapterDidError: function() {
    this.send('becameError');
    set(this, 'isError', true);
  },

  /**
    Override the default event firing from Ember.Evented to
    also call methods with the given name.

    @method trigger
    @private
    @param name
  */
  trigger: function(name) {
    Ember.tryInvoke(this, name, [].slice.call(arguments, 1));
    this._super.apply(this, arguments);
  },

  triggerLater: function() {
    this._deferredTriggers.push(arguments);
    once(this, '_triggerDeferredTriggers');
  },

  _triggerDeferredTriggers: function() {
    for (var i=0, l=this._deferredTriggers.length; i<l; i++) {
      this.trigger.apply(this, this._deferredTriggers[i]);
    }

    this._deferredTriggers = [];
  }
});

DS.Model.reopenClass({

  /**
    Alias DS.Model's `create` method to `_create`. This allows us to create DS.Model
    instances from within the store, but if end users accidentally call `create()`
    (instead of `createRecord()`), we can raise an error.

    @method _create
    @private
    @static
  */
  _create: DS.Model.create,

  /**
    Override the class' `create()` method to raise an error. This
    prevents end users from inadvertently calling `create()` instead
    of `createRecord()`. The store is still able to create instances
    by calling the `_create()` method. To create an instance of a
    `DS.Model` use [store.createRecord](DS.Store.html#method_createRecord).

    @method create
    @private
    @static
  */
  create: function() {
    throw new Ember.Error("You should not call `create` on a model. Instead, call `store.createRecord` with the attributes you would like to set.");
  }
});

})();



(function() {
/**
  @module ember-data
*/

var get = Ember.get;

/**
  @class Model
  @namespace DS
*/
DS.Model.reopenClass({
  /**
    A map whose keys are the attributes of the model (properties
    described by DS.attr) and whose values are the meta object for the
    property.

    Example

    ```javascript

    App.Person = DS.Model.extend({
      firstName: attr('string'),
      lastName: attr('string'),
      birthday: attr('date')
    });

    var attributes = Ember.get(App.Person, 'attributes')

    attributes.forEach(function(name, meta) {
      console.log(name, meta);
    });

    // prints:
    // firstName {type: "string", isAttribute: true, options: Object, parentType: function, name: "firstName"}
    // lastName {type: "string", isAttribute: true, options: Object, parentType: function, name: "lastName"}
    // birthday {type: "date", isAttribute: true, options: Object, parentType: function, name: "birthday"}
    ```

    @property attributes
    @static
    @type {Ember.Map}
    @readOnly
  */
  attributes: Ember.computed(function() {
    var map = Ember.Map.create();

    this.eachComputedProperty(function(name, meta) {
      if (meta.isAttribute) {
        Ember.assert("You may not set `id` as an attribute on your model. Please remove any lines that look like: `id: DS.attr('<type>')` from " + this.toString(), name !== 'id');

        meta.name = name;
        map.set(name, meta);
      }
    });

    return map;
  }),

  /**
    A map whose keys are the attributes of the model (properties
    described by DS.attr) and whose values are type of transformation
    applied to each attribute. This map does not include any
    attributes that do not have an transformation type.

    Example

    ```javascript
    App.Person = DS.Model.extend({
      firstName: attr(),
      lastName: attr('string'),
      birthday: attr('date')
    });

    var transformedAttributes = Ember.get(App.Person, 'transformedAttributes')

    transformedAttributes.forEach(function(field, type) {
      console.log(field, type);
    });

    // prints:
    // lastName string
    // birthday date
    ```

    @property transformedAttributes
    @static
    @type {Ember.Map}
    @readOnly
  */
  transformedAttributes: Ember.computed(function() {
    var map = Ember.Map.create();

    this.eachAttribute(function(key, meta) {
      if (meta.type) {
        map.set(key, meta.type);
      }
    });

    return map;
  }),

  /**
    Iterates through the attributes of the model, calling the passed function on each
    attribute.

    The callback method you provide should have the following signature (all
    parameters are optional):

    ```javascript
    function(name, meta);
    ```

    - `name` the name of the current property in the iteration
    - `meta` the meta object for the attribute property in the iteration

    Note that in addition to a callback, you can also pass an optional target
    object that will be set as `this` on the context.

    Example

    ```javascript
    App.Person = DS.Model.extend({
      firstName: attr('string'),
      lastName: attr('string'),
      birthday: attr('date')
    });

    App.Person.eachAttribute(function(name, meta) {
      console.log(name, meta);
    });

    // prints:
    // firstName {type: "string", isAttribute: true, options: Object, parentType: function, name: "firstName"}
    // lastName {type: "string", isAttribute: true, options: Object, parentType: function, name: "lastName"}
    // birthday {type: "date", isAttribute: true, options: Object, parentType: function, name: "birthday"}
   ```

    @method eachAttribute
    @param {Function} callback The callback to execute
    @param {Object} [target] The target object to use
    @static
  */
  eachAttribute: function(callback, binding) {
    get(this, 'attributes').forEach(function(name, meta) {
      callback.call(binding, name, meta);
    }, binding);
  },

  /**
    Iterates through the transformedAttributes of the model, calling
    the passed function on each attribute. Note the callback will not be
    called for any attributes that do not have an transformation type.

    The callback method you provide should have the following signature (all
    parameters are optional):

    ```javascript
    function(name, type);
    ```

    - `name` the name of the current property in the iteration
    - `type` a tring contrining the name of the type of transformed
      applied to the attribute

    Note that in addition to a callback, you can also pass an optional target
    object that will be set as `this` on the context.

    Example

    ```javascript
    App.Person = DS.Model.extend({
      firstName: attr(),
      lastName: attr('string'),
      birthday: attr('date')
    });

    App.Person.eachTransformedAttribute(function(name, type) {
      console.log(name, type);
    });

    // prints:
    // lastName string
    // birthday date
   ```

    @method eachTransformedAttribute
    @param {Function} callback The callback to execute
    @param {Object} [target] The target object to use
    @static
  */
  eachTransformedAttribute: function(callback, binding) {
    get(this, 'transformedAttributes').forEach(function(name, type) {
      callback.call(binding, name, type);
    });
  }
});


DS.Model.reopen({
  eachAttribute: function(callback, binding) {
    this.constructor.eachAttribute(callback, binding);
  }
});

function getDefaultValue(record, options, key) {
  if (typeof options.defaultValue === "function") {
    return options.defaultValue();
  } else {
    return options.defaultValue;
  }
}

function hasValue(record, key) {
  return record._attributes.hasOwnProperty(key) ||
         record._inFlightAttributes.hasOwnProperty(key) ||
         record._data.hasOwnProperty(key);
}

function getValue(record, key) {
  if (record._attributes.hasOwnProperty(key)) {
    return record._attributes[key];
  } else if (record._inFlightAttributes.hasOwnProperty(key)) {
    return record._inFlightAttributes[key];
  } else {
    return record._data[key];
  }
}

/**
  `DS.attr` defines an attribute on a [DS.Model](DS.Model.html).
  By default, attributes are passed through as-is, however you can specify an
  optional type to have the value automatically transformed.
  Ember Data ships with four basic transform types: `string`, `number`,
  `boolean` and `date`. You can define your own transforms by subclassing
  [DS.Transform](DS.Transform.html).

  `DS.attr` takes an optional hash as a second parameter, currently
  supported options are:

  - `defaultValue`: Pass a string or a function to be called to set the attribute
                    to a default value if none is supplied.

  Example

  ```javascript
  var attr = DS.attr;

  App.User = DS.Model.extend({
    username: attr('string'),
    email: attr('string'),
    verified: attr('boolean', {defaultValue: false})
  });
  ```

  @namespace
  @method attr
  @for DS
  @param {String} type the attribute type
  @param {Object} options a hash of options
  @return {Attribute}
*/

DS.attr = function(type, options) {
  options = options || {};

  var meta = {
    type: type,
    isAttribute: true,
    options: options
  };

  return Ember.computed(function(key, value) {
    if (arguments.length > 1) {
      Ember.assert("You may not set `id` as an attribute on your model. Please remove any lines that look like: `id: DS.attr('<type>')` from " + this.constructor.toString(), key !== 'id');
      var oldValue = this._attributes[key] || this._inFlightAttributes[key] || this._data[key];

      this.send('didSetProperty', {
        name: key,
        oldValue: oldValue,
        originalValue: this._data[key],
        value: value
      });

      this._attributes[key] = value;
      return value;
    } else if (hasValue(this, key)) {
      return getValue(this, key);
    } else {
      return getDefaultValue(this, options, key);
    }

  // `data` is never set directly. However, it may be
  // invalidated from the state manager's setData
  // event.
  }).property('data').meta(meta);
};


})();



(function() {
/**
  @module ember-data
*/

})();



(function() {
/**
  @module ember-data
*/

/**
  An AttributeChange object is created whenever a record's
  attribute changes value. It is used to track changes to a
  record between transaction commits.

  @class AttributeChange
  @namespace DS
  @private
  @constructor
*/
var AttributeChange = DS.AttributeChange = function(options) {
  this.record = options.record;
  this.store = options.store;
  this.name = options.name;
  this.value = options.value;
  this.oldValue = options.oldValue;
};

AttributeChange.createChange = function(options) {
  return new AttributeChange(options);
};

AttributeChange.prototype = {
  sync: function() {
    if (this.value !== this.oldValue) {
      this.record.send('becomeDirty');
      this.record.updateRecordArraysLater();
    }

    // TODO: Use this object in the commit process
    this.destroy();
  },

  /**
    If the AttributeChange is destroyed (either by being rolled back
    or being committed), remove it from the list of pending changes
    on the record.

    @method destroy
  */
  destroy: function() {
    delete this.record._changesToSync[this.name];
  }
};

})();



(function() {
/**
  @module ember-data
*/

var get = Ember.get, set = Ember.set;
var forEach = Ember.EnumerableUtils.forEach;

/**
  @class RelationshipChange
  @namespace DS
  @private
  @construtor
*/
DS.RelationshipChange = function(options) {
  this.parentRecord = options.parentRecord;
  this.childRecord = options.childRecord;
  this.firstRecord = options.firstRecord;
  this.firstRecordKind = options.firstRecordKind;
  this.firstRecordName = options.firstRecordName;
  this.secondRecord = options.secondRecord;
  this.secondRecordKind = options.secondRecordKind;
  this.secondRecordName = options.secondRecordName;
  this.changeType = options.changeType;
  this.store = options.store;

  this.committed = {};
};

/**
  @class RelationshipChangeAdd
  @namespace DS
  @private
  @construtor
*/
DS.RelationshipChangeAdd = function(options){
  DS.RelationshipChange.call(this, options);
};

/**
  @class RelationshipChangeRemove
  @namespace DS
  @private
  @construtor
*/
DS.RelationshipChangeRemove = function(options){
  DS.RelationshipChange.call(this, options);
};

DS.RelationshipChange.create = function(options) {
  return new DS.RelationshipChange(options);
};

DS.RelationshipChangeAdd.create = function(options) {
  return new DS.RelationshipChangeAdd(options);
};

DS.RelationshipChangeRemove.create = function(options) {
  return new DS.RelationshipChangeRemove(options);
};

DS.OneToManyChange = {};
DS.OneToNoneChange = {};
DS.ManyToNoneChange = {};
DS.OneToOneChange = {};
DS.ManyToManyChange = {};

DS.RelationshipChange._createChange = function(options){
  if(options.changeType === "add"){
    return DS.RelationshipChangeAdd.create(options);
  }
  if(options.changeType === "remove"){
    return DS.RelationshipChangeRemove.create(options);
  }
};


DS.RelationshipChange.determineRelationshipType = function(recordType, knownSide){
  var knownKey = knownSide.key, key, otherKind;
  var knownKind = knownSide.kind;

  var inverse = recordType.inverseFor(knownKey);

  if (inverse){
    key = inverse.name;
    otherKind = inverse.kind;
  }

  if (!inverse){
    return knownKind === "belongsTo" ? "oneToNone" : "manyToNone";
  }
  else{
    if(otherKind === "belongsTo"){
      return knownKind === "belongsTo" ? "oneToOne" : "manyToOne";
    }
    else{
      return knownKind === "belongsTo" ? "oneToMany" : "manyToMany";
    }
  }

};

DS.RelationshipChange.createChange = function(firstRecord, secondRecord, store, options){
  // Get the type of the child based on the child's client ID
  var firstRecordType = firstRecord.constructor, changeType;
  changeType = DS.RelationshipChange.determineRelationshipType(firstRecordType, options);
  if (changeType === "oneToMany"){
    return DS.OneToManyChange.createChange(firstRecord, secondRecord, store, options);
  }
  else if (changeType === "manyToOne"){
    return DS.OneToManyChange.createChange(secondRecord, firstRecord, store, options);
  }
  else if (changeType === "oneToNone"){
    return DS.OneToNoneChange.createChange(firstRecord, secondRecord, store, options);
  }
  else if (changeType === "manyToNone"){
    return DS.ManyToNoneChange.createChange(firstRecord, secondRecord, store, options);
  }
  else if (changeType === "oneToOne"){
    return DS.OneToOneChange.createChange(firstRecord, secondRecord, store, options);
  }
  else if (changeType === "manyToMany"){
    return DS.ManyToManyChange.createChange(firstRecord, secondRecord, store, options);
  }
};

DS.OneToNoneChange.createChange = function(childRecord, parentRecord, store, options) {
  var key = options.key;
  var change = DS.RelationshipChange._createChange({
      parentRecord: parentRecord,
      childRecord: childRecord,
      firstRecord: childRecord,
      store: store,
      changeType: options.changeType,
      firstRecordName: key,
      firstRecordKind: "belongsTo"
  });

  store.addRelationshipChangeFor(childRecord, key, parentRecord, null, change);

  return change;
};

DS.ManyToNoneChange.createChange = function(childRecord, parentRecord, store, options) {
  var key = options.key;
  var change = DS.RelationshipChange._createChange({
      parentRecord: childRecord,
      childRecord: parentRecord,
      secondRecord: childRecord,
      store: store,
      changeType: options.changeType,
      secondRecordName: options.key,
      secondRecordKind: "hasMany"
  });

  store.addRelationshipChangeFor(childRecord, key, parentRecord, null, change);
  return change;
};


DS.ManyToManyChange.createChange = function(childRecord, parentRecord, store, options) {
  // If the name of the belongsTo side of the relationship is specified,
  // use that
  // If the type of the parent is specified, look it up on the child's type
  // definition.
  var key = options.key;

  var change = DS.RelationshipChange._createChange({
      parentRecord: parentRecord,
      childRecord: childRecord,
      firstRecord: childRecord,
      secondRecord: parentRecord,
      firstRecordKind: "hasMany",
      secondRecordKind: "hasMany",
      store: store,
      changeType: options.changeType,
      firstRecordName:  key
  });

  store.addRelationshipChangeFor(childRecord, key, parentRecord, null, change);


  return change;
};

DS.OneToOneChange.createChange = function(childRecord, parentRecord, store, options) {
  var key;

  // If the name of the belongsTo side of the relationship is specified,
  // use that
  // If the type of the parent is specified, look it up on the child's type
  // definition.
  if (options.parentType) {
    key = options.parentType.inverseFor(options.key).name;
  } else if (options.key) {
    key = options.key;
  } else {
    Ember.assert("You must pass either a parentType or belongsToName option to OneToManyChange.forChildAndParent", false);
  }

  var change = DS.RelationshipChange._createChange({
      parentRecord: parentRecord,
      childRecord: childRecord,
      firstRecord: childRecord,
      secondRecord: parentRecord,
      firstRecordKind: "belongsTo",
      secondRecordKind: "belongsTo",
      store: store,
      changeType: options.changeType,
      firstRecordName:  key
  });

  store.addRelationshipChangeFor(childRecord, key, parentRecord, null, change);


  return change;
};

DS.OneToOneChange.maintainInvariant = function(options, store, childRecord, key){
  if (options.changeType === "add" && store.recordIsMaterialized(childRecord)) {
    var oldParent = get(childRecord, key);
    if (oldParent){
      var correspondingChange = DS.OneToOneChange.createChange(childRecord, oldParent, store, {
          parentType: options.parentType,
          hasManyName: options.hasManyName,
          changeType: "remove",
          key: options.key
        });
      store.addRelationshipChangeFor(childRecord, key, options.parentRecord , null, correspondingChange);
     correspondingChange.sync();
    }
  }
};

DS.OneToManyChange.createChange = function(childRecord, parentRecord, store, options) {
  var key;

  // If the name of the belongsTo side of the relationship is specified,
  // use that
  // If the type of the parent is specified, look it up on the child's type
  // definition.
  if (options.parentType) {
    key = options.parentType.inverseFor(options.key).name;
    DS.OneToManyChange.maintainInvariant( options, store, childRecord, key );
  } else if (options.key) {
    key = options.key;
  } else {
    Ember.assert("You must pass either a parentType or belongsToName option to OneToManyChange.forChildAndParent", false);
  }

  var change = DS.RelationshipChange._createChange({
      parentRecord: parentRecord,
      childRecord: childRecord,
      firstRecord: childRecord,
      secondRecord: parentRecord,
      firstRecordKind: "belongsTo",
      secondRecordKind: "hasMany",
      store: store,
      changeType: options.changeType,
      firstRecordName:  key
  });

  store.addRelationshipChangeFor(childRecord, key, parentRecord, change.getSecondRecordName(), change);


  return change;
};


DS.OneToManyChange.maintainInvariant = function(options, store, childRecord, key){
  if (options.changeType === "add" && childRecord) {
    var oldParent = get(childRecord, key);
    if (oldParent){
      var correspondingChange = DS.OneToManyChange.createChange(childRecord, oldParent, store, {
          parentType: options.parentType,
          hasManyName: options.hasManyName,
          changeType: "remove",
          key: options.key
        });
      store.addRelationshipChangeFor(childRecord, key, options.parentRecord, correspondingChange.getSecondRecordName(), correspondingChange);
      correspondingChange.sync();
    }
  }
};

/**
  @class RelationshipChange
  @namespace DS
*/
DS.RelationshipChange.prototype = {

  getSecondRecordName: function() {
    var name = this.secondRecordName, parent;

    if (!name) {
      parent = this.secondRecord;
      if (!parent) { return; }

      var childType = this.firstRecord.constructor;
      var inverse = childType.inverseFor(this.firstRecordName);
      this.secondRecordName = inverse.name;
    }

    return this.secondRecordName;
  },

  /**
    Get the name of the relationship on the belongsTo side.

    @method getFirstRecordName
    @return {String}
  */
  getFirstRecordName: function() {
    var name = this.firstRecordName;
    return name;
  },

  /**
    @method destroy
    @private
  */
  destroy: function() {
    var childRecord = this.childRecord,
        belongsToName = this.getFirstRecordName(),
        hasManyName = this.getSecondRecordName(),
        store = this.store;

    store.removeRelationshipChangeFor(childRecord, belongsToName, this.parentRecord, hasManyName, this.changeType);
  },

  getSecondRecord: function(){
    return this.secondRecord;
  },

  /**
    @method getFirstRecord
    @private
  */
  getFirstRecord: function() {
    return this.firstRecord;
  },

  coalesce: function(){
    var relationshipPairs = this.store.relationshipChangePairsFor(this.firstRecord);
    forEach(relationshipPairs, function(pair){
      var addedChange = pair["add"];
      var removedChange = pair["remove"];
      if(addedChange && removedChange) {
        addedChange.destroy();
        removedChange.destroy();
      }
    });
  }
};

DS.RelationshipChangeAdd.prototype = Ember.create(DS.RelationshipChange.create({}));
DS.RelationshipChangeRemove.prototype = Ember.create(DS.RelationshipChange.create({}));

// the object is a value, and not a promise
function isValue(object) {
  return typeof object === 'object' && (!object.then || typeof object.then !== 'function');
}

DS.RelationshipChangeAdd.prototype.changeType = "add";
DS.RelationshipChangeAdd.prototype.sync = function() {
  var secondRecordName = this.getSecondRecordName(),
      firstRecordName = this.getFirstRecordName(),
      firstRecord = this.getFirstRecord(),
      secondRecord = this.getSecondRecord();

  //Ember.assert("You specified a hasMany (" + hasManyName + ") on " + (!belongsToName && (newParent || oldParent || this.lastParent).constructor) + " but did not specify an inverse belongsTo on " + child.constructor, belongsToName);
  //Ember.assert("You specified a belongsTo (" + belongsToName + ") on " + child.constructor + " but did not specify an inverse hasMany on " + (!hasManyName && (newParent || oldParent || this.lastParentRecord).constructor), hasManyName);

  if (secondRecord instanceof DS.Model && firstRecord instanceof DS.Model) {
    if(this.secondRecordKind === "belongsTo"){
      secondRecord.suspendRelationshipObservers(function(){
        set(secondRecord, secondRecordName, firstRecord);
      });

     }
     else if(this.secondRecordKind === "hasMany"){
      secondRecord.suspendRelationshipObservers(function(){
        var relationship = get(secondRecord, secondRecordName);
        if (isValue(relationship)) { relationship.addObject(firstRecord); }
      });
    }
  }

  if (firstRecord instanceof DS.Model && secondRecord instanceof DS.Model && get(firstRecord, firstRecordName) !== secondRecord) {
    if(this.firstRecordKind === "belongsTo"){
      firstRecord.suspendRelationshipObservers(function(){
        set(firstRecord, firstRecordName, secondRecord);
      });
    }
    else if(this.firstRecordKind === "hasMany"){
      firstRecord.suspendRelationshipObservers(function(){
        var relationship = get(firstRecord, firstRecordName);
        if (isValue(relationship)) { relationship.addObject(secondRecord); }
      });
    }
  }

  this.coalesce();
};

DS.RelationshipChangeRemove.prototype.changeType = "remove";
DS.RelationshipChangeRemove.prototype.sync = function() {
  var secondRecordName = this.getSecondRecordName(),
      firstRecordName = this.getFirstRecordName(),
      firstRecord = this.getFirstRecord(),
      secondRecord = this.getSecondRecord();

  //Ember.assert("You specified a hasMany (" + hasManyName + ") on " + (!belongsToName && (newParent || oldParent || this.lastParent).constructor) + " but did not specify an inverse belongsTo on " + child.constructor, belongsToName);
  //Ember.assert("You specified a belongsTo (" + belongsToName + ") on " + child.constructor + " but did not specify an inverse hasMany on " + (!hasManyName && (newParent || oldParent || this.lastParentRecord).constructor), hasManyName);

  if (secondRecord instanceof DS.Model && firstRecord instanceof DS.Model) {
    if(this.secondRecordKind === "belongsTo"){
      secondRecord.suspendRelationshipObservers(function(){
        set(secondRecord, secondRecordName, null);
      });
    }
    else if(this.secondRecordKind === "hasMany"){
      secondRecord.suspendRelationshipObservers(function(){
        var relationship = get(secondRecord, secondRecordName);
        if (isValue(relationship)) { relationship.removeObject(firstRecord); }
      });
    }
  }

  if (firstRecord instanceof DS.Model && get(firstRecord, firstRecordName)) {
    if(this.firstRecordKind === "belongsTo"){
      firstRecord.suspendRelationshipObservers(function(){
        set(firstRecord, firstRecordName, null);
      });
     }
     else if(this.firstRecordKind === "hasMany"){
       firstRecord.suspendRelationshipObservers(function(){
         var relationship = get(firstRecord, firstRecordName);
         if (isValue(relationship)) { relationship.removeObject(secondRecord); }
      });
    }
  }

  this.coalesce();
};

})();



(function() {
/**
  @module ember-data
*/

})();



(function() {
var get = Ember.get, set = Ember.set,
    isNone = Ember.isNone;

/**
  @module ember-data
*/

function asyncBelongsTo(type, options, meta) {
  return Ember.computed(function(key, value) {
    var data = get(this, 'data'),
        store = get(this, 'store'),
        promiseLabel = "DS: Async belongsTo " + this + " : " + key;

    if (arguments.length === 2) {
      Ember.assert("You can only add a '" + type + "' record to this relationship", !value || value instanceof store.modelFor(type));
      return value === undefined ? null : DS.PromiseObject.create({ promise: Ember.RSVP.resolve(value, promiseLabel) });
    }

    var link = data.links && data.links[key],
        belongsTo = data[key];

    if(!isNone(belongsTo)) {
      var promise = store.fetchRecord(belongsTo) || Ember.RSVP.resolve(belongsTo, promiseLabel);
      return DS.PromiseObject.create({ promise: promise});
    } else if (link) {
      var resolver = Ember.RSVP.defer("DS: Async belongsTo (link) " + this + " : " + key);
      store.findBelongsTo(this, link, meta, resolver);
      return DS.PromiseObject.create({ promise: resolver.promise });
    } else {
      return null;
    }
  }).property('data').meta(meta);
}

/**
  `DS.belongsTo` is used to define One-To-One and One-To-Many
  relationships on a [DS.Model](DS.Model.html).


  `DS.belongsTo` takes an optional hash as a second parameter, currently
  supported options are:

  - `async`: A boolean value used to explicitly declare this to be an async relationship.
  - `inverse`: A string used to identify the inverse property on a
    related model in a One-To-Many relationship. See [Explicit Inverses](#toc_explicit-inverses)

  #### One-To-One
  To declare a one-to-one relationship between two models, use
  `DS.belongsTo`:

  ```javascript
  App.User = DS.Model.extend({
    profile: DS.belongsTo('profile')
  });

  App.Profile = DS.Model.extend({
    user: DS.belongsTo('user')
  });
  ```

  #### One-To-Many
  To declare a one-to-many relationship between two models, use
  `DS.belongsTo` in combination with `DS.hasMany`, like this:

  ```javascript
  App.Post = DS.Model.extend({
    comments: DS.hasMany('comment')
  });

  App.Comment = DS.Model.extend({
    post: DS.belongsTo('post')
  });
  ```

  @namespace
  @method belongsTo
  @for DS
  @param {String or DS.Model} type the model type of the relationship
  @param {Object} options a hash of options
  @return {Ember.computed} relationship
*/
DS.belongsTo = function(type, options) {
  if (typeof type === 'object') {
    options = type;
    type = undefined;
  } else {
    Ember.assert("The first argument DS.belongsTo must be a model type or string, like DS.belongsTo(App.Person)", !!type && (typeof type === 'string' || DS.Model.detect(type)));
  }

  options = options || {};

  var meta = { type: type, isRelationship: true, options: options, kind: 'belongsTo' };

  if (options.async) {
    return asyncBelongsTo(type, options, meta);
  }

  return Ember.computed(function(key, value) {
    var data = get(this, 'data'),
        store = get(this, 'store'), belongsTo, typeClass;

    if (typeof type === 'string') {
      typeClass = store.modelFor(type);
    } else {
      typeClass = type;
    }

    if (arguments.length === 2) {
      Ember.assert("You can only add a '" + type + "' record to this relationship", !value || value instanceof typeClass);
      return value === undefined ? null : value;
    }

    belongsTo = data[key];

    if (isNone(belongsTo)) { return null; }

    store.fetchRecord(belongsTo);

    return belongsTo;
  }).property('data').meta(meta);
};

/**
  These observers observe all `belongsTo` relationships on the record. See
  `relationships/ext` to see how these observers get their dependencies.

  @class Model
  @namespace DS
*/
DS.Model.reopen({

  /**
    @method belongsToWillChange
    @private
    @static
    @param record
    @param key
  */
  belongsToWillChange: Ember.beforeObserver(function(record, key) {
    if (get(record, 'isLoaded')) {
      var oldParent = get(record, key);

      if (oldParent) {
        var store = get(record, 'store'),
            change = DS.RelationshipChange.createChange(record, oldParent, store, { key: key, kind: "belongsTo", changeType: "remove" });

        change.sync();
        this._changesToSync[key] = change;
      }
    }
  }),

  /**
    @method belongsToDidChange
    @private
    @static
    @param record
    @param key
  */
  belongsToDidChange: Ember.immediateObserver(function(record, key) {
    if (get(record, 'isLoaded')) {
      var newParent = get(record, key);

      if (newParent) {
        var store = get(record, 'store'),
            change = DS.RelationshipChange.createChange(record, newParent, store, { key: key, kind: "belongsTo", changeType: "add" });

        change.sync();
      }
    }

    delete this._changesToSync[key];
  })
});

})();



(function() {
/**
  @module ember-data
*/

var get = Ember.get, set = Ember.set, setProperties = Ember.setProperties;

function asyncHasMany(type, options, meta) {
  return Ember.computed(function(key, value) {
    var relationship = this._relationships[key],
        promiseLabel = "DS: Async hasMany " + this + " : " + key;

    if (!relationship) {
      var resolver = Ember.RSVP.defer(promiseLabel);
      relationship = buildRelationship(this, key, options, function(store, data) {
        var link = data.links && data.links[key];
        var rel;
        if (link) {
          rel = store.findHasMany(this, link, meta, resolver);
        } else {
          rel = store.findMany(this, data[key], meta.type, resolver);
        }
        // cache the promise so we can use it
        // when we come back and don't need to rebuild
        // the relationship.
        set(rel, 'promise', resolver.promise);
        return rel;
      });
    }

    var promise = relationship.get('promise').then(function() {
      return relationship;
    }, null, "DS: Async hasMany records received");

    return DS.PromiseArray.create({ promise: promise });
  }).property('data').meta(meta);
}

function buildRelationship(record, key, options, callback) {
  var rels = record._relationships;

  if (rels[key]) { return rels[key]; }

  var data = get(record, 'data'),
      store = get(record, 'store');

  var relationship = rels[key] = callback.call(record, store, data);

  return setProperties(relationship, {
    owner: record, name: key, isPolymorphic: options.polymorphic
  });
}

function hasRelationship(type, options) {
  options = options || {};

  var meta = { type: type, isRelationship: true, options: options, kind: 'hasMany' };

  if (options.async) {
    return asyncHasMany(type, options, meta);
  }

  return Ember.computed(function(key, value) {
    return buildRelationship(this, key, options, function(store, data) {
      var records = data[key];
      Ember.assert("You looked up the '" + key + "' relationship on '" + this + "' but some of the associated records were not loaded. Either make sure they are all loaded together with the parent record, or specify that the relationship is async (`DS.hasMany({ async: true })`)", Ember.A(records).everyProperty('isEmpty', false));
      return store.findMany(this, data[key], meta.type);
    });
  }).property('data').meta(meta);
}

/**
  `DS.hasMany` is used to define One-To-Many and Many-To-Many
  relationships on a [DS.Model](DS.Model.html).

  `DS.hasMany` takes an optional hash as a second parameter, currently
  supported options are:

  - `async`: A boolean value used to explicitly declare this to be an async relationship.
  - `inverse`: A string used to identify the inverse property on a related model.

  #### One-To-Many
  To declare a one-to-many relationship between two models, use
  `DS.belongsTo` in combination with `DS.hasMany`, like this:

  ```javascript
  App.Post = DS.Model.extend({
    comments: DS.hasMany('comment')
  });

  App.Comment = DS.Model.extend({
    post: DS.belongsTo('post')
  });
  ```

  #### Many-To-Many
  To declare a many-to-many relationship between two models, use
  `DS.hasMany`:

  ```javascript
  App.Post = DS.Model.extend({
    tags: DS.hasMany('tag')
  });

  App.Tag = DS.Model.extend({
    posts: DS.hasMany('post')
  });
  ```

  #### Explicit Inverses

  Ember Data will do its best to discover which relationships map to
  one another. In the one-to-many code above, for example, Ember Data
  can figure out that changing the `comments` relationship should update
  the `post` relationship on the inverse because post is the only
  relationship to that model.

  However, sometimes you may have multiple `belongsTo`/`hasManys` for the
  same type. You can specify which property on the related model is
  the inverse using `DS.hasMany`'s `inverse` option:

  ```javascript
  var belongsTo = DS.belongsTo,
      hasMany = DS.hasMany;

  App.Comment = DS.Model.extend({
    onePost: belongsTo('post'),
    twoPost: belongsTo('post'),
    redPost: belongsTo('post'),
    bluePost: belongsTo('post')
  });

  App.Post = DS.Model.extend({
    comments: hasMany('comment', {
      inverse: 'redPost'
    })
  });
  ```

  You can also specify an inverse on a `belongsTo`, which works how
  you'd expect.

  @namespace
  @method hasMany
  @for DS
  @param {String or DS.Model} type the model type of the relationship
  @param {Object} options a hash of options
  @return {Ember.computed} relationship
*/
DS.hasMany = function(type, options) {
  if (typeof type === 'object') {
    options = type;
    type = undefined;
  }
  return hasRelationship(type, options);
};

})();



(function() {
var get = Ember.get, set = Ember.set;

/**
  @module ember-data
*/

/*
  This file defines several extensions to the base `DS.Model` class that
  add support for one-to-many relationships.
*/

/**
  @class Model
  @namespace DS
*/
DS.Model.reopen({

  /**
    This Ember.js hook allows an object to be notified when a property
    is defined.

    In this case, we use it to be notified when an Ember Data user defines a
    belongs-to relationship. In that case, we need to set up observers for
    each one, allowing us to track relationship changes and automatically
    reflect changes in the inverse has-many array.

    This hook passes the class being set up, as well as the key and value
    being defined. So, for example, when the user does this:

    ```javascript
    DS.Model.extend({
      parent: DS.belongsTo('user')
    });
    ```

    This hook would be called with "parent" as the key and the computed
    property returned by `DS.belongsTo` as the value.

    @method didDefineProperty
    @param proto
    @param key
    @param value
  */
  didDefineProperty: function(proto, key, value) {
    // Check if the value being set is a computed property.
    if (value instanceof Ember.Descriptor) {

      // If it is, get the metadata for the relationship. This is
      // populated by the `DS.belongsTo` helper when it is creating
      // the computed property.
      var meta = value.meta();

      if (meta.isRelationship && meta.kind === 'belongsTo') {
        Ember.addObserver(proto, key, null, 'belongsToDidChange');
        Ember.addBeforeObserver(proto, key, null, 'belongsToWillChange');
      }

      meta.parentType = proto.constructor;
    }
  }
});

/*
  These DS.Model extensions add class methods that provide relationship
  introspection abilities about relationships.

  A note about the computed properties contained here:

  **These properties are effectively sealed once called for the first time.**
  To avoid repeatedly doing expensive iteration over a model's fields, these
  values are computed once and then cached for the remainder of the runtime of
  your application.

  If your application needs to modify a class after its initial definition
  (for example, using `reopen()` to add additional attributes), make sure you
  do it before using your model with the store, which uses these properties
  extensively.
*/

DS.Model.reopenClass({
  /**
    For a given relationship name, returns the model type of the relationship.

    For example, if you define a model like this:

   ```javascript
    App.Post = DS.Model.extend({
      comments: DS.hasMany('comment')
    });
   ```

    Calling `App.Post.typeForRelationship('comments')` will return `App.Comment`.

    @method typeForRelationship
    @static
    @param {String} name the name of the relationship
    @return {subclass of DS.Model} the type of the relationship, or undefined
  */
  typeForRelationship: function(name) {
    var relationship = get(this, 'relationshipsByName').get(name);
    return relationship && relationship.type;
  },

  inverseFor: function(name) {
    var inverseType = this.typeForRelationship(name);

    if (!inverseType) { return null; }

    var options = this.metaForProperty(name).options;

    if (options.inverse === null) { return null; }
    
    var inverseName, inverseKind;

    if (options.inverse) {
      inverseName = options.inverse;
      inverseKind = Ember.get(inverseType, 'relationshipsByName').get(inverseName).kind;
    } else {
      var possibleRelationships = findPossibleInverses(this, inverseType);

      if (possibleRelationships.length === 0) { return null; }

      Ember.assert("You defined the '" + name + "' relationship on " + this + ", but multiple possible inverse relationships of type " + this + " were found on " + inverseType + ". Look at http://emberjs.com/guides/models/defining-models/#toc_explicit-inverses for how to explicitly specify inverses", possibleRelationships.length === 1);

      inverseName = possibleRelationships[0].name;
      inverseKind = possibleRelationships[0].kind;
    }

    function findPossibleInverses(type, inverseType, possibleRelationships) {
      possibleRelationships = possibleRelationships || [];

      var relationshipMap = get(inverseType, 'relationships');
      if (!relationshipMap) { return; }

      var relationships = relationshipMap.get(type);
      if (relationships) {
        possibleRelationships.push.apply(possibleRelationships, relationshipMap.get(type));
      }

      if (type.superclass) {
        findPossibleInverses(type.superclass, inverseType, possibleRelationships);
      }

      return possibleRelationships;
    }

    return {
      type: inverseType,
      name: inverseName,
      kind: inverseKind
    };
  },

  /**
    The model's relationships as a map, keyed on the type of the
    relationship. The value of each entry is an array containing a descriptor
    for each relationship with that type, describing the name of the relationship
    as well as the type.

    For example, given the following model definition:

    ```javascript
    App.Blog = DS.Model.extend({
      users: DS.hasMany('user'),
      owner: DS.belongsTo('user'),
      posts: DS.hasMany('post')
    });
    ```

    This computed property would return a map describing these
    relationships, like this:

    ```javascript
    var relationships = Ember.get(App.Blog, 'relationships');
    relationships.get(App.User);
    //=> [ { name: 'users', kind: 'hasMany' },
    //     { name: 'owner', kind: 'belongsTo' } ]
    relationships.get(App.Post);
    //=> [ { name: 'posts', kind: 'hasMany' } ]
    ```

    @property relationships
    @static
    @type Ember.Map
    @readOnly
  */
  relationships: Ember.computed(function() {
    var map = new Ember.MapWithDefault({
      defaultValue: function() { return []; }
    });

    // Loop through each computed property on the class
    this.eachComputedProperty(function(name, meta) {

      // If the computed property is a relationship, add
      // it to the map.
      if (meta.isRelationship) {
        if (typeof meta.type === 'string') {
          meta.type = this.store.modelFor(meta.type);
        }

        var relationshipsForType = map.get(meta.type);

        relationshipsForType.push({ name: name, kind: meta.kind });
      }
    });

    return map;
  }),

  /**
    A hash containing lists of the model's relationships, grouped
    by the relationship kind. For example, given a model with this
    definition:

    ```javascript
    App.Blog = DS.Model.extend({
      users: DS.hasMany('user'),
      owner: DS.belongsTo('user'),

      posts: DS.hasMany('post')
    });
    ```

    This property would contain the following:

    ```javascript
    var relationshipNames = Ember.get(App.Blog, 'relationshipNames');
    relationshipNames.hasMany;
    //=> ['users', 'posts']
    relationshipNames.belongsTo;
    //=> ['owner']
    ```

    @property relationshipNames
    @static
    @type Object
    @readOnly
  */
  relationshipNames: Ember.computed(function() {
    var names = { hasMany: [], belongsTo: [] };

    this.eachComputedProperty(function(name, meta) {
      if (meta.isRelationship) {
        names[meta.kind].push(name);
      }
    });

    return names;
  }),

  /**
    An array of types directly related to a model. Each type will be
    included once, regardless of the number of relationships it has with
    the model.

    For example, given a model with this definition:

    ```javascript
    App.Blog = DS.Model.extend({
      users: DS.hasMany('user'),
      owner: DS.belongsTo('user'),

      posts: DS.hasMany('post')
    });
    ```

    This property would contain the following:

    ```javascript
    var relatedTypes = Ember.get(App.Blog, 'relatedTypes');
    //=> [ App.User, App.Post ]
    ```

    @property relatedTypes
    @static
    @type Ember.Array
    @readOnly
  */
  relatedTypes: Ember.computed(function() {
    var type,
        types = Ember.A();

    // Loop through each computed property on the class,
    // and create an array of the unique types involved
    // in relationships
    this.eachComputedProperty(function(name, meta) {
      if (meta.isRelationship) {
        type = meta.type;

        if (typeof type === 'string') {
          type = get(this, type, false) || this.store.modelFor(type);
        }

        Ember.assert("You specified a hasMany (" + meta.type + ") on " + meta.parentType + " but " + meta.type + " was not found.",  type);

        if (!types.contains(type)) {
          Ember.assert("Trying to sideload " + name + " on " + this.toString() + " but the type doesn't exist.", !!type);
          types.push(type);
        }
      }
    });

    return types;
  }),

  /**
    A map whose keys are the relationships of a model and whose values are
    relationship descriptors.

    For example, given a model with this
    definition:

    ```javascript
    App.Blog = DS.Model.extend({
      users: DS.hasMany('user'),
      owner: DS.belongsTo('user'),

      posts: DS.hasMany('post')
    });
    ```

    This property would contain the following:

    ```javascript
    var relationshipsByName = Ember.get(App.Blog, 'relationshipsByName');
    relationshipsByName.get('users');
    //=> { key: 'users', kind: 'hasMany', type: App.User }
    relationshipsByName.get('owner');
    //=> { key: 'owner', kind: 'belongsTo', type: App.User }
    ```

    @property relationshipsByName
    @static
    @type Ember.Map
    @readOnly
  */
  relationshipsByName: Ember.computed(function() {
    var map = Ember.Map.create(), type;

    this.eachComputedProperty(function(name, meta) {
      if (meta.isRelationship) {
        meta.key = name;
        type = meta.type;

        if (!type && meta.kind === 'hasMany') {
          type = Ember.String.singularize(name);
        } else if (!type) {
          type = name;
        }

        if (typeof type === 'string') {
          meta.type = this.store.modelFor(type);
        }

        map.set(name, meta);
      }
    });

    return map;
  }),

  /**
    A map whose keys are the fields of the model and whose values are strings
    describing the kind of the field. A model's fields are the union of all of its
    attributes and relationships.

    For example:

    ```javascript

    App.Blog = DS.Model.extend({
      users: DS.hasMany('user'),
      owner: DS.belongsTo('user'),

      posts: DS.hasMany('post'),

      title: DS.attr('string')
    });

    var fields = Ember.get(App.Blog, 'fields');
    fields.forEach(function(field, kind) {
      console.log(field, kind);
    });

    // prints:
    // users, hasMany
    // owner, belongsTo
    // posts, hasMany
    // title, attribute
    ```

    @property fields
    @static
    @type Ember.Map
    @readOnly
  */
  fields: Ember.computed(function() {
    var map = Ember.Map.create();

    this.eachComputedProperty(function(name, meta) {
      if (meta.isRelationship) {
        map.set(name, meta.kind);
      } else if (meta.isAttribute) {
        map.set(name, 'attribute');
      }
    });

    return map;
  }),

  /**
    Given a callback, iterates over each of the relationships in the model,
    invoking the callback with the name of each relationship and its relationship
    descriptor.

    @method eachRelationship
    @static
    @param {Function} callback the callback to invoke
    @param {any} binding the value to which the callback's `this` should be bound
  */
  eachRelationship: function(callback, binding) {
    get(this, 'relationshipsByName').forEach(function(name, relationship) {
      callback.call(binding, name, relationship);
    });
  },

  /**
    Given a callback, iterates over each of the types related to a model,
    invoking the callback with the related type's class. Each type will be
    returned just once, regardless of how many different relationships it has
    with a model.

    @method eachRelatedType
    @static
    @param {Function} callback the callback to invoke
    @param {any} binding the value to which the callback's `this` should be bound
  */
  eachRelatedType: function(callback, binding) {
    get(this, 'relatedTypes').forEach(function(type) {
      callback.call(binding, type);
    });
  }
});

DS.Model.reopen({
  /**
    Given a callback, iterates over each of the relationships in the model,
    invoking the callback with the name of each relationship and its relationship
    descriptor.

    @method eachRelationship
    @param {Function} callback the callback to invoke
    @param {any} binding the value to which the callback's `this` should be bound
  */
  eachRelationship: function(callback, binding) {
    this.constructor.eachRelationship(callback, binding);
  }
});

})();



(function() {
/**
  @module ember-data
*/

})();



(function() {
/**
  @module ember-data
*/

var get = Ember.get, set = Ember.set;
var once = Ember.run.once;
var forEach = Ember.EnumerableUtils.forEach;

/**
  @class RecordArrayManager
  @namespace DS
  @private
  @extends Ember.Object
*/
DS.RecordArrayManager = Ember.Object.extend({
  init: function() {
    this.filteredRecordArrays = Ember.MapWithDefault.create({
      defaultValue: function() { return []; }
    });

    this.changedRecords = [];
  },

  recordDidChange: function(record) {
    this.changedRecords.push(record);
    once(this, this.updateRecordArrays);
  },

  recordArraysForRecord: function(record) {
    record._recordArrays = record._recordArrays || Ember.OrderedSet.create();
    return record._recordArrays;
  },

  /**
    This method is invoked whenever data is loaded into the store by the
    adapter or updated by the adapter, or when a record has changed.

    It updates all record arrays that a record belongs to.

    To avoid thrashing, it only runs at most once per run loop.

    @method updateRecordArrays
    @param {Class} type
    @param {Number|String} clientId
  */
  updateRecordArrays: function() {
    forEach(this.changedRecords, function(record) {
      if (get(record, 'isDeleted')) {
        this._recordWasDeleted(record);
      } else {
        this._recordWasChanged(record);
      }
    }, this);

    this.changedRecords = [];
  },

  _recordWasDeleted: function (record) {
    var recordArrays = record._recordArrays;

    if (!recordArrays) { return; }

    forEach(recordArrays, function(array) {
      array.removeRecord(record);
    });
  },

  _recordWasChanged: function (record) {
    var type = record.constructor,
        recordArrays = this.filteredRecordArrays.get(type),
        filter;

    forEach(recordArrays, function(array) {
      filter = get(array, 'filterFunction');
      this.updateRecordArray(array, filter, type, record);
    }, this);

    // loop through all manyArrays containing an unloaded copy of this
    // clientId and notify them that the record was loaded.
    var manyArrays = record._loadingRecordArrays;

    if (manyArrays) {
      for (var i=0, l=manyArrays.length; i<l; i++) {
        manyArrays[i].loadedRecord();
      }

      record._loadingRecordArrays = [];
    }
  },

  /**
    Update an individual filter.

    @method updateRecordArray
    @param {DS.FilteredRecordArray} array
    @param {Function} filter
    @param {Class} type
    @param {Number|String} clientId
  */
  updateRecordArray: function(array, filter, type, record) {
    var shouldBeInArray;

    if (!filter) {
      shouldBeInArray = true;
    } else {
      shouldBeInArray = filter(record);
    }

    var recordArrays = this.recordArraysForRecord(record);

    if (shouldBeInArray) {
      recordArrays.add(array);
      array.addRecord(record);
    } else if (!shouldBeInArray) {
      recordArrays.remove(array);
      array.removeRecord(record);
    }
  },

  /**
    This method is invoked if the `filterFunction` property is
    changed on a `DS.FilteredRecordArray`.

    It essentially re-runs the filter from scratch. This same
    method is invoked when the filter is created in th first place.

    @method updateFilter
    @param array
    @param type
    @param filter
  */
  updateFilter: function(array, type, filter) {
    var typeMap = this.store.typeMapFor(type),
        records = typeMap.records, record;

    for (var i=0, l=records.length; i<l; i++) {
      record = records[i];

      if (!get(record, 'isDeleted') && !get(record, 'isEmpty')) {
        this.updateRecordArray(array, filter, type, record);
      }
    }
  },

  /**
    Create a `DS.ManyArray` for a type and list of record references, and index
    the `ManyArray` under each reference. This allows us to efficiently remove
    records from `ManyArray`s when they are deleted.

    @method createManyArray
    @param {Class} type
    @param {Array} references
    @return {DS.ManyArray}
  */
  createManyArray: function(type, records) {
    var manyArray = DS.ManyArray.create({
      type: type,
      content: records,
      store: this.store
    });

    forEach(records, function(record) {
      var arrays = this.recordArraysForRecord(record);
      arrays.add(manyArray);
    }, this);

    return manyArray;
  },

  /**
    Create a `DS.RecordArray` for a type and register it for updates.

    @method createRecordArray
    @param {Class} type
    @return {DS.RecordArray}
  */
  createRecordArray: function(type) {
    var array = DS.RecordArray.create({
      type: type,
      content: Ember.A(),
      store: this.store,
      isLoaded: true
    });

    this.registerFilteredRecordArray(array, type);

    return array;
  },

  /**
    Create a `DS.FilteredRecordArray` for a type and register it for updates.

    @method createFilteredRecordArray
    @param {Class} type
    @param {Function} filter
    @return {DS.FilteredRecordArray}
  */
  createFilteredRecordArray: function(type, filter) {
    var array = DS.FilteredRecordArray.create({
      type: type,
      content: Ember.A(),
      store: this.store,
      manager: this,
      filterFunction: filter
    });

    this.registerFilteredRecordArray(array, type, filter);

    return array;
  },

  /**
    Create a `DS.AdapterPopulatedRecordArray` for a type with given query.

    @method createAdapterPopulatedRecordArray
    @param {Class} type
    @param {Object} query
    @return {DS.AdapterPopulatedRecordArray}
  */
  createAdapterPopulatedRecordArray: function(type, query) {
    return DS.AdapterPopulatedRecordArray.create({
      type: type,
      query: query,
      content: Ember.A(),
      store: this.store
    });
  },

  /**
    Register a RecordArray for a given type to be backed by
    a filter function. This will cause the array to update
    automatically when records of that type change attribute
    values or states.

    @method registerFilteredRecordArray
    @param {DS.RecordArray} array
    @param {Class} type
    @param {Function} filter
  */
  registerFilteredRecordArray: function(array, type, filter) {
    var recordArrays = this.filteredRecordArrays.get(type);
    recordArrays.push(array);

    this.updateFilter(array, type, filter);
  },

  // Internally, we maintain a map of all unloaded IDs requested by
  // a ManyArray. As the adapter loads data into the store, the
  // store notifies any interested ManyArrays. When the ManyArray's
  // total number of loading records drops to zero, it becomes
  // `isLoaded` and fires a `didLoad` event.
  registerWaitingRecordArray: function(record, array) {
    var loadingRecordArrays = record._loadingRecordArrays || [];
    loadingRecordArrays.push(array);
    record._loadingRecordArrays = loadingRecordArrays;
  }
});

})();



(function() {
/**
  @module ember-data
*/

var get = Ember.get, set = Ember.set;
var map = Ember.ArrayPolyfills.map;

var errorProps = ['description', 'fileName', 'lineNumber', 'message', 'name', 'number', 'stack'];

/**
  A `DS.InvalidError` is used by an adapter to signal the external API
  was unable to process a request because the content was not
  semantically correct or meaningful per the API. Usually this means a
  record failed some form of server side validation. When a promise
  from an adapter is rejected with a `DS.InvalidError` the record will
  transition to the `invalid` state and the errors will be set to the
  `errors` property on the record.

  Example

  ```javascript
  App.ApplicationAdapter = DS.RESTAdapter.extend({
    ajaxError: function(jqXHR) {
      var error = this._super(jqXHR);

      if (jqXHR && jqXHR.status === 422) {
        var jsonErrors = Ember.$.parseJSON(jqXHR.responseText)["errors"];
        return new DS.InvalidError(jsonErrors);
      } else {
        return error;
      }
    }
  });
  ```

  @class InvalidError
  @namespace DS
*/
DS.InvalidError = function(errors) {
  var tmp = Error.prototype.constructor.call(this, "The backend rejected the commit because it was invalid: " + Ember.inspect(errors));
  this.errors = errors;

  for (var i=0, l=errorProps.length; i<l; i++) {
    this[errorProps[i]] = tmp[errorProps[i]];
  }
};
DS.InvalidError.prototype = Ember.create(Error.prototype);

/**
  An adapter is an object that receives requests from a store and
  translates them into the appropriate action to take against your
  persistence layer. The persistence layer is usually an HTTP API, but
  may be anything, such as the browser's local storage. Typically the
  adapter is not invoked directly instead its functionality is accessed
  through the `store`.

  ### Creating an Adapter

  First, create a new subclass of `DS.Adapter`:

  ```javascript
  App.MyAdapter = DS.Adapter.extend({
    // ...your code here
  });
  ```

  To tell your store which adapter to use, set its `adapter` property:

  ```javascript
  App.store = DS.Store.create({
    adapter: App.MyAdapter.create()
  });
  ```

  `DS.Adapter` is an abstract base class that you should override in your
  application to customize it for your backend. The minimum set of methods
  that you should implement is:

    * `find()`
    * `createRecord()`
    * `updateRecord()`
    * `deleteRecord()`
    * `findAll()`
    * `findQuery()`

  To improve the network performance of your application, you can optimize
  your adapter by overriding these lower-level methods:

    * `findMany()`


  For an example implementation, see `DS.RESTAdapter`, the
  included REST adapter.

  @class Adapter
  @namespace DS
  @extends Ember.Object
*/

DS.Adapter = Ember.Object.extend({

  /**
    If you would like your adapter to use a custom serializer you can
    set the `defaultSerializer` property to be the name of the custom
    serializer.

    Note the `defaultSerializer` serializer has a lower priority then
    a model specific serializer (i.e. `PostSerializer`) or the
    `application` serializer.

    ```javascript
    var DjangoAdapter = DS.Adapter.extend({
      defaultSerializer: 'django'
    });
    ```

    @property defaultSerializer
    @type {String}
  */

  /**
    The `find()` method is invoked when the store is asked for a record that
    has not previously been loaded. In response to `find()` being called, you
    should query your persistence layer for a record with the given ID. Once
    found, you can asynchronously call the store's `push()` method to push
    the record into the store.

    Here is an example `find` implementation:

    ```javascript
    App.ApplicationAdapter = DS.Adapter.extend({
      find: function(store, type, id) {
        var url = [type, id].join('/');

        return new Ember.RSVP.Promise(function(resolve, reject) {
          jQuery.getJSON(url).then(function(data) {
            Ember.run(null, resolve, data);
          }, function(jqXHR) {
            jqXHR.then = null; // tame jQuery's ill mannered promises
            Ember.run(null, reject, jqXHR);
          });
        });
      }
    });
    ```

    @method find
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {String} id
    @return {Promise} promise
  */
  find: Ember.required(Function),

  /**
    The `findAll()` method is called when you call `find` on the store
    without an ID (i.e. `store.find('post')`).

    Example

    ```javascript
    App.ApplicationAdapter = DS.Adapter.extend({
      findAll: function(store, type, sinceToken) {
        var url = type;
        var query = { since: sinceToken };
        return new Ember.RSVP.Promise(function(resolve, reject) {
          jQuery.getJSON(url, query).then(function(data) {
            Ember.run(null, resolve, data);
          }, function(jqXHR) {
            jqXHR.then = null; // tame jQuery's ill mannered promises
            Ember.run(null, reject, jqXHR);
          });
        });
      }
    });
    ```

    @private
    @method findAll
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {String} sinceToken
    @return {Promise} promise
  */
  findAll: null,

  /**
    This method is called when you call `find` on the store with a
    query object as the second parameter (i.e. `store.find('person', {
    page: 1 })`).

    Example

    ```javascript
    App.ApplicationAdapter = DS.Adapter.extend({
      findQuery: function(store, type, query) {
        var url = type;
        return new Ember.RSVP.Promise(function(resolve, reject) {
          jQuery.getJSON(url, query).then(function(data) {
            Ember.run(null, resolve, data);
          }, function(jqXHR) {
            jqXHR.then = null; // tame jQuery's ill mannered promises
            Ember.run(null, reject, jqXHR);
          });
        });
      }
    });
    ```

    @private
    @method findQuery
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} query
    @param {DS.AdapterPopulatedRecordArray} recordArray
    @return {Promise} promise
  */
  findQuery: null,

  /**
    If the globally unique IDs for your records should be generated on the client,
    implement the `generateIdForRecord()` method. This method will be invoked
    each time you create a new record, and the value returned from it will be
    assigned to the record's `primaryKey`.

    Most traditional REST-like HTTP APIs will not use this method. Instead, the ID
    of the record will be set by the server, and your adapter will update the store
    with the new ID when it calls `didCreateRecord()`. Only implement this method if
    you intend to generate record IDs on the client-side.

    The `generateIdForRecord()` method will be invoked with the requesting store as
    the first parameter and the newly created record as the second parameter:

    ```javascript
    generateIdForRecord: function(store, record) {
      var uuid = App.generateUUIDWithStatisticallyLowOddsOfCollision();
      return uuid;
    }
    ```

    @method generateIdForRecord
    @param {DS.Store} store
    @param {DS.Model} record
    @return {String|Number} id
  */
  generateIdForRecord: null,

  /**
    Proxies to the serializer's `serialize` method.

    Example

    ```javascript
    App.ApplicationAdapter = DS.Adapter.extend({
      createRecord: function(store, type, record) {
        var data = this.serialize(record, { includeId: true });
        var url = type;

        // ...
      }
    });
    ```

    @method serialize
    @param {DS.Model} record
    @param {Object}   options
    @return {Object} serialized record
  */
  serialize: function(record, options) {
    return get(record, 'store').serializerFor(record.constructor.typeKey).serialize(record, options);
  },

  /**
    Implement this method in a subclass to handle the creation of
    new records.

    Serializes the record and send it to the server.

    Example

    ```javascript
    App.ApplicationAdapter = DS.Adapter.extend({
      createRecord: function(store, type, record) {
        var data = this.serialize(record, { includeId: true });
        var url = type;

        return new Ember.RSVP.Promise(function(resolve, reject) {
          jQuery.ajax({
            type: 'POST',
            url: url,
            dataType: 'json',
            data: data
          }).then(function(data) {
            Ember.run(null, resolve, data);
          }, function(jqXHR) {
            jqXHR.then = null; // tame jQuery's ill mannered promises
            Ember.run(null, reject, jqXHR);
          });
        });
      }
    });
    ```

    @method createRecord
    @param {DS.Store} store
    @param {subclass of DS.Model} type   the DS.Model class of the record
    @param {DS.Model} record
    @return {Promise} promise
  */
  createRecord: Ember.required(Function),

  /**
    Implement this method in a subclass to handle the updating of
    a record.

    Serializes the record update and send it to the server.

    Example

    ```javascript
    App.ApplicationAdapter = DS.Adapter.extend({
      updateRecord: function(store, type, record) {
        var data = this.serialize(record, { includeId: true });
        var id = record.get('id');
        var url = [type, id].join('/');

        return new Ember.RSVP.Promise(function(resolve, reject) {
          jQuery.ajax({
            type: 'PUT',
            url: url,
            dataType: 'json',
            data: data
          }).then(function(data) {
            Ember.run(null, resolve, data);
          }, function(jqXHR) {
            jqXHR.then = null; // tame jQuery's ill mannered promises
            Ember.run(null, reject, jqXHR);
          });
        });
      }
    });
    ```

    @method updateRecord
    @param {DS.Store} store
    @param {subclass of DS.Model} type   the DS.Model class of the record
    @param {DS.Model} record
    @return {Promise} promise
  */
  updateRecord: Ember.required(Function),

  /**
    Implement this method in a subclass to handle the deletion of
    a record.

    Sends a delete request for the record to the server.

    Example

    ```javascript
    App.ApplicationAdapter = DS.Adapter.extend({
      deleteRecord: function(store, type, record) {
        var data = this.serialize(record, { includeId: true });
        var id = record.get('id');
        var url = [type, id].join('/');

        return new Ember.RSVP.Promise(function(resolve, reject) {
          jQuery.ajax({
            type: 'DELETE',
            url: url,
            dataType: 'json',
            data: data
          }).then(function(data) {
            Ember.run(null, resolve, data);
          }, function(jqXHR) {
            jqXHR.then = null; // tame jQuery's ill mannered promises
            Ember.run(null, reject, jqXHR);
          });
        });
      }
    });
    ```

    @method deleteRecord
    @param {DS.Store} store
    @param {subclass of DS.Model} type   the DS.Model class of the record
    @param {DS.Model} record
    @return {Promise} promise
  */
  deleteRecord: Ember.required(Function),

  /**
    Find multiple records at once.

    By default, it loops over the provided ids and calls `find` on each.
    May be overwritten to improve performance and reduce the number of
    server requests.

    Example

    ```javascript
    App.ApplicationAdapter = DS.Adapter.extend({
      findMany: function(store, type, ids) {
        var url = type;
        return new Ember.RSVP.Promise(function(resolve, reject) {
          jQuery.getJSON(url, {ids: ids}).then(function(data) {
            Ember.run(null, resolve, data);
          }, function(jqXHR) {
            jqXHR.then = null; // tame jQuery's ill mannered promises
            Ember.run(null, reject, jqXHR);
          });
        });
      }
    });
    ```

    @method findMany
    @param {DS.Store} store
    @param {subclass of DS.Model} type   the DS.Model class of the records
    @param {Array}    ids
    @return {Promise} promise
  */
  findMany: function(store, type, ids) {
    var promises = map.call(ids, function(id) {
      return this.find(store, type, id);
    }, this);

    return Ember.RSVP.all(promises);
  }
});

})();



(function() {
/**
  @module ember-data
*/

var get = Ember.get, fmt = Ember.String.fmt,
    indexOf = Ember.EnumerableUtils.indexOf;

var counter = 0;

/**
  `DS.FixtureAdapter` is an adapter that loads records from memory.
  Its primarily used for development and testing. You can also use
  `DS.FixtureAdapter` while working on the API but are not ready to
  integrate yet. It is a fully functioning adapter. All CRUD methods
  are implemented. You can also implement query logic that a remote
  system would do. Its possible to do develop your entire application
  with `DS.FixtureAdapter`.

  For information on how to use the `FixtureAdapter` in your
  application please see the [FixtureAdapter
  guide](/guides/models/the-fixture-adapter/).

  @class FixtureAdapter
  @namespace DS
  @extends DS.Adapter
*/
DS.FixtureAdapter = DS.Adapter.extend({
  // by default, fixtures are already in normalized form
  serializer: null,

  /**
    If `simulateRemoteResponse` is `true` the `FixtureAdapter` will
    wait a number of milliseconds before resolving promises with the
    fixture values. The wait time can be configured via the `latency`
    property.

    @property simulateRemoteResponse
    @type {Boolean}
    @default true
  */
  simulateRemoteResponse: true,

  /**
    By default the `FixtureAdapter` will simulate a wait of the
    `latency` milliseconds before resolving promises with the fixture
    values. This behavior can be turned off via the
    `simulateRemoteResponse` property.

    @property latency
    @type {Number}
    @default 50
  */
  latency: 50,

  /**
    Implement this method in order to provide data associated with a type

    @method fixturesForType
    @param {Subclass of DS.Model} type
    @return {Array}
  */
  fixturesForType: function(type) {
    if (type.FIXTURES) {
      var fixtures = Ember.A(type.FIXTURES);
      return fixtures.map(function(fixture){
        var fixtureIdType = typeof fixture.id;
        if(fixtureIdType !== "number" && fixtureIdType !== "string"){
          throw new Error(fmt('the id property must be defined as a number or string for fixture %@', [fixture]));
        }
        fixture.id = fixture.id + '';
        return fixture;
      });
    }
    return null;
  },

  /**
    Implement this method in order to query fixtures data

    @method queryFixtures
    @param {Array} fixture
    @param {Object} query
    @param {Subclass of DS.Model} type
    @return {Promise|Array}
  */
  queryFixtures: function(fixtures, query, type) {
    Ember.assert('Not implemented: You must override the DS.FixtureAdapter::queryFixtures method to support querying the fixture store.');
  },

  /**
    @method updateFixtures
    @param {Subclass of DS.Model} type
    @param {Array} fixture
  */
  updateFixtures: function(type, fixture) {
    if(!type.FIXTURES) {
      type.FIXTURES = [];
    }

    var fixtures = type.FIXTURES;

    this.deleteLoadedFixture(type, fixture);

    fixtures.push(fixture);
  },

  /**
    Implement this method in order to provide json for CRUD methods

    @method mockJSON
    @param {Subclass of DS.Model} type
    @param {DS.Model} record
  */
  mockJSON: function(store, type, record) {
    return store.serializerFor(type).serialize(record, { includeId: true });
  },

  /**
    @method generateIdForRecord
    @param {DS.Store} store
    @param {DS.Model} record
    @return {String} id
  */
  generateIdForRecord: function(store) {
    return "fixture-" + counter++;
  },

  /**
    @method find
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {String} id
    @return {Promise} promise
  */
  find: function(store, type, id) {
    var fixtures = this.fixturesForType(type),
        fixture;

    Ember.assert("Unable to find fixtures for model type "+type.toString(), fixtures);

    if (fixtures) {
      fixture = Ember.A(fixtures).findProperty('id', id);
    }

    if (fixture) {
      return this.simulateRemoteCall(function() {
        return fixture;
      }, this);
    }
  },

  /**
    @method findMany
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Array} ids
    @return {Promise} promise
  */
  findMany: function(store, type, ids) {
    var fixtures = this.fixturesForType(type);

    Ember.assert("Unable to find fixtures for model type "+type.toString(), fixtures);

    if (fixtures) {
      fixtures = fixtures.filter(function(item) {
        return indexOf(ids, item.id) !== -1;
      });
    }

    if (fixtures) {
      return this.simulateRemoteCall(function() {
        return fixtures;
      }, this);
    }
  },

  /**
    @private
    @method findAll
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {String} sinceToken
    @return {Promise} promise
  */
  findAll: function(store, type) {
    var fixtures = this.fixturesForType(type);

    Ember.assert("Unable to find fixtures for model type "+type.toString(), fixtures);

    return this.simulateRemoteCall(function() {
      return fixtures;
    }, this);
  },

  /**
    @private
    @method findQuery
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} query
    @param {DS.AdapterPopulatedRecordArray} recordArray
    @return {Promise} promise
  */
  findQuery: function(store, type, query, array) {
    var fixtures = this.fixturesForType(type);

    Ember.assert("Unable to find fixtures for model type "+type.toString(), fixtures);

    fixtures = this.queryFixtures(fixtures, query, type);

    if (fixtures) {
      return this.simulateRemoteCall(function() {
        return fixtures;
      }, this);
    }
  },

  /**
    @method createRecord
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {DS.Model} record
    @return {Promise} promise
  */
  createRecord: function(store, type, record) {
    var fixture = this.mockJSON(store, type, record);

    this.updateFixtures(type, fixture);

    return this.simulateRemoteCall(function() {
      return fixture;
    }, this);
  },

  /**
    @method updateRecord
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {DS.Model} record
    @return {Promise} promise
  */
  updateRecord: function(store, type, record) {
    var fixture = this.mockJSON(store, type, record);

    this.updateFixtures(type, fixture);

    return this.simulateRemoteCall(function() {
      return fixture;
    }, this);
  },

  /**
    @method deleteRecord
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {DS.Model} record
    @return {Promise} promise
  */
  deleteRecord: function(store, type, record) {
    var fixture = this.mockJSON(store, type, record);

    this.deleteLoadedFixture(type, fixture);

    return this.simulateRemoteCall(function() {
      // no payload in a deletion
      return null;
    });
  },

  /*
    @method deleteLoadedFixture
    @private
    @param type
    @param record
  */
  deleteLoadedFixture: function(type, record) {
    var existingFixture = this.findExistingFixture(type, record);

    if(existingFixture) {
      var index = indexOf(type.FIXTURES, existingFixture);
      type.FIXTURES.splice(index, 1);
      return true;
    }
  },

  /*
    @method findExistingFixture
    @private
    @param type
    @param record
  */
  findExistingFixture: function(type, record) {
    var fixtures = this.fixturesForType(type);
    var id = get(record, 'id');

    return this.findFixtureById(fixtures, id);
  },

  /*
    @method findFixtureById
    @private
    @param fixtures
    @param id
  */
  findFixtureById: function(fixtures, id) {
    return Ember.A(fixtures).find(function(r) {
      if(''+get(r, 'id') === ''+id) {
        return true;
      } else {
        return false;
      }
    });
  },

  /*
    @method simulateRemoteCall
    @private
    @param callback
    @param context
  */
  simulateRemoteCall: function(callback, context) {
    var adapter = this;

    return new Ember.RSVP.Promise(function(resolve) {
      if (get(adapter, 'simulateRemoteResponse')) {
        // Schedule with setTimeout
        Ember.run.later(function() {
          resolve(callback.call(context));
        }, get(adapter, 'latency'));
      } else {
        // Asynchronous, but at the of the runloop with zero latency
        Ember.run.schedule('actions', null, function() {
          resolve(callback.call(context));
        });
      }
    }, "DS: FixtureAdapter#simulateRemoteCall");
  }
});

})();



(function() {
/**
  @module ember-data
*/

var get = Ember.get, set = Ember.set;
var forEach = Ember.ArrayPolyfills.forEach;
var map = Ember.ArrayPolyfills.map;

function coerceId(id) {
  return id == null ? null : id+'';
}

/**
  Normally, applications will use the `RESTSerializer` by implementing
  the `normalize` method and individual normalizations under
  `normalizeHash`.

  This allows you to do whatever kind of munging you need, and is
  especially useful if your server is inconsistent and you need to
  do munging differently for many different kinds of responses.

  See the `normalize` documentation for more information.

  ## Across the Board Normalization

  There are also a number of hooks that you might find useful to defined
  across-the-board rules for your payload. These rules will be useful
  if your server is consistent, or if you're building an adapter for
  an infrastructure service, like Parse, and want to encode service
  conventions.

  For example, if all of your keys are underscored and all-caps, but
  otherwise consistent with the names you use in your models, you
  can implement across-the-board rules for how to convert an attribute
  name in your model to a key in your JSON.

  ```js
  App.ApplicationSerializer = DS.RESTSerializer.extend({
    keyForAttribute: function(attr) {
      return Ember.String.underscore(attr).toUpperCase();
    }
  });
  ```

  You can also implement `keyForRelationship`, which takes the name
  of the relationship as the first parameter, and the kind of
  relationship (`hasMany` or `belongsTo`) as the second parameter.

  @class RESTSerializer
  @namespace DS
  @extends DS.JSONSerializer
*/
DS.RESTSerializer = DS.JSONSerializer.extend({
  /**
    If you want to do normalizations specific to some part of the payload, you
    can specify those under `normalizeHash`.

    For example, given the following json where the the `IDs` under
    `"comments"` are provided as `_id` instead of `id`.

    ```javascript
    {
      "post": {
        "id": 1,
        "title": "Rails is omakase",
        "comments": [ 1, 2 ]
      },
      "comments": [{
        "_id": 1,
        "body": "FIRST"
      }, {
        "_id": 2,
        "body": "Rails is unagi"
      }]
    }
    ```

    You use `normalizeHash` to normalize just the comments:

    ```javascript
    App.PostSerializer = DS.RESTSerializer.extend({
      normalizeHash: {
        comments: function(hash) {
          hash.id = hash._id;
          delete hash._id;
          return hash;
        }
      }
    });
    ```

    The key under `normalizeHash` is usually just the original key
    that was in the original payload. However, key names will be
    impacted by any modifications done in the `normalizePayload`
    method. The `DS.RESTSerializer`'s default implemention makes no
    changes to the payload keys.

    @property normalizeHash
    @type {Object}
    @default undefined
  */

  /**
    Normalizes a part of the JSON payload returned by
    the server. You should override this method, munge the hash
    and call super if you have generic normalization to do.

    It takes the type of the record that is being normalized
    (as a DS.Model class), the property where the hash was
    originally found, and the hash to normalize.

    For example, if you have a payload that looks like this:

    ```js
    {
      "post": {
        "id": 1,
        "title": "Rails is omakase",
        "comments": [ 1, 2 ]
      },
      "comments": [{
        "id": 1,
        "body": "FIRST"
      }, {
        "id": 2,
        "body": "Rails is unagi"
      }]
    }
    ```

    The `normalize` method will be called three times:

    * With `App.Post`, `"posts"` and `{ id: 1, title: "Rails is omakase", ... }`
    * With `App.Comment`, `"comments"` and `{ id: 1, body: "FIRST" }`
    * With `App.Comment`, `"comments"` and `{ id: 2, body: "Rails is unagi" }`

    You can use this method, for example, to normalize underscored keys to camelized
    or other general-purpose normalizations.

    If you want to do normalizations specific to some part of the payload, you
    can specify those under `normalizeHash`.

    For example, if the `IDs` under `"comments"` are provided as `_id` instead of
    `id`, you can specify how to normalize just the comments:

    ```js
    App.PostSerializer = DS.RESTSerializer.extend({
      normalizeHash: {
        comments: function(hash) {
          hash.id = hash._id;
          delete hash._id;
          return hash;
        }
      }
    });
    ```

    The key under `normalizeHash` is just the original key that was in the original
    payload.

    @method normalize
    @param {subclass of DS.Model} type
    @param {Object} hash
    @param {String} prop
    @returns {Object}
  */
  normalize: function(type, hash, prop) {
    this.normalizeId(hash);
    this.normalizeAttributes(type, hash);
    this.normalizeRelationships(type, hash);

    this.normalizeUsingDeclaredMapping(type, hash);

    if (this.normalizeHash && this.normalizeHash[prop]) {
      this.normalizeHash[prop](hash);
    }

    return this._super(type, hash, prop);
  },

  /**
    You can use this method to normalize all payloads, regardless of whether they
    represent single records or an array.

    For example, you might want to remove some extraneous data from the payload:

    ```js
    App.ApplicationSerializer = DS.RESTSerializer.extend({
      normalizePayload: function(type, payload) {
        delete payload.version;
        delete payload.status;
        return payload;
      }
    });
    ```

    @method normalizePayload
    @param {subclass of DS.Model} type
    @param {Object} hash
    @returns {Object} the normalized payload
  */
  normalizePayload: function(type, payload) {
    return payload;
  },

  /**
    @method normalizeId
    @private
  */
  normalizeId: function(hash) {
    var primaryKey = get(this, 'primaryKey');

    if (primaryKey === 'id') { return; }

    hash.id = hash[primaryKey];
    delete hash[primaryKey];
  },

  /**
    @method normalizeUsingDeclaredMapping
    @private
  */
  normalizeUsingDeclaredMapping: function(type, hash) {
    var attrs = get(this, 'attrs'), payloadKey, key;

    if (attrs) {
      for (key in attrs) {
        payloadKey = attrs[key];
        if (payloadKey && payloadKey.key) {
          payloadKey = payloadKey.key;
        }
        if (typeof payloadKey === 'string') {
          hash[key] = hash[payloadKey];
          delete hash[payloadKey];
        }
      }
    }
  },

  /**
    @method normalizeAttributes
    @private
  */
  normalizeAttributes: function(type, hash) {
    var payloadKey, key;

    if (this.keyForAttribute) {
      type.eachAttribute(function(key) {
        payloadKey = this.keyForAttribute(key);
        if (key === payloadKey) { return; }

        hash[key] = hash[payloadKey];
        delete hash[payloadKey];
      }, this);
    }
  },

  /**
    @method normalizeRelationships
    @private
  */
  normalizeRelationships: function(type, hash) {
    var payloadKey, key;

    if (this.keyForRelationship) {
      type.eachRelationship(function(key, relationship) {
        payloadKey = this.keyForRelationship(key, relationship.kind);
        if (key === payloadKey) { return; }

        hash[key] = hash[payloadKey];
        delete hash[payloadKey];
      }, this);
    }
  },

  /**
    Called when the server has returned a payload representing
    a single record, such as in response to a `find` or `save`.

    It is your opportunity to clean up the server's response into the normalized
    form expected by Ember Data.

    If you want, you can just restructure the top-level of your payload, and
    do more fine-grained normalization in the `normalize` method.

    For example, if you have a payload like this in response to a request for
    post 1:

    ```js
    {
      "id": 1,
      "title": "Rails is omakase",

      "_embedded": {
        "comment": [{
          "_id": 1,
          "comment_title": "FIRST"
        }, {
          "_id": 2,
          "comment_title": "Rails is unagi"
        }]
      }
    }
    ```

    You could implement a serializer that looks like this to get your payload
    into shape:

    ```js
    App.PostSerializer = DS.RESTSerializer.extend({
      // First, restructure the top-level so it's organized by type
      extractSingle: function(store, type, payload, id, requestType) {
        var comments = payload._embedded.comment;
        delete payload._embedded;

        payload = { comments: comments, post: payload };
        return this._super(store, type, payload, id, requestType);
      },

      normalizeHash: {
        // Next, normalize individual comments, which (after `extract`)
        // are now located under `comments`
        comments: function(hash) {
          hash.id = hash._id;
          hash.title = hash.comment_title;
          delete hash._id;
          delete hash.comment_title;
          return hash;
        }
      }
    })
    ```

    When you call super from your own implementation of `extractSingle`, the
    built-in implementation will find the primary record in your normalized
    payload and push the remaining records into the store.

    The primary record is the single hash found under `post` or the first
    element of the `posts` array.

    The primary record has special meaning when the record is being created
    for the first time or updated (`createRecord` or `updateRecord`). In
    particular, it will update the properties of the record that was saved.

    @method extractSingle
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} payload
    @param {String} id
    @param {'find'|'createRecord'|'updateRecord'|'deleteRecord'} requestType
    @returns {Object} the primary response to the original request
  */
  extractSingle: function(store, primaryType, payload, recordId, requestType) {
    payload = this.normalizePayload(primaryType, payload);

    var primaryTypeName = primaryType.typeKey,
        primaryRecord;

    for (var prop in payload) {
      var typeName  = this.typeForRoot(prop),
          isPrimary = typeName === primaryTypeName;

      // legacy support for singular resources
      if (isPrimary && Ember.typeOf(payload[prop]) !== "array" ) {
        primaryRecord = this.normalize(primaryType, payload[prop], prop);
        continue;
      }

      var type = store.modelFor(typeName);

      /*jshint loopfunc:true*/
      forEach.call(payload[prop], function(hash) {
        var typeName = this.typeForRoot(prop),
            type = store.modelFor(typeName),
            typeSerializer = store.serializerFor(type);

        hash = typeSerializer.normalize(type, hash, prop);

        var isFirstCreatedRecord = isPrimary && !recordId && !primaryRecord,
            isUpdatedRecord = isPrimary && coerceId(hash.id) === recordId;

        // find the primary record.
        //
        // It's either:
        // * the record with the same ID as the original request
        // * in the case of a newly created record that didn't have an ID, the first
        //   record in the Array
        if (isFirstCreatedRecord || isUpdatedRecord) {
          primaryRecord = hash;
        } else {
          store.push(typeName, hash);
        }
      }, this);
    }

    return primaryRecord;
  },

  /**
    Called when the server has returned a payload representing
    multiple records, such as in response to a `findAll` or `findQuery`.

    It is your opportunity to clean up the server's response into the normalized
    form expected by Ember Data.

    If you want, you can just restructure the top-level of your payload, and
    do more fine-grained normalization in the `normalize` method.

    For example, if you have a payload like this in response to a request for
    all posts:

    ```js
    {
      "_embedded": {
        "post": [{
          "id": 1,
          "title": "Rails is omakase"
        }, {
          "id": 2,
          "title": "The Parley Letter"
        }],
        "comment": [{
          "_id": 1,
          "comment_title": "Rails is unagi"
          "post_id": 1
        }, {
          "_id": 2,
          "comment_title": "Don't tread on me",
          "post_id": 2
        }]
      }
    }
    ```

    You could implement a serializer that looks like this to get your payload
    into shape:

    ```js
    App.PostSerializer = DS.RESTSerializer.extend({
      // First, restructure the top-level so it's organized by type
      // and the comments are listed under a post's `comments` key.
      extractArray: function(store, type, payload, id, requestType) {
        var posts = payload._embedded.post;
        var comments = [];
        var postCache = {};

        posts.forEach(function(post) {
          post.comments = [];
          postCache[post.id] = post;
        });

        payload._embedded.comment.forEach(function(comment) {
          comments.push(comment);
          postCache[comment.post_id].comments.push(comment);
          delete comment.post_id;
        }

        payload = { comments: comments, posts: payload };

        return this._super(store, type, payload, id, requestType);
      },

      normalizeHash: {
        // Next, normalize individual comments, which (after `extract`)
        // are now located under `comments`
        comments: function(hash) {
          hash.id = hash._id;
          hash.title = hash.comment_title;
          delete hash._id;
          delete hash.comment_title;
          return hash;
        }
      }
    })
    ```

    When you call super from your own implementation of `extractArray`, the
    built-in implementation will find the primary array in your normalized
    payload and push the remaining records into the store.

    The primary array is the array found under `posts`.

    The primary record has special meaning when responding to `findQuery`
    or `findHasMany`. In particular, the primary array will become the
    list of records in the record array that kicked off the request.

    If your primary array contains secondary (embedded) records of the same type,
    you cannot place these into the primary array `posts`. Instead, place the
    secondary items into an underscore prefixed property `_posts`, which will
    push these items into the store and will not affect the resulting query.

    @method extractArray
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} payload
    @param {'findAll'|'findMany'|'findHasMany'|'findQuery'} requestType
    @returns {Array} The primary array that was returned in response
      to the original query.
  */
  extractArray: function(store, primaryType, payload) {
    payload = this.normalizePayload(primaryType, payload);

    var primaryTypeName = primaryType.typeKey,
        primaryArray;

    for (var prop in payload) {
      var typeKey = prop,
          forcedSecondary = false;

      if (prop.charAt(0) === '_') {
        forcedSecondary = true;
        typeKey = prop.substr(1);
      }

      var typeName = this.typeForRoot(typeKey),
          type = store.modelFor(typeName),
          typeSerializer = store.serializerFor(type),
          isPrimary = (!forcedSecondary && (typeName === primaryTypeName));

      /*jshint loopfunc:true*/
      var normalizedArray = map.call(payload[prop], function(hash) {
        return typeSerializer.normalize(type, hash, prop);
      }, this);

      if (isPrimary) {
        primaryArray = normalizedArray;
      } else {
        store.pushMany(typeName, normalizedArray);
      }
    }

    return primaryArray;
  },

  /**
    This method allows you to push a payload containing top-level
    collections of records organized per type.

    ```js
    {
      "posts": [{
        "id": "1",
        "title": "Rails is omakase",
        "author", "1",
        "comments": [ "1" ]
      }],
      "comments": [{
        "id": "1",
        "body": "FIRST"
      }],
      "users": [{
        "id": "1",
        "name": "@d2h"
      }]
    }
    ```

    It will first normalize the payload, so you can use this to push
    in data streaming in from your server structured the same way
    that fetches and saves are structured.

    @method pushPayload
    @param {DS.Store} store
    @param {Object} payload
  */
  pushPayload: function(store, payload) {
    payload = this.normalizePayload(null, payload);

    for (var prop in payload) {
      var typeName = this.typeForRoot(prop),
          type = store.modelFor(typeName);

      /*jshint loopfunc:true*/
      var normalizedArray = map.call(Ember.makeArray(payload[prop]), function(hash) {
        return this.normalize(type, hash, prop);
      }, this);

      store.pushMany(typeName, normalizedArray);
    }
  },

  /**
    You can use this method to normalize the JSON root keys returned
    into the model type expected by your store.

    For example, your server may return underscored root keys rather than
    the expected camelcased versions.

    ```js
    App.ApplicationSerializer = DS.RESTSerializer.extend({
      typeForRoot: function(root) {
        var camelized = Ember.String.camelize(root);
        return Ember.String.singularize(camelized);
      }
    });
    ```

    @method typeForRoot
    @param {String} root
    @returns {String} the model's typeKey
  */
  typeForRoot: function(root) {
    return Ember.String.singularize(root);
  },

  // SERIALIZE

  /**
    Called when a record is saved in order to convert the
    record into JSON.

    By default, it creates a JSON object with a key for
    each attribute and belongsTo relationship.

    For example, consider this model:

    ```js
    App.Comment = DS.Model.extend({
      title: DS.attr(),
      body: DS.attr(),

      author: DS.belongsTo('user')
    });
    ```

    The default serialization would create a JSON object like:

    ```js
    {
      "title": "Rails is unagi",
      "body": "Rails? Omakase? O_O",
      "author": 12
    }
    ```

    By default, attributes are passed through as-is, unless
    you specified an attribute type (`DS.attr('date')`). If
    you specify a transform, the JavaScript value will be
    serialized when inserted into the JSON hash.

    By default, belongs-to relationships are converted into
    IDs when inserted into the JSON hash.

    ## IDs

    `serialize` takes an options hash with a single option:
    `includeId`. If this option is `true`, `serialize` will,
    by default include the ID in the JSON object it builds.

    The adapter passes in `includeId: true` when serializing
    a record for `createRecord`, but not for `updateRecord`.

    ## Customization

    Your server may expect a different JSON format than the
    built-in serialization format.

    In that case, you can implement `serialize` yourself and
    return a JSON hash of your choosing.

    ```js
    App.PostSerializer = DS.RESTSerializer.extend({
      serialize: function(post, options) {
        var json = {
          POST_TTL: post.get('title'),
          POST_BDY: post.get('body'),
          POST_CMS: post.get('comments').mapProperty('id')
        }

        if (options.includeId) {
          json.POST_ID_ = post.get('id');
        }

        return json;
      }
    });
    ```

    ## Customizing an App-Wide Serializer

    If you want to define a serializer for your entire
    application, you'll probably want to use `eachAttribute`
    and `eachRelationship` on the record.

    ```js
    App.ApplicationSerializer = DS.RESTSerializer.extend({
      serialize: function(record, options) {
        var json = {};

        record.eachAttribute(function(name) {
          json[serverAttributeName(name)] = record.get(name);
        })

        record.eachRelationship(function(name, relationship) {
          if (relationship.kind === 'hasMany') {
            json[serverHasManyName(name)] = record.get(name).mapBy('id');
          }
        });

        if (options.includeId) {
          json.ID_ = record.get('id');
        }

        return json;
      }
    });

    function serverAttributeName(attribute) {
      return attribute.underscore().toUpperCase();
    }

    function serverHasManyName(name) {
      return serverAttributeName(name.singularize()) + "_IDS";
    }
    ```

    This serializer will generate JSON that looks like this:

    ```js
    {
      "TITLE": "Rails is omakase",
      "BODY": "Yep. Omakase.",
      "COMMENT_IDS": [ 1, 2, 3 ]
    }
    ```

    ## Tweaking the Default JSON

    If you just want to do some small tweaks on the default JSON,
    you can call super first and make the tweaks on the returned
    JSON.

    ```js
    App.PostSerializer = DS.RESTSerializer.extend({
      serialize: function(record, options) {
        var json = this._super(record, options);

        json.subject = json.title;
        delete json.title;

        return json;
      }
    });
    ```

    @method serialize
    @param record
    @param options
  */
  serialize: function(record, options) {
    return this._super.apply(this, arguments);
  },

  /**
    You can use this method to customize the root keys serialized into the JSON.
    By default the REST Serializer sends camelized root keys.
    For example, your server may expect underscored root objects.

    ```js
    App.ApplicationSerializer = DS.RESTSerializer.extend({
      serializeIntoHash: function(data, type, record, options) {
        var root = Ember.String.decamelize(type.typeKey);
        data[root] = this.serialize(record, options);
      }
    });
    ```

    @method serializeIntoHash
    @param {Object} hash
    @param {subclass of DS.Model} type
    @param {DS.Model} record
    @param {Object} options
  */
  serializeIntoHash: function(hash, type, record, options) {
    hash[type.typeKey] = this.serialize(record, options);
  },

  /**
    You can use this method to customize how polymorphic objects are serialized.
    By default the JSON Serializer creates the key by appending `Type` to
    the attribute and value from the model's camelcased model name.

    @method serializePolymorphicType
    @param {DS.Model} record
    @param {Object} json
    @param {Object} relationship
  */
  serializePolymorphicType: function(record, json, relationship) {
    var key = relationship.key,
        belongsTo = get(record, key);
    key = this.keyForAttribute ? this.keyForAttribute(key) : key;
    json[key + "Type"] = belongsTo.constructor.typeKey;
  }
});

})();



(function() {
/**
  @module ember-data
*/

var get = Ember.get, set = Ember.set;
var forEach = Ember.ArrayPolyfills.forEach;

/**
  The REST adapter allows your store to communicate with an HTTP server by
  transmitting JSON via XHR. Most Ember.js apps that consume a JSON API
  should use the REST adapter.

  This adapter is designed around the idea that the JSON exchanged with
  the server should be conventional.

  ## JSON Structure

  The REST adapter expects the JSON returned from your server to follow
  these conventions.

  ### Object Root

  The JSON payload should be an object that contains the record inside a
  root property. For example, in response to a `GET` request for
  `/posts/1`, the JSON should look like this:

  ```js
  {
    "post": {
      "title": "I'm Running to Reform the W3C's Tag",
      "author": "Yehuda Katz"
    }
  }
  ```

  ### Conventional Names

  Attribute names in your JSON payload should be the camelCased versions of
  the attributes in your Ember.js models.

  For example, if you have a `Person` model:

  ```js
  App.Person = DS.Model.extend({
    firstName: DS.attr('string'),
    lastName: DS.attr('string'),
    occupation: DS.attr('string')
  });
  ```

  The JSON returned should look like this:

  ```js
  {
    "person": {
      "firstName": "Barack",
      "lastName": "Obama",
      "occupation": "President"
    }
  }
  ```

  ## Customization

  ### Endpoint path customization

  Endpoint paths can be prefixed with a `namespace` by setting the namespace
  property on the adapter:

  ```js
  DS.RESTAdapter.reopen({
    namespace: 'api/1'
  });
  ```
  Requests for `App.Person` would now target `/api/1/people/1`.

  ### Host customization

  An adapter can target other hosts by setting the `host` property.

  ```js
  DS.RESTAdapter.reopen({
    host: 'https://api.example.com'
  });
  ```

  ### Headers customization

  Some APIs require HTTP headers, e.g. to provide an API key. An array of
  headers can be added to the adapter which are passed with every request:

  ```js
  DS.RESTAdapter.reopen({
    headers: {
      "API_KEY": "secret key",
      "ANOTHER_HEADER": "Some header value"
    }
  });
  ```

  @class RESTAdapter
  @constructor
  @namespace DS
  @extends DS.Adapter
*/
DS.RESTAdapter = DS.Adapter.extend({
  defaultSerializer: '_rest',


  /**
    Endpoint paths can be prefixed with a `namespace` by setting the namespace
    property on the adapter:

    ```javascript
    DS.RESTAdapter.reopen({
      namespace: 'api/1'
    });
    ```

    Requests for `App.Post` would now target `/api/1/post/`.

    @property namespace
    @type {String}
  */

  /**
    An adapter can target other hosts by setting the `host` property.

    ```javascript
    DS.RESTAdapter.reopen({
      host: 'https://api.example.com'
    });
    ```

    Requests for `App.Post` would now target `https://api.example.com/post/`.

    @property host
    @type {String}
  */

  /**
    Some APIs require HTTP headers, e.g. to provide an API key. An array of
    headers can be added to the adapter which are passed with every request:

    ```javascript
    DS.RESTAdapter.reopen({
      headers: {
        "API_KEY": "secret key",
        "ANOTHER_HEADER": "Some header value"
      }
    });
    ```

    @property headers
    @type {Object}
  */

  /**
    Called by the store in order to fetch the JSON for a given
    type and ID.

    The `find` method makes an Ajax request to a URL computed by `buildURL`, and returns a
    promise for the resulting payload.

    This method performs an HTTP `GET` request with the id provided as part of the querystring.

    @method find
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {String} id
    @returns {Promise} promise
  */
  find: function(store, type, id) {
    return this.ajax(this.buildURL(type.typeKey, id), 'GET');
  },

  /**
    Called by the store in order to fetch a JSON array for all
    of the records for a given type.

    The `findAll` method makes an Ajax (HTTP GET) request to a URL computed by `buildURL`, and returns a
    promise for the resulting payload.

    @private
    @method findAll
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {String} sinceToken
    @returns {Promise} promise
  */
  findAll: function(store, type, sinceToken) {
    var query;

    if (sinceToken) {
      query = { since: sinceToken };
    }

    return this.ajax(this.buildURL(type.typeKey), 'GET', { data: query });
  },

  /**
    Called by the store in order to fetch a JSON array for
    the records that match a particular query.

    The `findQuery` method makes an Ajax (HTTP GET) request to a URL computed by `buildURL`, and returns a
    promise for the resulting payload.

    The `query` argument is a simple JavaScript object that will be passed directly
    to the server as parameters.

    @private
    @method findQuery
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Object} query
    @returns {Promise} promise
  */
  findQuery: function(store, type, query) {
    return this.ajax(this.buildURL(type.typeKey), 'GET', { data: query });
  },

  /**
    Called by the store in order to fetch a JSON array for
    the unloaded records in a has-many relationship that were originally
    specified as IDs.

    For example, if the original payload looks like:

    ```js
    {
      "id": 1,
      "title": "Rails is omakase",
      "comments": [ 1, 2, 3 ]
    }
    ```

    The IDs will be passed as a URL-encoded Array of IDs, in this form:

    ```
    ids[]=1&ids[]=2&ids[]=3
    ```

    Many servers, such as Rails and PHP, will automatically convert this URL-encoded array
    into an Array for you on the server-side. If you want to encode the
    IDs, differently, just override this (one-line) method.

    The `findMany` method makes an Ajax (HTTP GET) request to a URL computed by `buildURL`, and returns a
    promise for the resulting payload.

    @method findMany
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {Array} ids
    @returns {Promise} promise
  */
  findMany: function(store, type, ids) {
    return this.ajax(this.buildURL(type.typeKey), 'GET', { data: { ids: ids } });
  },

  /**
    Called by the store in order to fetch a JSON array for
    the unloaded records in a has-many relationship that were originally
    specified as a URL (inside of `links`).

    For example, if your original payload looks like this:

    ```js
    {
      "post": {
        "id": 1,
        "title": "Rails is omakase",
        "links": { "comments": "/posts/1/comments" }
      }
    }
    ```

    This method will be called with the parent record and `/posts/1/comments`.

    The `findHasMany` method will make an Ajax (HTTP GET) request to the originally specified URL.
    If the URL is host-relative (starting with a single slash), the
    request will use the host specified on the adapter (if any).

    @method findHasMany
    @param {DS.Store} store
    @param {DS.Model} record
    @param {String} url
    @returns {Promise} promise
  */
  findHasMany: function(store, record, url) {
    var host = get(this, 'host'),
        id   = get(record, 'id'),
        type = record.constructor.typeKey;

    if (host && url.charAt(0) === '/' && url.charAt(1) !== '/') {
      url = host + url;
    }

    return this.ajax(this.urlPrefix(url, this.buildURL(type, id)), 'GET');
  },

  /**
    Called by the store in order to fetch a JSON array for
    the unloaded records in a belongs-to relationship that were originally
    specified as a URL (inside of `links`).

    For example, if your original payload looks like this:

    ```js
    {
      "person": {
        "id": 1,
        "name": "Tom Dale",
        "links": { "group": "/people/1/group" }
      }
    }
    ```

    This method will be called with the parent record and `/people/1/group`.

    The `findBelongsTo` method will make an Ajax (HTTP GET) request to the originally specified URL.

    @method findBelongsTo
    @param {DS.Store} store
    @param {DS.Model} record
    @param {String} url
    @returns {Promise} promise
  */
  findBelongsTo: function(store, record, url) {
    var id   = get(record, 'id'),
        type = record.constructor.typeKey;

    return this.ajax(this.urlPrefix(url, this.buildURL(type, id)), 'GET');
  },

  /**
    Called by the store when a newly created record is
    saved via the `save` method on a model record instance.

    The `createRecord` method serializes the record and makes an Ajax (HTTP POST) request
    to a URL computed by `buildURL`.

    See `serialize` for information on how to customize the serialized form
    of a record.

    @method createRecord
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {DS.Model} record
    @returns {Promise} promise
  */
  createRecord: function(store, type, record) {
    var data = {};
    var serializer = store.serializerFor(type.typeKey);

    serializer.serializeIntoHash(data, type, record, { includeId: true });

    return this.ajax(this.buildURL(type.typeKey), "POST", { data: data });
  },

  /**
    Called by the store when an existing record is saved
    via the `save` method on a model record instance.

    The `updateRecord` method serializes the record and makes an Ajax (HTTP PUT) request
    to a URL computed by `buildURL`.

    See `serialize` for information on how to customize the serialized form
    of a record.

    @method updateRecord
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {DS.Model} record
    @returns {Promise} promise
  */
  updateRecord: function(store, type, record) {
    var data = {};
    var serializer = store.serializerFor(type.typeKey);

    serializer.serializeIntoHash(data, type, record);

    var id = get(record, 'id');

    return this.ajax(this.buildURL(type.typeKey, id), "PUT", { data: data });
  },

  /**
    Called by the store when a record is deleted.

    The `deleteRecord` method  makes an Ajax (HTTP DELETE) request to a URL computed by `buildURL`.

    @method deleteRecord
    @param {DS.Store} store
    @param {subclass of DS.Model} type
    @param {DS.Model} record
    @returns {Promise} promise
  */
  deleteRecord: function(store, type, record) {
    var id = get(record, 'id');

    return this.ajax(this.buildURL(type.typeKey, id), "DELETE");
  },

  /**
    Builds a URL for a given type and optional ID.

    By default, it pluralizes the type's name (for example, 'post'
    becomes 'posts' and 'person' becomes 'people'). To override the
    pluralization see [pathForType](#method_pathForType).

    If an ID is specified, it adds the ID to the path generated
    for the type, separated by a `/`.

    @method buildURL
    @param {String} type
    @param {String} id
    @returns {String} url
  */
  buildURL: function(type, id) {
    var url = [],
        host = get(this, 'host'),
        prefix = this.urlPrefix();

    if (type) { url.push(this.pathForType(type)); }
    if (id) { url.push(id); }

    if (prefix) { url.unshift(prefix); }

    url = url.join('/');
    if (!host && url) { url = '/' + url; }

    return url;
  },

  /**
    @method urlPrefix
    @private
    @param {String} path
    @param {String} parentUrl
    @return {String} urlPrefix
  */
  urlPrefix: function(path, parentURL) {
    var host = get(this, 'host'),
        namespace = get(this, 'namespace'),
        url = [];

    if (path) {
      // Absolute path
      if (path.charAt(0) === '/') {
        if (host) {
          path = path.slice(1);
          url.push(host);
        }
      // Relative path
      } else if (!/^http(s)?:\/\//.test(path)) {
        url.push(parentURL);
      }
    } else {
      if (host) { url.push(host); }
      if (namespace) { url.push(namespace); }
    }

    if (path) {
      url.push(path);
    }

    return url.join('/');
  },

  /**
    Determines the pathname for a given type.

    By default, it pluralizes the type's name (for example,
    'post' becomes 'posts' and 'person' becomes 'people').

    ### Pathname customization

    For example if you have an object LineItem with an
    endpoint of "/line_items/".

    ```js
    DS.RESTAdapter.reopen({
      pathForType: function(type) {
        var decamelized = Ember.String.decamelize(type);
        return Ember.String.pluralize(decamelized);
      };
    });
    ```

    @method pathForType
    @param {String} type
    @returns {String} path
  **/
  pathForType: function(type) {
    return Ember.String.pluralize(type);
  },

  /**
    Takes an ajax response, and returns a relavant error.

    Returning a `DS.InvalidError` from this method will cause the
    record to transition into the `invalid` state and make the
    `errors` object available on the record.

    ```javascript
    App.ApplicationAdapter = DS.RESTAdapter.extend({
      ajaxError: function(jqXHR) {
        var error = this._super(jqXHR);

        if (jqXHR && jqXHR.status === 422) {
          var jsonErrors = Ember.$.parseJSON(jqXHR.responseText)["errors"];

          return new DS.InvalidError(jsonErrors);
        } else {
          return error;
        }
      }
    });
    ```

    Note: As a correctness optimization, the default implementation of
    the `ajaxError` method strips out the `then` method from jquery's
    ajax response (jqXHR). This is important because the jqXHR's
    `then` method fulfills the promise with itself resulting in a
    circular "thenable" chain which may cause problems for some
    promise libraries.

    @method ajaxError
    @param  {Object} jqXHR
    @return {Object} jqXHR
  */
  ajaxError: function(jqXHR) {
    if (jqXHR) {
      jqXHR.then = null;
    }

    return jqXHR;
  },

  /**
    Takes a URL, an HTTP method and a hash of data, and makes an
    HTTP request.

    When the server responds with a payload, Ember Data will call into `extractSingle`
    or `extractArray` (depending on whether the original query was for one record or
    many records).

    By default, `ajax` method has the following behavior:

    * It sets the response `dataType` to `"json"`
    * If the HTTP method is not `"GET"`, it sets the `Content-Type` to be
      `application/json; charset=utf-8`
    * If the HTTP method is not `"GET"`, it stringifies the data passed in. The
      data is the serialized record in the case of a save.
    * Registers success and failure handlers.

    @method ajax
    @private
    @param {String} url
    @param {String} type The request type GET, POST, PUT, DELETE ect.
    @param {Object} hash
    @return {Promise} promise
  */
  ajax: function(url, type, hash) {
    var adapter = this;

    return new Ember.RSVP.Promise(function(resolve, reject) {
      hash = adapter.ajaxOptions(url, type, hash);

      hash.success = function(json) {
        Ember.run(null, resolve, json);
      };

      hash.error = function(jqXHR, textStatus, errorThrown) {
        Ember.run(null, reject, adapter.ajaxError(jqXHR));
      };

      Ember.$.ajax(hash);
    }, "DS: RestAdapter#ajax " + type + " to " + url);
  },

  /**
    @method ajaxOptions
    @private
    @param {String} url
    @param {String} type The request type GET, POST, PUT, DELETE ect.
    @param {Object} hash
    @return {Object} hash
  */
  ajaxOptions: function(url, type, hash) {
    hash = hash || {};
    hash.url = url;
    hash.type = type;
    hash.dataType = 'json';
    hash.context = this;

    if (hash.data && type !== 'GET') {
      hash.contentType = 'application/json; charset=utf-8';
      hash.data = JSON.stringify(hash.data);
    }

    if (this.headers !== undefined) {
      var headers = this.headers;
      hash.beforeSend = function (xhr) {
        forEach.call(Ember.keys(headers), function(key) {
          xhr.setRequestHeader(key, headers[key]);
        });
      };
    }


    return hash;
  }

});

})();



(function() {
/**
  @module ember-data
*/

})();



(function() {
DS.Model.reopen({

  /**
    Provides info about the model for debugging purposes
    by grouping the properties into more semantic groups.

    Meant to be used by debugging tools such as the Chrome Ember Extension.

    - Groups all attributes in "Attributes" group.
    - Groups all belongsTo relationships in "Belongs To" group.
    - Groups all hasMany relationships in "Has Many" group.
    - Groups all flags in "Flags" group.
    - Flags relationship CPs as expensive properties.

    @method _debugInfo
    @for DS.Model
    @private
  */
  _debugInfo: function() {
    var attributes = ['id'],
        relationships = { belongsTo: [], hasMany: [] },
        expensiveProperties = [];

    this.eachAttribute(function(name, meta) {
      attributes.push(name);
    }, this);

    this.eachRelationship(function(name, relationship) {
      relationships[relationship.kind].push(name);
      expensiveProperties.push(name);
    });

    var groups = [
      {
        name: 'Attributes',
        properties: attributes,
        expand: true
      },
      {
        name: 'Belongs To',
        properties: relationships.belongsTo,
        expand: true
      },
      {
        name: 'Has Many',
        properties: relationships.hasMany,
        expand: true
      },
      {
        name: 'Flags',
        properties: ['isLoaded', 'isDirty', 'isSaving', 'isDeleted', 'isError', 'isNew', 'isValid']
      }
    ];

    return {
      propertyInfo: {
        // include all other mixins / properties (not just the grouped ones)
        includeOtherProperties: true,
        groups: groups,
        // don't pre-calculate unless cached
        expensiveProperties: expensiveProperties
      }
    };
  }

});

})();



(function() {
/**
  @module ember-data
*/

})();



(function() {
/**
  Ember Data

  @module ember-data
  @main ember-data
*/

})();

(function() {
Ember.String.pluralize = function(word) {
  return Ember.Inflector.inflector.pluralize(word);
};

Ember.String.singularize = function(word) {
  return Ember.Inflector.inflector.singularize(word);
};

})();



(function() {
var BLANK_REGEX = /^\s*$/;

function loadUncountable(rules, uncountable) {
  for (var i = 0, length = uncountable.length; i < length; i++) {
    rules.uncountable[uncountable[i].toLowerCase()] = true;
  }
}

function loadIrregular(rules, irregularPairs) {
  var pair;

  for (var i = 0, length = irregularPairs.length; i < length; i++) {
    pair = irregularPairs[i];

    rules.irregular[pair[0].toLowerCase()] = pair[1];
    rules.irregularInverse[pair[1].toLowerCase()] = pair[0];
  }
}

/**
  Inflector.Ember provides a mechanism for supplying inflection rules for your
  application. Ember includes a default set of inflection rules, and provides an
  API for providing additional rules.

  Examples:

  Creating an inflector with no rules.

  ```js
  var inflector = new Ember.Inflector();
  ```

  Creating an inflector with the default ember ruleset.

  ```js
  var inflector = new Ember.Inflector(Ember.Inflector.defaultRules);

  inflector.pluralize('cow') //=> 'kine'
  inflector.singularize('kine') //=> 'cow'
  ```

  Creating an inflector and adding rules later.

  ```javascript
  var inflector = Ember.Inflector.inflector;

  inflector.pluralize('advice') // => 'advices'
  inflector.uncountable('advice');
  inflector.pluralize('advice') // => 'advice'

  inflector.pluralize('formula') // => 'formulas'
  inflector.irregular('formula', 'formulae');
  inflector.pluralize('formula') // => 'formulae'

  // you would not need to add these as they are the default rules
  inflector.plural(/$/, 's');
  inflector.singular(/s$/i, '');
  ```

  Creating an inflector with a nondefault ruleset.

  ```javascript
  var rules = {
    plurals:  [ /$/, 's' ],
    singular: [ /\s$/, '' ],
    irregularPairs: [
      [ 'cow', 'kine' ]
    ],
    uncountable: [ 'fish' ]
  };

  var inflector = new Ember.Inflector(rules);
  ```

  @class Inflector
  @namespace Ember
*/
function Inflector(ruleSet) {
  ruleSet = ruleSet || {};
  ruleSet.uncountable = ruleSet.uncountable || {};
  ruleSet.irregularPairs = ruleSet.irregularPairs || {};

  var rules = this.rules = {
    plurals:  ruleSet.plurals || [],
    singular: ruleSet.singular || [],
    irregular: {},
    irregularInverse: {},
    uncountable: {}
  };

  loadUncountable(rules, ruleSet.uncountable);
  loadIrregular(rules, ruleSet.irregularPairs);
}

Inflector.prototype = {
  /**
    @method plural
    @param {RegExp} regex
    @param {String} string
  */
  plural: function(regex, string) {
    this.rules.plurals.push([regex, string.toLowerCase()]);
  },

  /**
    @method singular
    @param {RegExp} regex
    @param {String} string
  */
  singular: function(regex, string) {
    this.rules.singular.push([regex, string.toLowerCase()]);
  },

  /**
    @method uncountable
    @param {String} regex
  */
  uncountable: function(string) {
    loadUncountable(this.rules, [string.toLowerCase()]);
  },

  /**
    @method irregular
    @param {String} singular
    @param {String} plural
  */
  irregular: function (singular, plural) {
    loadIrregular(this.rules, [[singular, plural]]);
  },

  /**
    @method pluralize
    @param {String} word
  */
  pluralize: function(word) {
    return this.inflect(word, this.rules.plurals, this.rules.irregular);
  },

  /**
    @method singularize
    @param {String} word
  */
  singularize: function(word) {
    return this.inflect(word, this.rules.singular,  this.rules.irregularInverse);
  },

  /**
    @protected

    @method inflect
    @param {String} word
    @param {Object} typeRules
    @param {Object} irregular
  */
  inflect: function(word, typeRules, irregular) {
    var inflection, substitution, result, lowercase, isBlank,
    isUncountable, isIrregular, isIrregularInverse, rule;

    isBlank = BLANK_REGEX.test(word);

    if (isBlank) {
      return word;
    }

    lowercase = word.toLowerCase();

    isUncountable = this.rules.uncountable[lowercase];

    if (isUncountable) {
      return word;
    }

    isIrregular = irregular && irregular[lowercase];

    if (isIrregular) {
      return isIrregular;
    }

    for (var i = typeRules.length, min = 0; i > min; i--) {
       inflection = typeRules[i-1];
       rule = inflection[0];

      if (rule.test(word)) {
        break;
      }
    }

    inflection = inflection || [];

    rule = inflection[0];
    substitution = inflection[1];

    result = word.replace(rule, substitution);

    return result;
  }
};

Ember.Inflector = Inflector;

})();



(function() {
Ember.Inflector.defaultRules = {
  plurals: [
    [/$/, 's'],
    [/s$/i, 's'],
    [/^(ax|test)is$/i, '$1es'],
    [/(octop|vir)us$/i, '$1i'],
    [/(octop|vir)i$/i, '$1i'],
    [/(alias|status)$/i, '$1es'],
    [/(bu)s$/i, '$1ses'],
    [/(buffal|tomat)o$/i, '$1oes'],
    [/([ti])um$/i, '$1a'],
    [/([ti])a$/i, '$1a'],
    [/sis$/i, 'ses'],
    [/(?:([^f])fe|([lr])f)$/i, '$1$2ves'],
    [/(hive)$/i, '$1s'],
    [/([^aeiouy]|qu)y$/i, '$1ies'],
    [/(x|ch|ss|sh)$/i, '$1es'],
    [/(matr|vert|ind)(?:ix|ex)$/i, '$1ices'],
    [/^(m|l)ouse$/i, '$1ice'],
    [/^(m|l)ice$/i, '$1ice'],
    [/^(ox)$/i, '$1en'],
    [/^(oxen)$/i, '$1'],
    [/(quiz)$/i, '$1zes']
  ],

  singular: [
    [/s$/i, ''],
    [/(ss)$/i, '$1'],
    [/(n)ews$/i, '$1ews'],
    [/([ti])a$/i, '$1um'],
    [/((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)(sis|ses)$/i, '$1sis'],
    [/(^analy)(sis|ses)$/i, '$1sis'],
    [/([^f])ves$/i, '$1fe'],
    [/(hive)s$/i, '$1'],
    [/(tive)s$/i, '$1'],
    [/([lr])ves$/i, '$1f'],
    [/([^aeiouy]|qu)ies$/i, '$1y'],
    [/(s)eries$/i, '$1eries'],
    [/(m)ovies$/i, '$1ovie'],
    [/(x|ch|ss|sh)es$/i, '$1'],
    [/^(m|l)ice$/i, '$1ouse'],
    [/(bus)(es)?$/i, '$1'],
    [/(o)es$/i, '$1'],
    [/(shoe)s$/i, '$1'],
    [/(cris|test)(is|es)$/i, '$1is'],
    [/^(a)x[ie]s$/i, '$1xis'],
    [/(octop|vir)(us|i)$/i, '$1us'],
    [/(alias|status)(es)?$/i, '$1'],
    [/^(ox)en/i, '$1'],
    [/(vert|ind)ices$/i, '$1ex'],
    [/(matr)ices$/i, '$1ix'],
    [/(quiz)zes$/i, '$1'],
    [/(database)s$/i, '$1']
  ],

  irregularPairs: [
    ['person', 'people'],
    ['man', 'men'],
    ['child', 'children'],
    ['sex', 'sexes'],
    ['move', 'moves'],
    ['cow', 'kine'],
    ['zombie', 'zombies']
  ],

  uncountable: [
    'equipment',
    'information',
    'rice',
    'money',
    'species',
    'series',
    'fish',
    'sheep',
    'jeans',
    'police'
  ]
};

})();



(function() {
if (Ember.EXTEND_PROTOTYPES === true || Ember.EXTEND_PROTOTYPES.String) {
  /**
    See {{#crossLink "Ember.String/pluralize"}}{{/crossLink}}

    @method pluralize
    @for String
  */
  String.prototype.pluralize = function() {
    return Ember.String.pluralize(this);
  };

  /**
    See {{#crossLink "Ember.String/singularize"}}{{/crossLink}}

    @method singularize
    @for String
  */
  String.prototype.singularize = function() {
    return Ember.String.singularize(this);
  };
}

})();



(function() {
Ember.Inflector.inflector = new Ember.Inflector(Ember.Inflector.defaultRules);

})();



(function() {

})();

(function() {
/**
  @module ember-data
*/

var get = Ember.get;
var forEach = Ember.EnumerableUtils.forEach;

DS.ActiveModelSerializer = DS.RESTSerializer.extend({
  // SERIALIZE

  /**
    Converts camelcased attributes to underscored when serializing.

    @method keyForAttribute
    @param {String} attribute
    @returns String
  */
  keyForAttribute: function(attr) {
    return Ember.String.decamelize(attr);
  },

  /**
    Underscores relationship names and appends "_id" or "_ids" when serializing
    relationship keys.

    @method keyForRelationship
    @param {String} key
    @param {String} kind
    @returns String
  */
  keyForRelationship: function(key, kind) {
    key = Ember.String.decamelize(key);
    if (kind === "belongsTo") {
      return key + "_id";
    } else if (kind === "hasMany") {
      return Ember.String.singularize(key) + "_ids";
    } else {
      return key;
    }
  },

  /**
    Does not serialize hasMany relationships by default.
  */
  serializeHasMany: Ember.K,

  /**
    Underscores the JSON root keys when serializing.

    @method serializeIntoHash
    @param {Object} hash
    @param {subclass of DS.Model} type
    @param {DS.Model} record
    @param {Object} options
  */
  serializeIntoHash: function(data, type, record, options) {
    var root = Ember.String.decamelize(type.typeKey);
    data[root] = this.serialize(record, options);
  },

  /**
    Serializes a polymorphic type as a fully capitalized model name.

    @method serializePolymorphicType
    @param {DS.Model} record
    @param {Object} json
    @param relationship
  */
  serializePolymorphicType: function(record, json, relationship) {
    var key = relationship.key,
        belongsTo = get(record, key);
    key = this.keyForAttribute(key);
    json[key + "_type"] = Ember.String.capitalize(belongsTo.constructor.typeKey);
  },

  // EXTRACT

  /**
    Extracts the model typeKey from underscored root objects.

    @method typeForRoot
    @param {String} root
    @returns String the model's typeKey
  */
  typeForRoot: function(root) {
    var camelized = Ember.String.camelize(root);
    return Ember.String.singularize(camelized);
  },

  /**
    Add extra step to `DS.RESTSerializer.normalize` so links are
    normalized.

    If your payload looks like this

    ```js
    {
      "post": {
        "id": 1,
        "title": "Rails is omakase",
        "links": { "flagged_comments": "api/comments/flagged" }
      }
    }
    ```
    The normalized version would look like this

    ```js
    {
      "post": {
        "id": 1,
        "title": "Rails is omakase",
        "links": { "flaggedComments": "api/comments/flagged" }
      }
    }
    ```

    @method normalize
    @param {subclass of DS.Model} type
    @param {Object} hash
    @param {String} prop
    @returns Object
  */

  normalize: function(type, hash, prop) {
    this.normalizeLinks(hash);

    return this._super(type, hash, prop);
  },

  /**
    Convert `snake_cased` links  to `camelCase`

    @method normalizeLinks
    @param {Object} hash
  */

  normalizeLinks: function(data){
    if (data.links) {
      var links = data.links;

      for (var link in links) {
        var camelizedLink = Ember.String.camelize(link);

        if (camelizedLink !== link) {
          links[camelizedLink] = links[link];
          delete links[link];
        }
      }
    }
  },

  /**
    Normalize the polymorphic type from the JSON.

    Normalize:
    ```js
      {
        id: "1"
        minion: { type: "evil_minion", id: "12"}
      }
    ```

    To:
    ```js
      {
        id: "1"
        minion: { type: "evilMinion", id: "12"}
      }
    ```

    @method normalizeRelationships
    @private
  */
  normalizeRelationships: function(type, hash) {
    var payloadKey, payload;

    if (this.keyForRelationship) {
      type.eachRelationship(function(key, relationship) {
        if (relationship.options.polymorphic) {
          payloadKey = this.keyForAttribute(key);
          payload = hash[payloadKey];
          if (payload && payload.type) {
            payload.type = this.typeForRoot(payload.type);
          } else if (payload && relationship.kind === "hasMany") {
            var self = this;
            forEach(payload, function(single) {
              single.type = self.typeForRoot(single.type);
            });
          }
        } else {
          payloadKey = this.keyForRelationship(key, relationship.kind);
          payload = hash[payloadKey];
        }

        hash[key] = payload;

        if (key !== payloadKey) {
          delete hash[payloadKey];
        }
      }, this);
    }
  }
});

})();



(function() {
var get = Ember.get;
var forEach = Ember.EnumerableUtils.forEach;

/**
  The EmbeddedRecordsMixin allows you to add embedded record support to your
  serializers.
  To set up embedded records, you include the mixin into the serializer and then
  define your embedded relations.

  ```js
  App.PostSerializer = DS.ActiveModelSerializer.extend(DS.EmbeddedRecordsMixin, {
    attrs: {
      comments: {embedded: 'always'}
    }
  })
  ```

  Currently only `{embedded: 'always'}` records are supported.

  @class EmbeddedRecordsMixin
  @namespace DS
*/
DS.EmbeddedRecordsMixin = Ember.Mixin.create({

  /**
    Serialize has-may relationship when it is configured as embedded objects.

    @method serializeHasMany
  */
  serializeHasMany: function(record, json, relationship) {
    var key   = relationship.key,
        attrs = get(this, 'attrs'),
        embed = attrs && attrs[key] && attrs[key].embedded === 'always';

    if (embed) {
      json[this.keyForAttribute(key)] = get(record, key).map(function(relation) {
        var data = relation.serialize(),
            primaryKey = get(this, 'primaryKey');

        data[primaryKey] = get(relation, primaryKey);

        return data;
      }, this);
    }
  },

  /**
    Extract embedded objects out of the payload for a single object
    and add them as sideloaded objects instead.

    @method extractSingle
  */
  extractSingle: function(store, primaryType, payload, recordId, requestType) {
    var root = this.keyForAttribute(primaryType.typeKey),
        partial = payload[root];

    updatePayloadWithEmbedded(store, this, primaryType, partial, payload);

    return this._super(store, primaryType, payload, recordId, requestType);
  },

  /**
    Extract embedded objects out of a standard payload
    and add them as sideloaded objects instead.

    @method extractArray
  */
  extractArray: function(store, type, payload) {
    var root = this.keyForAttribute(type.typeKey),
        partials = payload[Ember.String.pluralize(root)];

    forEach(partials, function(partial) {
      updatePayloadWithEmbedded(store, this, type, partial, payload);
    }, this);

    return this._super(store, type, payload);
  }
});

function updatePayloadWithEmbedded(store, serializer, type, partial, payload) {
  var attrs = get(serializer, 'attrs');

  if (!attrs) {
    return;
  }

  type.eachRelationship(function(key, relationship) {
    var expandedKey, embeddedTypeKey, attribute, ids,
        config = attrs[key],
        serializer = store.serializerFor(relationship.type.typeKey),
        primaryKey = get(serializer, "primaryKey");

    if (relationship.kind !== "hasMany") {
      return;
    }

    if (config && (config.embedded === 'always' || config.embedded === 'load')) {
      // underscore forces the embedded records to be side loaded.
      // it is needed when main type === relationship.type
      embeddedTypeKey = '_' + Ember.String.pluralize(relationship.type.typeKey);
      expandedKey = this.keyForRelationship(key, relationship.kind);
      attribute  = this.keyForAttribute(key);
      ids = [];

      if (!partial[attribute]) {
        return;
      }

      payload[embeddedTypeKey] = payload[embeddedTypeKey] || [];

      forEach(partial[attribute], function(data) {
        var embeddedType = store.modelFor(relationship.type.typeKey);
        updatePayloadWithEmbedded(store, serializer, embeddedType, data, payload);
        ids.push(data[primaryKey]);
        payload[embeddedTypeKey].push(data);
      });

      partial[expandedKey] = ids;
      delete partial[attribute];
    }
  }, serializer);
}
})();



(function() {
/**
  @module ember-data
*/

var forEach = Ember.EnumerableUtils.forEach;

/**
  The ActiveModelAdapter is a subclass of the RESTAdapter designed to integrate
  with a JSON API that uses an underscored naming convention instead of camelcasing.
  It has been designed to work out of the box with the
  [active_model_serializers](http://github.com/rails-api/active_model_serializers)
  Ruby gem.

  This adapter extends the DS.RESTAdapter by making consistent use of the camelization,
  decamelization and pluralization methods to normalize the serialized JSON into a
  format that is compatible with a conventional Rails backend and Ember Data.

  ## JSON Structure

  The ActiveModelAdapter expects the JSON returned from your server to follow
  the REST adapter conventions substituting underscored keys for camelcased ones.

  ### Conventional Names

  Attribute names in your JSON payload should be the underscored versions of
  the attributes in your Ember.js models.

  For example, if you have a `Person` model:

  ```js
  App.FamousPerson = DS.Model.extend({
    firstName: DS.attr('string'),
    lastName: DS.attr('string'),
    occupation: DS.attr('string')
  });
  ```

  The JSON returned should look like this:

  ```js
  {
    "famous_person": {
      "first_name": "Barack",
      "last_name": "Obama",
      "occupation": "President"
    }
  }
  ```

  @class ActiveModelAdapter
  @constructor
  @namespace DS
  @extends DS.Adapter
**/

DS.ActiveModelAdapter = DS.RESTAdapter.extend({
  defaultSerializer: '_ams',
  /**
    The ActiveModelAdapter overrides the `pathForType` method to build
    underscored URLs by decamelizing and pluralizing the object type name.

    ```js
      this.pathForType("famousPerson");
      //=> "famous_people"
    ```

    @method pathForType
    @param {String} type
    @returns String
  */
  pathForType: function(type) {
    var decamelized = Ember.String.decamelize(type);
    return Ember.String.pluralize(decamelized);
  },

  /**
    The ActiveModelAdapter overrides the `ajaxError` method
    to return a DS.InvalidError for all 422 Unprocessable Entity
    responses.

    A 422 HTTP response from the server generally implies that the request
    was well formed but the API was unable to process it because the
    content was not semantically correct or meaningful per the API.

    For more information on 422 HTTP Error code see 11.2 WebDAV RFC 4918
    https://tools.ietf.org/html/rfc4918#section-11.2

    @method ajaxError
    @param jqXHR
    @returns error
  */
  ajaxError: function(jqXHR) {
    var error = this._super(jqXHR);

    if (jqXHR && jqXHR.status === 422) {
      var jsonErrors = Ember.$.parseJSON(jqXHR.responseText)["errors"],
          errors = {};

      forEach(Ember.keys(jsonErrors), function(key) {
        errors[Ember.String.camelize(key)] = jsonErrors[key];
      });

      return new DS.InvalidError(errors);
    } else {
      return error;
    }
  }
});

})();



(function() {

})();



(function() {
Ember.onLoad('Ember.Application', function(Application) {
  Application.initializer({
    name: "activeModelAdapter",

    initialize: function(container, application) {
      application.register('serializer:_ams', DS.ActiveModelSerializer);
      application.register('adapter:_ams', DS.ActiveModelAdapter);
    }
  });
});

})();



(function() {

})();


})();
