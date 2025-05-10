package persistance.search;

import model.entities.Activity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchPostgresDAO implements SearchDAO
{
  private static SearchPostgresDAO instance;

  private static final Map<Integer, String> TYPE_NAMES = new HashMap<>();

  static
  {
    TYPE_NAMES.put(1, "Music");
    TYPE_NAMES.put(2, "Sports");
    TYPE_NAMES.put(3, "Technology");
    TYPE_NAMES.put(4, "Art");
    TYPE_NAMES.put(5, "Literature");
    TYPE_NAMES.put(6, "Gaming");
    TYPE_NAMES.put(7, "Cooking");
    TYPE_NAMES.put(8, "Fitness");
    TYPE_NAMES.put(9, "Film");
    TYPE_NAMES.put(10, "Nature");
  }

  private SearchPostgresDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized SearchPostgresDAO getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new SearchPostgresDAO();
    }
    return instance;
  }

  @Override public List<Activity> search(String city, String type)
  {
    List<Activity> results = new ArrayList<>();
    try (Connection connection = getConnection())
    {
      StringBuilder query = new StringBuilder(
          "SELECT a.event_id, a.name, a.type, a.address, a.city, "
              + "a.meeting_date, a.meeting_time, a.price, a.description "
              + "FROM activity a " + "WHERE 1=1 ");

      List<Object> params = new ArrayList<>();

      if (city != null && !city.isEmpty())
      {
        query.append("AND a.city = ? ");
        params.add(city);
      }

      if (type != null && !type.isEmpty())
      {
        Integer typeId = null;
        for (Map.Entry<Integer, String> entry : TYPE_NAMES.entrySet())
        {
          if (entry.getValue().equals(type))
          {
            typeId = entry.getKey();
            break;
          }
        }
        if (typeId != null)
        {
          query.append("AND a.type = ? ");
          params.add(typeId);
        }
      }

      System.out.println("Executing query: " + query.toString());
      System.out.println("Parameters: " + params);

      PreparedStatement statement = connection.prepareStatement(
          query.toString());
      for (int i = 0; i < params.size(); i++)
      {
        statement.setObject(i + 1, params.get(i));
      }

      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next())
      {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int typeId = resultSet.getInt("type");
        String typeName = TYPE_NAMES.get(typeId);
        String address = resultSet.getString("address");
        String cityName = resultSet.getString("city");
        String meetingDate = resultSet.getString("meeting_date");
        String meetingTime = resultSet.getString("meeting_time");
        int price = resultSet.getInt("price");
        String description = resultSet.getString("description");

        results.add(
            new Activity(id, name, typeName, address, cityName, meetingDate,
                meetingTime, price, description));
      }
      System.out.println("Found " + results.size() + " activities");
    }
    catch (SQLException e)
    {
      System.err.println("SQL Error in search: " + e.getMessage());
      e.printStackTrace();
    }
    return results;
  }

  public List<Activity> getAllActivities()
  {
    List<Activity> results = new ArrayList<>();
    try (Connection connection = getConnection())
    {
      String query = "SELECT a.event_id, a.name, a.type, a.address, a.city, "
          + "a.meeting_date, a.meeting_time, a.price, a.description "
          + "FROM activity a";

      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next())
      {
        int id = resultSet.getInt("event_id");
        String name = resultSet.getString("name");
        int typeId = resultSet.getInt("type");
        String typeName = TYPE_NAMES.get(typeId);
        String address = resultSet.getString("address");
        String cityName = resultSet.getString("city");
        String meetingDate = resultSet.getString("meeting_date");
        String meetingTime = resultSet.getString("meeting_time");
        int price = resultSet.getInt("price");
        String description = resultSet.getString("description");

        results.add(
            new Activity(id, name, typeName, address, cityName, meetingDate,
                meetingTime, price, description));
      }
    }
    catch (SQLException e)
    {
      System.err.println("SQL Error in getAll: " + e.getMessage());
      e.printStackTrace();
    }
    return results;
  }

  public String getActivityDescription(int activityId)
  {
    String description = null;
    try (Connection connection = getConnection())
    {
      String query = "SELECT description FROM activity WHERE event_id = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, activityId);

      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next())
      {
        description = resultSet.getString("description");
      }
    }
    catch (SQLException e)
    {
      System.err.println("SQL Error in getActivityDescription: " + e.getMessage());
      e.printStackTrace();
    }
    return description;
  }

  private static Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres?currentSchema=viahack",
        "postgres", "141220");
  }
}