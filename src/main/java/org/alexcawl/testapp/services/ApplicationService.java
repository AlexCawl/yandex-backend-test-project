package org.alexcawl.testapp.services;

import lombok.extern.slf4j.Slf4j;
import org.alexcawl.testapp.entities.SystemItem;
import org.alexcawl.testapp.model.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ApplicationService {
    @Resource
    private DeleteService deleteService;
    @Resource
    private PostService postService;
    @Resource
    private GetService getService;

    public List <SystemItem> postOperationValidation(SystemItemImportRequest body) throws CustomValidationException {
        List <SystemItem> resultList = new ArrayList<>();

        try {
            OffsetDateTime validTime = OffsetDateTime.parse(body.getUpdateDate());
            for (SystemItemImport item: body.getItems()) {
                if (item.getType() == SystemItemType.FOLDER) {
                    if (item.getUrl() != null || item.getSize() != null) {
                        throw new CustomValidationException();
                    }
                    resultList.add(new SystemItem(item, validTime));
                    continue;
                }

                if (item.getType() == SystemItemType.FILE) {
                    if (item.getUrl() == null || item.getSize() == null) {
                        throw new CustomValidationException();
                    }
                    if (item.getUrl().chars().filter(ch -> ch == '/').count() > 255) {
                        throw new CustomValidationException();
                    }
                    resultList.add(new SystemItem(item, validTime));
                }
            }
            return resultList;
        } catch (Exception e) {
            throw new CustomValidationException();
        }
    }

    public void postOperation(SystemItemImportRequest body) throws CustomValidationException {
        List <SystemItem> resultList = postOperationValidation(body);
        postService.post(resultList);
    }

    public void deleteOperation(String id, String date) throws CustomValidationException, ItemNotFoundException {
        try {
            OffsetDateTime dateTime = OffsetDateTime.parse(date);
            deleteService.delete(id, dateTime);
        } catch (Exception e) {
            throw new CustomValidationException();
        }
    }

    public SystemGraphOut getOperation(String id) throws ConstraintViolationException, ItemNotFoundException {
        return getService.get(id);
    }
}
