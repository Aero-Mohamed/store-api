package com.hassan.store.controllers;

import com.hassan.store.dtos.CreateUserRequest;
import com.hassan.store.dtos.UpdateUserRequest;
import com.hassan.store.dtos.UserDto;
import com.hassan.store.entities.User;
import com.hassan.store.mappers.UserMapper;
import com.hassan.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers(
        @RequestParam(required = false, defaultValue = "", name="sort") String sort
    ){
        if(!Set.of("name", "email").contains(sort)){
            sort = "id";
        }

        var users = userRepository.findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toDto)
                .toList();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<UserDto> createUser(
            @RequestBody CreateUserRequest request,
            UriComponentsBuilder uriBuilder
    ){
        var user = userMapper.toEntity(request);
        userRepository.save(user);

        var userDto = userMapper.toDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name="id") Long id,
            @RequestBody UpdateUserRequest request
    ){
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }

        userMapper.update(request, user);
        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toDto(user));
    }
}
