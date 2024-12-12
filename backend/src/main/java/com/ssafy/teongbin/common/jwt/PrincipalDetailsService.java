package com.ssafy.teongbin.common.jwt;

import com.ssafy.teongbin.user.entity.User;
import com.ssafy.teongbin.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService의 loadUserByEmail");
        Optional<User> userEntity = userRepository.findByEmail(email);
        if ( userEntity.isEmpty() ){
            throw new RuntimeException("유효하지 않은 유저입니다");
        }
        User user = userEntity.get();
//        return new back.shoppingMart.config.auth.PrincipalDetails(userEntity);
        return new PrincipalDetails(user);
    }
}
