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
@Table(name = "tbluser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname",columnDefinition = "varchar(32)")
    @MinLength(value = 2)
    @MaxLength(value = 32)
    private String firstname;

    @Column(name = "lastname",columnDefinition = "varchar(32)")
    @MinLength(value = 2)
    @MaxLength(value = 32)
    private String lastname;

    @Column(name = "email",columnDefinition = "varchar(64)", nullable = false)
    private String email;

    @Column(name = "password",columnDefinition = "varchar(16)", nullable = false)
    @MinLength(value = 8)
    @MaxLength(value = 16)
    private String password;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Post> posts;

    @CreationTimestamp
    @Column(name = "created_at")
    private String createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "deleted_at")
    private String deletedAt;

}
