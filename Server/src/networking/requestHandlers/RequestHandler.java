package networking.requestHandlers;

import java.sql.SQLException;

public interface RequestHandler
{
  Object handle(String action, Object payload) throws SQLException;
}
