package com.ecore.roles.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class Team {

    @Id
    private UUID id;

    private String name;

    private UUID teamLeadId;

    private List<UUID> teamMemberIds;
}
