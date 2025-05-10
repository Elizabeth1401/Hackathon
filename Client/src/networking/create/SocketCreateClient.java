package networking.create;

import dtos.Request;
import dtos.create.CreateActivityRequest;
import networking.SocketService;

public class SocketCreateClient implements CreateClient
{
  @Override public void createActivity(CreateActivityRequest activity)
  {
    Request request = new Request("create", "create", activity);
    SocketService.sendRequest(request);
  }
}
