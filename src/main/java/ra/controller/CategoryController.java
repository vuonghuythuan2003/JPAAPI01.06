package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.DataError;
import ra.model.entity.Category;
import ra.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")

public class CategoryController {
    @Autowired
    private CategoryService categoryService;

//    @GetMapping
//    public ResponseEntity<List<Category>> findAll(){
//        List<Category> categorys = categoryService.findAll();
//        return new ResponseEntity<>(categorys, HttpStatus.OK);
//    }
    @GetMapping("")
    public ResponseEntity<Page<Category>> index(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "3") int limit,
            @RequestParam(name = "sort_by", defaultValue = "categoryName") String sortBy,
            @RequestParam(name = "oderBy", defaultValue = "asc") String oderBy
    ) {
        Sort sort = oderBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Category> categories = categoryService.findAll(pageable);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Category> create(@RequestBody Category category){
        Category categoryNew = categoryService.save(category);
        return new ResponseEntity<>(categoryNew, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if(category == null) {
            return new ResponseEntity<>(new DataError("Mã id không tồn tại", 404),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Category category){
        if(categoryService.findById(id) != null){
            category.setId(id);
            Category categoryNew = categoryService.save(category);
            return new ResponseEntity<>(categoryNew, HttpStatus.OK);
        }
        return new ResponseEntity<>(new DataError("ID không tồn tại", 404), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if(category == null) {
            return new ResponseEntity<>(new DataError("Mã id không tồn tại", 404), HttpStatus.NOT_FOUND);
        }
        categoryService.delete(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }



}
