package PM_Tool.Web_Prj.payload;


import PM_Tool.Web_Prj.Model.ProjectSettings;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectRequest {
    private String name;
    private String description;
    private List<String> teamIds;
    private ProjectSettings settings;
}