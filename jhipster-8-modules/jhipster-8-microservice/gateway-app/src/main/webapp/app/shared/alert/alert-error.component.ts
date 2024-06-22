import { Component, inject, OnDestroy, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { Alert, AlertService } from 'app/core/util/alert.service';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { AlertError } from './alert-error.model';

@Component({
  standalone: true,
  selector: 'jhi-alert-error',
  templateUrl: './alert-error.component.html',
  imports: [CommonModule, NgbModule],
})
export class AlertErrorComponent implements OnDestroy {
  alerts = signal<Alert[]>([]);
  errorListener: Subscription;
  httpErrorListener: Subscription;

  private alertService = inject(AlertService);
  private eventManager = inject(EventManager);

  constructor() {
    this.errorListener = this.eventManager.subscribe('gatewayApp.error', (response: EventWithContent<unknown> | string) => {
      const errorResponse = (response as EventWithContent<AlertError>).content;
      this.addErrorAlert(errorResponse.message);
    });

    this.httpErrorListener = this.eventManager.subscribe('gatewayApp.httpError', (response: EventWithContent<unknown> | string) => {
      this.handleHttpError(response);
    });
  }

  setClasses(alert: Alert): { [key: string]: boolean } {
    const classes = { 'jhi-toast': Boolean(alert.toast) };
    if (alert.position) {
      return { ...classes, [alert.position]: true };
    }
    return classes;
  }

  ngOnDestroy(): void {
    this.eventManager.destroy(this.errorListener);
    this.eventManager.destroy(this.httpErrorListener);
  }

  close(alert: Alert): void {
    alert.close?.(this.alerts());
  }

  private addErrorAlert(message?: string): void {
    this.alertService.addAlert({ type: 'danger', message }, this.alerts());
  }

  private handleHttpError(response: EventWithContent<unknown> | string): void {
    const httpErrorResponse = (response as EventWithContent<HttpErrorResponse>).content;
    switch (httpErrorResponse.status) {
      // connection refused, server not reachable
      case 0:
        this.addErrorAlert('Server not reachable');
        break;

      case 400: {
        this.handleBadRequest(httpErrorResponse);
        break;
      }

      case 404:
        this.addErrorAlert('Not found');
        break;

      default:
        this.handleDefaultError(httpErrorResponse);
    }
  }

  private handleBadRequest(httpErrorResponse: HttpErrorResponse): void {
    const arr = httpErrorResponse.headers.keys();
    let errorHeader: string | null = null;
    for (const entry of arr) {
      if (entry.toLowerCase().endsWith('app-error')) {
        errorHeader = httpErrorResponse.headers.get(entry);
      }
    }
    if (errorHeader) {
      this.addErrorAlert(errorHeader);
    } else if (httpErrorResponse.error !== '' && httpErrorResponse.error.fieldErrors) {
      this.handleFieldsError(httpErrorResponse);
    } else if (httpErrorResponse.error !== '' && httpErrorResponse.error.message) {
      this.addErrorAlert(httpErrorResponse.error.detail ?? httpErrorResponse.error.message);
    } else {
      this.addErrorAlert(httpErrorResponse.error);
    }
  }

  private handleDefaultError(httpErrorResponse: HttpErrorResponse): void {
    if (httpErrorResponse.error !== '' && httpErrorResponse.error.message) {
      this.addErrorAlert(httpErrorResponse.error.detail ?? httpErrorResponse.error.message);
    } else {
      this.addErrorAlert(httpErrorResponse.error);
    }
  }

  private handleFieldsError(httpErrorResponse: HttpErrorResponse): void {
    const fieldErrors = httpErrorResponse.error.fieldErrors;
    for (const fieldError of fieldErrors) {
      if (['Min', 'Max', 'DecimalMin', 'DecimalMax'].includes(fieldError.message)) {
        fieldError.message = 'Size';
      }
      // convert 'something[14].other[4].id' to 'something[].other[].id' so translations can be written to it
      const convertedField: string = fieldError.field.replace(/\[\d*\]/g, '[]');
      const fieldName: string = convertedField.charAt(0).toUpperCase() + convertedField.slice(1);
      this.addErrorAlert(`Error on field "${fieldName}"`);
    }
  }
}
