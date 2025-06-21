package com.grocery.backend.mappers;

import com.grocery.backend.dto.ProductDto;
import com.grocery.backend.dto.ProductUpdateDto;
import com.grocery.backend.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    ProductDto toProductDto(Product product);

    @Mapping(target = "id",  ignore = true)
    @Mapping(target = "normalizedName", ignore = true)
    @Mapping(target = "location",  ignore = true)
    void updateProduct(ProductUpdateDto productUpdateDto, @MappingTarget Product product);

}
