import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 11523,
  login: 'QPhXaU',
};

export const sampleWithPartialData: IUser = {
  id: 27660,
  login: 'e-',
};

export const sampleWithFullData: IUser = {
  id: 29453,
  login: '@.a',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
