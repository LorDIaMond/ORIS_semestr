package interfaces.repo;

import modules.Category;

import java.util.List;

public interface ICategoryRepository {
    List<Category> findAll();
}
