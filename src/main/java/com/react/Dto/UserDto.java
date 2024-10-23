package com.react.Dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Long id;
	private String fullName;
	
	private String website;
	
	private String location;
	
	private String bio;
	private String birthDate;
	private String email;
	private String mobile;
	private String image;
	private String backgroundImage;
	private boolean req_user;
	private boolean login_with_google;

	private List<TwitDto> twits=new ArrayList<>();
    
	private List<UserDto> followers=new ArrayList<>();
	
	private List<UserDto> following=new ArrayList<>();
	
	private boolean followed;
	
	private boolean isVerified;
	
	
	
}
