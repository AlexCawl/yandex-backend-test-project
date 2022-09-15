package org.alexcawl.testapp.model;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.alexcawl.testapp.entities.SystemItem;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class GraphOfItems {
  @JsonProperty("id")
  private String id;

  @JsonProperty("url")
  private String url;

  @JsonProperty("date")
  private String date;

  @JsonProperty("parentId")
  private String parentId;

  @JsonProperty("type")
  private SystemItemType type;

  @JsonProperty("size")
  private Long size;

  @JsonProperty("children")
  private List<GraphOfItems> children = null;

  public GraphOfItems(SystemItem systemItem) {
    this.id = systemItem.getId();
    this.url = systemItem.getUrl();
    this.date = systemItem.getDate().format(DateTimeFormatter.ISO_INSTANT);
    this.parentId = systemItem.getParentId();
    this.type = systemItem.getType();
    this.size = systemItem.getSize();
    if (this.type == SystemItemType.FOLDER) {
      this.children = new ArrayList<>();
    }
  }

  public void addChildrenItem(GraphOfItems childrenItem) {
    if (this.children == null) {
      this.children = new ArrayList<>();
    }
    this.children.add(childrenItem);
  }

  public GraphOfItems merge(NodeResponse container) {
    if (this.getSize() == null) {
      this.size = container.getSize();
    } else {
      this.size += container.getSize();
    }
    if (OffsetDateTime.parse(this.date).compareTo(container.getDate()) < 0) {
      this.date = DateTimeFormatter.ISO_INSTANT.format(container.getDate());
    }
    return this;
  }
}