package org.alexcawl.testapp.entities;

import lombok.*;
import org.alexcawl.testapp.model.GraphAuxiliaryContainer;
import org.alexcawl.testapp.model.SystemItemImport;
import org.alexcawl.testapp.model.SystemItemType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SystemItem {
    @Id
    @NotNull
    private String id;
    private String url;
    private String parentId;
    @NotNull
    private SystemItemType type;
    private Long size;
    private OffsetDateTime date;

    public SystemItem(SystemItemImport systemItemImport, OffsetDateTime date) {
        this.id = systemItemImport.getId();
        this.url = systemItemImport.getUrl();
        this.parentId = systemItemImport.getParentId();
        this.type = systemItemImport.getType();
        this.size = systemItemImport.getSize();
        this.date = date;
    }

    public void update(GraphAuxiliaryContainer container) {
        if (this.size == null) {
            this.size = 0L;
        }
        this.size += container.getSize();

    }

    public void updateDate(OffsetDateTime date) {
        this.date = date;
    }
}
