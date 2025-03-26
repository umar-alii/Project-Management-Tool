package PM_Tool.Web_Prj.Service;


import PM_Tool.Web_Prj.payload.ProjectRequest;
import PM_Tool.Web_Prj.payload.ProjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            Authentication authentication,
            @RequestBody ProjectRequest request) {
        String ownerId = authentication.getName();
        ProjectResponse response = projectService.createProject(ownerId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getMyProjects(Authentication authentication) {
        String ownerId = authentication.getName();
        List<ProjectResponse> projects = projectService.getProjectsByOwner(ownerId);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProject(
            Authentication authentication,
            @PathVariable String projectId) {
        String ownerId = authentication.getName();
        ProjectResponse response = projectService.getProjectById(projectId, ownerId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(
            Authentication authentication,
            @PathVariable String projectId,
            @RequestBody ProjectRequest request) {
        String ownerId = authentication.getName();
        ProjectResponse response = projectService.updateProject(projectId, ownerId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(
            Authentication authentication,
            @PathVariable String projectId) {
        String ownerId = authentication.getName();
        projectService.deleteProject(projectId, ownerId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{projectId}/archive")
    public ResponseEntity<ProjectResponse> archiveProject(
            Authentication authentication,
            @PathVariable String projectId) {
        String ownerId = authentication.getName();
        ProjectResponse response = projectService.archiveProject(projectId, ownerId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{projectId}/unarchive")
    public ResponseEntity<ProjectResponse> unarchiveProject(
            Authentication authentication,
            @PathVariable String projectId) {
        String ownerId = authentication.getName();
        ProjectResponse response = projectService.unarchiveProject(projectId, ownerId);
        return ResponseEntity.ok(response);
    }
}