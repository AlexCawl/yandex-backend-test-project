package org.alexcawl.testapp.model;

import lombok.*;
import org.alexcawl.testapp.entities.SystemItem;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class GraphAuxiliaryContainer {
    private Long size = 0L;
    private OffsetDateTime date;

    public GraphAuxiliaryContainer(SystemGraphOut node) {
        if (node.getSize() != null) {
            this.size = node.getSize();
        }
        this.date = OffsetDateTime.parse(node.getDate());
    }
}
