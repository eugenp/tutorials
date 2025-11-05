import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'car',
    data: { pageTitle: 'Cars' },
    loadChildren: () => import('./carsapp/car/car.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
