export interface Author {
  id: number;
  name: string;
}

export interface Book {
  id: number;
  title: string;
  author: Author;
  imageUrl: string;
  imageUrlSmall: string;
}

export interface Work {
  book: Book[];
}

export interface SearchResponse {
  resultsStart: number;
  resultsEnd: number;
  totalResults: number;
  work: Work[];
}

export interface GoodreadsSearchResponse {
  searchResponse?: SearchResponse; // successful response
  message?: string; // error response
}
