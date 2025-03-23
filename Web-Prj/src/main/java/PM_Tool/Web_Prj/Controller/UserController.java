package PM_Tool.Web_Prj.Controller;


import PM_Tool.Web_Prj.Service.UserService;
import PM_Tool.Web_Prj.payload.UserProfileResponse;
import PM_Tool.Web_Prj.payload.UserProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}