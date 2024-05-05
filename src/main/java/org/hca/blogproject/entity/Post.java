package org.hca.blogproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hca.blogproject.utility.annotation.MaxLength;
import org.hca.blogproject.utility.annotation.MinLength;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tblpost")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title",columnDefinition = "varchar(64)", nullable = false)
    @MinLength(value = 2)
    @MaxLength(value = 64)
    private String title;

    @Column(name = "content",columnDefinition = "varchar(2048)", nullable = false)
    @MinLength(value = 2)
    @MaxLength(value = 2048)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @Column(name = "is_deleted")
//    private boolean isDeleted;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tblpost-category",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tblpost-likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likes;

    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER)
    private List<Comment> comments;

    @CreationTimestamp
    @Column(name = "created_at")
    private String createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private String updatedAt;

//    @Column(name = "deleted_at")
//    private String deletedAt;
}
