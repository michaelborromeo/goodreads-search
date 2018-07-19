package goodreads.search.model;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * This object is meant to handle the incoming XML from the Goodreads API and as well it is serialized into JSON
 * for outgoing search results. Only certain useful fields are mapped by this object.
 */
@Data
@XmlRootElement(name = "GoodreadsResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public final class GoodreadsResponse {
  @XmlElement(name = "search")
  private GoodreadsSearchResponse searchResponse;

  @Data
  @XmlAccessorType(XmlAccessType.FIELD)
  static final class GoodreadsSearchResponse {
    @XmlElement(name = "results-start")
    private int resultsStart;

    @XmlElement(name = "results-end")
    private int resultsEnd;

    @XmlElement(name = "total-results")
    private int totalResults;

    @XmlElementWrapper(name = "results")
    @XmlElement(name = "work")
    private List<GoodreadsWork> work;
  }

  @Data
  @XmlAccessorType(XmlAccessType.FIELD)
  static final class GoodreadsWork {
    @XmlElement(name = "best_book")
    private List<GoodreadsBook> book;
  }

  @Data
  @XmlAccessorType(XmlAccessType.FIELD)
  static final class GoodreadsBook {
    @XmlElement(name = "id")
    private int id;

    @XmlElement(name = "title")
    private String title;

    @XmlElement(name = "author")
    private GoodreadsAuthor author;

    @XmlElement(name = "image_url")
    private String imageUrl;

    @XmlElement(name = "small_image_url")
    private String imageUrlSmall;
  }

  @Data
  @XmlAccessorType(XmlAccessType.FIELD)
  static final class GoodreadsAuthor {
    @XmlElement(name = "id")
    private int id;

    @XmlElement(name = "name")
    private String name;
  }
}
