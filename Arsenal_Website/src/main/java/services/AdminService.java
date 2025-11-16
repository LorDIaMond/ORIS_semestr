package services;

import interfaces.repo.ICategoryRepository;
import interfaces.repo.IProductRepository;
import interfaces.serv.IAdminService;
import modules.Category;
import modules.Product;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AdminService implements IAdminService {

    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;

    public AdminService(IProductRepository productRepository, ICategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createProduct(String name, String description, double price, long categoryId, Part imagePart, String contextPath) {
        if (imagePart == null || imagePart.getSize() == 0) {
            throw new IllegalArgumentException("Image is required");
        }

        // Обработка изображения
        String fileName = processImage(imagePart, name);
        saveImageToFileSystem(imagePart, fileName, contextPath);

        Product product = new Product();
        product.setName(name.trim());
        product.setDescription(description != null ? description.trim() : "");
        product.setPrice(price);
        product.setImageUrl(fileName);
        product.setCategoryId(categoryId);

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(long productId) {
        productRepository.delete(productId);
    }

    private String processImage(Part imagePart, String productName) {
        String originalFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
        String ext = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
        if (!ext.matches("\\.(jpg|jpeg|png|gif)$")) {
            throw new IllegalArgumentException("Invalid image format");
        }

        String cleanName = productName.replaceAll("[^a-zA-Z0-9\\s\\-_]", "")
                .replace("/", "-")
                .replace("\\", "-")
                .replace(":", "-")
                .replace(".", "-")
                .trim()
                .replaceAll("\\s+", "_")
                .replaceAll("[-_]{2,}", "-");

        return cleanName + "_" + System.currentTimeMillis() + ext;
    }

    private void saveImageToFileSystem(Part imagePart, String fileName, String contextPath) {
        try {
            String uploadPath = contextPath + "/images/products";
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            imagePart.write(uploadDir.resolve(fileName).toString());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public void updateProduct(long id, String name, String description, double price, long categoryId) {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        product.setName(name.trim());
        product.setDescription(description != null ? description.trim() : "");
        product.setPrice(price);
        product.setCategoryId(categoryId);

        productRepository.update(product);
    }
}
