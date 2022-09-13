package org.alexcawl.testapp.services;

import org.alexcawl.testapp.dao.SystemItemRepository;
import org.alexcawl.testapp.entities.SystemItem;
import org.alexcawl.testapp.model.GraphAuxiliaryContainer;
import org.alexcawl.testapp.model.ItemNotFoundException;
import org.alexcawl.testapp.model.SystemGraphOut;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class GetService {
    @Resource
    private SystemItemRepository systemItemRepository;

    private GraphAuxiliaryContainer dfs(SystemGraphOut graph) {
        List <SystemItem> childrenNodes = systemItemRepository.findAllByParentId(graph.getId());
        for (SystemItem currentItem : childrenNodes) {
            // превращаем текущего ребенка в вершину нового графа
            SystemGraphOut currentNode = new SystemGraphOut(currentItem);
            // находим параметры вершины ниже
            GraphAuxiliaryContainer currentNodeParams = dfs(currentNode);
            System.out.println(currentNodeParams.toString());

            // обновляем параметры этой вершины
            graph.merge(currentNodeParams);
            graph.addChildrenItem(currentNode);
        }

        return new GraphAuxiliaryContainer(graph);
    }


    /*private GraphAuxiliaryContainer dfs(SystemItem graphPeak, SystemGraphOut graph) {
        List <SystemItem> childrenNodes = systemItemRepository.findAllByParentId(graphPeak.getId());
        for (SystemItem currentItem : childrenNodes) {
            // превращаем текущего ребенка в вершину нового графа
            SystemGraphOut currentNode = new SystemGraphOut(currentItem);
            // находим параметры вершины ниже
            GraphAuxiliaryContainer currentNodeParams = dfs(currentItem, currentNode);
            System.out.println(currentNodeParams.toString());

            // обновляем параметры этой вершины
            graph.merge(currentNodeParams);
            graph.addChildrenItem(currentNode);
        }

        return new GraphAuxiliaryContainer(graphPeak);
    }*/



    /*private SystemGraphOut deepFirstSearch(SystemItem startPoint, SystemGraphOut resultItem) {
        List<SystemItem> newNodesToCheck = systemItemRepository.findAllByParentId(startPoint.getId());
        for (SystemItem systemItem : newNodesToCheck) {
            SystemGraphOut currentNode = deepFirstSearch(systemItem, new SystemGraphOut(systemItem));
            resultItem.addChildrenItem(currentNode);
        }

        return resultItem;
    }

    private Long sizeSummary(SystemGraphOut graph) {
        Long currentNodeSize = 0L;
        for (SystemGraphOut child: graph.getChildren()) {
            if (child.getType().equals(SystemItemType.FOLDER)) {
                currentNodeSize += sizeSummary(child);
            } else {
                currentNodeSize += child.getSize();
            }
        }
        graph.setSize(currentNodeSize);
        return currentNodeSize;
    }*/

    public SystemGraphOut get(String id) throws ItemNotFoundException {
        Optional<SystemItem> myItemObject = systemItemRepository.findById(id);
        if (myItemObject.isEmpty()) {
            throw new ItemNotFoundException();
        } else {
            SystemGraphOut resultPeak = new SystemGraphOut(myItemObject.get());
            dfs(resultPeak);
            return resultPeak;
            /*SystemItem graphPeak = myItemObject.get();
            SystemGraphOut result = new SystemGraphOut(graphPeak);
            // достаем первую вершину
            // добавляем остальное
            SystemGraphOut resultGraph = deepFirstSearch(myItemObject.get(), beginNode);
            // считаем размеры
            resultGraph.setSize(sizeSummary(resultGraph));
            return resultGraph;*/
        }
    }
}
