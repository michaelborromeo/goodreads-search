import {Component} from '@angular/core';
import {orderBy} from 'lodash';

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
  sortBy: any;
  errorMessage: string;

  constructor(private apiService: ApiService) {
    this.results = null;
    this.pageList = [1];
    this.submitting = false;
    this.sortBy = {field: 'title', order: 'asc'};
  }

  submitQuery(query, page): void {
    this.submitting = true;
    this.apiService.getSearchResults(query, page)
      .subscribe(response => {
        this.results = response.searchResponse;
        this.sortResults();
        this.setPageList(this.results);
        this.submitting = false;
      }, error => {
        this.submitting = false;
        this.pageList = [1];
        this.results = null;
        this.errorMessage = error.message;
        console.error(error);
      });
  }

  sortResults() {
    if (this.sortBy.field === 'title') {
      this.results.work = orderBy(this.results.work, 'book[0].title', this.sortBy.order);
    } else {
      this.results.work = orderBy(this.results.work, 'book[0].author.name', this.sortBy.order);
    }
  }

  setPageList(results: SearchResponse) {
    this.pageList = [];
    const maxPage = this.getMaxPage(results);
    for (let i = 1; i <= maxPage; i++) {
      this.pageList.push(i);
    }
  }

  toggleSortByTitle() {
    if (this.sortBy.field === 'title') {
      if (this.sortBy.order === 'asc') {
        this.sortBy.order = 'desc';
      } else {
        this.sortBy.order = 'asc';
      }
    } else {
      this.sortBy.field = 'title';
      this.sortBy.order = 'asc';
    }
    this.sortResults();
  }

  toggleSortByAuthor() {
    if (this.sortBy.field === 'author') {
      if (this.sortBy.order === 'asc') {
        this.sortBy.order = 'desc';
      } else {
        this.sortBy.order = 'asc';
      }
    } else {
      this.sortBy.field = 'author';
      this.sortBy.order = 'asc';
    }
    this.sortResults();
  }

  getSortArrow(field: string, order: string) {
    if (this.sortBy.field === field) {
      return order === 'asc' ? '↑' : '↓';
    } else {
      return '';
    }
  }

  getMaxPage(results: SearchResponse) {
    return Math.min(MAX_PAGE, Math.ceil(results.totalResults / PAGE_SIZE));
  }
}
