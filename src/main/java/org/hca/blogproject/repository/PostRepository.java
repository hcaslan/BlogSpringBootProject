package org.hca.blogproject.repository;

import org.hca.blogproject.dto.response.PostResponseDto;
import org.hca.blogproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findByUserId(Long id);

    @Query("SELECT p FROM Post p JOIN p.categories c WHERE c.id = :categoryId")
    List<Post> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT DISTINCT p FROM Post p JOIN p.categories c WHERE p.content LIKE %:search_key% OR p.title LIKE %:search_key%")
    List<Post> search(@Param("search_key") String search_key);

    @Query("SELECT p FROM Post p JOIN p.categories c WHERE c.name = :categoryName")
    List<Post> findByCategoryName(@Param("categoryName") String name);

    List<Post> findAllByOrderByCreatedAtDesc();
}
