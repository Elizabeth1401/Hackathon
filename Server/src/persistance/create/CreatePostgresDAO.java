package persistance.create;

import model.entities.Activity;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreatePostgresDAO implements CreateDAO
{
  private static CreatePostgresDAO instance;

  private CreatePostgresDAO() throws SQLException
  {
    DriverManager.registerDriver(new org.postgresql.Driver());
  }

  public static synchronized CreatePostgresDAO getInstance() throws SQLException
  {
    if (instance == null)
    {
      instance = new CreatePostgresDAO();
    }
    return instance;
  }

  private static Connection getConnection() throws SQLException
  {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres?currentSchema=viahack",
        "postgres", "141220");
  }

  private static final Map<String, Integer> ACTIVITY_TYPES = new HashMap<>();

  static
  {
    ACTIVITY_TYPES.put("Music", 1);
    ACTIVITY_TYPES.put("Sports", 2);
    ACTIVITY_TYPES.put("Technology", 3);
    ACTIVITY_TYPES.put("Art", 4);
    ACTIVITY_TYPES.put("Literature", 5);
    ACTIVITY_TYPES.put("Gaming", 6);
    ACTIVITY_TYPES.put("Cooking", 7);
    ACTIVITY_TYPES.put("Fitness", 8);
    ACTIVITY_TYPES.put("Film", 9);
    ACTIVITY_TYPES.put("Nature", 10);
  }

  @Override public Activity create(String name, String type, String address,
      String city, String meetingDate, String meetingTime, int price,
      String description)
  {
    try (Connection connection = getConnection())
    {

      // Debug print statements
      System.out.println("Inserting activity:");
      System.out.println("Name: " + name);
      System.out.println("Type: " + type);
      System.out.println("Address: " + address);
      System.out.println("City: " + city);
      System.out.println("Date: " + meetingDate);
      System.out.println("Time: " + meetingTime);
      System.out.println("Price: " + price);
      System.out.println("Description: " + description);

      int typeId;
      switch (type)
      {
        case "Music":
          typeId = 1;
          break;
        case "Sports":
          typeId = 2;
          break;
        case "Technology":
          typeId = 3;
          break;
        case "Art":
          typeId = 4;
          break;
        case "Literature":
          typeId = 5;
          break;
        case "Gaming":
          typeId = 6;
          break;
        case "Cooking":
          typeId = 7;
          break;
        case "Fitness":
          typeId = 8;
          break;
        case "Film":
          typeId = 9;
          break;
        case "Nature":
          typeId = 10;
          break;
        default:
          throw new IllegalArgumentException("Invalid activity type: " + type);
      }

      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO activity(name, type, address, city, meeting_date, meeting_time, price, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?);",
          PreparedStatement.RETURN_GENERATED_KEYS);

      statement.setString(1, name);
      statement.setInt(2, typeId);
      statement.setString(3, address);
      statement.setString(4, city);
      statement.setString(5, meetingDate);
      statement.setString(6, meetingTime);
      statement.setInt(7, price);
      statement.setString(8, description);

      int rowsAffected = statement.executeUpdate();
      System.out.println("Rows inserted: " + rowsAffected);

      ResultSet keys = statement.getGeneratedKeys();
      if (keys.next())
      {
        return new Activity(keys.getInt(1), name, type, address, city,
            meetingDate, meetingTime, price, description);
      }
      else
      {
        throw new SQLException("No keys generated");
      }
    }
    catch (SQLException e)
    {
      System.err.println("SQL Insert Error: " + e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

  @Override public Activity readById(int id)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT * FROM activity WHERE id = ?;");
      statement.setInt(1, id);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next())
      {
        String name = resultSet.getString("name");
        String type = resultSet.getString("type");
        String address = resultSet.getString("address");
        String city = resultSet.getString("city");
        String meetingDate = resultSet.getString("meeting_date");
        String meetingTime = resultSet.getString("meeting_time");
        int price = resultSet.getInt("price");
        String description = resultSet.getString("description");
        return new Activity(id, name, type, address, city, meetingDate,
            meetingTime, price, description);
      }
      else
      {
        throw new SQLException("No event found with id: " + id);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  public List<Activity> readByName(String name)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT * FROM activity WHERE name = ?;");
      statement.setString(1, "%" + name + "%");
      ResultSet resultSet = statement.executeQuery();
      ArrayList<Activity> result = new ArrayList<>();
      while (resultSet.next())
      {
        int id = resultSet.getInt("id");
        String type = resultSet.getString("type");
        String address = resultSet.getString("address");
        String city = resultSet.getString("city");
        String meetingDate = resultSet.getString("meeting_date");
        String meetingTime = resultSet.getString("meeting_time");
        int price = resultSet.getInt("price");
        String description = resultSet.getString("description");
        Activity activity = new Activity(id, name, type, address, city,
            meetingDate, meetingTime, price, description);
        result.add(activity);
      }
      return result;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  public List<Activity> readAll()
  {
    List<Activity> result = new ArrayList<>();
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "SELECT * FROM activity;");
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next())
      {
        Activity activity = new Activity(resultSet.getInt("id"),
            resultSet.getString("name"), resultSet.getString("type"),
            resultSet.getString("address"), resultSet.getString("city"),
            resultSet.getString("meeting_date"),
            resultSet.getString("meeting_time"), resultSet.getInt("price"),
            resultSet.getString("description"));
        result.add(activity);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return result;
  }

  public void update(Activity activity)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "UPDATE activity SET name = ?, type = ?, address = ?, city = ?, meeting_date = ?, meeting_time = ?, price = ?, description = ? WHERE id = ?;");
      statement.setString(1, activity.getName());
      statement.setString(2, activity.getType());
      statement.setString(3, activity.getAddress());
      statement.setString(4, activity.getCity());
      statement.setInt(5,
          Integer.parseInt(String.valueOf(activity.getPrice())));
      statement.setString(6, activity.getMeetingDate());
      statement.setString(7, activity.getMeetingTime());
      statement.setString(8, activity.getDescription());
      statement.setInt(9, activity.getId());
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }

  public void delete(Activity activity)
  {
    try (Connection connection = getConnection())
    {
      PreparedStatement statement = connection.prepareStatement(
          "DELETE FROM activity WHERE id = ?;");
      statement.setInt(1, activity.getId());
      statement.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
}
