jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, waitForAsync, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';

import { UserManagementService } from '../service/user-management.service';

import UserManagementDeleteDialogComponent from './user-management-delete-dialog.component';

describe('User Management Delete Component', () => {
  let comp: UserManagementDeleteDialogComponent;
  let fixture: ComponentFixture<UserManagementDeleteDialogComponent>;
  let service: UserManagementService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, UserManagementDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(UserManagementDeleteDialogComponent, '')
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserManagementDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(UserManagementService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of({}));

        // WHEN
        comp.confirmDelete('user');
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith('user');
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));
  });
});
