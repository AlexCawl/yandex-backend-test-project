package org.alexcawl.testapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SystemItemImportRequest {
    @JsonProperty("items")
    private final List<SystemItemImport> items = new ArrayList<>();

    @JsonProperty("updateDate")
    private String updateDate = null;
}
