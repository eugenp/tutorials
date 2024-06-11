import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { provideHttpClient } from '@angular/common/http';
import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), provideHttpClient()],
};

export const reverseProxyUri = 'http://LOCALHOST_NAME:7080';
export const baseUri = `${reverseProxyUri}/angular-ui/`;
