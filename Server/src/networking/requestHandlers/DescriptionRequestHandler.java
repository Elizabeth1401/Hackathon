package networking.requestHandlers;

import services.description.DescriptionService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DescriptionRequestHandler implements RequestHandler
{
  private final DescriptionService descriptionService;

  public DescriptionRequestHandler(DescriptionService descriptionService)
  {
    this.descriptionService = descriptionService;
  }

  @Override
  public Object handle(String action, Object payload) throws SQLException
  {
    if ("getDescription".equalsIgnoreCase(action))
    {
      if (payload instanceof Integer)
      {
        int activityId = (Integer) payload;
        String description = descriptionService.getActivityDescription(activityId);

        if (description == null)
        {
          return Map.of("error", "No description found for activity ID: " + activityId);
        }

        return Map.of("description", description);
      }
      else
      {
        return Map.of("error", "Invalid payload type. Expected Integer activityId.");
      }
    }

    return Map.of("error", "Unsupported action: " + action);
  }
}
