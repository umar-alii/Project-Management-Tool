package PM_Tool.Web_Prj.Model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectSettings {
    private boolean allowGuestUsers;
    private String defaultStatus;
}