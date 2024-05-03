package org.hca.blogproject.service;

import lombok.RequiredArgsConstructor;
import org.hca.blogproject.dto.request.CategoryRequestDto;
import org.hca.blogproject.dto.response.CategoryResponseDto;
import org.hca.blogproject.entity.Category;
import org.hca.blogproject.mapper.CategoryMapper;
import org.hca.blogproject.repository.CategoryRepository;
import org.hca.blogproject.service.rules.CategoryBusinessRules;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryBusinessRules categoryBusinessRules;

    public CategoryResponseDto saveDto(CategoryRequestDto dto) {
        categoryBusinessRules.checkIfNull(dto.name());

        Category category;
        if(categoryBusinessRules.isCategoryAlreadyExistsByName(dto.name())){
            category = categoryRepository.findByName(dto.name()).get(); //checked at line 28
            category.setDeleted(false);
            category.setDeletedAt(null);
            if(category.getDescription() == null){
                category.setDescription(dto.description());
            }
        }else{
            category = CategoryMapper.INSTANCE.categoryRequestDtoToCategory(dto);
            categoryBusinessRules.validateCategoryFieldLengths(category);
        }
        categoryRepository.save(category);
        return CategoryMapper.INSTANCE.categoryToCategoryResponseDto(category);
    }
    
    public CategoryResponseDto updateDto(Long id, CategoryRequestDto request) {
        categoryBusinessRules.checkIfNull(request.name());
        categoryBusinessRules.checkIfCategoryNameTakenBySomeoneElse(request.name(), id);
        categoryBusinessRules.checkIfCategoryDeleted(id);
        categoryBusinessRules.checkIfExistsById(id);

        Category categoryToUpdate = CategoryMapper.INSTANCE.categoryRequestDtoToCategory(request);
        categoryBusinessRules.validateCategoryFieldLengths(categoryToUpdate);

        categoryToUpdate.setId(id);
        categoryRepository.save(categoryToUpdate);
        return CategoryMapper.INSTANCE.categoryToCategoryResponseDto(categoryToUpdate);
    }

    public CategoryResponseDto findDtoById(Long id){
        categoryBusinessRules.checkIfExistsById(id);
        categoryBusinessRules.checkIfCategoryDeleted(id);

        return CategoryMapper.INSTANCE.categoryToCategoryResponseDto(categoryRepository.findById(id).get());//checked at business rules
    }

    public List<CategoryResponseDto> findAllDto() {
        return  categoryRepository.findAll()
                .stream()
                .filter(category ->!category.isDeleted())
                .map(CategoryMapper.INSTANCE::categoryToCategoryResponseDto)
                .collect(Collectors.toList());
    }

    public CategoryResponseDto setToDeletedDto(Long id) {
        categoryBusinessRules.checkIfExistsById(id);
        categoryBusinessRules.checkIfCategoryDeleted(id);

        Category categoryToDelete = categoryRepository.findById(id).get();//checked at business rules
        categoryToDelete.setDeleted(true);
        categoryToDelete.setDeletedAt(LocalDateTime.now().toString());
        categoryRepository.save(categoryToDelete);
        return CategoryMapper.INSTANCE.categoryToCategoryResponseDto(categoryToDelete);
    }

    public Category save(Category category){
        categoryBusinessRules.checkIfNull(category.getName());

        return categoryRepository.save(category);
    }

    public Category findCategoryByNameReturnCategory(String categoryName) {
        categoryBusinessRules.checkIfCategoryExistsByName(categoryName);

        return categoryRepository.findByName(categoryName).get(); //checked at business rules
    }

    public CategoryResponseDto findCategoryByNameReturnDto(String categoryName) {
        categoryBusinessRules.checkIfCategoryExistsByName(categoryName);

        return categoryMapper.categoryToCategoryResponseDto(categoryRepository.findByName(categoryName).get());//checked at business rules
    }
//    public CategoryResponseDto deleteDto(Long id) {
//        categoryBusinessRules.checkIfExistsById(id);
//
//        Category categoryToDelete = categoryRepository.findById(id).get();//checked at business rules
//        categoryRepository.delete(categoryToDelete);
//        return CategoryMapper.INSTANCE.categoryToCategoryResponseDto(categoryToDelete);
//    }
}