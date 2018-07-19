package goodreads.search.controller;

import goodreads.search.model.ErrorResponse;
import goodreads.search.model.GoodreadsResponse;
import goodreads.search.service.GoodreadsApiService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.xml.bind.JAXBException;

@RestController
@CrossOrigin
public class SearchController {
  private static Logger logger = LogManager.getLogger(SearchController.class);

  private GoodreadsApiService apiService;

  @Autowired
  public SearchController(GoodreadsApiService apiService) {
    this.apiService = apiService;
  }

  /**
   * Handle exceptions for this controller by sending the user the exception message then logging the error.
   *
   * @param e
   * @param request
   * @return
   */
  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handleException(RuntimeException e, WebRequest request) {
    logger.error(e.getMessage(), e);
    return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handle incoming requests for book searches and send back responses in JSON.
   *
   * @param query
   * @param page
   * @return
   * @throws JAXBException
   */
  @RequestMapping(value = "/api/search/books", method = RequestMethod.GET,
      produces = {"application/json"})
  public ResponseEntity<GoodreadsResponse> searchBooks(@RequestParam String query, @RequestParam String page) throws JAXBException {
    // gracefully handle invalid page values
    int pageNumber = 1;
    try {
      pageNumber = Integer.parseInt(page);
    } catch (NumberFormatException e) {
      logger.info("Unable to parse page number: " + page);
    }

    return new ResponseEntity<GoodreadsResponse>(apiService.getSearchResults(query, pageNumber), HttpStatus.OK);
  }
}
