package persistance.search;

import model.entities.Activity;

import java.util.List;

public interface SearchDAO
{
  List<Activity> search(String city, String type);
}