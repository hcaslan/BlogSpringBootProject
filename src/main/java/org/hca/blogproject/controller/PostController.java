package org.hca.blogproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.request.PostRequestDto;
import org.hca.blogproject.dto.response.DetailedPostResponseDto;
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
    @PostMapping(CREATE)
    public ResponseEntity<PostResponseDto> save(@RequestBody PostRequestDto dto) {
        return ResponseEntity.ok(postService.saveDto(dto));
    }

    //findAll
    @GetMapping(GET_ALL)
    public ResponseEntity<List<PostResponseDto>> findAll() {
        return ResponseEntity.ok(postService.findAllDto());
    }

    //findById
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<DetailedPostResponseDto> findDetailedDtoById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findDetailedDtoById(id));
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
//    @DeleteMapping(DELETE)
//    public ResponseEntity<PostResponseDto> setToDeletedDto(@PathVariable Long id){
//        return ResponseEntity.ok(postService.setToDeletedDto(id));
//    }
    @GetMapping(GET_POSTS_BY_USER_ID)
    public ResponseEntity<List<PostResponseDto>> findByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(postService.findByUserId(userId));
    }
    @GetMapping(GET_POSTS_BY_CATEGORY_ID)
    public ResponseEntity<List<PostResponseDto>> findByCategoryId(@PathVariable Long categoryId){
        return ResponseEntity.ok(postService.findByCategoryId(categoryId));
    }
    @GetMapping(GET_POSTS_BY_CATEGORY_NAME)
    public ResponseEntity<List<PostResponseDto>> findByCategoryName(@RequestParam String category){
        return ResponseEntity.ok(postService.findByCategoryName(category));
    }
    @GetMapping(SEARCH)
    public ResponseEntity<List<PostResponseDto>> search(@RequestParam String searchKey){
        return ResponseEntity.ok(postService.search(searchKey));
    }
    @PostMapping(LIKE)
    public ResponseEntity<DetailedPostResponseDto> like(@RequestParam Long user_id, @RequestParam Long post_id){
        return ResponseEntity.ok(postService.like(user_id,post_id));
    }
    @PostMapping(UNLIKE)
    public ResponseEntity<DetailedPostResponseDto> unlike(@RequestParam Long user_id, @RequestParam Long post_id){
        return ResponseEntity.ok(postService.unlike(user_id,post_id));
    }
    @GetMapping(GET_POSTS_IN_CHRONOLOGICAL_ORDER)
    public ResponseEntity<List<PostResponseDto>> getPostsInChronologicalOrder(){
        return ResponseEntity.ok(postService.getPostsInChronologicalOrder());
    }
}
