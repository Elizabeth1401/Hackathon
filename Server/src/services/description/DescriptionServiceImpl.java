package services.description;

import persistance.search.SearchPostgresDAO;

public class DescriptionServiceImpl implements DescriptionService
{
  private final SearchPostgresDAO searchPostgresDAO;

  public DescriptionServiceImpl(SearchPostgresDAO searchPostgresDAO)
  {
    this.searchPostgresDAO = searchPostgresDAO;
  }
  @Override
  public String getActivityDescription(int activityId)
  {
    try
    {
      return SearchPostgresDAO.getInstance().getActivityDescription(activityId);
    }
    catch (Exception e)
    {
      System.err.println("Error in DescriptionServiceImpl: " + e.getMessage());
      e.printStackTrace();
      return null;
    }
  }
}
