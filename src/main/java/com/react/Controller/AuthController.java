package com.react.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.react.Config.JwtProvider;
import com.react.Exception.UserException;
import com.react.Model.User;
import com.react.Model.Varification;
import com.react.Repository.UserRepository;
import com.react.Response.AuthResponse;
import com.react.Service.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> register(@RequestBody User user)throws UserException{
		String email=user.getEmail();
		String password=user.getPassword();
		String fullName=user.getFullName();
		String birthDate=user.getBirthDate();
		
		User isEmailExist=userRepository.findByEmail(email);
		if(isEmailExist!=null) {
			throw new UserException("user allready present with this email :"+email);
		}
		User newUser=new User();
		newUser.setEmail(email);
		newUser.setPassword(passwordEncoder.encode(password));
		newUser.setFullName(fullName);
		newUser.setBirthDate(birthDate);
		newUser.setVarification(new Varification());
		
		User savedUser=userRepository.save(newUser);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt=jwtProvider.generateToken(authentication);
		return new ResponseEntity<AuthResponse>(new AuthResponse(jwt,true),HttpStatus.CREATED);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> login(@RequestBody User user) throws UserException{
		String email=user.getEmail();
		String password=user.getPassword();
		Authentication authentication=authenticate(email,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt=jwtProvider.generateToken(authentication);
		return new ResponseEntity<AuthResponse>(new AuthResponse(jwt,true),HttpStatus.ACCEPTED);
	}

	private Authentication authenticate(String email,String password) throws UserException {
		UserDetails userDetails=customUserDetailsService.loadUserByUsername(email);
		if(userDetails!=null) {
			if(passwordEncoder.matches(password, userDetails.getPassword())) {
				Authentication authentication=new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
				return authentication;
			}else {
				throw new BadCredentialsException("password not mathed");
			}
		}
		throw new UserException("user not found with email :"+email);
	}
}
