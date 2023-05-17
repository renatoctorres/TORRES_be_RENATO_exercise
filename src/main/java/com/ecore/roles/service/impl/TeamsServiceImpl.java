package com.ecore.roles.service.impl;

import com.ecore.roles.client.TeamsClient;
import com.ecore.roles.client.model.Team;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.service.TeamsService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeamsServiceImpl implements TeamsService {
    @Autowired
    private TeamsClient teamsClient;

    public Team getTeam(UUID id) {
        val team = teamsClient. getTeam(id).getBody();
        if(team==null){
            throw new ResourceNotFoundException(Team.class,id);
        }
        return team;
    }
    public List<Team> getTeams() {
        return teamsClient.getTeams().getBody();
    }

}
