package net.subey.cctwitter.service;

import lombok.extern.log4j.Log4j2;
import net.subey.cctwitter.entity.User;
import net.subey.cctwitter.exception.UserNotFoundException;
import net.subey.cctwitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findByNick(String nick){
        User user = userRepository.findUserByNick(nick);
        if(user == null){
            throw new UserNotFoundException(nick);
        }
        return user;
    }
    public User findOrCreate(String nick){
        log.info("FindOrCreate: {}", nick);
        User user = userRepository.findUserByNick(nick);
        if(user == null){
            user = User.builder().nick(nick).build();
            userRepository.save(user);
        }
        return user;
    }
    public void save(User user){
        log.info("Save: {}", user);
        userRepository.save(user);
    }
}
