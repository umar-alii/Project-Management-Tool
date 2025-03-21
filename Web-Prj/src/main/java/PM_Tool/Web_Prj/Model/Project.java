package PM_Tool.Web_Prj.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "projects")
public class Project {
    @Id
    private String id;

    private String name;
    private String description;
    private String ownerId;
    private List<String> teamIds;
    private List<String> epicIds;
    private boolean archived;
    private ProjectSettings settings;
}