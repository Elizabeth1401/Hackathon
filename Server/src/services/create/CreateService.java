package services.create;

import dtos.create.CreateActivityRequest;

import java.sql.SQLException;

public interface CreateService
{
  void create(CreateActivityRequest request) throws SQLException;
}
