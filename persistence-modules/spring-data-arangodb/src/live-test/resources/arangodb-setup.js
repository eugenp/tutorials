#!/usr/bin/arangosh --javascript.execute

print('creating database: baeldung-database & user: baeldung');
db._createDatabase("baeldung-database", {}, [{ username: "baeldung", passwd: "password", active: true}]);