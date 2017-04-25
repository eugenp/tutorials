import {Component, OnInit, Input, OnChanges} from "@angular/core";
import {Rating} from "../rating";
import {Principal} from "../principal";
import {HttpService} from "../http.service";
import {Response} from "@angular/http";
import {Observable} from "rxjs";

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

  constructor(private httpService: HttpService) { }

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
    this.httpService.getRatings(this.bookId, this.principal.credentials)
      .map((response: Response) => response.json())
      .map(ratings => {
        return Observable.from(ratings)
      })
      .flatMap(x => x)
      .map((data: any) => new Rating(data.id, data.bookId, data.stars))
      .subscribe((rating: Rating) => {
        console.log(rating);
        this.ratings.push(rating);
      });
  }

  onSaveRating() {
    console.log(this.newRating);
    let ratingCopy: Rating = Object.assign({}, this.newRating);
    this.httpService.createRating(ratingCopy, this.principal.credentials)
      .map((response: Response) => response.json())
      .map((data: any) => new Rating(data.id, data.bookId, data.stars))
      .subscribe((rating: Rating) => {
        console.log(rating);
        this.ratings.push(rating);
      });
  }

  updateRating() {
    this.httpService.updateRating(this.newRating, this.principal.credentials)
      .subscribe(() => {
        this.newRating = new Rating(null, this.bookId, 1);
      })
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
    let rating = this.ratings[index];
    this.httpService.deleteRating(rating.id, this.principal.credentials)
      .subscribe(() => {
        if (this.ratings[index] === this.newRating) {
          this.newRating = new Rating(null, this.bookId, 1);
        }
        this.ratings.splice(index, 1);
      });

  }

}
