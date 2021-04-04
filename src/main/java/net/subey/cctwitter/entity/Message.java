package net.subey.cctwitter.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import net.subey.cctwitter.view.View;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Id.class)
    private Long id;

    @JsonView(View.Post.class)
    @Schema(example = "Example test message")
    @NotBlank
    @Size(min = 1, max = 140)
    @Column(length = 140)
    private String body;

    @Schema(hidden = true)
    @JsonBackReference
    @ManyToOne
    @JoinColumn(nullable=false)
    private User user;
}
