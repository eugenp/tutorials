import { Component, inject, OnInit } from '@angular/core';

import SharedModule from 'app/shared/shared.module';
import { GatewayRoutesService } from './gateway-routes.service';
import { GatewayRoute } from './gateway-route.model';

@Component({
  standalone: true,
  selector: 'jhi-gateway',
  templateUrl: './gateway.component.html',
  providers: [GatewayRoutesService],
  imports: [SharedModule],
})
export default class GatewayComponent implements OnInit {
  gatewayRoutes: GatewayRoute[] = [];
  updatingRoutes = false;

  private gatewayRoutesService = inject(GatewayRoutesService);

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
    this.updatingRoutes = true;
    this.gatewayRoutesService.findAll().subscribe(gatewayRoutes => {
      this.gatewayRoutes = gatewayRoutes;
      this.updatingRoutes = false;
    });
  }
}
