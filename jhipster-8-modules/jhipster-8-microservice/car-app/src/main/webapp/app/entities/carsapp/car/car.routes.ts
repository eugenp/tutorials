import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CarComponent } from './list/car.component';
import { CarDetailComponent } from './detail/car-detail.component';
import { CarUpdateComponent } from './update/car-update.component';
import CarResolve from './route/car-routing-resolve.service';

const carRoute: Routes = [
  {
    path: '',
    component: CarComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CarDetailComponent,
    resolve: {
      car: CarResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CarUpdateComponent,
    resolve: {
      car: CarResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CarUpdateComponent,
    resolve: {
      car: CarResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default carRoute;
