import { NgModule } from '@angular/core';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from './helpers/mock-language.service';

@NgModule({
    providers: [
        MockBackend,
        BaseRequestOptions,
        {
            provide: JhiLanguageService,
            useClass: MockLanguageService
        },
        {
            provide: Http,
            useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                return new Http(backendInstance, defaultOptions);
            },
            deps: [MockBackend, BaseRequestOptions]
        }
    ]
})
export class BaeldungTestModule {}
