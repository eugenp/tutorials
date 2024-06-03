import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICar } from '../car.model';
import { CarService } from '../service/car.service';

@Component({
  standalone: true,
  templateUrl: './car-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CarDeleteDialogComponent {
  car?: ICar;

  protected carService = inject(CarService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.carService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
