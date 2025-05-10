package view.description;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import persistance.search.SearchPostgresDAO;
import services.description.DescriptionService;
import services.description.DescriptionServiceImpl;
import startup.ViewHandler;
import startup.ViewType;
import view.common.Controller;
import viewModel.DescriptionVM;

import java.sql.SQLException;

public class DescriptionViewController implements Controller
{
  private final DescriptionVM viewModel;

  @FXML private Label nameLabel;
  @FXML private TextField textInput;
  @FXML private Button backButton;

  private final DescriptionService descriptionService = new DescriptionServiceImpl(
      SearchPostgresDAO.getInstance());
  
  private String activityName;
  private int activityId;

  public DescriptionViewController(DescriptionVM vm) throws SQLException
  {
    this.viewModel = vm;
  }

  public void setActivityData(String activityName, int activityId)
  {
    this.activityName = activityName;
    this.activityId = activityId;
    
    if(nameLabel != null)
    {
      updateUI();
    }
  }

  @FXML public void initialize()
  {
    nameLabel.textProperty().bindBidirectional(viewModel.nameProperty());
    textInput.textProperty().bindBidirectional(viewModel.descriptionProperty());
    backButton.setOnAction(e -> onBackButton());

    if(activityName != null)
    {
      updateUI();
    }
  }

  private void updateUI()
  {
    nameLabel.setText(activityName);
    String description = descriptionService.getActivityDescription(activityId);
    textInput.setText(description != null ? description : "No description available.");

    viewModel.nameProperty().set(activityName);
    viewModel.descriptionProperty().set(description != null ? description : "No description available.");
  }

  private void onBackButton()
  {
    ViewHandler.showView(ViewType.SEARCH);
  }
}
