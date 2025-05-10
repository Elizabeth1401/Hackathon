package startup;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import networking.description.DescriptionClient;
import networking.description.SocketDescriptionClient;
import networking.search.SearchClient;
import networking.search.SocketSearchClient;
import networking.create.CreateClient;
import networking.create.SocketCreateClient;
import view.create.CreateAnActivityViewController;
import view.description.DescriptionViewController;
import view.search.SearchViewController;
import viewModel.CreateAnActivityVM;
import viewModel.DescriptionVM;
import viewModel.SearchVM;
import view.common.Controller;

import java.io.IOException;
import java.sql.SQLException;

public class ViewHandler
{
  private static Stage stage;

  private static String tempActivityName;
  private static int tempActivityId;
  private static boolean hasActivityData = false;

  public ViewHandler(Stage stage)
  {
    this.stage = stage;
  }

  public void start()
  {
    showView(ViewType.SEARCH);
    //showView(ViewType.CREATE);
    stage.show();
  }

  public static void setActivityData(String activityName, int activityId)
  {
    tempActivityName = activityName;
    tempActivityId = activityId;
    hasActivityData = true;
  }

  public static void showView(ViewType viewToShow)
  {
    try
    {
      switch (viewToShow)
      {
        case SEARCH -> openSearchView();
        case CREATE -> openCreateView();
        case DESCRIPTION -> openDescriptionView();
        default -> throw new RuntimeException("View not found.");
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }
  }

  public static void openSearchView() throws IOException
  {
    SearchClient client = new SocketSearchClient();
    SearchVM vm = new SearchVM(client);
    SearchViewController controller = new SearchViewController(vm);
    String viewTitle = "Search";
    String viewSubPath = "search/SearchView.fxml";
    openView(viewTitle, viewSubPath, controller);
  }

  public static void openCreateView() throws IOException
  {
    CreateClient client = new SocketCreateClient();
    CreateAnActivityVM vm = new CreateAnActivityVM(client);
    CreateAnActivityViewController controller = new CreateAnActivityViewController(
        vm);
    String viewTitle = "Create";
    String viewSubPath = "create/CreateAnActivityView.fxml";
    openView(viewTitle, viewSubPath, controller);
  }

  public static void showDescriptionView(String activityName, int activityId) {
    setActivityData(activityName, activityId);
    showView(ViewType.DESCRIPTION);
  }

  public static void openDescriptionView() throws IOException, SQLException
  {
    DescriptionClient client = new SocketDescriptionClient();
    DescriptionVM vm = new DescriptionVM(client);
    DescriptionViewController controller = new DescriptionViewController(vm);

    if (hasActivityData) {
      controller.setActivityData(tempActivityName, tempActivityId);
      // Clear the flag after use
      hasActivityData = false;
    }

    String viewTitle = "Description";
    String viewSubPath = "description/DescriptionView.fxml";
    openView(viewTitle, viewSubPath, controller);
  }

  private static void openView(String viewTitle, String viewSubPath,
      Controller controller) throws IOException
  {
    FXMLLoader fxmlLoader = new FXMLLoader(
        ViewHandler.class.getResource("../view/" + viewSubPath));
    fxmlLoader.setControllerFactory(ignore -> controller);

    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle(viewTitle);
    stage.setScene(scene);
  }
}
