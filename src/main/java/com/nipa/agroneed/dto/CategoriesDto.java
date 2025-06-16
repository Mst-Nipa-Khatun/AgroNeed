package com.nipa.agroneed.dto;

import lombok.Data;

@Data
public class CategoriesDto extends BaseDto {
    private String categoryName;
    private Long parentId;

}
