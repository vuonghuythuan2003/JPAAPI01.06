package ra.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.dto.product.ProductRequestDto;
import ra.model.dto.product.ProductResponseDto;
import ra.model.entity.Product;
import ra.repository.ProductRepository;
import ra.service.CategoryService;
import ra.service.ProductService;
import ra.service.UploadFileService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UploadFileService uploadFileService;

    @Override
    public List<ProductResponseDto> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> ProductResponseDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .status(product.getStatus())
                .image(product.getImage())
                .categoryName(product.getCategory().getCategoryName())
                .build()
        ).collect(Collectors.toList());
    }

    @Override
    public Page<ProductResponseDto> pagenate(Pageable pageable) {
        List<Product> products = productRepository.findAll(pageable).getContent();
        List<ProductResponseDto> responseDTO;
        responseDTO = products.stream().map(product -> ProductResponseDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .status(product.getStatus())
                .image(product.getImage())
                .categoryName(product.getCategory().getCategoryName())
                .build()
        ).collect(Collectors.toList());
        return new PageImpl<>(responseDTO, pageable, productRepository.count());
    }

    @Override
    public ProductResponseDto save(ProductRequestDto productRequestDto, Long id) throws IOException {
        String fileName;

        // Xử lý upload file
        if (productRequestDto.getImage() != null && !productRequestDto.getImage().isEmpty()) {
            try {
                fileName = uploadFileService.uploaFile(productRequestDto.getImage());
            } catch (IOException e) {
                throw new RuntimeException("File upload failed: " + e.getMessage());
            }
        } else {
            fileName = null;
        }

        Product product;

        if (id == null) {
            // Thêm mới sản phẩm
            product = Product.builder()
                    .productName(productRequestDto.getProductName())
                    .price(productRequestDto.getPrice())
                    .status(productRequestDto.getStatus())
                    .image(fileName)
                    .category(categoryService.findById(productRequestDto.getCategoryId()))
                    .build();
        } else {
            // Cập nhật sản phẩm
            product = productRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy mã sản phẩm: " + id));

            product.setProductName(productRequestDto.getProductName());
            product.setPrice(productRequestDto.getPrice());
            product.setStatus(productRequestDto.getStatus());
            product.setCategory(categoryService.findById(productRequestDto.getCategoryId()));

            if (fileName != null) {
                product.setImage(fileName);
            }
        }

        // Lưu sản phẩm
        Product savedProduct = productRepository.save(product);

        // Chuyển đổi sang DTO
        return ProductResponseDto.builder()
                .id(savedProduct.getId())
                .productName(savedProduct.getProductName())
                .price(savedProduct.getPrice())
                .status(savedProduct.getStatus())
                .image(savedProduct.getImage())
                .categoryName(savedProduct.getCategory().getCategoryName())
                .build();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy mã sản phẩm: " + id));
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy mã sản phẩm: " + id);
        }
        productRepository.deleteById(id);
    }


}
