package org.hca.blogproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.request.PostRequestDto;
import org.hca.blogproject.dto.response.PostResponseDto;
import org.hca.blogproject.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hca.blogproject.constant.EndPoints.*;

@RestController
@RequestMapping(ROOT + POST)
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping
    public ResponseEntity<PostResponseDto> save(@RequestBody PostRequestDto dto) {
        return ResponseEntity.ok(postService.saveDto(dto));
    }

    //findAll
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findAll() {
        return ResponseEntity.ok(postService.findAllDto());
    }

    //findById
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findDtoById(id));
    }

    //update
    @PutMapping(UPDATE)
    public ResponseEntity<PostResponseDto> update(@PathVariable Long id, @Valid @RequestBody PostRequestDto request){
        return ResponseEntity.ok(postService.updateDto(id,request));
    }

    //delete and remove from existence
    @DeleteMapping(DELETE)
    public ResponseEntity<PostResponseDto> deleteDto(@PathVariable Long id){
        return ResponseEntity.ok(postService.deleteDto(id));
    }

    //delete but continue to store in database
    @PutMapping(SET_TO_DELETED)
    public ResponseEntity<PostResponseDto> setToDeletedDto(@PathVariable Long id){
        return ResponseEntity.ok(postService.setToDeletedDto(id));
    }
}
