import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AuthorityDetailComponent } from './authority-detail.component';

describe('Authority Management Detail Component', () => {
  let comp: AuthorityDetailComponent;
  let fixture: ComponentFixture<AuthorityDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthorityDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AuthorityDetailComponent,
              resolve: { authority: () => of({ name: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AuthorityDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorityDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load authority on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AuthorityDetailComponent);

      // THEN
      expect(instance.authority()).toEqual(expect.objectContaining({ name: 'ABC' }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
