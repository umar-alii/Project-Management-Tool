package PM_Tool.Web_Prj.Service;


import PM_Tool.Web_Prj.Model.Project;
import PM_Tool.Web_Prj.Repository.ProjectRepository;
import PM_Tool.Web_Prj.payload.ProjectRequest;
import PM_Tool.Web_Prj.payload.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectResponse createProject(String ownerId, ProjectRequest request) {
        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .ownerId(ownerId)
                .teamIds(request.getTeamIds())
                .epicIds(List.of())
                .archived(false)
                .settings(request.getSettings())
                .build();
        project = projectRepository.save(project);
        return toProjectResponse(project);
    }

    public List<ProjectResponse> getProjectsByOwner(String ownerId) {
        return projectRepository.findByOwnerId(ownerId)
                .stream()
                .map(this::toProjectResponse)
                .collect(Collectors.toList());
    }

    public ProjectResponse getProjectById(String projectId, String ownerId) {
        Project project = projectRepository.findByIdAndOwnerId(projectId, ownerId)
                .orElseThrow(() -> new RuntimeException("Project not found or access denied"));
        return toProjectResponse(project);
    }

    public ProjectResponse updateProject(String projectId, String ownerId, ProjectRequest request) {
        Project project = projectRepository.findByIdAndOwnerId(projectId, ownerId)
                .orElseThrow(() -> new RuntimeException("Project not found or access denied"));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setTeamIds(request.getTeamIds());
        project.setSettings(request.getSettings());
        project = projectRepository.save(project);

        return toProjectResponse(project);
    }

    public void deleteProject(String projectId, String ownerId) {
        Project project = projectRepository.findByIdAndOwnerId(projectId, ownerId)
                .orElseThrow(() -> new RuntimeException("Project not found or access denied"));
        projectRepository.delete(project);
    }

    public ProjectResponse archiveProject(String projectId, String ownerId) {
        Project project = projectRepository.findByIdAndOwnerId(projectId, ownerId)
                .orElseThrow(() -> new RuntimeException("Project not found or access denied"));
        project.setArchived(true);
        project = projectRepository.save(project);
        return toProjectResponse(project);
    }

    public ProjectResponse unarchiveProject(String projectId, String ownerId) {
        Project project = projectRepository.findByIdAndOwnerId(projectId, ownerId)
                .orElseThrow(() -> new RuntimeException("Project not found or access denied"));
        project.setArchived(false);
        project = projectRepository.save(project);
        return toProjectResponse(project);
    }

    private ProjectResponse toProjectResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .ownerId(project.getOwnerId())
                .teamIds(project.getTeamIds())
                .epicIds(project.getEpicIds())
                .archived(project.isArchived())
                .settings(project.getSettings())
                .build();
    }
}