import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Book } from 'app/shared/model/book.model';
import { BookService } from './book.service';
import { BookComponent } from './book.component';
import { BookDetailComponent } from './book-detail.component';
import { BookUpdateComponent } from './book-update.component';
import { BookDeletePopupComponent } from './book-delete-dialog.component';
import { IBook } from 'app/shared/model/book.model';

@Injectable({ providedIn: 'root' })
export class BookResolve implements Resolve<IBook> {
    constructor(private service: BookService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBook> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Book>) => response.ok),
                map((book: HttpResponse<Book>) => book.body)
            );
        }
        return of(new Book());
    }
}

export const bookRoute: Routes = [
    {
        path: '',
        component: BookComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Books'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BookDetailComponent,
        resolve: {
            book: BookResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Books'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BookUpdateComponent,
        resolve: {
            book: BookResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Books'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BookUpdateComponent,
        resolve: {
            book: BookResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Books'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bookPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BookDeletePopupComponent,
        resolve: {
            book: BookResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Books'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
