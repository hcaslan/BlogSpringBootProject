package org.hca.blogproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.request.UserRequestDto;
import org.hca.blogproject.dto.response.DetailedUserResponseDto;
import org.hca.blogproject.dto.response.UserResponseDto;
import org.hca.blogproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hca.blogproject.constant.EndPoints.*;


@RestController
@RequestMapping(ROOT + USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //save
    @PostMapping
    public ResponseEntity<UserResponseDto> save(@Valid @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(userService.saveDto(dto));
    }

    //findAll
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAllDto());
    }

    //findById
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<DetailedUserResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findDtoById(id));
    }

    //update
    @PutMapping(UPDATE)
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @Valid @RequestBody UserRequestDto request){
        return ResponseEntity.ok(userService.updateDto(id,request));
    }

    //delete and remove from existence
//    @DeleteMapping(DELETE)
//    public ResponseEntity<UserResponseDto> deleteDto(@PathVariable Long id){
//        return ResponseEntity.ok(userService.deleteDto(id));
//    }

    //delete but continue to store in database. I thought it was better to mark it as deleted than to delete it completely.
    @DeleteMapping(DELETE)
    public ResponseEntity<UserResponseDto> setToDeletedDto(@PathVariable Long id){
        return ResponseEntity.ok(userService.setToDeletedDto(id));
    }

    @PostMapping(REACTIVATE_USER)
    public ResponseEntity<UserResponseDto> reActivateUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.reActivateUser(id));
    }
}
