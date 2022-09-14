package org.alexcawl.testapp.services;

import lombok.extern.slf4j.Slf4j;
import org.alexcawl.testapp.dao.SystemItemRepository;
import org.alexcawl.testapp.entities.SystemItem;
import org.alexcawl.testapp.model.NodeResponse;
import org.alexcawl.testapp.model.GraphOfItems;
import org.alexcawl.testapp.model.ItemNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class GetService {
    @Resource
    private SystemItemRepository systemItemRepository;

    private NodeResponse deepFirstGet(GraphOfItems graph) {
        List <SystemItem> childrenNodes = systemItemRepository.findAllByParentId(graph.getId());

        for (SystemItem currentItem : childrenNodes) {
            GraphOfItems currentNode = new GraphOfItems(currentItem);
            NodeResponse currentNodeParams = deepFirstGet(currentNode);

            graph.merge(currentNodeParams);
            graph.addChildrenItem(currentNode);
        }

        return new NodeResponse(graph);
    }

    public GraphOfItems get(String id) throws ItemNotFoundException {
        Optional<SystemItem> myItemObject = systemItemRepository.findById(id);
        if (myItemObject.isEmpty()) {
            throw new ItemNotFoundException();
        } else {
            GraphOfItems resultPeak = new GraphOfItems(myItemObject.get());
            deepFirstGet(resultPeak);
            return resultPeak;
        }
    }
}
