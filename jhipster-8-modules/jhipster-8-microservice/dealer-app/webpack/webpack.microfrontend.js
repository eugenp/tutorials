const { withModuleFederationPlugin } = require('@angular-architects/module-federation/webpack');
const packageJson = require('../package.json');
// Microfrontend api, should match across gateway and microservices.
const apiVersion = '0.0.1';

const sharedDefaults = { singleton: true, strictVersion: true, requiredVersion: apiVersion };
const shareAllDefaults = { ...sharedDefaults, requiredVersion: 'auto' };
const shareMappings = (...mappings) => Object.fromEntries(mappings.map(map => [map, { ...sharedDefaults, version: apiVersion }]));

const shareDependencies = ({ skipList = [] } = {}) =>
  Object.fromEntries(
    Object.entries(packageJson.dependencies)
      .filter(([dependency]) => !skipList.includes(dependency))
      .map(([dependency, version]) => [dependency, { ...sharedDefaults, version, requiredVersion: version }]),
  );

let sharedDependencies = shareDependencies({ skipList: ['@angular/localize', 'zone.js'] });
sharedDependencies = {
  ...sharedDependencies,
  '@angular/common/http': sharedDependencies['@angular/common'],
  'rxjs/operators': sharedDependencies.rxjs,
};

module.exports = (config, options, targetOptions) => {
  return withModuleFederationPlugin({
    name: 'dealerapp',
    exposes: {
      './entity-navbar-items': 'app/entities/entity-navbar-items.ts',
      './entity-routes': 'app/entities/entity.routes.ts',
    },
    shared: {
      ...sharedDependencies,
      ...shareMappings(
        'app/config/input.constants',
        'app/config/pagination.constants',
        'app/config/translation.config',
        'app/core/auth',
        'app/core/config',
        'app/core/interceptor',
        'app/core/request',
        'app/core/util',
        'app/shared',
        'app/shared/alert',
        'app/shared/auth',
        'app/shared/date',
        'app/shared/language',
        'app/shared/pagination',
        'app/shared/sort',
      ),
    },
  });
};
