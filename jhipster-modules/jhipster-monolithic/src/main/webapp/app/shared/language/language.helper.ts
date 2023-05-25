import { Injectable } from '@angular/core';
import { Title }     from '@angular/platform-browser';
import { Router, ActivatedRouteSnapshot } from '@angular/router';
import { TranslateService, TranslationChangeEvent, LangChangeEvent } from 'ng2-translate/ng2-translate';

import { LANGUAGES } from './language.constants';

@Injectable()
export class JhiLanguageHelper {

    constructor (private translateService: TranslateService, private titleService: Title, private router: Router) {
        this.init();
    }

    getAll(): Promise<any> {
        return Promise.resolve(LANGUAGES);
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

    private init () {
        this.translateService.onTranslationChange.subscribe((event: TranslationChangeEvent) => {
            this.updateTitle();
        });

        this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
            this.updateTitle();
        });
    }

    private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
        let title: string = (routeSnapshot.data && routeSnapshot.data['pageTitle']) ? routeSnapshot.data['pageTitle'] : 'baeldungApp';
        if (routeSnapshot.firstChild) {
            title = this.getPageTitle(routeSnapshot.firstChild) || title;
        }
        return title;
    }
}
