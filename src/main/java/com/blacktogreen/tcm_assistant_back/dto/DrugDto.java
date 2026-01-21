package com.blacktogreen.tcm_assistant_back.dto;

import java.util.List;

public record DrugDto(
    Long id,
    String name,
    String latinName,
    List<String> alternativeChineseNames,
    String nature,
    List<String> flavors,
    List<String> movement,
    String categoryName,
    int numberOfStars) {}
