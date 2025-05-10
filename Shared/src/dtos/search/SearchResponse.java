package dtos.search;

import java.io.Serializable;
import java.util.List;

public record SearchResponse(List<ActivityDTO> activities)
    implements Serializable
{
  @Override public String toString()
  {
    return "SearchResponse{" + "activities=" + activities + '}';
  }
}