package net.subey.cctwitter.service;

import net.subey.cctwitter.entity.Message;
import net.subey.cctwitter.exception.AlreadyFollowException;
import net.subey.cctwitter.exception.UserNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TwitterServiceTest {

    private final static String MSG_1 = "Test msg 1";
    private final static String MSG_2 = "Test msg 2";
    @Autowired
    TwitterService twitterService;

    @Test
    @Order(1)
    public void bobPostMessages() {
        Message msg = twitterService.postMessage("Bob", MSG_1);
        assertEquals(1L, msg.getId());
        msg = twitterService.postMessage("Bob", MSG_2);
        assertEquals(2L, msg.getId());
    }

    @Test
    @Order(2)
    public void verifyBobMessages() {
        Page<Message> res = twitterService.getMessage("Bob", null);
        assertEquals(2L, res.getTotalElements());
        assertEquals(MSG_2, res.getContent().get(0).getBody());
        assertEquals(MSG_1, res.getContent().get(1).getBody());
    }

    @Test
    @Order(3)
    public void cantFollowNoExistUser() {
        String user = "Bob";
        String follow = "Tom";

        Exception e = assertThrows(UserNotFoundException.class, ()
                -> twitterService.follow(user, follow));

        assertEquals(
                new UserNotFoundException(follow).getMessage(),
                e.getMessage());
    }

    @Test
    @Order(4)
    public void tomPostMessages() {
        Message msg = twitterService.postMessage("Tom", MSG_1);
        assertEquals(3L, msg.getId());
        msg = twitterService.postMessage("Tom", MSG_2);
        assertEquals(4L, msg.getId());
    }
    @Test
    @Order(5)
    public void verifyTomMessages() {
        Page<Message> res = twitterService.getMessage("Tom", null);
        assertEquals(2L, res.getTotalElements());
        assertEquals(MSG_2, res.getContent().get(0).getBody());
        assertEquals(MSG_1, res.getContent().get(1).getBody());
    }

    @Test
    @Order(6)
    public void bobFollowTom() {
        String user = "Bob";
        String follow = "Tom";
        twitterService.follow(user, follow);

        Exception e = assertThrows(AlreadyFollowException.class, ()
                -> twitterService.follow(user, follow));

        assertEquals(
                new AlreadyFollowException(follow).getMessage(),
                e.getMessage());
    }

    @Test
    @Order(7)
    public void bobTimeline() {
        Page<Message> res = twitterService.getTimeline("Bob", null);
        assertEquals(2L, res.getTotalElements());
        // Tom's messages in reverse order
        assertEquals(4L, res.getContent().get(0).getId());
        assertEquals(3L, res.getContent().get(1).getId());
    }
}
