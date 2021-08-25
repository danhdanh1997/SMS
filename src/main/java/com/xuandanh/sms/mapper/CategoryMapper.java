package com.xuandanh.sms.mapper;

import com.xuandanh.sms.domain.Category;
import com.xuandanh.sms.dto.CategoryDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Component
public class CategoryMapper {
    public List<CategoryDTO> addressToAddressDTOs(List<Category> categoryList) {
        return categoryList.stream().filter(Objects::nonNull).map(this::categoryToCategoryDTO).collect(Collectors.toList());
    }

    public CategoryDTO categoryToCategoryDTO(Category category){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(category,CategoryDTO.class);
    }

    public List<CategoryDTO>categoryToCategoryDTO(List<Category>categoryList){
        return categoryList.stream().filter(Objects::nonNull).map(this::categoryToCategoryDTO).collect(Collectors.toList());
    }
    public List<Category> categoryDTOToCategory(List<CategoryDTO> categoryDTOList) {
        return categoryDTOList.stream().filter(Objects::nonNull).map(this::categoryDTOToCategory).collect(Collectors.toList());
    }

    public Category categoryDTOToCategory(CategoryDTO categoryDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(categoryDTO , Category.class);
    }
}