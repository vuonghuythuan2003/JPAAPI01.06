package ra.service;

import org.springframework.data.domain.Page;
import ra.model.entity.Category;
import org.springframework.data.domain.Pageable;


public interface CategoryService {
    Page<Category> findAll(Pageable pageable);
    Category findById(Long id);
    Category save(Category category);
    void delete(Long id);

}
