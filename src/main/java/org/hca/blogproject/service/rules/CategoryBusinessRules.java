package org.hca.blogproject.service.rules;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hca.blogproject.entity.Category;
import org.hca.blogproject.exception.BusinessException;
import org.hca.blogproject.exception.ErrorType;
import org.hca.blogproject.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryBusinessRules {
    private final CategoryRepository categoryRepository;
    public void checkIfCategoryExistsById(Long id) {
        if (!categoryRepository.existsById(id)) throw new BusinessException(ErrorType.CATEGORY_NOT_FOUND);
    }

    public void checkIfCategoryExistsByName(String name) {
        if (categoryRepository.existsByName(name)) throw new BusinessException(ErrorType.CATEGORY_ALREADY_EXISTS);
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

