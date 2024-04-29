package org.hca.blogproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.request.UserRequestDto;
import org.hca.blogproject.dto.response.UserResponseDto;
import org.hca.blogproject.service.UserService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserResponseDto> save(@RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(userService.saveDto(dto));
    }

    //findAll
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAllDto());
    }

    //findById
    @GetMapping(FIND_BY_ID)
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findDtoById(id));
    }

    //update
    @PutMapping(UPDATE)
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @Valid @RequestBody UserRequestDto request){
        return ResponseEntity.ok(userService.updateDto(id,request));
    }

    //delete and remove from existence
    @DeleteMapping(DELETE)
    public ResponseEntity<UserResponseDto> deleteDto(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteDto(id));
    }

    //delete but continue to store in database
    @PutMapping(SET_TO_DELETED)
    public ResponseEntity<UserResponseDto> setToDeletedDto(@PathVariable Long id){
        return ResponseEntity.ok(userService.setToDeletedDto(id));
    }
}
