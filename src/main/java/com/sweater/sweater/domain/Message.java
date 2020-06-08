package com.sweater.sweater.domain;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @NotBlank(message = "Please fill the message.")
    @Length(max = 2048, message = "Message too long. Longer than 2048 symbols.")
    private String text;

    @NonNull
    @Length(max = 256, message = "Tag too long. Longer than 256 symbols.")
    private String tag;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public String getAuthorName() {
        return author == null ? "\"none\"" : author.getUsername();
    }

    private String filename;
}
