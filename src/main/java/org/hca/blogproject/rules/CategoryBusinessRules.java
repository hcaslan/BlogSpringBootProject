package org.hca.blogproject.rules;

import org.hca.blogproject.entity.Category;
import org.hca.blogproject.exception.ValidationException;
import org.hca.blogproject.exception.ErrorType;
import org.hca.blogproject.repository.CategoryRepository;
import org.hca.blogproject.rules.manager.BusinessRulesManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * @BusinessRules
 * These classes provide a structured approach to validate entities and perform custom validation checks specific to the project's requirements
 */
@Service
public class CategoryBusinessRules extends BusinessRulesManager<Category,Long> {
    private final CategoryRepository categoryRepository;

    public CategoryBusinessRules(JpaRepository<Category, Long> jpaRepository, CategoryRepository categoryRepository) {
        super(jpaRepository);
        this.categoryRepository = categoryRepository;
    }

    public void checkIfCategoryExistsByName(String name) {
        if (!categoryRepository.existsByName(name)) throw new ValidationException(ErrorType.CATEGORY_NOT_FOUND);
    }

    public boolean isCategoryAlreadyExistsByName(String name) {
        if (categoryRepository.existsByName(name)){
           if(!categoryRepository.findByName(name).get().isDeleted()){ //checked at line 30
               throw new ValidationException(ErrorType.CATEGORY_ALREADY_EXISTS);
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
            if(category.isDeleted()) throw new ValidationException(ErrorType.CATEGORY_DELETED);
        }
    }
    public void checkIfCategoryDeleted(String name){
        Optional<Category> optionalCategory = categoryRepository.findByName(name);
        if(optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            if(category.isDeleted()) throw new ValidationException(ErrorType.CATEGORY_DELETED);
        }
    }
    public void checkIfCategoryNameTakenBySomeoneElse(String name, Long category_id) {
        if (categoryRepository.existsByNameAndIdNot(name, category_id)) throw new ValidationException(ErrorType.CATEGORY_ALREADY_EXISTS);

    }
    public void validateCategoryFieldLengths(Category category){
        try{
            Field nameField = category.getClass().getDeclaredField("name");
            validateFieldLength(category.getName(),nameField);
            Field descriptionField = category.getClass().getDeclaredField("description");
            validateFieldLength(category.getDescription(),descriptionField);

        }catch (NoSuchFieldException e){
            throw new ValidationException(ErrorType.FIELD_ERROR);
        }
    }
}

