import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { UserMgmtDialogComponent } from './user-management-dialog.component';
import { User, UserService } from '../../shared';

@Injectable()
export class UserModalService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private userService: UserService
    ) {}

    open (component: Component, login?: string): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (login) {
            this.userService.find(login).subscribe(user => this.userModalRef(component, user));
        } else {
            return this.userModalRef(component, new User());
        }
    }

    userModalRef(component: Component, user: User): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.user = user;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
