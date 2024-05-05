package org.hca.blogproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.request.CommentRequestDto;
import org.hca.blogproject.dto.response.CommentResponseDto;
import org.hca.blogproject.dto.response.DetailedCommentResponseDto;

import org.hca.blogproject.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hca.blogproject.constant.EndPoints.*;

@RestController
@RequestMapping(ROOT + COMMENT)
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping(CREATE)
    public ResponseEntity<DetailedCommentResponseDto> comment(@RequestBody CommentRequestDto dto){
        return ResponseEntity.ok(commentService.comment(dto));
    }
    @DeleteMapping(DELETE)
    public ResponseEntity<CommentResponseDto> delete(@PathVariable Long id){
        return ResponseEntity.ok(commentService.delete(id));
    }
    @GetMapping(GET_ALL)
    public ResponseEntity<List<DetailedCommentResponseDto>> findAll(){
        return ResponseEntity.ok(commentService.findAll());
    }
    @PutMapping(UPDATE)
    public ResponseEntity<CommentResponseDto> update(@PathVariable Long id, @Valid @RequestBody CommentRequestDto request){
        return ResponseEntity.ok(commentService.updateDto(id,request));
    }

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<DetailedCommentResponseDto> findDetailedDtoById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findDetailedDtoById(id));
    }
}
