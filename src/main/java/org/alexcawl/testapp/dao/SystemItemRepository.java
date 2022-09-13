package org.alexcawl.testapp.dao;

import org.alexcawl.testapp.entities.SystemItem;
import org.alexcawl.testapp.model.SystemItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface SystemItemRepository extends JpaRepository<SystemItem, String> {
    List<SystemItem> findAllByParentId(String parentID);
    boolean existsByIdAndTypeIsNot(String parentId, @NotNull SystemItemType type);
}
