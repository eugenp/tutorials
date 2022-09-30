import { Component, OnDestroy } from '@angular/core';
import { TranslateService } from 'ng2-translate';
import { EventManager, AlertService } from 'ng-jhipster';
import { Subscription } from 'rxjs/Rx';

@Component({
    selector: 'jhi-alert-error',
    template: `
        <div class="alerts" role="alert">
            <div *ngFor="let alert of alerts"  [ngClass]="{\'alert.position\': true, \'toast\': alert.toast}">
                <ngb-alert type="{{alert.type}}" close="alert.close(alerts)"><pre>{{ alert.msg }}</pre></ngb-alert>
            </div>
        </div>`
})
export class JhiAlertErrorComponent implements OnDestroy {

    alerts: any[];
    cleanHttpErrorListener: Subscription;

    constructor(private alertService: AlertService, private eventManager: EventManager, private translateService: TranslateService) {
        this.alerts = [];

        this.cleanHttpErrorListener = eventManager.subscribe('baeldungApp.httpError', (response) => {
            let i;
            let httpResponse = response.content;
            switch (httpResponse.status) {
                // connection refused, server not reachable
                case 0:
                    this.addErrorAlert('Server not reachable', 'error.server.not.reachable');
                    break;

                case 400:
                    let arr = Array.from(httpResponse.headers._headers);
                    let headers = [];
                    for (i = 0; i < arr.length; i++) {
                        if (arr[i][0].endsWith('app-error') || arr[i][0].endsWith('app-params')) {
                            headers.push(arr[i][0]);
                        }
                    }
                    headers.sort();
                    let errorHeader = httpResponse.headers.get(headers[0]);
                    let entityKey = httpResponse.headers.get(headers[1]);
                    if (errorHeader) {
                        let entityName = translateService.instant('global.menu.entities.' + entityKey);
                        this.addErrorAlert(errorHeader, errorHeader, {entityName: entityName});
                    } else if (httpResponse.text() !== '' && httpResponse.json() && httpResponse.json().fieldErrors) {
                        let fieldErrors = httpResponse.json().fieldErrors;
                        for (i = 0; i < fieldErrors.length; i++) {
                            let fieldError = fieldErrors[i];
                            // convert 'something[14].other[4].id' to 'something[].other[].id' so translations can be written to it
                            let convertedField = fieldError.field.replace(/\[\d*\]/g, '[]');
                            let fieldName = translateService.instant('baeldungApp.' +
                                fieldError.objectName + '.' + convertedField);
                            this.addErrorAlert(
                                'Field ' + fieldName + ' cannot be empty', 'error.' + fieldError.message, {fieldName: fieldName});
                        }
                    } else if (httpResponse.text() !== '' && httpResponse.json() && httpResponse.json().message) {
                        this.addErrorAlert(httpResponse.json().message, httpResponse.json().message, httpResponse.json());
                    } else {
                        this.addErrorAlert(httpResponse.text());
                    }
                    break;

                case 404:
                    this.addErrorAlert('Not found', 'error.url.not.found');
                    break;

                default:
                    if (httpResponse.text() !== '' && httpResponse.json() && httpResponse.json().message) {
                        this.addErrorAlert(httpResponse.json().message);
                    } else {
                        this.addErrorAlert(JSON.stringify(httpResponse)); // Fixme find a way to parse httpResponse
                    }
            }
        });
    }

    ngOnDestroy() {
        if (this.cleanHttpErrorListener !== undefined && this.cleanHttpErrorListener !== null) {
            this.eventManager.destroy(this.cleanHttpErrorListener);
            this.alerts = [];
        }
    }

    addErrorAlert (message, key?, data?) {
        key = key && key !== null ? key : message;
        this.alerts.push(
            this.alertService.addAlert(
                {
                    type: 'danger',
                    msg: key,
                    params: data,
                    timeout: 5000,
                    toast: this.alertService.isToast(),
                    scoped: true
                },
                this.alerts
            )
        );
    }
}
