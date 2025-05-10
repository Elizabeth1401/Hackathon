package networking.requestHandlers;

import dtos.create.CreateActivityRequest;
import services.create.CreateService;

import java.sql.SQLException;

public class CreateRequestHandler implements RequestHandler
{
  private final CreateService createService;

  public CreateRequestHandler(CreateService createService)
  {
    this.createService = createService;
  }

  public Object handle(String action, Object payload) throws SQLException
  {
    switch (action)
    {
      case "create" -> createService.create((CreateActivityRequest) payload);
    }
    return null;
  }
}

