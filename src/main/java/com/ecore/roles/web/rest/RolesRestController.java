package com.ecore.roles.web.rest;

import com.ecore.roles.model.Role;
import com.ecore.roles.service.MembershipsService;
import com.ecore.roles.service.RolesService;
import com.ecore.roles.web.dto.RoleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ecore.roles.web.dto.RoleDto.fromModel;

@RestController
@RequestMapping(value = "/v1/roles")
public class RolesRestController {
    @Autowired
    private RolesService rolesService;
    @Autowired
    private MembershipsService membershipsService;
    @Operation(summary = "Create a Role")
    @ApiResponse(responseCode = "201", description = "Role created with success!")
    @ApiResponse(responseCode = "400", description = "Role already existing!")
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RoleDto> createRole(
            @Valid @RequestBody RoleDto role) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(fromModel(rolesService.createRole(role.toModel())));
    }

    @Operation(summary = "Returns a List of Roles")
    @ApiResponse(responseCode = "200", description = "List of Roles successfully found!")
    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<RoleDto>> getRoles() {

        List<Role> roles = rolesService.getRoles();
        val roleDtoList = roles.stream()
                .map(RoleDto::fromModel)
                .collect(Collectors.toList()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleDtoList);
    }

    @Operation(summary = "Find an Role by ID")
    @ApiResponse(responseCode = "200", description = "Role found successfully by ID!")
    @ApiResponse(responseCode = "404", description = "Role not found!")
    @GetMapping(
            path = "/{roleId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RoleDto> getRole(
            @PathVariable UUID roleId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fromModel(rolesService.getRole(roleId)));
    }

    @Operation(summary = "Find an Role by Params")
    @ApiResponse(responseCode = "200", description = "Role found successfully by params!")
    @ApiResponse(responseCode = "400", description = "Bad Request!")
    @GetMapping(
            path = "/search",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RoleDto> search(
            @RequestParam UUID teamMemberId, @RequestParam UUID teamId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fromModel(membershipsService.search(teamMemberId,teamId).getRole()));

    }

}
