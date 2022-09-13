package org.alexcawl.testapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SystemItemImport {
    @JsonProperty("id")
    private String id;

    @JsonProperty("url")
    private String url;

    @JsonProperty("parentId")
    private String parentId;

    @JsonProperty("type")
    private SystemItemType type;

    @JsonProperty("size")
    private Long size;

    @Schema(example = "элемент_1_1", required = true, description = "Уникальный идентификатор")
    public String getId() {
        return id;
    }

    @Schema(example = "/file/url1", description = "Ссылка на файл. Для папок поле равно null.")
    public String getUrl() {
        return url;
    }

    @Schema(example = "элемент_1_1", description = "id родительской папки. Может быть равно null")
    public String getParentId() {
        return parentId;
    }

    @Schema(example = "FILE", required = true, description = "Тип элемента: FILE, FOLDER")
    public SystemItemType getType() {
        return type;
    }

    @Schema(example = "85", description = "Целое число, для папок поле должно содержать null.")
    public Long getSize() {
        return size;
    }
}
