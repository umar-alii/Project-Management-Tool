package PM_Tool.Web_Prj.Service;


import PM_Tool.Web_Prj.Model.Task;
import PM_Tool.Web_Prj.Repository.TaskRepository;
import PM_Tool.Web_Prj.payload.TaskRequest;
import PM_Tool.Web_Prj.payload.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final EpicService epicService;

    public TaskResponse createTask(TaskRequest request) {
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .epicId(request.getEpicId())
                .projectId(request.getProjectId())
                .status(request.getStatus() == null ? "TODO" : request.getStatus())
                .assigneeIds(request.getAssigneeIds())
                .deadline(request.getDeadline())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Task saved = taskRepository.save(task);
        if (saved.getEpicId() != null) {
            epicService.addTaskToEpic(saved.getEpicId(), saved.getId());
        }
        return toResponse(saved);
    }

    public List<TaskResponse> getTasksByEpic(String epicId) {
        return taskRepository.findByEpicId(epicId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getTasksByProject(String projectId) {
        return taskRepository.findByProjectId(projectId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getTasksByAssignee(String assigneeId) {
        return taskRepository.findByAssigneeIdsContaining(assigneeId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse getTask(String taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        return toResponse(task);
    }

    public TaskResponse updateTask(String taskId, TaskRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setAssigneeIds(request.getAssigneeIds());
        task.setDeadline(request.getDeadline());
        task.setStatus(request.getStatus());
        task.setUpdatedAt(LocalDateTime.now());
        // Epic and project can be changed as well if needed
        task.setEpicId(request.getEpicId());
        task.setProjectId(request.getProjectId());

        return toResponse(taskRepository.save(task));
    }

    public void deleteTask(String taskId) {
        taskRepository.deleteById(taskId);
    }

    private TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .epicId(task.getEpicId())
                .projectId(task.getProjectId())
                .status(task.getStatus())
                .assigneeIds(task.getAssigneeIds())
                .deadline(task.getDeadline())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}