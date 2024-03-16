import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Principal} from "./principal";

@Injectable({
  providedIn: "root"
})
export class HttpService {
  constructor(private httpClient: HttpClient) {
  }

  me() {
    return this.httpClient.get<Principal>("/me", {
      headers: this.makeOptions(),
      responseType: 'json',
      observe: 'response'
    })
  }

  logout() {
    return this.httpClient.post("/logout", '', {
      headers: this.makeOptions(),
      responseType: 'text'
    })
  }

  private makeOptions(): HttpHeaders {
    return new HttpHeaders({'Content-Type': 'application/json'});
  }
}
