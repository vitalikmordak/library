package entities;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Book {
    private Long id;
    private String name;
    private byte[] content;
    private Integer pageCount;
    private String isbn;
    private Long genreId;
    private Long authorId;
    private Integer publishDate;
    private Long publisherId;
    private byte[] image;
    private Genre genre;
    private Author author;
    private Publisher publisher;
    private boolean edit;


    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "content")
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Basic
    @Column(name = "page_count")
    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    @Basic
    @Column(name = "isbn")
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "genre_id")
    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    @Basic
    @Column(name = "author_id")
    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Basic
    @Column(name = "publish_year")
    public Integer getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Integer publishYear) {
        this.publishDate = publishYear;
    }

    @Basic
    @Column(name = "publisher_id")
    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    @Basic
    @Column(name = "image")
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(name, book.name) &&
                Arrays.equals(content, book.content) &&
                Objects.equals(pageCount, book.pageCount) &&
                Objects.equals(isbn, book.isbn) &&
                Objects.equals(genreId, book.genreId) &&
                Objects.equals(authorId, book.authorId) &&
                Objects.equals(publishDate, book.publishDate) &&
                Objects.equals(publisherId, book.publisherId) &&
                Arrays.equals(image, book.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, pageCount, isbn, genreId, authorId, publishDate, publisherId);
        result = 31 * result + Arrays.hashCode(content);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @ManyToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Transient
    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }
}
