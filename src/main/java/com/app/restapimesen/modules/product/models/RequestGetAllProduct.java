package com.app.restapimesen.modules.product.models;

import com.app.restapimesen.models.TypeOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestGetAllProduct {
    private String storeId;
    private Optional<TypeOrder> order = Optional.of(TypeOrder.ASC);
    private Optional<List<Integer>> categories;
    private Optional<TypePrice> price;
}
