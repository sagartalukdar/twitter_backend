package com.react.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.react.Dto.LikeDto;
import com.react.Dto.Mapper.LikeDtoMapper;
import com.react.Exception.TwitException;
import com.react.Exception.UserException;
import com.react.Model.TwitLike;
import com.react.Model.User;
import com.react.Service.LikeService;
import com.react.Service.UserService;

@RestController
@RequestMapping("/api")
public class LikeController {

	@Autowired
	private UserService userService;
	@Autowired
	private LikeService likeService;
	
	@PostMapping("/{twitId}/like")
	public ResponseEntity<LikeDto> likeTwit(@PathVariable Long twitId,@RequestHeader("Authorization")String jwt) throws UserException, TwitException{
		User user=userService.findUserProfileByJwt(jwt);
		TwitLike like=likeService.likeTwit(twitId, user);
		
		LikeDto likeDto=LikeDtoMapper.toLikeDto(like, user);
		return new ResponseEntity<LikeDto>(likeDto,HttpStatus.CREATED);
	}
	
	@GetMapping("/twit/{twitId}")
	public ResponseEntity<List<LikeDto>> getAllLikes(@PathVariable Long twitId,@RequestHeader("Authorization")String jwt) throws UserException, TwitException{
		User user=userService.findUserProfileByJwt(jwt);
		List<TwitLike> likes=likeService.getAllLikes(twitId);
		
		List<LikeDto> likeDtos=LikeDtoMapper.toLikeDtos(likes, user);
		return new ResponseEntity<List<LikeDto>>(likeDtos,HttpStatus.CREATED);
	}
	
}
