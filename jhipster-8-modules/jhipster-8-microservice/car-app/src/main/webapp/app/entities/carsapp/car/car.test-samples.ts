import { ICar, NewCar } from './car.model';

export const sampleWithRequiredData: ICar = {
  id: 21972,
};

export const sampleWithPartialData: ICar = {
  id: 1045,
  price: 12594.9,
};

export const sampleWithFullData: ICar = {
  id: 11055,
  price: 25722.91,
};

export const sampleWithNewData: NewCar = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
