package com.blacktogreen.tcm_assistant_back.service;

import com.blacktogreen.tcm_assistant_back.controller.NotFoundException;
import com.blacktogreen.tcm_assistant_back.mapper.DrugCategoryMapper;
import com.blacktogreen.tcm_assistant_back.model.DrugCategory;
import com.blacktogreen.tcm_assistant_back.repository.DrugCategoryRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class DrugCategoryService {

  private final DrugCategoryRepository drugCategoryRepository;
  private final DrugCategoryMapper drugCategoryMapper;

  @Transactional(readOnly = true)
  public List<DrugCategory> getAllCategories() {
    return drugCategoryRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<DrugCategory> getCategoryById(Long id) {
    return drugCategoryRepository.findById(id);
  }

  /**
   * Updates the values of the category that can be set by the user : name and description
   *
   * @param category
   * @return the updated {@link DrugCategory}
   */
  public DrugCategory updateCategoryUserInfos(long id, DrugCategory category) {
    DrugCategory toUpdate =
        drugCategoryRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new NotFoundException(
                        String.format("Cannot find DrugCategory with id %d", category.getId())));
    toUpdate.setName(category.getName());
    toUpdate.setDescription(category.getDescription());
    return saveCategory(toUpdate);
  }

  /**
   * Creates a {@link DrugCategory}
   *
   * @param category
   * @return the created {@link DrugCategory}
   */
  public DrugCategory createCategory(DrugCategory category) {
    return saveCategory(category);
  }

  private DrugCategory saveCategory(DrugCategory category) {
    return drugCategoryRepository.save(category);
  }

  public void deleteCategory(Long id) {
    drugCategoryRepository.deleteById(id);
  }
}
