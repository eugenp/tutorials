import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '97eaaa20-15e5-4298-bfb0-176a0873b446',
};

export const sampleWithPartialData: IAuthority = {
  name: '937312e3-fce3-495e-a42f-c73bf46e371b',
};

export const sampleWithFullData: IAuthority = {
  name: 'f3edd6f0-9777-43b4-9006-3aa544c3cc90',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
