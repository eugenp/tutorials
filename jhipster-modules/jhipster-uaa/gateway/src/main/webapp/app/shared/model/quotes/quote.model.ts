import { Moment } from 'moment';

export interface IQuote {
    id?: number;
    symbol?: string;
    price?: number;
    lastTrade?: Moment;
}

export class Quote implements IQuote {
    constructor(public id?: number, public symbol?: string, public price?: number, public lastTrade?: Moment) {}
}
