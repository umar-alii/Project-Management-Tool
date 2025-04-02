package PM_Tool.Web_Prj.Service;


import PM_Tool.Web_Prj.Model.Team;
import PM_Tool.Web_Prj.Model.TeamMember;
import PM_Tool.Web_Prj.Repository.TeamRepository;
import PM_Tool.Web_Prj.Repository.UserRepository;
import PM_Tool.Web_Prj.payload.InviteUserRequest;
import PM_Tool.Web_Prj.payload.TeamRequest;
import PM_Tool.Web_Prj.payload.TeamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamResponse createTeam(String ownerId, TeamRequest request) {
        TeamMember ownerMember = TeamMember.builder()
                .userId(ownerId)
                .role("ADMIN")
                .build();

        Team team = Team.builder()
                .name(request.getName())
                .description(request.getDescription())
                .ownerId(ownerId)
                .members(new ArrayList<>(List.of(ownerMember)))
                .invitedEmails(new ArrayList<>())
                .build();
        team = teamRepository.save(team);
        return toResponse(team);
    }

    public List<TeamResponse> getTeamsForUser(String userId) {
        List<Team> teams = teamRepository.findByMembersUserId(userId);
        return teams.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public TeamResponse getTeam(String teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        return toResponse(team);
    }

    public TeamResponse updateTeam(String teamId, TeamRequest request, String requesterId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        if (!team.getOwnerId().equals(requesterId))
            throw new RuntimeException("Only owner can update the team");

        team.setName(request.getName());
        team.setDescription(request.getDescription());
        return toResponse(teamRepository.save(team));
    }

    public void deleteTeam(String teamId, String requesterId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        if (!team.getOwnerId().equals(requesterId))
            throw new RuntimeException("Only owner can delete the team");
        teamRepository.deleteById(teamId);
    }

    public TeamResponse inviteUser(String teamId, InviteUserRequest inviteRequest, String requesterId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        if (!team.getOwnerId().equals(requesterId))
            throw new RuntimeException("Only owner can invite users");

        if (team.getInvitedEmails().contains(inviteRequest.getEmail()))
            throw new RuntimeException("User already invited");

        // Simulate sending email invitation (implement email logic as needed)
        team.getInvitedEmails().add(inviteRequest.getEmail());
        team = teamRepository.save(team);
        // TODO: Send actual email invitation with accept link/token

        return toResponse(team);
    }

    public TeamResponse acceptInvitation(String teamId, String userEmail, String userId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        if (!team.getInvitedEmails().contains(userEmail))
            throw new RuntimeException("No invitation for this email");

        // Remove from invited, add as MEMBER
        team.getInvitedEmails().remove(userEmail);
        team.getMembers().add(TeamMember.builder().userId(userId).role("MEMBER").build());

        team = teamRepository.save(team);
        return toResponse(team);
    }

    public TeamResponse removeMember(String teamId, String memberId, String requesterId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        if (!team.getOwnerId().equals(requesterId))
            throw new RuntimeException("Only owner can remove members");

        team.setMembers(team.getMembers().stream()
                .filter(member -> !member.getUserId().equals(memberId))
                .collect(Collectors.toList()));

        team = teamRepository.save(team);
        return toResponse(team);
    }

    public TeamResponse changeMemberRole(String teamId, String memberId, String newRole, String requesterId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        if (!team.getOwnerId().equals(requesterId))
            throw new RuntimeException("Only owner can change roles");

        for (TeamMember member : team.getMembers()) {
            if (member.getUserId().equals(memberId)) {
                member.setRole(newRole);
                break;
            }
        }
        team = teamRepository.save(team);
        return toResponse(team);
    }

    private TeamResponse toResponse(Team team) {
        return TeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .description(team.getDescription())
                .ownerId(team.getOwnerId())
                .members(team.getMembers())
                .invitedEmails(team.getInvitedEmails())
                .build();
    }
}