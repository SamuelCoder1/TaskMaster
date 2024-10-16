package com.riwi.TaskMaster.controllers;
import com.riwi.RiwiTech.application.dtos.exception.UnauthorizedAccessException;
import com.riwi.RiwiTech.application.dtos.requests.UserRequest;
import com.riwi.RiwiTech.application.dtos.requests.UserWithoutId;
import com.riwi.RiwiTech.application.dtos.requests.UserWithoutIdAndRole;
import com.riwi.RiwiTech.application.dtos.responses.UserDTO;
import com.riwi.RiwiTech.application.exceptions.GenericNotFoundExceptions;
import com.riwi.RiwiTech.domain.entities.User;
import com.riwi.RiwiTech.domain.ports.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    IUserService userService;

    @Operation(summary =  "Create a new user being admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "403", description = "Access denied: Only an admin can create users.")

    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody UserWithoutId userDTO) {
        User user = userService.create(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Operation(summary =  "Create a new user being user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserWithoutIdAndRole userDTO) {
        User user = userService.register(userDTO);
        return ResponseEntity.status(201).body(user);
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied: Only an admin can read users.")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<List<User>> readAll() {
        List<User> users = userService.readAll();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Access denied: Only an admin can create users.")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/readById/{id}")
    public ResponseEntity<?> readById(@PathVariable Long id) {
        try{
            User user = userService.readById(id)
                    .orElseThrow(() -> new GenericNotFoundExceptions("User not found with id: " + id));
            return ResponseEntity.ok(user);
        }catch (UnauthorizedAccessException error){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("dsvdsf");
        }
    }

    @Operation(summary = "Update an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "403", description = "Access denied: Only an admin can update users.")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/update/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody UserWithoutId userWithoutId) {
        User updatedUser = userService.update(id, userWithoutId);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Access denied: Only an admin can delete users.")
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
