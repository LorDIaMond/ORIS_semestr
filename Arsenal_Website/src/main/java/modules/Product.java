package modules;

public class Product {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Long categoryId;

    public Product(Long id, Long categoryId, String imageUrl, Double price, String description, String name) {
        this.id = id;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.name = name;
    }
    public Product(Long categoryId, String imageUrl, Double price, String description, String name) {
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.name = name;
    }

    public Product() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
}