export interface ICar {
  id: number;
  price?: number | null;
}

export type NewCar = Omit<ICar, 'id'> & { id: null };
