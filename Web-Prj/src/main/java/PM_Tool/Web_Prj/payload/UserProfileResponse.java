package PM_Tool.Web_Prj.payload;


import PM_Tool.Web_Prj.Model.UserPreferences;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileResponse {
    private String id;
    private String username;
    private String email;
    private String profilePictureUrl;
    private UserPreferences preferences;
}