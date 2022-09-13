package org.alexcawl.testapp.resources;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.alexcawl.testapp.model.*;
import org.alexcawl.testapp.services.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;


@Api(tags = "Application controller")
@RestController
@Slf4j
public class ApplicationController {
    @Resource
    private ApplicationService applicationService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вставка или обновление прошли успешно."),
            @ApiResponse(responseCode = "400", description = "Невалидная схема документа или входные данные не верны.", content = @Content(mediaType = "application/json"))
    })
    @PostMapping(value = "/imports",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public ResponseEntity<?> importsRequest(@RequestBody SystemItemImportRequest body) {
        log.info("importsRequest");
        try {
            applicationService.postOperation(body);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception error) { // VALIDATION EXCEPTION
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    "Validation Failed"),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Удаление прошло успешно."),
            @ApiResponse(responseCode = "400", description = "Невалидная схема документа или входные данные не верны.", content = @Content(mediaType = "application/json", schema = @Schema(allOf = AppError.class))),
            @ApiResponse(responseCode = "404", description = "Элемент не найден.", content = @Content(mediaType = "application/json")) })
    @DeleteMapping(value = "/delete/{id}",
            produces = {"application/json"}
    )
    public ResponseEntity<?> deleteRequest(@PathVariable(name = "id") String id, @RequestParam(name = "date") String date) {
        try {
            applicationService.deleteOperation(id, date);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomValidationException error) { // VALIDATION EXCEPTION
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    "Validation Failed"),
                    HttpStatus.BAD_REQUEST
            );
        } catch (ItemNotFoundException error) { // ITEM NOT FOUND EXCEPTION
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                    "Item not found"),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация об элементе.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Невалидная схема документа или входные данные не верны.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Элемент не найден.", content = @Content(mediaType = "application/json"))
    })
    @GetMapping(value = "/nodes/{id}",
            produces = {"application/json"}
    )
    public ResponseEntity<?> nodesRequest(@PathVariable("id") String id) {
        log.info("nodesRequest");
        try {
            SystemGraphOut out = applicationService.getOperation(id);
            return new ResponseEntity<>(out,
                    HttpStatus.OK
            );
        } catch (ConstraintViolationException error) { // VALIDATION EXCEPTION
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    "Validation Failed"),
                    HttpStatus.BAD_REQUEST
            );
        } catch (ItemNotFoundException error) { // ITEM NOT FOUND EXCEPTION
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                    "Item not found"),
                    HttpStatus.NOT_FOUND
            );
        }
    }
}
