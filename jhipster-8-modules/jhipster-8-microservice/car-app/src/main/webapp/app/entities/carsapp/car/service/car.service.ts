import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICar, NewCar } from '../car.model';

export type PartialUpdateCar = Partial<ICar> & Pick<ICar, 'id'>;

export type EntityResponseType = HttpResponse<ICar>;
export type EntityArrayResponseType = HttpResponse<ICar[]>;

@Injectable({ providedIn: 'root' })
export class CarService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cars', 'carsapp');

  create(car: NewCar): Observable<EntityResponseType> {
    return this.http.post<ICar>(this.resourceUrl, car, { observe: 'response' });
  }

  update(car: ICar): Observable<EntityResponseType> {
    return this.http.put<ICar>(`${this.resourceUrl}/${this.getCarIdentifier(car)}`, car, { observe: 'response' });
  }

  partialUpdate(car: PartialUpdateCar): Observable<EntityResponseType> {
    return this.http.patch<ICar>(`${this.resourceUrl}/${this.getCarIdentifier(car)}`, car, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICar>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICar[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCarIdentifier(car: Pick<ICar, 'id'>): number {
    return car.id;
  }

  compareCar(o1: Pick<ICar, 'id'> | null, o2: Pick<ICar, 'id'> | null): boolean {
    return o1 && o2 ? this.getCarIdentifier(o1) === this.getCarIdentifier(o2) : o1 === o2;
  }

  addCarToCollectionIfMissing<Type extends Pick<ICar, 'id'>>(carCollection: Type[], ...carsToCheck: (Type | null | undefined)[]): Type[] {
    const cars: Type[] = carsToCheck.filter(isPresent);
    if (cars.length > 0) {
      const carCollectionIdentifiers = carCollection.map(carItem => this.getCarIdentifier(carItem));
      const carsToAdd = cars.filter(carItem => {
        const carIdentifier = this.getCarIdentifier(carItem);
        if (carCollectionIdentifiers.includes(carIdentifier)) {
          return false;
        }
        carCollectionIdentifiers.push(carIdentifier);
        return true;
      });
      return [...carsToAdd, ...carCollection];
    }
    return carCollection;
  }
}
