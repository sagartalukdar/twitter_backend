package com.react.Service;

import java.util.List;

import com.react.Exception.TwitException;
import com.react.Exception.UserException;
import com.react.Model.Twit;
import com.react.Model.User;
import com.react.Request.TwitReplyRequest;

public interface TwitService {

	public Twit createTwit(Twit req,User user);
	public List<Twit> findAllTwit();
	public Twit reTwit(Long twitId,User user) throws TwitException;
	public Twit findByTwitId(Long twitId) throws TwitException;
	public void deleteTwitById(Long twitId,Long userId) throws TwitException, UserException;
	public Twit removeUserFromReTwit(Long twitId,User user); 
	public Twit createReply(TwitReplyRequest req,User user) throws TwitException;
	public List<Twit> getUserTwits(User user);
	public List<Twit> getUserLikedTwits(User user);
}
