package com.react.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.react.Dto.TwitDto;
import com.react.Dto.UserDto;
import com.react.Dto.Mapper.TwitDtoMapper;
import com.react.Dto.Mapper.UserDtoMapper;
import com.react.Exception.TwitException;
import com.react.Exception.UserException;
import com.react.Model.Twit;
import com.react.Model.User;
import com.react.Request.TwitReplyRequest;
import com.react.Service.UserService;
import com.react.Util.UserUtil;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<UserDto> getUserProfile(@RequestHeader("Authorization")String jwt) throws UserException, TwitException{
		User user=userService.findUserProfileByJwt(jwt);
		UserDto userDto=UserDtoMapper.toUserDto(user,user);
		userDto.setReq_user(true);
		return new ResponseEntity<UserDto>(userDto,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> findUserById(@PathVariable Long userId ,@RequestHeader("Authorization")String jwt) throws UserException{
		User reqUser=userService.findUserProfileByJwt(jwt);
		User user=userService.findUserById(userId);

		UserDto userDto=UserDtoMapper.toUserDto(reqUser, user);
		userDto.setReq_user(UserUtil.isReqUser(reqUser, user));	 
		userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
		return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<UserDto>> searchUsers(@RequestParam("query") String query ,@RequestHeader("Authorization")String jwt) throws UserException{
		User reqUser=userService.findUserProfileByJwt(jwt);
		List<User> users=userService.searchUser(query);

		List<UserDto> userDtos=UserDtoMapper.toUserDtos(users);
		return new ResponseEntity<List<UserDto>>(userDtos,HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<UserDto> updateUser(@RequestBody User user ,@RequestHeader("Authorization")String jwt) throws UserException{
		User reqUser=userService.findUserProfileByJwt(jwt);
		User updateUser=userService.updateUser(reqUser.getId(), user);

		UserDto userDto=UserDtoMapper.toUserDto(reqUser, updateUser);
		return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
	}	
	
	@PutMapping("/{userId}/follow")
	public ResponseEntity<UserDto> followUser(@PathVariable Long userId  ,@RequestHeader("Authorization")String jwt) throws UserException{
		User reqUser=userService.findUserProfileByJwt(jwt);
		User followUser=userService.findUserById(userId);
		
		followUser=userService.followUser(followUser.getId(), reqUser);

		UserDto userDto=UserDtoMapper.toUserDto(reqUser, followUser);
		userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, followUser));

		return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
	}
	
	
	
}
