package org.hca.blogproject.repository;

import org.hca.blogproject.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long categoryId);

    Category findByName(String categoryName);
}
