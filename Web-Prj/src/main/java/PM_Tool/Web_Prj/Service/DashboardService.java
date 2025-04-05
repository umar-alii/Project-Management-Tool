package PM_Tool.Web_Prj.Service;


import PM_Tool.Web_Prj.Model.Epic;
import PM_Tool.Web_Prj.Model.Project;
import PM_Tool.Web_Prj.Model.Task;
import PM_Tool.Web_Prj.Repository.EpicRepository;
import PM_Tool.Web_Prj.Repository.ProjectRepository;
import PM_Tool.Web_Prj.Repository.TaskRepository;
import PM_Tool.Web_Prj.Repository.UserRepository;
import PM_Tool.Web_Prj.payload.DashboardProjectOverviewResponse;
import PM_Tool.Web_Prj.payload.KanbanColumn;
import PM_Tool.Web_Prj.payload.TaskListItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProjectRepository projectRepository;
    private final EpicRepository epicRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public DashboardProjectOverviewResponse getProjectOverview(String projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        List<Epic> epics = epicRepository.findByProjectId(projectId);
        List<Task> tasks = taskRepository.findByProjectId(projectId);

        int totalEpics = epics.size();
        int totalTasks = tasks.size();
        int tasksTodo = 0, tasksInProgress = 0, tasksDone = 0;
        Map<String, List<TaskListItem>> kanbanMap = new HashMap<>();
        List<TaskListItem> taskList = new ArrayList<>();

        // Map for assignee name lookup
        Map<String, String> userIdToName = new HashMap<>();
        userRepository.findAll().forEach(u -> userIdToName.put(u.getId(), u.getUsername()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Task task : tasks) {
            String assigneeName = "";
            if (task.getAssigneeIds() != null && !task.getAssigneeIds().isEmpty()) {
                String assigneeId = task.getAssigneeIds().get(0); // Primary assignee
                assigneeName = userIdToName.getOrDefault(assigneeId, "");
            }
            TaskListItem item = TaskListItem.builder()
                    .taskId(task.getId())
                    .title(task.getTitle())
                    .epicId(task.getEpicId())
                    .status(task.getStatus())
                    .assigneeName(assigneeName)
                    .deadline(task.getDeadline() != null ? task.getDeadline().format(formatter) : "")
                    .build();
            taskList.add(item);
            // Kanban logic
            String status = task.getStatus() == null ? "TODO" : task.getStatus();
            kanbanMap.computeIfAbsent(status, k -> new ArrayList<>()).add(item);

            // Progress logic
            if ("TODO".equalsIgnoreCase(status)) tasksTodo++;
            else if ("IN_PROGRESS".equalsIgnoreCase(status)) tasksInProgress++;
            else if ("DONE".equalsIgnoreCase(status)) tasksDone++;
        }

        int done = tasksDone;
        double completionPercent = totalTasks == 0 ? 0 : (done * 100.0 / totalTasks);

        List<KanbanColumn> kanbanColumns = Arrays.asList(
                KanbanColumn.builder().status("TODO").tasks(kanbanMap.getOrDefault("TODO", List.of())).build(),
                KanbanColumn.builder().status("IN_PROGRESS").tasks(kanbanMap.getOrDefault("IN_PROGRESS", List.of())).build(),
                KanbanColumn.builder().status("DONE").tasks(kanbanMap.getOrDefault("DONE", List.of())).build()
        );

        return DashboardProjectOverviewResponse.builder()
                .projectId(projectId)
                .projectName(project.getName())
                .totalEpics(totalEpics)
                .totalTasks(totalTasks)
                .tasksTodo(tasksTodo)
                .tasksInProgress(tasksInProgress)
                .tasksDone(tasksDone)
                .completionPercent(completionPercent)
                .kanbanColumns(kanbanColumns)
                .taskList(taskList)
                .build();
    }
}