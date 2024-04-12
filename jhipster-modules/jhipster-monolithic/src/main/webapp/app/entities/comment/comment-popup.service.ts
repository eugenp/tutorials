import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Comment } from './comment.model';
import { CommentService } from './comment.service';
@Injectable()
export class CommentPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private commentService: CommentService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.commentService.find(id).subscribe(comment => {
                if (comment.creationDate) {
                    comment.creationDate = {
                        year: comment.creationDate.getFullYear(),
                        month: comment.creationDate.getMonth() + 1,
                        day: comment.creationDate.getDate()
                    };
                }
                this.commentModalRef(component, comment);
            });
        } else {
            return this.commentModalRef(component, new Comment());
        }
    }

    commentModalRef(component: Component, comment: Comment): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.comment = comment;
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
