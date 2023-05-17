package com.ecore.roles.web.dto;

import com.ecore.roles.client.model.Team;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;
import java.util.UUID;
@Builder
public class TeamDto {

    private UUID id;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID teamLeadId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<UUID> teamMemberIds;
    public static TeamDto fromModel(Team team) {
        if (team == null) {
            return null;
        }
        return TeamDto.builder()
                .id(team.getId())
                .name(team.getName())
                .teamLeadId(team.getTeamLeadId())
                .teamMemberIds(team.getTeamMemberIds())
                .build();
    }
}
