package net.subey.cctwitter.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.subey.cctwitter.pagination.PageResp;
import net.subey.cctwitter.entity.Message;
import net.subey.cctwitter.entity.User;
import net.subey.cctwitter.service.TwitterService;
import net.subey.cctwitter.view.View;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag(name = "TwitterController")
public class TwitterController {

    @Autowired
    TwitterService twitterService;

    @JsonView(View.Id.class)
    @Operation(summary = "Post message by user",
            responses = {
                    @ApiResponse(responseCode = "200")
            })
    @PostMapping("/user/{nick}/message")
    public Message message(
            @PathVariable @Parameter(example = "Bob") String nick,
            @RequestBody @JsonView(View.Post.class) @Valid Message message) {
        return twitterService.postMessage(nick, message.getBody());
    }

    @JsonView(View.Get.class)
    @Operation(summary = "Get user messages in reverse chronological order.",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404", description = "User %s not found.")
            })
    @GetMapping("/user/{nick}/wall")
    public PageResp<Message> wall(
            @PathVariable @Parameter(example = "Bob") String nick,
            @Parameter(hidden = true) @ParameterObject Pageable pageable) {
        return new PageResp<Message>(twitterService.getMessage(nick, pageable));
    }

    @JsonView(View.Get.class)
    @Operation(summary = "Get user followed messages in reverse chronological order.",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404", description = "User %s not found.")
            })
    @GetMapping("/user/{nick}/timeline")
    public PageResp<Message> timeline(
            @PathVariable @Parameter(example = "Bob") String nick,
            @Parameter(hidden = true) @ParameterObject Pageable pageable) {
        return new PageResp<Message>(twitterService.getTimeline(nick, pageable));
    }

    @Operation(summary = "Follow user",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "User %s not found."),
                    @ApiResponse(responseCode = "400", description = "You already following %s."),
                    @ApiResponse(responseCode = "400", description = "Can't follow yourself")
            })
    @PostMapping("/user/{nick}/follow")
    public void follow(
            @PathVariable @Parameter(example = "Bob") String nick,
            @RequestBody @JsonView(View.Post.class) User follow) {
        twitterService.follow(nick, follow.getNick());
    }
}

