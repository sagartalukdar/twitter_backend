package com.react.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Twit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private User user;
	
	private String content;
	private String image;
	private String video;
	
	@OneToMany(mappedBy = "twit",cascade = CascadeType.ALL)
	private List<TwitLike> likes=new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Twit> replyTwits=new ArrayList<>();
	
	@ManyToMany
	private List<User> reTwitUsers=new ArrayList<>();
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	private Twit replyFor;
	
	private boolean isReply;
	private boolean isTwit;
	
	private LocalDateTime createdAt;
	
}
