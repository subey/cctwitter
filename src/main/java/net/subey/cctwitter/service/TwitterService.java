package net.subey.cctwitter.service;

import lombok.extern.log4j.Log4j2;
import net.subey.cctwitter.entity.Message;
import net.subey.cctwitter.entity.User;
import net.subey.cctwitter.exception.AlreadyFollowException;
import net.subey.cctwitter.exception.FollowYourselfException;
import net.subey.cctwitter.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;

@Log4j2
@Service
public class TwitterService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserService userService;

    public Message postMessage(String nick, String body){
        log.info("PostMessage: nick={}, body={}", nick, body);
        return messageRepository.save(Message.builder()
                .body(body)
                .user(userService.findOrCreate(nick))
                .build());
    }
    public Page<Message> getMessage(String nick, Pageable pageable){
        log.info("GetMessage: nick={}", nick);
        return messageRepository.findAllByUserOrderByIdDesc(
                userService.findByNick(nick), pageable);
    }
    public Page<Message> getTimeline(String nick, Pageable pageable){
        return messageRepository.findAllByUserFollowing(
                userService.findByNick(nick), pageable);
    }
    @Transactional
    public void follow(String nick, String followingNick){
        log.info("Follow: {} -> {}", nick, followingNick);
        if(nick.equals(followingNick)){
            throw new FollowYourselfException();
        }
        User user = userService.findByNick(nick);
        User following = userService.findByNick(followingNick);
        if(user.getFollowing() == null){
            user.setFollowing(new HashSet<>());
        }
        if(user.getFollowing().contains(following)){
            throw new AlreadyFollowException(followingNick);
        }
        user.getFollowing().add(following);
        userService.save(user);
    }
}
