package services.search;

import model.entities.Activity;
import persistance.search.SearchPostgresDAO;

import java.util.List;

public class SearchServiceImpl implements SearchService
{
  private final SearchPostgresDAO searchPostgresDAO;

  public SearchServiceImpl(SearchPostgresDAO searchPostgresDAO)
  {
    this.searchPostgresDAO = searchPostgresDAO;
  }

  @Override public List<Activity> search(String city, String type)
  {
    return searchPostgresDAO.search(city, type);
  }

  // Optional: method to fetch all activities
  public List<Activity> getAllActivities()
  {
    return searchPostgresDAO.getAllActivities();
  }
}
