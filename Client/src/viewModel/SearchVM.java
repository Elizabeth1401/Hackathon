package viewModel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.entities.Activity;
import networking.search.SearchClient;
import dtos.search.SearchRequest;

import java.util.List;

public class SearchVM
{
  private final SearchClient searchClient;

  public SearchVM(SearchClient searchClient)
  {
    this.searchClient = searchClient;

    selectedCity.addListener((obs, oldVal, newVal) -> applyFilters());
    selectedType.addListener((obs, oldVal, newVal) -> applyFilters());

    loadActivities();
  }

  private final ObservableList<String> cities = FXCollections.observableArrayList("Copenhagen", "Aarhus", "Odense", "Aalborg", "Esbjerg", "Randers",
      "Frederiksberg", "Roskilde", "Vejle", "Kolding");
  private final ObservableList<String> types = FXCollections.observableArrayList("Music", "Sports", "Technology", "Art", "Literature", "Gaming",
      "Cooking", "Fitness", "Film", "Nature");

  private final StringProperty selectedCity = new SimpleStringProperty();
  private final StringProperty selectedType = new SimpleStringProperty();

  private final ObservableList<Activity> allActivities = FXCollections.observableArrayList();
  private final ObservableList<Activity> filteredActivities = FXCollections.observableArrayList();

  private final ObjectProperty<Activity> selectedActivity = new SimpleObjectProperty<>();

  public void loadActivities()
  {
    try
    {
      SearchRequest request = new SearchRequest("", "");
      System.out.println("Sending search request: " + request);
      List<Activity> activities = searchClient.search(request);
      System.out.println("Received activities: " + activities.size());
      allActivities.setAll(activities);
      applyFilters();
    }
    catch (Exception e)
    {
      System.err.println("Failed to load activities: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private void applyFilters()
  {
    filteredActivities.setAll(allActivities.filtered(activity -> {
      boolean cityMatch = selectedCity.get() == null || selectedCity.get()
          .equals(activity.getCity());
      boolean typeMatch = selectedType.get() == null || selectedType.get()
          .equals(activity.getType());
      return cityMatch && typeMatch;
    }));
  }

  public void setSelectedActivity(Activity activity)
  {
    selectedActivity.set(activity);
  }

  public Activity getSelectedActivity()
  {
    return selectedActivity.get();
  }

  public ObjectProperty<Activity> selectedActivityProperty()
  {
    return selectedActivity;
  }

  public ObservableList<String> getCities()
  {
    return cities;
  }

  public ObservableList<String> getTypes()
  {
    return types;
  }

  public StringProperty selectedCityProperty()
  {
    return selectedCity;
  }

  public StringProperty selectedTypeProperty()
  {
    return selectedType;
  }

  public ObservableList<Activity> getFilteredActivities()
  {
    return filteredActivities;
  }
}
