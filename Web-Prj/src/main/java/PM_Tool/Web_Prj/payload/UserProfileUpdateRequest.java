package PM_Tool.Web_Prj.payload;


import PM_Tool.Web_Prj.Model.UserPreferences;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileUpdateRequest {
    private String username;
    private UserPreferences preferences;
}