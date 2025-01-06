package ra.model.dto.product;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponseDto {
    private Long id;
    private String productName;
    private Double price;
    private String image;
    private Boolean status;
    private String categoryName;
}
