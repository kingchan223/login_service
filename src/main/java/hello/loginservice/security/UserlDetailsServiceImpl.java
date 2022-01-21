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
public class UserlDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("email = " + email);
        User findUser = userRepository.findByEmail(email);
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUsername(findUser.getEmail());
        securityUser.setPassword(findUser.getPassword());
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(findUser.getRole()));

        securityUser.setAuthorities(authorities);
        System.out.println("해당 사용자 email이씀");
        return securityUser;
    }
}
