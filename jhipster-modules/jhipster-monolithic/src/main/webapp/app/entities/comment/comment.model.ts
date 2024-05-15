import { Post } from '../post';
export class Comment {
    constructor(
        public id?: number,
        public text?: string,
        public creationDate?: any,
        public post?: Post,
    ) {
    }
}
