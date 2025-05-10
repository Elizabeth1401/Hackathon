package services.search;

import model.entities.Activity;

import java.util.List;

public interface SearchService
{
  List<Activity> search(String city, String type);
}
