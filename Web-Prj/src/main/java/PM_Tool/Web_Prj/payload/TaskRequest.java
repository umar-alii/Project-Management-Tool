package PM_Tool.Web_Prj.payload;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskRequest {
    private String title;
    private String description;
    private String epicId;
    private String projectId;
    private List<String> assigneeIds;
    private LocalDateTime deadline;
    private String status;
}