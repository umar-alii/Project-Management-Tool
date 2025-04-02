package PM_Tool.Web_Prj.payload;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamRequest {
    private String name;
    private String description;
}