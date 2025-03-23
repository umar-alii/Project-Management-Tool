package PM_Tool.Web_Prj.Service;


import PM_Tool.Web_Prj.Model.User;
import PM_Tool.Web_Prj.Repository.UserRepository;
import PM_Tool.Web_Prj.payload.UserProfileResponse;
import PM_Tool.Web_Prj.payload.UserProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final String uploadDir = "uploads/profile-pictures"; // Make sure this folder exists or create it

    public UserProfileResponse getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .profilePictureUrl(user.getProfilePictureUrl())
                .preferences(user.getPreferences())
                .build();
    }

    public UserProfileResponse updateUserProfile(String email, UserProfileUpdateRequest updateRequest) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(updateRequest.getUsername());
        user.setPreferences(updateRequest.getPreferences());
        userRepository.save(user);
        return getUserProfile(email);
    }

    public String uploadProfilePicture(String email, MultipartFile file) throws IOException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Ensure upload directory exists
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        // Save file with unique name
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        String fileName = "profile_" + user.getId() + "_" + System.currentTimeMillis() + extension;
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Store file path or URL in user profile
        String url = "/users/profile-picture/" + fileName; // REST endpoint to serve it
        user.setProfilePictureUrl(url);
        userRepository.save(user);

        return url;
    }

    public byte[] getProfilePicture(String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir, fileName);
        return Files.readAllBytes(filePath);
    }
}