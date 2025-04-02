package PM_Tool.Web_Prj.payload;


import PM_Tool.Web_Prj.Model.TeamMember;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamResponse {
    private String id;
    private String name;
    private String description;
    private String ownerId;
    private List<TeamMember> members;
    private List<String> invitedEmails;
}