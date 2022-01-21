package hello.loginservice.security;

import hello.loginservice.entity.User;
import hello.loginservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("email = " + email);
        User findUser = userRepository.findByEmail(email);
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setUsername(findUser.getEmail());
        userDetails.setPassword(findUser.getPassword());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(findUser.getRole()));

        userDetails.setAuthorities(authorities);
        System.out.println("해당 사용자 email이씀");
        return userDetails;
    }


}
