package view.search;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Activity;
import startup.ViewHandler;
import startup.ViewType;
import viewModel.SearchVM;
import view.common.Controller;

public class SearchViewController implements Controller
{
  @FXML private ComboBox<String> cityComboBox;
  @FXML private ComboBox<String> typeComboBox;
  @FXML private Button createAnActivityButton;
  @FXML private TableView<Activity> activityTable;
  @FXML private TableColumn<Activity, String> cityColumn;
  @FXML private TableColumn<Activity, String> nameColumn;
  @FXML private TableColumn<Activity, String> typeColumn;
  @FXML private TableColumn<Activity, String> addressColumn;
  @FXML private TableColumn<Activity, String> dateColumn;
  @FXML private TableColumn<Activity, String> timeColumn;
  @FXML private TableColumn<Activity, String> priceColumn;
  @FXML private Button continueButton;
  @FXML private Button backButton;

  private final SearchVM viewModel;

  public SearchViewController(SearchVM vm)
  {
    this.viewModel = vm;
  }

  @FXML public void initialize()
  {
    cityComboBox.setItems(viewModel.getCities());
    typeComboBox.setItems(viewModel.getTypes());

    cityComboBox.valueProperty()
        .bindBidirectional(viewModel.selectedCityProperty());
    typeComboBox.valueProperty()
        .bindBidirectional(viewModel.selectedTypeProperty());

    activityTable.setItems(viewModel.getFilteredActivities());

    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("meetingDate"));
    timeColumn.setCellValueFactory(new PropertyValueFactory<>("meetingTime"));
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    createAnActivityButton.setOnAction(e -> onCreateAnActivityButton());

    continueButton.setOnAction(e -> onContinueButton());

    backButton.setOnAction(e -> onBackButton());
  }

  private void onBackButton()
  {
    ViewHandler.showView(ViewType.SEARCH);
  }

  private void onCreateAnActivityButton()
  {
    ViewHandler.showView(ViewType.CREATE);
  }

  private void onContinueButton()
  {
    Activity selected = activityTable.getSelectionModel().getSelectedItem();
    if (selected == null)
    {
      showAlert("No Selection", "Please select an activity first.");
      return;
    }
    viewModel.setSelectedActivity(selected);
    ViewHandler.setActivityData(selected.getName(), selected.getId());
    ViewHandler.showView(ViewType.DESCRIPTION);
  }

  private void showAlert(String title, String message)
  {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}
