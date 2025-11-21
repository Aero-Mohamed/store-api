package com.hassan.store.controllers;

import com.hassan.store.dtos.UserDto;
import com.hassan.store.entities.User;
import com.hassan.store.mappers.UserMapper;
import com.hassan.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
