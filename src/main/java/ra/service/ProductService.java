package ra.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.model.dto.product.ProductRequestDto;
import ra.model.dto.product.ProductResponseDto;
import ra.model.entity.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Page<ProductResponseDto> pagenate(Pageable pageable);
    ProductResponseDto save(ProductRequestDto  productRequestDto, Long id) throws IOException;
    Product findById(Long id);
    void delete(Long id);
    List<ProductResponseDto> findAll();

}
