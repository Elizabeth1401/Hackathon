package dtos.search;

import java.io.Serializable;

public record SearchRequest(String city, String type) implements Serializable
{
  @Override public String toString()
  {
    return "SearchRequest{" + "city='" + city + '\'' + ", type='" + type + '\''
        + '}';
  }
}