package com.ecore.roles.web.rest;

import com.ecore.roles.model.Role;
import com.ecore.roles.service.MembershipsService;
import com.ecore.roles.service.RolesService;
import com.ecore.roles.web.RolesApi;
import com.ecore.roles.web.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.ecore.roles.web.dto.RoleDto.fromModel;

@RestController
@RequestMapping(value = "/v1/roles")
public class RolesRestController implements RolesApi {
    @Autowired
    private RolesService rolesService;
    @Autowired
    private MembershipsService membershipsService;

    @PostMapping(
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<RoleDto> createRole(
            @Valid @RequestBody RoleDto role) {
        return ResponseEntity
                .status(201)
                .body(fromModel(rolesService.CreateRole(role.toModel())));
    }

    @GetMapping(
            produces = {"application/json"})
    public ResponseEntity<List<RoleDto>> getRoles() {

        List<Role> getRoles = rolesService.GetRoles();

        List<RoleDto> roleDtoList = new ArrayList<>();

        for (Role role : getRoles) {
            RoleDto roleDto = fromModel(role);
            roleDtoList.add(roleDto);
        }

        return ResponseEntity
                .status(200)
                .body(roleDtoList);
    }

    @GetMapping(
            path = "/{roleId}",
            produces = {"application/json"})
    public ResponseEntity<RoleDto> getRole(
            @PathVariable UUID roleId) {
        return ResponseEntity
                .status(200)
                .body(fromModel(rolesService.GetRole(roleId)));
    }


    @GetMapping(
            path = "/search",
            produces = {"application/json"})
    public ResponseEntity<RoleDto> search(
            @RequestParam UUID teamMemberId, @RequestParam UUID teamId) {
        return ResponseEntity
                .status(200)
                .body(fromModel(membershipsService.search(teamMemberId,teamId).getRole()));

    }

}
