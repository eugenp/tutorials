import { Component, inject, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { ProfileService } from './profile.service';

@Component({
  standalone: true,
  selector: 'jhi-page-ribbon',
  template: `
    @if (ribbonEnv$ | async; as ribbonEnv) {
      <div class="ribbon">
        <a href="">{{ { dev: 'Development' }[ribbonEnv ?? ''] }}</a>
      </div>
    }
  `,
  styleUrl: './page-ribbon.component.scss',
  imports: [SharedModule],
})
export default class PageRibbonComponent implements OnInit {
  ribbonEnv$?: Observable<string | undefined>;

  private profileService = inject(ProfileService);

  ngOnInit(): void {
    this.ribbonEnv$ = this.profileService.getProfileInfo().pipe(map(profileInfo => profileInfo.ribbonEnv));
  }
}
