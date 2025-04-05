package PM_Tool.Web_Prj.payload;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardProjectOverviewResponse {
    private String projectId;
    private String projectName;
    private int totalEpics;
    private int totalTasks;
    private int tasksTodo;
    private int tasksInProgress;
    private int tasksDone;
    private double completionPercent;
    private List<KanbanColumn> kanbanColumns;
    private List<TaskListItem> taskList;
}



