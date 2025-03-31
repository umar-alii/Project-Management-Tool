package PM_Tool.Web_Prj.payload;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EpicResponse {
    private String id;
    private String name;
    private String description;
    private String projectId;
    private List<String> taskIds;
}