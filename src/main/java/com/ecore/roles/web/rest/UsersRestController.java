package com.ecore.roles.web.rest;

import com.ecore.roles.service.UsersService;
import com.ecore.roles.web.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ecore.roles.web.dto.UserDto.fromModel;

@RestController
@RequestMapping(value = "/v1/users")
public class UsersRestController {
    @Autowired
    private UsersService usersService;
    @Operation(summary = "Returns a List of Users")
    @ApiResponse(responseCode = "200", description = "List of Users successfully found!")
    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usersService.getUsers().stream()
                        .map(UserDto::fromModel)
                        .collect(Collectors.toList()));
    }
    @Operation(summary = "Find an User by ID")
    @ApiResponse(responseCode = "200", description = "User found successfully!")
    @GetMapping(
            path = "/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserDto> getUser(
            @PathVariable UUID userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fromModel(usersService.getUser(userId)));
    }

}
