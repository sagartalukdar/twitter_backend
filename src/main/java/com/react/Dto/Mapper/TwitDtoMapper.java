package com.react.Dto.Mapper;

import java.util.ArrayList;
import java.util.List;

import com.react.Dto.TwitDto;
import com.react.Dto.UserDto;
import com.react.Model.Twit;
import com.react.Model.User;
import com.react.Util.TwitUtil;

public class TwitDtoMapper {

	public static TwitDto toTwitDto(Twit twit,User reqUser) {
		UserDto user=UserDtoMapper.toUserDto(reqUser, twit.getUser());
		boolean isLiked=TwitUtil.isLikedByReqUser(reqUser,twit);
		boolean isRetwited=TwitUtil.isReTwitedByReqUser(reqUser, twit);
		
		List<Long>retwitedUserId=new ArrayList<>();
		for(User user1:twit.getReTwitUsers()) {
			retwitedUserId.add(user1.getId());
		}
		TwitDto twitDto=new TwitDto();
		twitDto.setId(twit.getId());
		twitDto.setContent(twit.getContent());
		twitDto.setCreatedAt(twit.getCreatedAt());
		twitDto.setTotalLikes(twit.getLikes().size());
		twitDto.setTotalReplies(twit.getReplyTwits().size());
		twitDto.setTotalRetweets(twit.getReTwitUsers().size());
		twitDto.setUser(user);
		twitDto.setLiked(isLiked);
		twitDto.setRetwit(isRetwited);
		twitDto.setRetwitUsersId(retwitedUserId);
		twitDto.setReplyTwits(toTwitDtos(twit.getReplyTwits(), reqUser));
		twitDto.setVideo(twit.getVideo());
		twitDto.setImage(twit.getImage());
		return twitDto;
	}
	
	public static List<TwitDto> toTwitDtos(List<Twit> twits,User reqUser) {
		List<TwitDto> twitDtos=new ArrayList<>();
		for(Twit twit:twits) {
			TwitDto twitDto=toReplyTwitDto(twit,reqUser);
			twitDtos.add(twitDto);
		}
		return twitDtos;
	}

	private static TwitDto toReplyTwitDto(Twit twit, User reqUser) {
		UserDto user=UserDtoMapper.toUserDto(reqUser, twit.getUser());
		boolean isLiked=TwitUtil.isLikedByReqUser(reqUser,twit);
		boolean isRetwited=TwitUtil.isReTwitedByReqUser(reqUser, twit);
		
		List<Long>retwitedUserId=new ArrayList<>();
		for(User user1:twit.getReTwitUsers()) {
			retwitedUserId.add(user1.getId());
		}
		List<TwitDto> replyTwits=new ArrayList<>();
		for(Twit twit2:twit.getReplyTwits()) {
			replyTwits.add(toTwitDto(twit2, reqUser));
		}
		
		TwitDto twitDto=new TwitDto();
		twitDto.setId(twit.getId());
		twitDto.setContent(twit.getContent());
		twitDto.setCreatedAt(twit.getCreatedAt());
		twitDto.setTotalLikes(twit.getLikes().size());
		twitDto.setTotalReplies(twit.getReplyTwits().size());
		twitDto.setTotalRetweets(twit.getReTwitUsers().size());
		twitDto.setUser(user);
		twitDto.setLiked(isLiked);
		twitDto.setRetwit(isRetwited);
		twitDto.setRetwitUsersId(retwitedUserId);
		twitDto.setVideo(twit.getVideo());
		twitDto.setImage(twit.getImage());
		twitDto.setReplyTwits(replyTwits);
		return twitDto;
	}
	
	
}
