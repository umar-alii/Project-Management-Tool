package PM_Tool.Web_Prj.payload;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KanbanColumn {
    private String status; // TODO, IN_PROGRESS, DONE
    private List<TaskListItem> tasks;
}