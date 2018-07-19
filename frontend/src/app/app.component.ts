import {Component} from '@angular/core';

import {ApiService} from './services/api.service';
import {SearchResponse} from './services/api.service.types';

// you can apparently only page to 100?
const MAX_PAGE = 100;

// page size is fixed at 20?
const PAGE_SIZE = 20;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  results: SearchResponse;
  pageList: Array<number>;
  submitting: boolean;

  constructor(private apiService: ApiService) {
    this.results = null;
    this.pageList = [1];
    this.submitting = false;
  }

  submitQuery(query, page): void {
    this.submitting = true;
    this.apiService.getSearchResults(query, page)
      .subscribe(response => {
        const results: SearchResponse = response.searchResponse;
        this.setPageList(results);
        this.submitting = false;
        console.log(results);
      }, error => {
        this.submitting = false;
        console.error(JSON.stringify(error));
      });
  }

  setPageList(results: SearchResponse) {
    this.pageList = [];
    const maxPage = this.getMaxPage(results);
    for (let i = 1; i <= maxPage; i++) {
      this.pageList.push(i);
    }
  }

  getMaxPage(results: SearchResponse) {
    return Math.min(MAX_PAGE, Math.ceil(results.totalResults / PAGE_SIZE));
  }
}
