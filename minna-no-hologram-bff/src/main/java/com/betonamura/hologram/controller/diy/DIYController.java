package com.betonamura.hologram.controller.diy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.betonamura.hologram.config.ApiConfig;
import com.betonamura.hologram.controller.ErrorResponse;
import com.betonamura.hologram.controller.Pagination;
import com.betonamura.hologram.domain.diy.DiyCard;
import com.betonamura.hologram.domain.diy.DiyDetail;
import com.betonamura.hologram.repository.diy.DiyRepository;

import jakarta.validation.Valid;

@RestController
public class DIYController {
    private final DiyRepository diyRepository;

    @Autowired
    public DIYController(final DiyRepository diyRepository) {
        this.diyRepository = diyRepository;
    }

    /*
     * Search for DIYs with pagination.
     * 
     * @param request DIYsRequest containing pagination and search parameters.
     * 
     * @return ResponseEntity containing DIYsResponse with the list of DIYs and
     * pagination info,
     */
    @GetMapping(ApiConfig.DIY_SEARCH)
    public ResponseEntity<?> search(@Valid @ModelAttribute DIYsRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .findFirst().orElse("Validation failed");
            ErrorResponse errorResponse = new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        final List<DiyCard> diys = diyRepository.search(request.getOffset(), request.getLimit());
        final Pagination pagination = new Pagination(request.getOffset(), request.getLimit(),
                diys.size(), 1);
        final DIYsResponse response = new DIYsResponse(diys, pagination);

        return ResponseEntity.ok(response);
    }

    @GetMapping(ApiConfig.DIY_DETAIL)
    public ResponseEntity<?> getDIY(@Valid @ModelAttribute DIYDetailRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .findFirst().orElse("Validation failed");
            ErrorResponse errorResponse = new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        final DiyDetail detail = diyRepository.getDiyDetail(request.getSlugId());
        if (detail == null) {
            ErrorResponse errorResponse = new ErrorResponse(String.valueOf(HttpStatus.NOT_FOUND.value()),
                    "DIY article not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(detail);
    }
}
