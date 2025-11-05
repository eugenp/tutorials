/**
 * Angular bootstrap Date adapter
 */
import { Injectable } from '@angular/core';
import { NgbDateAdapter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import dayjs from 'dayjs/esm';

@Injectable()
export class NgbDateDayjsAdapter extends NgbDateAdapter<dayjs.Dayjs> {
  fromModel(date: dayjs.Dayjs | null): NgbDateStruct | null {
    if (date && dayjs.isDayjs(date) && date.isValid()) {
      return { year: date.year(), month: date.month() + 1, day: date.date() };
    }
    return null;
  }

  toModel(date: NgbDateStruct | null): dayjs.Dayjs | null {
    return date ? dayjs(`${date.year}-${date.month}-${date.day}`) : null;
  }
}
