package org.hca.blogproject.service.rules;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.entity.Category;
import org.hca.blogproject.exception.BusinessException;
import org.hca.blogproject.exception.ErrorType;
import org.hca.blogproject.repository.CategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @BusinessRules
 */
@Service
public class CategoryBusinessRules extends BusinessRulesManager<Category,Long>{
    private final CategoryRepository categoryRepository;

    public CategoryBusinessRules(JpaRepository<Category, Long> jpaRepository, CategoryRepository categoryRepository) {
        super(jpaRepository);
        this.categoryRepository = categoryRepository;
    }

    public void checkIfCategoryExistsByName(String name) {
        if (!categoryRepository.existsByName(name)) throw new BusinessException(ErrorType.CATEGORY_NOT_FOUND);
    }

    public boolean checkIfCategoryAlreadyExistsByName(String name) {
        if (categoryRepository.existsByName(name)){
           if(!categoryRepository.findByName(name).isDeleted()){
               throw new BusinessException(ErrorType.CATEGORY_ALREADY_EXISTS);
           }
           return true;
        }
        return false;
    }
    public boolean isCategoryExistsByName(String name) {
        return (categoryRepository.existsByName(name));
    }

    public void checkIfCategoryDeleted(Long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            if(category.isDeleted()) throw new BusinessException(ErrorType.CATEGORY_DELETED);
        }
    }
    public void checkIfCategoryNameTakenBySomeoneElse(String name, Long category_id) {
        if (categoryRepository.existsByNameAndIdNot(name, category_id)) throw new BusinessException(ErrorType.CATEGORY_ALREADY_EXISTS);

    }
}

