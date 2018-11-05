import { Injectable, RendererFactory2, Renderer2 } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRouteSnapshot } from '@angular/router';
import { TranslateService, LangChangeEvent } from '@ngx-translate/core';
import { BehaviorSubject, Observable } from 'rxjs';

import { LANGUAGES } from 'app/core/language/language.constants';

@Injectable({ providedIn: 'root' })
export class JhiLanguageHelper {
    renderer: Renderer2 = null;
    private _language: BehaviorSubject<string>;

    constructor(
        private translateService: TranslateService,
        // tslint:disable-next-line: no-unused-variable
        private rootRenderer: RendererFactory2,
        private titleService: Title,
        private router: Router
    ) {
        this._language = new BehaviorSubject<string>(this.translateService.currentLang);
        this.renderer = rootRenderer.createRenderer(document.querySelector('html'), null);
        this.init();
    }

    getAll(): Promise<any> {
        return Promise.resolve(LANGUAGES);
    }

    get language(): Observable<string> {
        return this._language.asObservable();
    }

    /**
     * Update the window title using params in the following
     * order:
     * 1. titleKey parameter
     * 2. $state.$current.data.pageTitle (current state page title)
     * 3. 'global.title'
     */
    updateTitle(titleKey?: string) {
        if (!titleKey) {
            titleKey = this.getPageTitle(this.router.routerState.snapshot.root);
        }

        this.translateService.get(titleKey).subscribe(title => {
            this.titleService.setTitle(title);
        });
    }

    private init() {
        this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
            this._language.next(this.translateService.currentLang);
            this.renderer.setAttribute(document.querySelector('html'), 'lang', this.translateService.currentLang);
            this.updateTitle();
        });
    }

    private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
        let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : 'gatewayApp';
        if (routeSnapshot.firstChild) {
            title = this.getPageTitle(routeSnapshot.firstChild) || title;
        }
        return title;
    }
}
