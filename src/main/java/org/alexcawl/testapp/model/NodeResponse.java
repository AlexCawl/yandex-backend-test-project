package org.alexcawl.testapp.model;

import lombok.*;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class NodeResponse {
    private Long size = 0L;
    private OffsetDateTime date;

    public NodeResponse(GraphOfItems node) {
        if (node.getSize() != null) {
            this.size = node.getSize();
        }
        this.date = OffsetDateTime.parse(node.getDate());
    }
}
