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
        Category category;
        if(categoryBusinessRules.checkIfCategoryAlreadyExistsByName(dto.name())){
            category = categoryRepository.findByName(dto.name());
            category.setDeleted(false);
            if(category.getDescription() == null){
                category.setDescription(dto.description());
            }
        }else{
            category = CategoryMapper.INSTANCE.categoryRequestDtoToCategory(dto);
        }
        categoryRepository.save(category);
        return CategoryMapper.INSTANCE.categoryToCategoryResponseDto(category);
    }
    
    public CategoryResponseDto updateDto(Long id, CategoryRequestDto request) {
        categoryBusinessRules.checkIfCategoryNameTakenBySomeoneElse(request.name(), id);
        categoryBusinessRules.checkIfCategoryDeleted(id);
        categoryBusinessRules.checkIfCategoryExistsById(id);

        Category categoryToUpdate = CategoryMapper.INSTANCE.categoryRequestDtoToCategory(request);
        categoryToUpdate.setId(id);
        categoryRepository.save(categoryToUpdate);
        return CategoryMapper.INSTANCE.categoryToCategoryResponseDto(categoryToUpdate);
    }
    public CategoryResponseDto findDtoById(Long id){
        categoryBusinessRules.checkIfCategoryDeleted(id);
        categoryBusinessRules.checkIfCategoryExistsById(id);

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
        categoryBusinessRules.checkIfCategoryDeleted(id);
        categoryBusinessRules.checkIfCategoryExistsById(id);

        Category categoryToDelete = categoryRepository.findById(id).get();//checked at business rules
        categoryToDelete.setDeleted(true);
        categoryToDelete.setDeletedAt(LocalDateTime.now().toString());
        categoryRepository.save(categoryToDelete);
        return CategoryMapper.INSTANCE.categoryToCategoryResponseDto(categoryToDelete);
    }

    public CategoryResponseDto deleteDto(Long id) {
        categoryBusinessRules.checkIfCategoryExistsById(id);

        Category categoryToDelete = categoryRepository.findById(id).get();//checked at business rules
        categoryRepository.delete(categoryToDelete);
        return CategoryMapper.INSTANCE.categoryToCategoryResponseDto(categoryToDelete);
    }

    public Category save(Category category){
        return categoryRepository.save(category);
    }

    public Category findCategoryByNameReturnCategory(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }
    public CategoryResponseDto findCategoryByNameReturnDto(String categoryName) {
        categoryBusinessRules.checkIfCategoryExistsByName(categoryName);

        return categoryMapper.categoryToCategoryResponseDto(categoryRepository.findByName(categoryName));
    }

}