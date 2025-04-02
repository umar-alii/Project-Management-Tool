package PM_Tool.Web_Prj.Model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamMember {
    private String userId;
    private String role; // e.g. "ADMIN", "MEMBER"
}