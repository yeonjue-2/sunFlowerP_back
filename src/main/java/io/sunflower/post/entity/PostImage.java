package io.sunflower.post.entity;

import io.sunflower.common.TimeStamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String postImageUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public PostImage(String postImageUrl, Post post) {
        this.postImageUrl = postImageUrl;
        this.post = post;
    }

    public void addPost(Post post) {
        this.post = post;
    }
}