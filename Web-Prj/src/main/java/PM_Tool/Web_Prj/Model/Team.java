package PM_Tool.Web_Prj.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "teams")
public class Team {
    @Id
    private String id;

    private String name;
    private String description;
    private String ownerId; // userId of creator/owner
    private List<TeamMember> members; // list of team members with roles/permissions
    private List<String> invitedEmails; // pending invitations by email
}