package PM_Tool.Web_Prj.Controller;


import PM_Tool.Web_Prj.Service.ProjectService;
import PM_Tool.Web_Prj.Service.UserService;
import PM_Tool.Web_Prj.payload.ProjectRequest;
import PM_Tool.Web_Prj.payload.ProjectResponse;
import PM_Tool.Web_Prj.payload.UserProfileResponse;
import PM_Tool.Web_Prj.payload.UserProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Get current user's profile
    @GetMapping("/me")
    public UserProfileResponse getProfile(Authentication authentication) {
        String email = authentication.getName();
        return userService.getUserProfile(email);
    }

    // Update user's profile
    @PutMapping("/me")
    public UserProfileResponse updateProfile(Authentication authentication,
                                             @RequestBody UserProfileUpdateRequest request) {
        String email = authentication.getName();
        return userService.updateUserProfile(email, request);
    }

    // Upload profile picture
    @PostMapping("/profile-picture")
    public ResponseEntity<String> uploadProfilePicture(Authentication authentication,
                                                       @RequestParam("file") MultipartFile file) {
        String email = authentication.getName();
        try {
            String url = userService.uploadProfilePicture(email, file);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading profile picture: " + e.getMessage());
        }
    }

    // Serve profile picture
    @GetMapping("/profile-picture/{fileName:.+}")
    public ResponseEntity<byte[]> serveProfilePicture(@PathVariable String fileName) {
        try {
            byte[] image = userService.getProfilePicture(fileName);
            // You can improve this to set proper Content-Type based on the file extension
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(image);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RestController
    @RequestMapping("/projects")
    @RequiredArgsConstructor
    public static class ProjectController {

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
}