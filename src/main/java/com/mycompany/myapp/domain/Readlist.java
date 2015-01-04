package com.mycompany.myapp.domain;

import java.io.Serializable;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "T_READLIST")
public class Readlist implements Serializable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "user_login", referencedColumnName = "login")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;
	
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Readlist readlist = (Readlist) o;

        if (id != null ? !id.equals(readlist.id) : readlist.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
    
    @Override
    public String toString() {
    	return "Readlist{" +
                "id=" + id +
                '}';
    }

}
