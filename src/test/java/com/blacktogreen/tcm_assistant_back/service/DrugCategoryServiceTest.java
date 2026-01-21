package com.blacktogreen.tcm_assistant_back.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.blacktogreen.tcm_assistant_back.model.Drug;
import com.blacktogreen.tcm_assistant_back.model.DrugCategory;
import com.blacktogreen.tcm_assistant_back.repository.DrugCategoryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DrugServiceTest {

  @Mock private DrugCategoryRepository drugCategoryRepository;

  @InjectMocks private DrugCategoryService drugCategoryService;

  @Test
  void create_shouldSaveDrugCategory() {
    DrugCategory drugCategory =
        DrugCategory.builder().name("Libèrent surface du vent chaleur").build();
    when(drugCategoryRepository.save(any())).thenReturn(drugCategory);

    DrugCategory result = drugCategoryService.createCategory(drugCategory);

    assertThat(result.getName()).isEqualTo("Libèrent surface du vent chaleur");
    verify(drugCategoryRepository).save(drugCategory);
  }

  @Test
  void update_shouldOnlyUpdateNameAndDescription() {
    Drug drug = Drug.builder().chineseName("ren shen").build();
    DrugCategory drugCategory =
        DrugCategory.builder()
            .name("old-name")
            .description("old-description")
            .drugs(List.of(drug))
            .build();
    when(drugCategoryRepository.save(any(DrugCategory.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    when(drugCategoryRepository.findById(any())).thenReturn(Optional.of(drugCategory));

    DrugCategory updatedDrugCategory =
        DrugCategory.builder()
            .name("new-name")
            .description("new-description")
            .drugs(List.of())
            .build();

    DrugCategory result = drugCategoryService.updateCategoryUserInfos(1L, updatedDrugCategory);

    assertThat(result.getName()).isEqualTo("new-name");
    assertThat(result.getDescription()).isEqualTo("new-description");
    assertThat(result.getDrugs().size()).isEqualTo(1);

    verify(drugCategoryRepository).save(drugCategory);
  }
}
