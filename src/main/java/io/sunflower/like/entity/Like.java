package io.sunflower.like.entity;

import io.sunflower.post.entity.Post;
import io.sunflower.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity(name = "likes")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }

}

