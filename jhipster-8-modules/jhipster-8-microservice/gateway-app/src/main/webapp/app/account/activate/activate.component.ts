import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { mergeMap } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { ActivateService } from './activate.service';

@Component({
  standalone: true,
  selector: 'jhi-activate',
  imports: [SharedModule, RouterModule],
  templateUrl: './activate.component.html',
})
export default class ActivateComponent implements OnInit {
  error = signal(false);
  success = signal(false);

  private activateService = inject(ActivateService);
  private route = inject(ActivatedRoute);

  ngOnInit(): void {
    this.route.queryParams.pipe(mergeMap(params => this.activateService.get(params.key))).subscribe({
      next: () => this.success.set(true),
      error: () => this.error.set(true),
    });
  }
}
