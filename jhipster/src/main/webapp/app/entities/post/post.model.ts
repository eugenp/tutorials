import { User } from '../../shared';
export class Post {
    constructor(
        public id?: number,
        public title?: string,
        public content?: string,
        public creationDate?: any,
        public creator?: User,
    ) {
    }
}
