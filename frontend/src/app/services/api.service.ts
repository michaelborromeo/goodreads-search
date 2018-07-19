import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {GoodreadsSearchResponse} from './api.service.types';
import {environment} from '../../environments/environment';

@Injectable()
export class ApiService {
  constructor(private http: HttpClient) {
  }

  getSearchResults(query, page): Observable<GoodreadsSearchResponse> {
    return this.http.get<GoodreadsSearchResponse>(
      `${environment.apiServer}/api/search/books?query=${query}&page=${page}`);
  }
}
