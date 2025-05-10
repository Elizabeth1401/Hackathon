package startup;

import javafx.application.Application;
import javafx.stage.Stage;

public class RunClient extends Application
{
  @Override public void start(Stage stage) throws Exception
  {
    ViewHandler viewHandler = new ViewHandler(stage);
    viewHandler.start();
  }

  public static void main(String[] args)
  {
    RunClient.launch(args);
  }
}