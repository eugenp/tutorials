import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Comment } from './comment.model';
import { CommentService } from './comment.service';

@Component({
    selector: 'jhi-comment-detail',
    templateUrl: './comment-detail.component.html'
})
export class CommentDetailComponent implements OnInit, OnDestroy {

    comment: Comment;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private commentService: CommentService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['comment']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.commentService.find(id).subscribe(comment => {
            this.comment = comment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
