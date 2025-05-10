package dtos.search;

import java.io.Serializable;

public record ActivityDTO(int id, String name, String type, String address,
                          String city, String meetingDate, String meetingTime,
                          String description) implements Serializable
{
  @Override public String toString()
  {
    return "ActivityDTO{" + "id=" + id + ", name='" + name + '\'' + ", type='"
        + type + '\'' + ", address='" + address + '\'' + ", city='" + city
        + '\'' + ", meetingDate='" + meetingDate + '\'' + ", meetingTime='"
        + meetingTime + '\'' + ", description='" + description + '\'' + '}';
  }

}