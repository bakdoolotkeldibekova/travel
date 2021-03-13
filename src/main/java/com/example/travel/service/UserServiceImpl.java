package com.example.travel.service;

import com.example.travel.dto.ModeratorDTO;
import com.example.travel.dto.UserChangePasswordDTO;
import com.example.travel.dto.UserDTO;
import com.example.travel.entity.ResponseMessage;
import com.example.travel.entity.Role;
import com.example.travel.entity.User;
import com.example.travel.exception.ResourceNotFoundException;
import com.example.travel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MailService mailService;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public ResponseMessage createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        Role role = (Role) roleService.getById(3L).getBody();
        user.setRole(role);
        userRepository.save(user);
        return new ResponseMessage(HttpStatus.OK.value(), "ok!", user);
    }

    @Override
    public ResponseMessage createModerator(ModeratorDTO moderatorDTO) {
        User user = new User();
        user.setName(moderatorDTO.getName());
        user.setSurname(moderatorDTO.getSurname());
        user.setEmail(moderatorDTO.getEmail());
        user.setPassword(encoder.encode(moderatorDTO.getPassword()));
        Role role = (Role) roleService.getById(2L).getBody();
        user.setRole(role);
        user.setDateOfBirth(moderatorDTO.getDateOfBirth());
        user.setDescription(moderatorDTO.getDescription());
        user.setOrganization(moderatorDTO.getOrganization());
        userRepository.save(user);
        return new ResponseMessage(HttpStatus.OK.value(), "ok!", user);
    }

    @Override
    public ResponseMessage getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User id " + id +" not found!"));
        return new ResponseMessage(HttpStatus.OK.value(), "ok!", user);

    }

    @Override
    public ResponseMessage changeName(String email, String name) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new ResourceNotFoundException("User email " + email + " not found!");

        user.setName(name);
        userRepository.save(user);
        return new ResponseMessage(HttpStatus.OK.value(), email + " user's name successfully changed", user);

    }

    @Override
    public ResponseMessage changeSurname(String email, String surname) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new ResourceNotFoundException("User email " + email + " not found!");

        user.setSurname(surname);
        userRepository.save(user);
        return new ResponseMessage(HttpStatus.OK.value(), email + " user's surname successfully changed", user);

    }

    @Override
    public ResponseMessage changeRole(String email, Long roleId) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new ResourceNotFoundException("User email " + email + " not found!");

        Role role = (Role) roleService.getById(roleId).getBody();
        user.setRole(role);
        userRepository.save(user);
        return new ResponseMessage(HttpStatus.OK.value(), email + " user's role successfully changed", user);
    }

    @Override
    public ResponseMessage changePassword(UserChangePasswordDTO userChangePasswordDTO) {
        User user = userRepository.findByEmail(userChangePasswordDTO.getEmail());
        if (user == null)
            throw new ResourceNotFoundException("User email " + userChangePasswordDTO.getEmail() + " not found!");

        boolean isPasswordMatch = encoder.matches(userChangePasswordDTO.getOldPassword(), user.getPassword());

        if (!isPasswordMatch)
            return new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), "old password is wrong", null);

        user.setPassword(encoder.encode(userChangePasswordDTO.getNewPassword()));
        userRepository.save(user);
        return new ResponseMessage(HttpStatus.OK.value(), userChangePasswordDTO.getEmail() + " user's password successfully changed", user);
    }


    @Override
    public ResponseMessage sendForgotPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return new ResponseMessage(HttpStatus.NOT_FOUND.value(), "User email " + email + " not found", null);

        String message = "Hello, ! \n" +
                " Please, visit next link to change your password: https://travel-kg.herokuapp.com/user/changeForgotPassword/" + email;
        if (mailService.send(user.getEmail(), "NEOLABS: Change password", message))
            return new ResponseMessage(HttpStatus.OK.value(), "Successfully sent", message);

        return new ResponseMessage(HttpStatus.BAD_GATEWAY.value(), "smtp server failure, request was not sent", null);
    }

    @Override
    public ResponseMessage changeForgotPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return new ResponseMessage(HttpStatus.NOT_FOUND.value(), "User email " + email + " not found", null);

        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
        return new ResponseMessage(HttpStatus.OK.value(), email + " user's password successfully changed", user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllByName(String name) {
        return userRepository.findAllByNameContainingIgnoringCase(name);
    }

    @Override
    public List<User> getAllBySurname(String surname) {
        return userRepository.findAllBySurnameContainingIgnoringCase(surname);
    }

    @Override
    public List<User> getAllByDateCreatedAfter(String after) {
        //  String str = "2016-03-04 11:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(after, formatter);
        return userRepository.findAllByDateCreatedAfter(dateTime);
    }

    @Override
    public List<User> getAllByDateCreatedBefore(String before) {
        //  String str = "2016-03-04 11:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(before, formatter);
        return userRepository.findAllByDateCreatedBefore(dateTime);
    }

    @Override
    public List<User> getALlByRoleId(Long roleId) {
        return userRepository.findAllByRoleId(roleId);
    }

    @Override
    public Page<User> getByPage(List<User> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        List<User> output = new ArrayList<>();
        if (start <= end) {
            output = list.subList(start, end);
        }
        return new PageImpl<>(output, pageable, list.size());
    }
}
