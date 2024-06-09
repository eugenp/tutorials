export interface IAuthority {
  name: string;
}

export type NewAuthority = Omit<IAuthority, 'name'> & { name: null };
