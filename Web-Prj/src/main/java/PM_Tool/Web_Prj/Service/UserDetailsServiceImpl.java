package PM_Tool.Web_Prj.Service;


import PM_Tool.Web_Prj.Repository.UserRepository;
import PM_Tool.Web_Prj.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // If your roles field is a List<String>
        List<SimpleGrantedAuthority> authorities = user.getRoles() != null
                ? user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList())
                : List.of();

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                !user.isLocked(),
                authorities
        );
    }
}