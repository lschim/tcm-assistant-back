package com.blacktogreen.tcm_assistant_back.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = "drug_association",
    uniqueConstraints = @UniqueConstraint(columnNames = {"drug_id", "associated_drug_id"}))
public class DrugAssociation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter(AccessLevel.NONE)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "drug_id")
  private Drug drug;

  @ManyToOne
  @JoinColumn(name = "associated_drug_id")
  private Drug associatedDrug;

  @ElementCollection
  @CollectionTable(
      name = "drug_association_effects",
      joinColumns = @JoinColumn(name = "association_id"))
  @Column(name = "effect")
  private List<String> effects;

  @Builder
  public DrugAssociation(Drug drug, Drug associatedDrug, List<String> effects) {
    this.drug = drug;
    this.associatedDrug = associatedDrug;
    this.effects = effects != null ? effects : new ArrayList<>();
  }
}
