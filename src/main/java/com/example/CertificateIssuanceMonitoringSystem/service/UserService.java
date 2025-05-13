//package com.example.authproject.service;
//
//import com.example.authproject.model.Role;
//import com.example.authproject.model.User;
//import com.example.authproject.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import java.util.Set;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public User registerNewUserAccount(User user, boolean isAdmin) {
//        // Check if username or email already exists
//        if (userRepository.existsByUsername(user.getUsername())) {
//            throw new RuntimeException("Username already exists");
//        }
//        if (userRepository.existsByEmail(user.getEmail())) {
//            throw new RuntimeException("Email already exists");
//        }
//
//        // Encode password
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        // Set roles based on registration type
//        user.setRoles(isAdmin ?
//                Set.of(Role.ROLE_ADMIN) :
//                Set.of(Role.ROLE_APPLICANT));
//
//        return userRepository.save(user);
//    }
//}

package com.example.CertificateIssuanceMonitoringSystem.service;

import com.example.CertificateIssuanceMonitoringSystem.model.ProfileUpdateDto;
import com.example.CertificateIssuanceMonitoringSystem.model.Role;
import com.example.CertificateIssuanceMonitoringSystem.model.User;
import com.example.CertificateIssuanceMonitoringSystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerNewUserAccount(User user, boolean isAdmin) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

//         Default to APPLICANT if not explicitly set as ADMIN
       Set<Role> roles = new HashSet<>();
       roles.add(isAdmin ? Role.ROLE_ADMIN : Role.ROLE_APPLICANT);
       user.setRoles(roles);
       return userRepository.save(user);
    }

    public int  countUsers() {
        return (int) userRepository.count();
    }

    @Transactional
    public User updateProfile(String username, ProfileUpdateDto profileDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setFullName(profileDto.getFullName());
        user.setEmail(profileDto.getEmail());
        user.setPhoneNumber(profileDto.getPhoneNumber());
        user.setAddress(profileDto.getAddress());
        user.setCity(profileDto.getCity());
        user.setState(profileDto.getState());
        user.setZipCode(profileDto.getZipCode());

        return userRepository.save(user);
    }
}
