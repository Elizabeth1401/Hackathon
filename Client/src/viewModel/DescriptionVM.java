package viewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import networking.description.DescriptionClient;

public class DescriptionVM
{
  private final DescriptionClient descriptionService;

  private final StringProperty name = new SimpleStringProperty("");
  private final StringProperty description = new SimpleStringProperty("");

  public DescriptionVM(DescriptionClient descriptionService)
  {
    this.descriptionService = descriptionService;
  }
  public StringProperty nameProperty()
  {
    return name;
  }

  public StringProperty descriptionProperty()
  {
    return description;
  }

  public String getName()
  {
    return name.get();
  }

  public String getDescription()
  {
    return description.get();
  }

}
