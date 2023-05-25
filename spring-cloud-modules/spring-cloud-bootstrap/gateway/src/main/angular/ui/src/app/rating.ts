export class Rating{
  id: number;
  bookId: number;
  stars: number;

  constructor(id: number, bookId: number, stars: number) {
    this.id = id;
    this.bookId = bookId;
    this.stars = stars;
  }
}
