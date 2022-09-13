package org.alexcawl.testapp.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Тип элемента - папка или файл
 */
public enum SystemItemType {
  FILE("FILE"),
    FOLDER("FOLDER");

  private String value;

  SystemItemType(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static SystemItemType fromValue(String text) {
    for (SystemItemType b : SystemItemType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
