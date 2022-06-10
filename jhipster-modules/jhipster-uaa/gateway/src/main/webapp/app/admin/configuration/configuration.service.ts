import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';

@Injectable({ providedIn: 'root' })
export class JhiConfigurationService {
    constructor(private http: HttpClient) {}

    get(): Observable<any> {
        return this.http.get(SERVER_API_URL + 'management/configprops', { observe: 'response' }).pipe(
            map((res: HttpResponse<any>) => {
                const properties: any[] = [];
                const propertiesObject = this.getConfigPropertiesObjects(res.body);
                for (const key in propertiesObject) {
                    if (propertiesObject.hasOwnProperty(key)) {
                        properties.push(propertiesObject[key]);
                    }
                }

                return properties.sort((propertyA, propertyB) => {
                    return propertyA.prefix === propertyB.prefix ? 0 : propertyA.prefix < propertyB.prefix ? -1 : 1;
                });
            })
        );
    }

    getConfigPropertiesObjects(res: Object) {
        // This code is for Spring Boot 2
        if (res['contexts'] !== undefined) {
            for (const key in res['contexts']) {
                // If the key is not bootstrap, it will be the ApplicationContext Id
                // For default app, it is baseName
                // For microservice, it is baseName-1
                if (!key.startsWith('bootstrap')) {
                    return res['contexts'][key]['beans'];
                }
            }
        }
        // by default, use the default ApplicationContext Id
        return res['contexts']['gateway']['beans'];
    }

    getEnv(): Observable<any> {
        return this.http.get(SERVER_API_URL + 'management/env', { observe: 'response' }).pipe(
            map((res: HttpResponse<any>) => {
                const properties: any = {};
                const propertySources = res.body['propertySources'];

                for (const propertyObject of propertySources) {
                    const name = propertyObject['name'];
                    const detailProperties = propertyObject['properties'];
                    const vals: any[] = [];
                    for (const keyDetail in detailProperties) {
                        if (detailProperties.hasOwnProperty(keyDetail)) {
                            vals.push({ key: keyDetail, val: detailProperties[keyDetail]['value'] });
                        }
                    }
                    properties[name] = vals;
                }
                return properties;
            })
        );
    }
}
