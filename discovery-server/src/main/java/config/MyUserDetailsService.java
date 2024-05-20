//package config;
//
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class MyUserDetailsService implements UserDetailsService {
//    @Transactional
//    @Override
//    public UserDetails loadUserByUsername(String login){
//        return User.builder()
//                .username("user")
//                .password("pass")
//                .roles("USER")
//                .build();
//    }
//// for authorities in the future
////    private static SimpleGrantedAuthority getAuthority(Utilizer utilizer){
////        return new SimpleGrantedAuthority(string);
////    }
//}
