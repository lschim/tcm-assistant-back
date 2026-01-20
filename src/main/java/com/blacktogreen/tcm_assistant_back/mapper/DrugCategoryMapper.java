package com.blacktogreen.tcm_assistant_back.mapper;

import com.blacktogreen.tcm_assistant_back.dto.DrugCategoryDto;
import com.blacktogreen.tcm_assistant_back.model.DrugCategory;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class DrugCategoryMapper {

  public DrugCategoryDto toDto(DrugCategory entity) {
    return new DrugCategoryDto(entity.getId(), entity.getName(), entity.getDescription());
  }

  public DrugCategory toEntity(DrugCategoryDto dto) {
    return new DrugCategory(dto.name(), dto.description(), new ArrayList<>());
  }
}
