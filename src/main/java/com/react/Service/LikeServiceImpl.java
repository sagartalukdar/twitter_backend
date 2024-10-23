package com.react.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.react.Exception.TwitException;
import com.react.Model.Twit;
import com.react.Model.TwitLike;
import com.react.Model.User;
import com.react.Repository.TwitLikeRepository;
import com.react.Repository.TwitRepository;

@Service
public class LikeServiceImpl implements LikeService {

	@Autowired
	private TwitLikeRepository twitLikeRepository;
	@Autowired
	private TwitService twitService;
	@Autowired
	private TwitRepository twitRepository;
	
	@Override
	public TwitLike likeTwit(Long twitId, User user) throws TwitException {
		TwitLike isLikeExist=twitLikeRepository.isLikeExist(user.getId(), twitId);
        if(isLikeExist!=null) {
        	twitLikeRepository.deleteById(isLikeExist.getId());
        	return isLikeExist;
        }
        Twit twit=twitService.findByTwitId(twitId);
        
        TwitLike twitLike=new TwitLike();
        twitLike.setTwit(twit);
        twitLike.setUser(user);
        
        TwitLike savedLike=twitLikeRepository.save(twitLike);
        
        twit.getLikes().add(savedLike);
        twitRepository.save(twit);
        return savedLike;
	}

	@Override
	public List<TwitLike> getAllLikes(Long twitId) throws TwitException {
		Twit twit=twitService.findByTwitId(twitId);
		List<TwitLike> likes=twitLikeRepository.findLikeByTwitId(twitId);
		return likes;
	}

}
