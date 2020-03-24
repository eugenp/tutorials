import {Component, OnInit, Input, OnChanges} from "@angular/core";
import {Rating} from "../rating";
import {Principal} from "../principal";
import {HttpService} from "../http.service";
import {Response} from "@angular/http";

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
    this.httpService.getRatings(this.bookId)
      .subscribe((response: Response) => {
        let responseJson: any[] = response.json();
        responseJson.forEach(rating => this.ratings.push(new Rating(rating.id, rating.bookId, rating.stars)))
      }, (error) => {
        console.log(error);
      });
  }

  onSaveRating() {
    console.log(this.newRating);
    let ratingCopy: Rating = Object.assign({}, this.newRating);
    this.httpService.createRating(ratingCopy)
      .subscribe((response: Response) => {
        let ratingJson = response.json()
        this.ratings.push(new Rating(ratingJson.id, ratingJson.bookId, ratingJson.stars))
      }, (error) => {
        console.log(error);
      });
  }

  updateRating() {
    this.httpService.updateRating(this.newRating)
      .subscribe(() => {
        this.newRating = new Rating(null, this.bookId, 1);
      }, (error) => {
        console.log(error);
      });
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
    this.httpService.deleteRating(rating.id)
      .subscribe(() => {
        if (this.ratings[index] === this.newRating) {
          this.newRating = new Rating(null, this.bookId, 1);
        }
        this.ratings.splice(index, 1);
      }, (error) => {
        console.log(error);
      });

  }

}
