import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 11725,
  login: '_x@zLH',
};

export const sampleWithPartialData: IUser = {
  id: 8685,
  login: 't@z-p',
};

export const sampleWithFullData: IUser = {
  id: 20563,
  login: 'c8vf@P',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
