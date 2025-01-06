package ra.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ra.model.dto.DataError;
import ra.model.dto.product.ProductRequestDto;
import ra.model.dto.product.ProductResponseDto;
import ra.model.entity.Product;
import ra.service.ProductService;
import ra.service.UploadFileService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UploadFileService uploadFileService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findAll(){
        List<ProductResponseDto> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/pagenateVHT")
    public ResponseEntity<Page<ProductResponseDto>> pagenate(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "3") int limit,
            @RequestParam(name = "sortBy", defaultValue = "productName") String sortBy,
            @RequestParam(name = "orderBy", defaultValue = "asc") String orderBy
    ) {
        Sort sort = orderBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<ProductResponseDto> products = productService.pagenate(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @ModelAttribute ProductRequestDto productRequestDto) {
        try {
            ProductResponseDto productResponse = productService.save(productRequestDto, null);
            return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataError("Lỗi khi thêm sản phẩm: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Product productById = productService.findById(id);
        if(productById == null){
            return new ResponseEntity<>(new DataError("Không tìm thấy ID", 404), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productById, HttpStatus.OK);
    }

    @PutMapping(value = "/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> edit(
            @PathVariable Long id,
            @Valid @ModelAttribute ProductRequestDto productRequestDto) throws IOException {

        Product productById = productService.findById(id);
        if (productById == null) {
            return new ResponseEntity<>(new DataError("Không tìm thấy ID", 404), HttpStatus.NOT_FOUND);
        }

        try {
            ProductResponseDto productResponse = productService.save(productRequestDto, id);
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new DataError("Lỗi khi chỉnh sửa sản phẩm: " + e.getMessage(), 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Product productById = productService.findById(id);
        if(productById == null){
            return new ResponseEntity<>(new DataError("Không tìm thấy ID", 400), HttpStatus.BAD_REQUEST );
        }
        productService.delete(id);
        return new ResponseEntity<>(productById, HttpStatus.OK);
    }

}
