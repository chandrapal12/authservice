package org.example.service;

import lombok.AllArgsConstructor;
import org.example.entities.User;
import org.example.eventProducer.UserProducer;
import org.example.model.UserDto;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private UserProducer userProducer;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userInDb = userRepository.findByUsername(username);

        if(userInDb==null){
            throw new UsernameNotFoundException("could not found user...");
        }


        return new CustomUserDetails(userInDb);

    }

    public User checkIfUserAlreadyExist(UserDto userDto){
        return  userRepository.findByUsername(userDto.getUsername());


    }


    public Boolean signupUser(UserDto userDto){
        // validate email and password
        if(Objects.nonNull(checkIfUserAlreadyExist(userDto))){
            return false;
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        String userId = UUID.randomUUID().toString();
        userRepository.save(new User(userDto.getUserId(), userDto.getUsername(),
                userDto.getPassword(), new HashSet<>()));
        //pushEventToKafka
        userProducer.sendEventToKafka(userDto);
        return true;
    }

}
