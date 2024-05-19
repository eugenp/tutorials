import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAuthority } from '../authority.model';

@Component({
  standalone: true,
  selector: 'jhi-authority-detail',
  templateUrl: './authority-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AuthorityDetailComponent {
  authority = input<IAuthority | null>(null);

  previousState(): void {
    window.history.back();
  }
}
