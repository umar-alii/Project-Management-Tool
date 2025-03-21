package PM_Tool.Web_Prj.Controller;


import PM_Tool.Web_Prj.Model.User;
import PM_Tool.Web_Prj.Service.AuthService;
import PM_Tool.Web_Prj.payload.AuthRequest;
import PM_Tool.Web_Prj.payload.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}