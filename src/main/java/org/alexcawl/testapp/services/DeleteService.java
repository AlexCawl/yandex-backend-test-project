package org.alexcawl.testapp.services;

import lombok.extern.slf4j.Slf4j;
import org.alexcawl.testapp.dao.SystemItemRepository;
import org.alexcawl.testapp.entities.SystemItem;
import org.alexcawl.testapp.model.ItemNotFoundException;
import org.alexcawl.testapp.model.SystemItemType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DeleteService {
    @Resource
    private SystemItemRepository systemItemRepository;

    private List <String> deepFirstDelete(SystemItem currentNode) {
        List <String> deleteList = new ArrayList<>();

        if (systemItemRepository.existsById(currentNode.getId())) {
            deleteList.add(currentNode.getId());

            if (currentNode.getType() == SystemItemType.FILE) {
                return deleteList;
            }

            if (currentNode.getType() == SystemItemType.FOLDER) {
                List<SystemItem> newNodesToCheck = systemItemRepository.findAllByParentId(currentNode.getId());

                for (SystemItem newNode: newNodesToCheck) {
                    deleteList.addAll(deepFirstDelete(newNode));
                }

                return deleteList;
            }
        }

        return deleteList;
    }

    public void delete(String id, OffsetDateTime date) throws ItemNotFoundException {
        Optional<SystemItem> myItemObject = systemItemRepository.findById(id);
        if (myItemObject.isEmpty()) {
            throw new ItemNotFoundException();
        } else {
            String nodeParentID = myItemObject.get().getParentId();
            if (nodeParentID != null) {
                SystemItem parentNode = systemItemRepository.findById(nodeParentID).get();
                parentNode.updateDate(date);
                systemItemRepository.save(parentNode);
            }

            List<String> listToDelete = new ArrayList<>(deepFirstDelete(myItemObject.get()));
            systemItemRepository.deleteAllById(listToDelete);
        }
    }
}
