package networking.search;

import dtos.search.SearchRequest;
import model.entities.Activity;
import persistance.search.SearchPostgresDAO;

import java.sql.SQLException;
import java.util.List;

public class SocketSearchClient implements SearchClient
{
  @Override public List<Activity> search(SearchRequest request)
  {
    try
    {
      if((request.city() == null || request.city().isEmpty() && (request.type() == null || request.type().isEmpty())))
      {
        return SearchPostgresDAO.getInstance().getAllActivities();
      }
      else
      {
        return SearchPostgresDAO.getInstance().search(request.city(), request.type());
      }
    }
    catch (SQLException e)
    {
      System.err.println("Error initializing CreateSearchDAO: " + e.getMessage());
      e.printStackTrace();
      throw new RuntimeException("Failed to search activities", e);
    }
  }
}
