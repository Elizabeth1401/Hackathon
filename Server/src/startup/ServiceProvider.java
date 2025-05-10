package startup;

import networking.requestHandlers.CreateRequestHandler;
import networking.requestHandlers.RequestHandler;
import networking.requestHandlers.SearchRequestHandler;
import networking.requestHandlers.DescriptionRequestHandler;
import persistance.create.CreateDAO;
import persistance.create.CreatePostgresDAO;
import persistance.search.SearchPostgresDAO;
import services.create.CreateService;
import services.create.CreateServiceImpl;
import services.description.DescriptionService;
import services.description.DescriptionServiceImpl;
import services.search.SearchService;
import services.search.SearchServiceImpl;
import utilities.ConsoleLogger;
import utilities.LogLevel;
import utilities.Logger;

import java.sql.SQLException;

public class ServiceProvider
{
  public RequestHandler getCreateRequestHandler()
  {
    return new CreateRequestHandler(getCreateService());
  }

  public RequestHandler getSearchRequestHandler()
  {
    return new SearchRequestHandler(getSearchService(), getSearchDao());
  }

  public RequestHandler getDescriptionRequestHandler()
  {
    return new DescriptionRequestHandler(getDescriptionService());
  }

  private static CreateService getCreateService()
  {
    return new CreateServiceImpl(getCreateDao());
  }

  private static SearchService getSearchService()
  {
    return new SearchServiceImpl(getSearchDao());
  }

  private static DescriptionService getDescriptionService()
  {
    return new DescriptionServiceImpl(getSearchDao());
  }



  private static CreateDAO getCreateDao()
  {
    try
    {
      return CreatePostgresDAO.getInstance();
    }
    catch (SQLException e)
    {
      throw new RuntimeException("Failed to initialize DAO",
          e); // or handle as needed
    }
  }

  private static SearchPostgresDAO getSearchDao()
  {
    try
    {
      return SearchPostgresDAO.getInstance();
    }
    catch (SQLException e)
    {
      throw new RuntimeException("Failed to initial e"); // or handle as needed
    }
  }

  public Logger getLogger()
  {
    return new ConsoleLogger(LogLevel.INFO);
  }
}
