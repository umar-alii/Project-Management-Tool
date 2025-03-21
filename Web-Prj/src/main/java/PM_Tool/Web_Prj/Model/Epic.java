package PM_Tool.Web_Prj.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "epics")
public class Epic {
    @Id
    private String id;

    private String name;
    private String description;
    private String projectId;
    private List<String> taskIds;
}