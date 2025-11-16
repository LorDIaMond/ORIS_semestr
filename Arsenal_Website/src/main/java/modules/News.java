package modules;

import java.sql.Timestamp;

public class News {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private String externalUrl;
    private Timestamp publishedAt;

    public News() {}

    public News(Long id, String title, String content, String imageUrl, String externalUrl, Timestamp publishedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.externalUrl = externalUrl;
        this.publishedAt = publishedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getExternalUrl() { return externalUrl; }
    public void setExternalUrl(String externalUrl) { this.externalUrl = externalUrl; }

    public Timestamp getPublishedAt() { return publishedAt; }
    public void setPublishedAt(Timestamp publishedAt) { this.publishedAt = publishedAt; }

}
