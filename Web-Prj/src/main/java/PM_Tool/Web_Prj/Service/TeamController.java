package PM_Tool.Web_Prj.Service;


import PM_Tool.Web_Prj.payload.InviteUserRequest;
import PM_Tool.Web_Prj.payload.TeamRequest;
import PM_Tool.Web_Prj.payload.TeamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamResponse> createTeam(Authentication authentication,
                                                   @RequestBody TeamRequest request) {
        String ownerId = authentication.getName();
        return ResponseEntity.ok(teamService.createTeam(ownerId, request));
    }

    @GetMapping
    public ResponseEntity<List<TeamResponse>> getMyTeams(Authentication authentication) {
        String userId = authentication.getName();
        return ResponseEntity.ok(teamService.getTeamsForUser(userId));
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponse> getTeam(@PathVariable String teamId) {
        return ResponseEntity.ok(teamService.getTeam(teamId));
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<TeamResponse> updateTeam(Authentication authentication,
                                                   @PathVariable String teamId,
                                                   @RequestBody TeamRequest request) {
        String requesterId = authentication.getName();
        return ResponseEntity.ok(teamService.updateTeam(teamId, request, requesterId));
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(Authentication authentication,
                                           @PathVariable String teamId) {
        String requesterId = authentication.getName();
        teamService.deleteTeam(teamId, requesterId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{teamId}/invite")
    public ResponseEntity<TeamResponse> inviteUser(Authentication authentication,
                                                   @PathVariable String teamId,
                                                   @RequestBody InviteUserRequest inviteRequest) {
        String requesterId = authentication.getName();
        return ResponseEntity.ok(teamService.inviteUser(teamId, inviteRequest, requesterId));
    }

    @PostMapping("/{teamId}/accept-invitation")
    public ResponseEntity<TeamResponse> acceptInvitation(Authentication authentication,
                                                         @PathVariable String teamId,
                                                         @RequestParam String email) {
        String userId = authentication.getName();
        return ResponseEntity.ok(teamService.acceptInvitation(teamId, email, userId));
    }

    @PostMapping("/{teamId}/remove-member/{memberId}")
    public ResponseEntity<TeamResponse> removeMember(Authentication authentication,
                                                     @PathVariable String teamId,
                                                     @PathVariable String memberId) {
        String requesterId = authentication.getName();
        return ResponseEntity.ok(teamService.removeMember(teamId, memberId, requesterId));
    }

    @PostMapping("/{teamId}/change-role/{memberId}")
    public ResponseEntity<TeamResponse> changeRole(Authentication authentication,
                                                   @PathVariable String teamId,
                                                   @PathVariable String memberId,
                                                   @RequestParam String newRole) {
        String requesterId = authentication.getName();
        return ResponseEntity.ok(teamService.changeMemberRole(teamId, memberId, newRole, requesterId));
    }
}