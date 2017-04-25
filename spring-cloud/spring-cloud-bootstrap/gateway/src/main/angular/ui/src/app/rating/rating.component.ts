import {Component, OnInit, Input, OnChanges} from "@angular/core";
import {Rating} from "../rating";
import {Principal} from "../principal";

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.css']
})
export class RatingComponent implements OnInit, OnChanges {

  @Input() bookId: number;
  @Input() principal: Principal = null;
  ratings: Rating[] = [];
  stars: number[] = [1,2,3,4,5];
  newRating: Rating = null;

  constructor() { }

  ngOnInit() {}

  ngOnChanges() {
    this.newRating = new Rating(null, this.bookId, 1);
    this.ratings = [];
    this.loadRatings();
  }

  findWidth(rating: Rating): String {
    let percent: number = (rating.stars/5)*100;
    return percent.toString() + '%';
  }

  private loadRatings() {
    let rating: Rating = new Rating(1, this.bookId, 1);
    let rating1: Rating = new Rating(1, this.bookId, 1);
    this.ratings.push(rating, rating1);
  }

  onSubmit() {
    console.log(this.newRating);
    let ratingCopy: Rating = Object.assign({}, this.newRating, {id: Math.floor(Math.random() * 1000)});
    this.ratings.push(ratingCopy);
  }

  selectRating(rating: Rating) {
    if (this.principal.isAdmin()) {
      this.newRating = rating;
    }
  }

  cancelSelection() {
    this.newRating = new Rating(null, this.bookId, 1);
  }

  deleteRating(index: number) {
    if (this.ratings[index] === this.newRating) {
      this.newRating = new Rating(null, this.bookId, 1);
    }
    this.ratings.splice(index, 1);
  }

}
