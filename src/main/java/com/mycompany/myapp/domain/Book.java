package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Book.
 */
@Entity
@Table(name = "T_BOOK")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "summary")
    private String summary;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "rate")
    private int rate;
    
    @Column(name = "rate_people")
    private int rate_people;

    @Column(name = "picture")   
    private String picture;

    @ManyToMany
    private Set<Author> authors = new HashSet<>();

    @OneToMany(mappedBy = "book")   
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany
    private Set<User> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
    
    public int getRatePeople() {
        return rate_people;
    }

    public void setRatePeople(int rate_people) {
        this.rate_people = rate_people;
    }

    public String getPicture() {
        return  picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Set<Author> getAuthors() {
    	return authors;
    }

    public void setAuthors(Set<Author> authors) {    	
        this.authors = authors;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Book book = (Book) o;

        if (id != null ? !id.equals(book.id) : book.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + "'" +
                ", summary='" + summary + "'" +
                ", filePath='" + filePath + "'" +
                ", rate='" + rate + "'" +
                ", picture='" + picture + "'" +
                ", comments='" + comments.toString() + "'"+
                '}';
    }
}
