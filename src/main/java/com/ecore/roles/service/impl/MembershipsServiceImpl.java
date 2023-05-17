package com.ecore.roles.service.impl;

import com.ecore.roles.client.model.Team;
import com.ecore.roles.exception.InvalidArgumentException;
import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.exception.UserNotAllowedException;
import com.ecore.roles.model.Membership;
import com.ecore.roles.model.Role;
import com.ecore.roles.repository.MembershipRepository;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.MembershipsService;
import com.ecore.roles.service.TeamsService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.Optional.ofNullable;

/**
 *  Membership Implementation
 */
@Log4j2
@Service
public class MembershipsServiceImpl implements MembershipsService {
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TeamsService teamService;

    /**
     *
     * @param m Membership Model
     * @return The Membership assigned with Role
     */
    public Membership assignRoleToMembership(@NonNull Membership m) {
        UUID roleId = ofNullable(m.getRole()).map(Role::getId)
                .orElseThrow(() -> new InvalidArgumentException(Role.class));
        if (membershipRepository.findByUserIdAndTeamId(m.getUserId(), m.getTeamId())
                .isPresent()) {
            throw new ResourceExistsException(Membership.class);
        }
        validateRole(roleId);
        val team = teamService.getTeam(m.getTeamId());
        if(!team.getTeamMemberIds().contains(m.getUserId())){
            throw new UserNotAllowedException(Membership.class);
        }

        return membershipRepository.save(m);

    }

    /**
     *  Validate if this Role exists in DB
     * @param roleId
     */
    private void validateRole(UUID roleId) {
        val role = roleRepository.findById(roleId);
        if(role.isEmpty()){
            throw new ResourceNotFoundException(Role.class, roleId);
        }
    }

    /**
     *
     * @param rid The Role ID
     * @return List of Memberships assigned with this Role ID
     */
    public List<Membership> getMemberships(@NonNull UUID rid) {
        return membershipRepository.findByRoleId(rid);
    }

    /**
     *
     * @param userId User ID Entity Input
     * @param teamId Team ID Entity Input
     * @return Membership with params inputted
     */
    public Membership search(UUID userId, UUID teamId) {
        return membershipRepository
                .findByUserIdAndTeamId(userId, teamId)
                .orElseThrow (() -> new ResourceNotFoundException(Team.class, teamId));
    }

}
