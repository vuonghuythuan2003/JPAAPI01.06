package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
}
