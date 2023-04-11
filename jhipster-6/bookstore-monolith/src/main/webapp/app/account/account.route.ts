import { Routes } from '@angular/router';

import { settingsRoute } from './';

const ACCOUNT_ROUTES = [settingsRoute];

export const accountState: Routes = [
    {
        path: '',
        children: ACCOUNT_ROUTES
    }
];
