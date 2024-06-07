import { Routes } from '@angular/router';
import { AboutView } from './about.view';
import { HomeView } from './home.view';
import { LoginErrorView } from './login-error.view';

export const routes: Routes = [
  { path: '', component: HomeView },
  { path: 'about', component: AboutView },
  { path: 'login-error', component: LoginErrorView },
  { path: '**', redirectTo: '/' },
];
