package PM_Tool.Web_Prj.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskListItem {
    private String taskId;
    private String title;
    private String epicId;
    private String status;
    private String assigneeName;
    private String deadline;
}