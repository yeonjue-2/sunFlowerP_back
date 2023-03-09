package io.sunflower.comment.entity;

import io.sunflower.comment.dto.CommentRequest;
import io.sunflower.common.TimeStamped;
import io.sunflower.post.dto.PostRequest;
import io.sunflower.post.entity.Post;
import io.sunflower.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@DynamicInsert
public class Comment extends TimeStamped {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(CommentRequest request, Post post, User user) {
        this.commentContents = request.getCommentContent();
        this.post = post;
        this.user = user;
    }

    public void addPost(Post post) {
        this.post = post;
//        post.addUser(this);
    }


    public void update(CommentRequest request) {
        this.commentContents = request.getCommentContent();
    }
}
