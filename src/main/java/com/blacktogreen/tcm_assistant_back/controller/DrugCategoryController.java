package com.blacktogreen.tcm_assistant_back.controller;

import com.blacktogreen.tcm_assistant_back.dto.DrugCategoryDto;
import com.blacktogreen.tcm_assistant_back.mapper.DrugCategoryMapper;
import com.blacktogreen.tcm_assistant_back.model.DrugCategory;
import com.blacktogreen.tcm_assistant_back.service.DrugCategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drug-categories")
public class DrugCategoryController {

  private final DrugCategoryService drugCategoryService;
  private final DrugCategoryMapper drugCategoryMapper;

  @Autowired
  public DrugCategoryController(
      DrugCategoryService drugCategoryService, DrugCategoryMapper drugCategoryMapper) {
    this.drugCategoryService = drugCategoryService;
    this.drugCategoryMapper = drugCategoryMapper;
  }

  @GetMapping
  public List<DrugCategoryDto> getAllCategories() {
    return drugCategoryService.getAllCategories().stream().map(drugCategoryMapper::toDto).toList();
  }

  @GetMapping("/{id}")
  public DrugCategoryDto getCategoryById(@PathVariable Long id) {
    return drugCategoryService
        .getCategoryById(id)
        .map(drugCategoryMapper::toDto)
        .orElseThrow(() -> new RuntimeException("DrugCategory not found"));
  }

  @PostMapping
  public DrugCategoryDto createCategory(@RequestBody CreateDrugCategoryDto createDrugCategoryDto) {
    return drugCategoryMapper.toDto(
        drugCategoryService.createCategory(createDrugCategoryDto.toEntity()));
  }

  @PutMapping("/{id}")
  public DrugCategoryDto updateCategory(
      @PathVariable Long id, @RequestBody DrugCategoryDto category) {
    return drugCategoryMapper.toDto(
        drugCategoryService.updateCategoryUserInfos(drugCategoryMapper.toEntity(category)));
  }

  @DeleteMapping("/{id}")
  public void deleteCategory(@PathVariable Long id) {
    drugCategoryService.deleteCategory(id);
  }

  public record CreateDrugCategoryDto(String name, String description) {
    DrugCategory toEntity() {
      return new DrugCategory(name, description, null);
    }
  }
}
