package com.jonnie.ecommerce.product;

import jakarta.validation.constraints.NotNull;

public record PurchaseRequest(
        @NotNull(message = "product  should be present")
        Integer productId,
        @NotNull(message = "quantity should be present")
        double quantity
) {

}
