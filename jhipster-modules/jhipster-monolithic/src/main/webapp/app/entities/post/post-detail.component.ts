import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Post } from './post.model';
import { PostService } from './post.service';

@Component({
    selector: 'jhi-post-detail',
    templateUrl: './post-detail.component.html'
})
export class PostDetailComponent implements OnInit, OnDestroy {

    post: Post;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private postService: PostService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['post']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.postService.find(id).subscribe(post => {
            this.post = post;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
