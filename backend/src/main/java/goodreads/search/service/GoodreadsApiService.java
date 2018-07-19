package goodreads.search.service;

import goodreads.search.model.GoodreadsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;

@Component
public class GoodreadsApiService {
  private Unmarshaller unmarshaller;
  private RestTemplate restTemplate;

  @Value("${goodreads.api.key}")
  private String apiKey;

  @Value("${goodreads.api.secret}")
  private String apiSecret;

  @Value("${goodreads.api.server}")
  private String apiServer;

  @Value("${goodreads.api.search.endpoint}")
  private String searchApiEndpoint;

  /**
   * Create the necessary objects to make API calls.
   *
   * @throws JAXBException If for whatever reason this exception is thrown then the app will fail on startup.
   */
  public GoodreadsApiService() throws JAXBException {
    // TODO make beans out of these and inject them into this to enable proper testing of this class
    JAXBContext jaxbContext = JAXBContext.newInstance(GoodreadsResponse.class);
    unmarshaller = jaxbContext.createUnmarshaller();
    restTemplate = new RestTemplate();
  }

  /**
   * Call the goodreads API to search books and return the result.
   *
   * @param query
   * @param page
   * @return The goodreads search response.
   * @throws JAXBException This exception will be handled in the controller.
   */
  public GoodreadsResponse getSearchResults(String query, int page) throws JAXBException {
    String uri = this.apiServer + this.searchApiEndpoint + "?q={query}&key={key}&page={page}";
    return (GoodreadsResponse) unmarshaller.unmarshal(
        new ByteArrayInputStream(restTemplate.getForObject(uri, String.class, query, apiKey, page).getBytes()));
  }
}
