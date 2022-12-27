package com.coding.blog.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
	
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
	private Integer postId;
	
    @Column(name = "post_title" , length = 100, nullable = false)
	private String title;
	
    @Column(length =  10000)
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
	
	//relationship with category entity primary key
	@ManyToOne
	@JoinColumn(name = "category_id")  // to change name of column joined
	private Category category;
	
	//relationship with user entity primary key
	@ManyToOne
	private User user;
	
	//relationship with comment entity primary key
	@OneToMany(mappedBy = "post" , cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<>();
	

}
