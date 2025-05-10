package persistance.create;

import model.entities.Activity;

import java.util.List;

public interface CreateDAO
{
  public Activity create(String name, String type, String address, String city,
      String meetingDate, String meetingTime, int price, String description);

  public Activity readById(int id);

  public List<Activity> readByName(String name);

  public void update(Activity activity);

  public void delete(Activity activity);
}
