package com.react.Service;

import java.util.List;

import com.react.Exception.TwitException;
import com.react.Model.TwitLike;
import com.react.Model.User;

public interface LikeService {

	public TwitLike likeTwit(Long twitId,User user) throws TwitException;
	
	public List<TwitLike> getAllLikes(Long twitId) throws TwitException;
}
