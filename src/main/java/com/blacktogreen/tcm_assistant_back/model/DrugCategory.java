package com.blacktogreen.tcm_assistant_back.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DrugCategory {

  @Builder
  public DrugCategory(String name, String description, List<Drug> drugs) {
    this.name = name;
    this.description = description;
    this.drugs = drugs != null ? drugs : new ArrayList<>();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter(AccessLevel.NONE)
  private Long id;

  private String name;

  private String description;

  @OneToMany(mappedBy = "primaryCategory")
  private List<Drug> drugs;
}
