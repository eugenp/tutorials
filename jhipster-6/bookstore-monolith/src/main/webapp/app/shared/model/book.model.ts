import { Moment } from 'moment';

export interface IBook {
    id?: number;
    title?: string;
    author?: string;
    published?: Moment;
    quantity?: number;
    price?: number;
}

export class Book implements IBook {
    constructor(
        public id?: number,
        public title?: string,
        public author?: string,
        public published?: Moment,
        public quantity?: number,
        public price?: number
    ) {}
}
