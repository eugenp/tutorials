import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Post } from './post.model';
import { PostService } from './post.service';
@Injectable()
export class PostPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private postService: PostService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.postService.find(id).subscribe(post => {
                if (post.creationDate) {
                    post.creationDate = {
                        year: post.creationDate.getFullYear(),
                        month: post.creationDate.getMonth() + 1,
                        day: post.creationDate.getDate()
                    };
                }
                this.postModalRef(component, post);
            });
        } else {
            return this.postModalRef(component, new Post());
        }
    }

    postModalRef(component: Component, post: Post): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.post = post;
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
