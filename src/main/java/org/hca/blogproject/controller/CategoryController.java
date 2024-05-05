package org.hca.blogproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.request.CategoryRequestDto;
import org.hca.blogproject.dto.response.CategoryResponseDto;
import org.hca.blogproject.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static org.hca.blogproject.constant.EndPoints.*;

@RestController
@RequestMapping(ROOT + CATEGORY)
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    //save
    @PostMapping(CREATE)
    public ResponseEntity<CategoryResponseDto> save(@RequestBody CategoryRequestDto dto) {
        return ResponseEntity.ok(categoryService.saveDto(dto));
    }

    //findAll
    @GetMapping(GET_ALL)
    public ResponseEntity<List<CategoryResponseDto>> findAll() {
        return ResponseEntity.ok(categoryService.findAllDto());
    }

    //findById
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<CategoryResponseDto> findDtoById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findDtoById(id));
    }

    //update
    @PutMapping(UPDATE)
    public ResponseEntity<CategoryResponseDto> update(@PathVariable Long id, @Valid @RequestBody CategoryRequestDto request){
        return ResponseEntity.ok(categoryService.updateDto(id,request));
    }

    //delete and remove from existence
//    @DeleteMapping(DELETE)
//    public ResponseEntity<CategoryResponseDto> deleteDto(@PathVariable Long id){
//        return ResponseEntity.ok(categoryService.deleteDto(id));
//    }

    //delete but continue to store in database
    @DeleteMapping(DELETE)
    public ResponseEntity<CategoryResponseDto> setToDeletedDto(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.setToDeletedDto(id));
    }
    @GetMapping(FIND_BY_NAME)
    public ResponseEntity<CategoryResponseDto> findByName(@RequestParam String Name) {
        return ResponseEntity.ok(categoryService.findCategoryByNameReturnDto(Name));
    }

}
