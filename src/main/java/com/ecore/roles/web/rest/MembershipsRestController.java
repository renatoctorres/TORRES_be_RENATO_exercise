package com.ecore.roles.web.rest;

import com.ecore.roles.model.Membership;
import com.ecore.roles.service.MembershipsService;
import com.ecore.roles.web.dto.MembershipDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ecore.roles.web.dto.MembershipDto.fromModel;

@RestController
@RequestMapping(value = "/v1/roles/memberships")
public class MembershipsRestController {
    @Autowired
    private MembershipsService membershipsService;
    @Operation(summary = "Assign a Role to a Membership")
    @ApiResponse(responseCode = "200", description = "Role assigned to a Membership with Success!")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MembershipDto> assignRoleToMembership(
            @NotNull @Valid @RequestBody MembershipDto membershipDto) {
        Membership membership = membershipsService.assignRoleToMembership(membershipDto.toModel());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(fromModel(membership));

    }

    @Operation(summary = "Returns a List of Memberships by Role ID")
    @ApiResponse(responseCode = "200", description = "List of Memberships successfully found by Role ID!")
    @GetMapping(
            path = "/search",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<MembershipDto>> getMemberships(
            @RequestParam UUID roleId) {

        List<Membership> memberships = membershipsService.getMemberships(roleId);
        val membershipsDto = memberships.stream()
                .map(MembershipDto::fromModel)
                .collect(Collectors.toList()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(membershipsDto);

    }

}
