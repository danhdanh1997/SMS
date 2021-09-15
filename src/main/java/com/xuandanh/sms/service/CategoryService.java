package com.xuandanh.sms.service;

import com.xuandanh.sms.domain.Category;
import com.xuandanh.sms.dto.CategoryDTO;
import com.xuandanh.sms.exception.ResourceNotFoundException;
import com.xuandanh.sms.mapper.CategoryMapper;
import com.xuandanh.sms.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Optional<CategoryDTO>findOne(int categoriesId){
        return Optional.ofNullable(categoryMapper.categoryToCategoryDTO(categoryRepository
                .findById(categoriesId).orElseThrow(()-> new ResourceNotFoundException("category with id:"+categoriesId+" not exist"))));
    }

    public Optional<List<CategoryDTO>>findAll(){
        return Optional.ofNullable(categoryMapper.categoryToCategoryDTO(
                categoryRepository.findAll()));
    }

    public Optional<CategoryDTO>createCategory(Category category){
        return Optional.of(categoryMapper.categoryToCategoryDTO(categoryRepository.save(category)));
    }

    public Optional<CategoryDTO>updateCategory(Category category){
        return Optional.ofNullable(Optional.of(categoryRepository
                .findById(category.getCategoriesId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(category1 -> {
                    category1.setCategoriesName(category.getCategoriesName());
                    category1.setLastUpdate(category.getLastUpdate());
                    return categoryMapper.categoryToCategoryDTO(categoryRepository.save(category1));
                }).orElseThrow(() -> new ResourceNotFoundException("category with id:" + category.getCategoriesId() + " not exist")));
    }
}
