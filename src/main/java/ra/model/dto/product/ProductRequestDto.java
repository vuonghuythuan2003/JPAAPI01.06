package ra.model.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String productName;
    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Giá phải lớn hơn không")
    private Double price;
    private MultipartFile image;
    private Boolean status;
    @NotNull(message = "Category không được để trống")
    private Long categoryId;

}
