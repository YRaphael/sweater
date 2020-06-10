package com.sweater.sweater.service;

import com.sweater.sweater.domain.Role;
import com.sweater.sweater.domain.User;
import com.sweater.sweater.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byUsername = userRepository.findByUsername(username);
        if (byUsername == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return byUsername;
    }

    public boolean addUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        //user.setRoles(new HashSet<Role>(Arrays.asList(new Role[]{Role.ADMIN, Role.USER})));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        sendMail(user);
        return true;
    }

    private void sendMail(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String format = String.format("Hello, %s!\n" +
                            "Welcome to Sweater. Please visit next link http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode());

            mailSender.send(user.getEmail(),
                    "Activation code",
                    format
            );
        }
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);
        return true;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void saveUser(User user, String username, Map<String, Object> form) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        user.getRoles().clear();
        for (String key :
                form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    public boolean updateProfile(User user, String password, String password2, String email, Model model) {
        if (password == null || StringUtils.isEmpty(password)) {
            model.addAttribute("passwordError", "Password can't be empty. Changes not save.");
            return false;
        }
        if (password2 == null || StringUtils.isEmpty(password2)) {
            model.addAttribute("password2Error", "Password confirm can't be empty. Changes not save.");
            return false;
        }
        if (!password.equals(password2)) {
            model.addAttribute("passwordError", "Password and password confirm must be equal. Changes not save.");
            return false;
        }

        String userEmail = user.getEmail();
        boolean isEmailChanged = ((email != null && !email.equals(userEmail)) || (userEmail != null && !userEmail.equals(email))) && !StringUtils.isEmpty(email);
        if (isEmailChanged) {
            user.setEmail(email);
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
                user.setActive(false);
            }
        }
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        userRepository.save(user);
        if (isEmailChanged) {
            sendMail(user);
        }
        return true;
    }
}
