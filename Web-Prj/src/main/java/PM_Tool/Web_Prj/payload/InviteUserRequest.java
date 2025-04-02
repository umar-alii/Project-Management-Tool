package PM_Tool.Web_Prj.payload;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InviteUserRequest {
    private String email;
}