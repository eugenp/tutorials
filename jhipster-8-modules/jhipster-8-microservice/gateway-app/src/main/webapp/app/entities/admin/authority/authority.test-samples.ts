import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '74836a4b-3398-4256-a14f-ff579aec95c0',
};

export const sampleWithPartialData: IAuthority = {
  name: '1e7cf7f6-12e8-4842-aab8-0461e0524401',
};

export const sampleWithFullData: IAuthority = {
  name: '20e24bb0-fa4b-4dc6-8772-4cd68247692e',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
