package networking;

import dtos.Request;
import dtos.Response;
import dtos.error.ErrorResponse;
import model.exceptions.NotFoundException;
import model.exceptions.ValidationException;
import networking.exceptions.InvalidActionException;
import model.exceptions.ServerFailureException;
import networking.requestHandlers.RequestHandler;
import startup.ServiceProvider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.sql.SQLException;

import utilities.Logger;
import utilities.LogLevel;

public class MainSocketHandler implements Runnable
{

  private final Socket clientSocket;
  private final ServiceProvider serviceProvider;
  private final Logger logger;

  public MainSocketHandler(Socket clientSocket, ServiceProvider serviceProvider)
  {
    this.clientSocket = clientSocket;
    this.serviceProvider = serviceProvider;
    logger = serviceProvider.getLogger();
  }

  @Override public void run()
  {
    try
    {
      ObjectInputStream incomingData = new ObjectInputStream(
          clientSocket.getInputStream());
      ObjectOutputStream outgoingData = new ObjectOutputStream(
          clientSocket.getOutputStream());
      handleRequestWithErrorHandling(incomingData, outgoingData);
    }
    catch (IOException e)
    {
      logger.log(Arrays.toString(e.getStackTrace()), LogLevel.ERROR);
    }
    finally
    {
      try
      {
        clientSocket.close();
      }
      catch (IOException e)
      {
      }
    }
  }

  private void handleRequestWithErrorHandling(ObjectInputStream incomingData,
      ObjectOutputStream outgoingData) throws IOException
  {
    try
    {
      handleRequest(incomingData, outgoingData);
    }
    catch (NotFoundException | InvalidActionException | ValidationException e)
    {
      logger.log(e.getMessage(), LogLevel.INFO);
      ErrorResponse payload = new ErrorResponse(e.getMessage());
      Response error = new Response("ERROR", payload);
      outgoingData.writeObject(error);
    }
    catch (ServerFailureException e)
    {
      logger.log(Arrays.toString(e.getStackTrace()) + "\n" + e.getMessage(),
          LogLevel.ERROR);
      ErrorResponse payload = new ErrorResponse(e.getMessage());
      Response error = new Response("SERVER_FAILURE", payload);
      outgoingData.writeObject(error);
    }
    catch (ClassCastException e)
    {
      logger.log(e.getMessage(), LogLevel.INFO);
      ErrorResponse payload = new ErrorResponse("Invalid request");
      Response error = new Response("ERROR", payload);
      outgoingData.writeObject(error);
    }
    catch (Exception e)
    {
      logger.log(Arrays.toString(e.getStackTrace()), LogLevel.ERROR);
      ErrorResponse payload = new ErrorResponse(e.getMessage());
      Response error = new Response("SERVER_FAILURE", payload);
      outgoingData.writeObject(error);
    }
  }

  private void handleRequest(ObjectInputStream incomingData,
      ObjectOutputStream outgoingData)
      throws IOException, ClassNotFoundException, SQLException
  {
    Request request = (Request) incomingData.readObject();
    logger.log("Incoming request: " + request.handler() + "/" + request.action()
        + ". Body: " + request.payload(), LogLevel.INFO);

    RequestHandler handler = switch (request.handler())
    {
      case "create" -> serviceProvider.getCreateRequestHandler();
      case "search" -> serviceProvider.getSearchRequestHandler();
      case "description" -> serviceProvider.getDescriptionRequestHandler();
      default -> throw new IllegalStateException(
          "Unexpected value: " + request.handler());
    };

    Object result = handler.handle(request.action(), request.payload());
    Response response = new Response("SUCCESS", result);
    outgoingData.writeObject(response);
  }
}
