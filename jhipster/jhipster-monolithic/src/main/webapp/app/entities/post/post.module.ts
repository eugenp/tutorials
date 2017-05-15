import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BaeldungSharedModule } from '../../shared';
import { BaeldungAdminModule } from '../../admin/admin.module';

import {
    PostService,
    PostPopupService,
    PostComponent,
    PostDetailComponent,
    PostDialogComponent,
    PostPopupComponent,
    PostDeletePopupComponent,
    PostDeleteDialogComponent,
    postRoute,
    postPopupRoute,
} from './';

let ENTITY_STATES = [
    ...postRoute,
    ...postPopupRoute,
];

@NgModule({
    imports: [
        BaeldungSharedModule,
        BaeldungAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PostComponent,
        PostDetailComponent,
        PostDialogComponent,
        PostDeleteDialogComponent,
        PostPopupComponent,
        PostDeletePopupComponent,
    ],
    entryComponents: [
        PostComponent,
        PostDialogComponent,
        PostPopupComponent,
        PostDeleteDialogComponent,
        PostDeletePopupComponent,
    ],
    providers: [
        PostService,
        PostPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BaeldungPostModule {}
