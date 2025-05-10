package dtos.create;

import java.io.Serializable;

public record CreateActivityRequest(int id, String name, String address,
                                    String type, String city,
                                    String meetingDate, String meetingTime,
                                    int price, String description)
    implements Serializable
{

  @Override public String toString()
  {
    return "CreateActivityRequest{" + "id=" + id + ", name='" + name + '\''
        + ", address='" + address + '\'' + ", type='" + type + '\'' + ", city='"
        + city + '\'' + ", meetingDate='" + meetingDate + '\''
        + ", meetingTime='" + meetingTime + '\'' + ", price=" + price
        + ", description='" + description + '\'' + '}';
  }
}
