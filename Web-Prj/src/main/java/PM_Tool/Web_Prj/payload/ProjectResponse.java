package PM_Tool.Web_Prj.payload;


import PM_Tool.Web_Prj.Model.ProjectSettings;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponse {
    private String id;
    private String name;
    private String description;
    private String ownerId;
    private List<String> teamIds;
    private List<String> epicIds;
    private boolean archived;
    private ProjectSettings settings;
}