import {Component, OnInit, Input, OnChanges} from "@angular/core";
import {Rating} from "../rating";

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.css']
})
export class RatingComponent implements OnInit, OnChanges {

  @Input() bookId: number;

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
    let rating: Rating = new Rating(1, this.bookId, this.bookId);
    let rating1: Rating = new Rating(1, this.bookId, this.bookId);
    this.ratings.push(rating, rating1);
  }

  onSubmit() {
    console.log(this.newRating);
    let ratingCopy: Rating = Object.assign({}, this.newRating, {id: 101});
    this.ratings.push(ratingCopy);
  }

}
