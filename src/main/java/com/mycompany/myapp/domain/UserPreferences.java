package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A UserPreferences.
 */
@Entity
@Table(name = "T_USERPREFERENCES")
public class UserPreferences implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "read_books")
    private Integer readBooks;

    @ManyToOne
    private User user;

    @ManyToOne
    private Genre genre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReadBooks() {
        return readBooks;
    }

    public void setReadBooks(Integer readBooks) {
        this.readBooks = readBooks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserPreferences userPreferences = (UserPreferences) o;

        if (id != null ? !id.equals(userPreferences.id) : userPreferences.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "UserPreferences{" +
                "id=" + id +
                ", readBooks='" + readBooks + "'" +
                '}';
    }
}
