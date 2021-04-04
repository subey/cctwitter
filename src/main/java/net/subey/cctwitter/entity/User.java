package net.subey.cctwitter.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import net.subey.cctwitter.view.View;

import javax.persistence.*;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Id.class)
    private Long id;

    @JsonView(View.Post.class)
    @Schema(example = "Tom")
    private String nick;

    @Schema(hidden = true)
    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy="user", orphanRemoval = true)
    private Set<Message> messages;

    @Schema(hidden = true)
    @ToString.Exclude
    @ManyToMany
    private Set<User> following;

}
