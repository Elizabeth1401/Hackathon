package services.create;

import dtos.create.CreateActivityRequest;
import model.entities.Activity;
import persistance.create.CreateDAO;

import java.sql.SQLException;

public class CreateServiceImpl implements CreateService
{
  private final CreateDAO createDao;

  public CreateServiceImpl(CreateDAO createDao)
  {
    this.createDao = createDao;
  }

  public void create(CreateActivityRequest request) throws SQLException
  {
    Activity activity = new Activity(request.id(), request.name(),
        request.type(), request.address(), request.city(),
        request.meetingDate(), request.meetingTime(), request.price(),
        request.description());

    createDao.create(activity.getName(), activity.getType(),
        activity.getAddress(), activity.getCity(), activity.getMeetingDate(),
        activity.getMeetingTime(), activity.getPrice(),
        activity.getDescription());

  }
}
