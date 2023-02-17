import { NgModule } from '@angular/core';

import { BookstoreSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [BookstoreSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [BookstoreSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class BookstoreSharedCommonModule {}
