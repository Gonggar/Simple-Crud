package edu.bjtu.sei.simplecrud.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import edu.bjtu.sei.simplecrud.domain.User;
import edu.bjtu.sei.simplecrud.controller.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
}
