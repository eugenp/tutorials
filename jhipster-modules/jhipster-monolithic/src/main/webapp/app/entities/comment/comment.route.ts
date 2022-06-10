import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CommentComponent } from './comment.component';
import { CommentDetailComponent } from './comment-detail.component';
import { CommentPopupComponent } from './comment-dialog.component';
import { CommentDeletePopupComponent } from './comment-delete-dialog.component';

import { Principal } from '../../shared';


export const commentRoute: Routes = [
  {
    path: 'comment',
    component: CommentComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'baeldungApp.comment.home.title'
    }
  }, {
    path: 'comment/:id',
    component: CommentDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'baeldungApp.comment.home.title'
    }
  }
];

export const commentPopupRoute: Routes = [
  {
    path: 'comment-new',
    component: CommentPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'baeldungApp.comment.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'comment/:id/edit',
    component: CommentPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'baeldungApp.comment.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'comment/:id/delete',
    component: CommentDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'baeldungApp.comment.home.title'
    },
    outlet: 'popup'
  }
];
