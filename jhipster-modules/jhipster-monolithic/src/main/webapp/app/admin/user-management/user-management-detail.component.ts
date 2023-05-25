import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';

import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-user-mgmt-detail',
    templateUrl: './user-management-detail.component.html'
})
export class UserMgmtDetailComponent implements OnInit, OnDestroy {

    user: User;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private userService: UserService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['user-management']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['login']);
        });
    }

    load (login) {
        this.userService.find(login).subscribe(user => {
            this.user = user;
        });
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
