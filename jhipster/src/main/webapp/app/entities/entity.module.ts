import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BaeldungPostModule } from './post/post.module';
import { BaeldungCommentModule } from './comment/comment.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        BaeldungPostModule,
        BaeldungCommentModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BaeldungEntityModule {}
