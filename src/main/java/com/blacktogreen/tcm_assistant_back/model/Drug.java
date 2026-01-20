package com.blacktogreen.tcm_assistant_back.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "drugs")
public class Drug {

  @Builder
  public Drug(
      String chineseName,
      List<String> alternativeChineseNames,
      String latinName,
      String frenchName,
      DrugNature nature,
      List<DrugFlavor> flavors,
      List<Organ> tropism,
      List<DrugMovement> movements,
      DrugCategory primaryCategory,
      List<String> contraindications,
      List<String> effects,
      String dosage,
      List<DrugAssociation> associationsAsOwner,
      List<DrugAssociation> associationsAsAssociated) {
    this.chineseName = chineseName;
    this.alternativeChineseNames =
        alternativeChineseNames != null ? alternativeChineseNames : new ArrayList<>();
    this.latinName = latinName;
    this.frenchName = frenchName;
    this.nature = nature;
    this.flavors = flavors != null ? flavors : new ArrayList<>();
    this.tropism = tropism != null ? tropism : new ArrayList<>();
    this.movements = movements != null ? movements : new ArrayList<>();
    this.primaryCategory = primaryCategory;
    this.contraindications = contraindications != null ? contraindications : new ArrayList<>();
    this.effects = effects != null ? effects : new ArrayList<>();
    this.dosage = dosage;
    this.associationsAsOwner =
        associationsAsOwner != null ? associationsAsOwner : new ArrayList<>();
    this.associationsAsAssociated =
        associationsAsAssociated != null ? associationsAsAssociated : new ArrayList<>();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter(AccessLevel.NONE)
  private Long id;

  @Column(nullable = false, unique = true)
  private String chineseName; // name in pinyin

  @ElementCollection
  @CollectionTable(name = "alternative_names", joinColumns = @JoinColumn(name = "drug_id"))
  @Column(name = "name")
  private List<String> alternativeChineseNames;

  private String latinName; // nom latin

  private String frenchName;

  @Enumerated(EnumType.STRING)
  private DrugNature nature;

  @ElementCollection(targetClass = DrugFlavor.class)
  @Enumerated(EnumType.STRING)
  @CollectionTable(name = "drug_flavors", joinColumns = @JoinColumn(name = "drug_id"))
  @Column(name = "flavor")
  private List<DrugFlavor> flavors;

  @ElementCollection(targetClass = Organ.class)
  @Enumerated(EnumType.STRING)
  @CollectionTable(name = "drug_tropism", joinColumns = @JoinColumn(name = "drug_id"))
  @Column(name = "tropism")
  private List<Organ> tropism;

  @ElementCollection(targetClass = Organ.class)
  @Enumerated(EnumType.STRING)
  @CollectionTable(name = "drug_movement", joinColumns = @JoinColumn(name = "drug_id"))
  @Column(name = "movement")
  private List<DrugMovement> movements;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private DrugCategory primaryCategory;

  @ElementCollection
  @CollectionTable(name = "drug_contraindications", joinColumns = @JoinColumn(name = "drug_id"))
  @Column(name = "contraindication", length = 1000)
  private List<String> contraindications;

  @ElementCollection
  @CollectionTable(name = "drug_effects", joinColumns = @JoinColumn(name = "drug_id"))
  @Column(name = "effect", length = 1000)
  private List<String> effects;

  private String dosage;

  @OneToMany(mappedBy = "drug")
  private List<DrugAssociation> associationsAsOwner;

  @OneToMany(mappedBy = "associatedDrug")
  private List<DrugAssociation> associationsAsAssociated;

  public List<Drug> getAllAssociatedDrugs() {
    Set<Drug> all = new HashSet<>();
    if (associationsAsOwner != null) {
      associationsAsOwner.forEach(a -> all.add(a.getAssociatedDrug()));
    }
    if (associationsAsAssociated != null) {
      associationsAsAssociated.forEach(a -> all.add(a.getDrug()));
    }
    return new ArrayList<>(all);
  }
}
