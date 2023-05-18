package com.ecore.roles.web.rest;

import com.ecore.roles.service.TeamsService;
import com.ecore.roles.web.dto.TeamDto;
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

import static com.ecore.roles.web.dto.TeamDto.fromModel;

@RestController
@RequestMapping(value = "/v1/teams")
public class TeamsRestController {
    @Autowired
    private TeamsService teamsService;
    @Operation(summary = "Returns a List of Teams")
    @ApiResponse(responseCode = "200", description = "List of Teams successfully found!")
    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<TeamDto>> getTeams() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(teamsService.getTeams().stream()
                        .map(TeamDto::fromModel)
                        .collect(Collectors.toList()));
    }

    @Operation(summary = "Find an Team by ID")
    @ApiResponse(responseCode = "200", description = "Team found successfully!")
    @ApiResponse(responseCode = "404", description = "Team not found!")
    @GetMapping(
            path = "/{teamId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TeamDto> getTeam(
            @PathVariable UUID teamId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fromModel(teamsService.getTeam(teamId)));
    }

}
