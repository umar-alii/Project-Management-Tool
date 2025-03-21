package PM_Tool.Web_Prj.Model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamInvitation {
    private String email;
    private String invitedByUserId;
    private String status; // e.g., "PENDING", "ACCEPTED", "DECLINED"
    private String token;
    private Long expiration; // timestamp
}