package PM_Tool.Web_Prj.Service;


import PM_Tool.Web_Prj.Model.Epic;
import PM_Tool.Web_Prj.Repository.EpicRepository;
import PM_Tool.Web_Prj.payload.EpicRequest;
import PM_Tool.Web_Prj.payload.EpicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EpicService {

    private final EpicRepository epicRepository;

    public EpicResponse createEpic(EpicRequest request) {
        Epic epic = Epic.builder()
                .name(request.getName())
                .description(request.getDescription())
                .projectId(request.getProjectId())
                .taskIds(List.of())
                .build();
        Epic saved = epicRepository.save(epic);
        return toResponse(saved);
    }

    public List<EpicResponse> getEpicsByProject(String projectId) {
        return epicRepository.findByProjectId(projectId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public EpicResponse getEpic(String epicId) {
        Epic epic = epicRepository.findById(epicId)
                .orElseThrow(() -> new RuntimeException("Epic not found"));
        return toResponse(epic);
    }

    public EpicResponse updateEpic(String epicId, EpicRequest request) {
        Epic epic = epicRepository.findById(epicId)
                .orElseThrow(() -> new RuntimeException("Epic not found"));
        epic.setName(request.getName());
        epic.setDescription(request.getDescription());
        epic.setProjectId(request.getProjectId());
        return toResponse(epicRepository.save(epic));
    }

    public void deleteEpic(String epicId) {
        epicRepository.deleteById(epicId);
    }

    public void addTaskToEpic(String epicId, String taskId) {
        Epic epic = epicRepository.findById(epicId)
                .orElseThrow(() -> new RuntimeException("Epic not found"));
        List<String> taskIds = epic.getTaskIds();
        taskIds.add(taskId);
        epic.setTaskIds(taskIds);
        epicRepository.save(epic);
    }

    private EpicResponse toResponse(Epic epic) {
        return EpicResponse.builder()
                .id(epic.getId())
                .name(epic.getName())
                .description(epic.getDescription())
                .projectId(epic.getProjectId())
                .taskIds(epic.getTaskIds())
                .build();
    }
}