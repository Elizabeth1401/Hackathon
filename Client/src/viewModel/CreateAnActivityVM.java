package viewModel;

import dtos.create.CreateActivityRequest;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import networking.create.CreateClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreateAnActivityVM
{
  private final CreateClient createService;
  private final ObservableList<String> cities = FXCollections.observableArrayList(
      "Copenhagen", "Aarhus", "Odense", "Aalborg", "Esbjerg", "Randers",
      "Frederiksberg", "Roskilde", "Vejle", "Kolding");
  private final ObservableList<String> types = FXCollections.observableArrayList(
      "Music", "Sports", "Technology", "Art", "Literature", "Gaming", "Cooking",
      "Fitness", "Film", "Nature");

  private final ObservableList<String> times = FXCollections.observableArrayList(
      "12:30", "13:00", "14:00", "14:30", "15:00", "16:00", "17:00");

  private final IntegerProperty id = new SimpleIntegerProperty();
  private final IntegerProperty price = new SimpleIntegerProperty(0);
  private final StringProperty description = new SimpleStringProperty("");
  private final StringProperty name = new SimpleStringProperty("");
  private final StringProperty address = new SimpleStringProperty("");
  private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
  private final StringProperty time = new SimpleStringProperty("");
  private final StringProperty city = new SimpleStringProperty("");
  private final StringProperty type = new SimpleStringProperty("");

  private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd");

  public CreateAnActivityVM(CreateClient createService)
  {
    this.createService = createService;
  }

  public IntegerProperty priceProperty()
  {
    return price;
  }

  public StringProperty descriptionProperty()
  {
    return description;
  }

  public int getPrice()
  {
    return price.get();
  }

  public void setPrice(int value)
  {
    price.set(value);
  }

  public String getDescription()
  {
    return description.get();
  }

  public ObservableList<String> getCities()
  {
    return cities;
  }

  public ObservableList<String> getTypes()
  {
    return types;
  }

  public StringProperty nameProperty()
  {
    return name;
  }

  public StringProperty addressProperty()
  {
    return address;
  }

  public ObservableList<String> getTimes()
  {
    return times;
  }

  public ObjectProperty<LocalDate> dateProperty()
  {
    return date;
  }

  public StringProperty timeProperty()
  {
    return time;
  }

  public StringProperty cityProperty()
  {
    return city;
  }

  public StringProperty typeProperty()
  {
    return type;
  }

  public String getName()
  {
    return name.get();
  }

  public String getAddress()
  {
    return address.get();
  }

  public String getDateAsString()
  {
    return date.get() != null ? date.get().format(dateFormatter) : "";
  }

  public LocalDate getDate()
  {
    return date.get();
  }

  public String getTime()
  {
    return time.get();
  }

  public String getCity()
  {
    return city.get();
  }

  public String getType()
  {
    return type.get();
  }

  public String getPriceAsString()
  {
    return String.valueOf(price.get());
  }

  public void save()
  {
    System.out.println("Saving");
    System.out.println("Name: " + getName());
    System.out.println("Address: " + getAddress());
    System.out.println("Date: " + getDateAsString());
    System.out.println("Time: " + getTime());
    System.out.println("City: " + getCity());
    System.out.println("Type: " + getType());
    System.out.println("Price: " + getPriceAsString());
    System.out.println("Description: " + getDescription());

    try
    {
      createService.createActivity(
          new CreateActivityRequest(id.get(), name.get(), address.get(),
              type.get(), city.get(), getDateAsString(), time.get(),
              price.get(), description.get()));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}