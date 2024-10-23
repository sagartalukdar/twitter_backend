package com.react.Dto.Mapper;

import java.util.ArrayList;
import java.util.List;

import com.react.Dto.LikeDto;
import com.react.Dto.TwitDto;
import com.react.Dto.UserDto;
import com.react.Model.TwitLike;
import com.react.Model.User;

public class LikeDtoMapper {

	public static LikeDto toLikeDto(TwitLike like,User reqUser) {
		UserDto user=UserDtoMapper.toUserDto(reqUser, like.getUser());
		UserDto reqUserDto=UserDtoMapper.toUserDto(reqUser,like.getUser());
		TwitDto twit=TwitDtoMapper.toTwitDto(like.getTwit(), reqUser);
				
		LikeDto likeDto=new LikeDto();
		likeDto.setId(like.getId());
		likeDto.setTwit(twit);
		likeDto.setUser(user);
		return likeDto;
	}
	
	public static List<LikeDto> toLikeDtos(List<TwitLike> likes,User reqUser){
		List<LikeDto>likeDtos=new ArrayList<>();
		for(TwitLike like:likes) {
			UserDto user=UserDtoMapper.toUserDto(reqUser, like.getUser());
			TwitDto twit=TwitDtoMapper.toTwitDto(like.getTwit(), reqUser);
			
			LikeDto likeDto=new LikeDto();
			likeDto.setId(like.getId());
			likeDto.setTwit(twit);
			likeDto.setUser(user);
			likeDtos.add(likeDto);
		}
		return likeDtos;
	}
}
