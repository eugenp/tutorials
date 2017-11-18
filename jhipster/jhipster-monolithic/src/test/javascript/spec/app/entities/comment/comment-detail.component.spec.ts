import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CommentDetailComponent } from '../../../../../../main/webapp/app/entities/comment/comment-detail.component';
import { CommentService } from '../../../../../../main/webapp/app/entities/comment/comment.service';
import { Comment } from '../../../../../../main/webapp/app/entities/comment/comment.model';

describe('Component Tests', () => {

    describe('Comment Management Detail Component', () => {
        let comp: CommentDetailComponent;
        let fixture: ComponentFixture<CommentDetailComponent>;
        let service: CommentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [CommentDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    {
                        provide: JhiLanguageService,
                        useClass: MockLanguageService
                    },
                    CommentService
                ]
            }).overrideComponent(CommentDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommentService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Comment(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.comment).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
