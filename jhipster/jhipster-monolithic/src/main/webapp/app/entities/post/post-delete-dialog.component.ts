import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Post } from './post.model';
import { PostPopupService } from './post-popup.service';
import { PostService } from './post.service';

@Component({
    selector: 'jhi-post-delete-dialog',
    templateUrl: './post-delete-dialog.component.html'
})
export class PostDeleteDialogComponent {

    post: Post;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private postService: PostService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['post']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.postService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'postListModification',
                content: 'Deleted an post'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-post-delete-popup',
    template: ''
})
export class PostDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private postPopupService: PostPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.postPopupService
                .open(PostDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
