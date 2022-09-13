package org.alexcawl.testapp.services;

import lombok.extern.slf4j.Slf4j;
import org.alexcawl.testapp.dao.SystemItemRepository;
import org.alexcawl.testapp.entities.SystemItem;
import org.alexcawl.testapp.model.CustomValidationException;
import org.alexcawl.testapp.model.SystemItemType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostService {
    @Resource
    private SystemItemRepository systemItemRepository;

    private boolean hasParent(HashMap<String, SystemItem> checkingHashMap, String IdOfParentNode) {
        if (IdOfParentNode == null) {
            return true;
        }

        if (checkingHashMap.containsKey(IdOfParentNode)) {
            return checkingHashMap.get(IdOfParentNode).getType() == SystemItemType.FOLDER;
        }

        return systemItemRepository.existsByIdAndTypeIsNot(IdOfParentNode, SystemItemType.FILE);
    }

    private void parentSynchronising(HashMap<String, SystemItem> checkingHashMap) throws CustomValidationException {
        for (SystemItem items: checkingHashMap.values()) {
            if (!hasParent(checkingHashMap, items.getParentId())) {
                throw new CustomValidationException();
            }
        }
    }

    public void post(List <SystemItem> listOfElementsToInsert) throws CustomValidationException {
        HashMap<String, SystemItem> checkingHashMap = (HashMap<String, SystemItem>) listOfElementsToInsert
                .stream()
                .collect(Collectors.toMap(SystemItem::getId, Function.identity()));

        parentSynchronising(checkingHashMap);
        listOfElementsToInsert.sort(Comparator.comparing(SystemItem::getType).reversed());

        systemItemRepository.saveAll(listOfElementsToInsert);
    }
}
