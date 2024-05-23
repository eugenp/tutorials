import { Component, inject, OnInit, signal } from '@angular/core';
import { FormGroup, FormControl, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';

import SharedModule from 'app/shared/shared.module';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

const initialAccount: Account = {} as Account;

@Component({
  standalone: true,
  selector: 'jhi-settings',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
  templateUrl: './settings.component.html',
})
export default class SettingsComponent implements OnInit {
  success = signal(false);

  settingsForm = new FormGroup({
    firstName: new FormControl(initialAccount.firstName, {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
    }),
    lastName: new FormControl(initialAccount.lastName, {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
    }),
    email: new FormControl(initialAccount.email, {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email],
    }),
    langKey: new FormControl(initialAccount.langKey, { nonNullable: true }),

    activated: new FormControl(initialAccount.activated, { nonNullable: true }),
    authorities: new FormControl(initialAccount.authorities, { nonNullable: true }),
    imageUrl: new FormControl(initialAccount.imageUrl, { nonNullable: true }),
    login: new FormControl(initialAccount.login, { nonNullable: true }),
  });

  private accountService = inject(AccountService);

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.settingsForm.patchValue(account);
      }
    });
  }

  save(): void {
    this.success.set(false);

    const account = this.settingsForm.getRawValue();
    this.accountService.save(account).subscribe(() => {
      this.success.set(true);

      this.accountService.authenticate(account);
    });
  }
}
