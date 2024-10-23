package com.react.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.react.Dto.TwitDto;
import com.react.Dto.Mapper.TwitDtoMapper;
import com.react.Exception.TwitException;
import com.react.Exception.UserException;
import com.react.Model.Twit;
import com.react.Model.User;
import com.react.Request.TwitReplyRequest;
import com.react.Response.ApiResponse;
import com.react.Service.TwitService;
import com.react.Service.UserService;

@RestController
@RequestMapping("/api/twits")
public class TwitController {

	@Autowired
	private TwitService twitService;
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public ResponseEntity<List<TwitDto>> getAllTwits(@RequestHeader("Authorization")String jwt) throws UserException{
		User user=userService.findUserProfileByJwt(jwt);
		List<Twit> twits=twitService.findAllTwit();
		
		List<TwitDto> twitDtos=TwitDtoMapper.toTwitDtos(twits, user);
		
		return new ResponseEntity<List<TwitDto>>(twitDtos,HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<TwitDto> createTwit(@RequestBody Twit req,@RequestHeader("Authorization")String jwt) throws UserException{
		User user=userService.findUserProfileByJwt(jwt);
		Twit twit=twitService.createTwit(req, user);
		TwitDto twitDto=TwitDtoMapper.toTwitDto(twit, user);		
		return new ResponseEntity<TwitDto>(twitDto,HttpStatus.CREATED);
	}
	
	@PostMapping("/reply")
	public ResponseEntity<TwitDto> replyTwit(@RequestBody TwitReplyRequest req,@RequestHeader("Authorization")String jwt) throws UserException, TwitException{
		User user=userService.findUserProfileByJwt(jwt);
		Twit twit=twitService.createReply(req, user);
		TwitDto twitDto=TwitDtoMapper.toTwitDto(twit, user);		
		return new ResponseEntity<TwitDto>(twitDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/{twitId}/retwit")
	public ResponseEntity<TwitDto> reTwit(@PathVariable Long twitId ,@RequestHeader("Authorization")String jwt) throws UserException, TwitException{
		User user=userService.findUserProfileByJwt(jwt);
		Twit twit=twitService.reTwit(twitId, user);
		TwitDto twitDto=TwitDtoMapper.toTwitDto(twit, user);		
		return new ResponseEntity<TwitDto>(twitDto,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{twitId}")
	public ResponseEntity<TwitDto> findTwitById(@PathVariable Long twitId ,@RequestHeader("Authorization")String jwt) throws UserException, TwitException{
		User user=userService.findUserProfileByJwt(jwt);
		Twit twit=twitService.findByTwitId(twitId);
		TwitDto twitDto=TwitDtoMapper.toTwitDto(twit, user);		
		return new ResponseEntity<TwitDto>(twitDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{twitId}")
	public ResponseEntity<ApiResponse> deleteTwit(@PathVariable Long twitId ,@RequestHeader("Authorization")String jwt) throws UserException, TwitException{
		User user=userService.findUserProfileByJwt(jwt);
		twitService.deleteTwitById(twitId,user.getId());
		
		ApiResponse res=new ApiResponse("twit deleted succcessfully ",true);
		return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<TwitDto>> getAllUserTwits(@PathVariable Long userId,@RequestHeader("Authorization")String jwt) throws UserException{
		User user=userService.findUserProfileByJwt(jwt);
		List<Twit> twits=twitService.getUserTwits(user);
		
		List<TwitDto> twitDtos=TwitDtoMapper.toTwitDtos(twits, user);
		
		return new ResponseEntity<List<TwitDto>>(twitDtos,HttpStatus.OK);
	}
	
	@GetMapping("/user/{userId}/likes")
	public ResponseEntity<List<TwitDto>> findTwitByLikesContainsUser(@PathVariable Long userId,@RequestHeader("Authorization")String jwt) throws UserException{
		User user=userService.findUserProfileByJwt(jwt);
		List<Twit> twits=twitService.getUserLikedTwits(user);
		
		List<TwitDto> twitDtos=TwitDtoMapper.toTwitDtos(twits, user);
		
		return new ResponseEntity<List<TwitDto>>(twitDtos,HttpStatus.OK);
	}
	
	
	
}
