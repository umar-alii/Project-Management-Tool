package PM_Tool.Web_Prj.Model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferences {
    private String theme;
    private boolean emailNotifications;
}