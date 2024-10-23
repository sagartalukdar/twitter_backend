package com.react.Dto.Mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.react.Dto.UserDto;
import com.react.Model.User;
import com.react.Util.UserUtil;

public class UserDtoMapper {

	public static UserDto toUserDto(User reqUser,User user) {
		UserDto userDto=new UserDto();
		userDto.setId(user.getId());
		
		userDto.setReq_user(UserUtil.isReqUser(reqUser, user));
		userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
		
		userDto.setEmail(user.getEmail());
		userDto.setFullName(user.getFullName());
		userDto.setImage(user.getImage());
		userDto.setBackgroundImage(user.getBackgroundImage());
		userDto.setBio(user.getBio());
		userDto.setBirthDate(user.getBirthDate());
		userDto.setFollowers(toUserDtos(user.getFollowers()));
		userDto.setFollowing(toUserDtos(user.getFollowings()));
		userDto.setLogin_with_google(user.isLogin_with_google());
		userDto.setLocation(user.getLocation());
		userDto.setWebsite(user.getWebsite());

		return userDto;
	}
	
	public static List<UserDto> toUserDtos(List<User> followers){
		List<UserDto> userDtos=new ArrayList<>();
		
		for(User user:followers) {
			UserDto userDto=new UserDto();
			userDto.setId(user.getId());
			userDto.setEmail(user.getEmail());
			userDto.setFullName(user.getFullName());
			userDto.setImage(user.getImage());
			userDtos.add(userDto);
		}
		return userDtos;
	}
	
	
}
