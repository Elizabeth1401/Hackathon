package networking.search;

import dtos.search.SearchRequest;
import model.entities.Activity;

import java.util.List;

public interface SearchClient
{
  List<Activity> search(SearchRequest request);
}
