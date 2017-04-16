import {Injectable} from "@angular/core";
import {BaseRequestOptions, Headers} from "@angular/http";
/**
 * Created by tschi on 4/16/2017.
 */
@Injectable()
export class DefaultRequestOptions extends BaseRequestOptions {
  headers = new Headers({
    'X-Requested-With':'XMLHttpRequest',
  });
}
