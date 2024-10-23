package com.react.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.react.Exception.TwitException;
import com.react.Exception.UserException;
import com.react.Model.Twit;
import com.react.Model.User;
import com.react.Repository.TwitRepository;
import com.react.Repository.UserRepository;
import com.react.Request.TwitReplyRequest;

@Service
public class TwitServiceImpl implements TwitService{

	@Autowired
	private TwitRepository twitRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Twit createTwit(Twit req, User user) {
		Twit twit=new Twit();
		twit.setContent(req.getContent());
		twit.setCreatedAt(LocalDateTime.now());
		twit.setImage(req.getImage());
		twit.setUser(user);
		twit.setReply(false);
		twit.setTwit(true);
		twit.setVideo(req.getVideo());
		return twitRepository.save(twit);
	}

	@Override
	public List<Twit> findAllTwit() {
		return twitRepository.findAllByIsTwitTrueOrderByCreatedAtDesc();
	}

	@Override
	public Twit reTwit(Long twitId, User user) throws TwitException {
		Twit twit=findByTwitId(twitId);
		if(twit.getReTwitUsers().contains(user)) {
			twit.getReTwitUsers().remove(user);
		}else {
			twit.getReTwitUsers().add(user);
		}
		return twitRepository.save(twit);
	}

	@Override
	public Twit findByTwitId(Long twitId) throws TwitException {
		Twit twit=twitRepository.findById(twitId)
				.orElseThrow(()->new TwitException("twit not found by id :"+twitId));
		return twit;
	}

	@Override
	public void deleteTwitById(Long twitId, Long userId) throws TwitException, UserException {
		Twit twit=findByTwitId(twitId);		
		if(!userId.equals(twit.getUser().getId())) {
			throw new UserException("you can't delete another user's twit");
		}
		twitRepository.deleteById(twitId);
	}

	@Override
	public Twit removeUserFromReTwit(Long twitId, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Twit createReply(TwitReplyRequest req, User user) throws TwitException {
		
		Twit replyFor=findByTwitId(req.getTwitId());
		
		Twit twit=new Twit();
		twit.setContent(req.getContent());
		twit.setCreatedAt(LocalDateTime.now());
		twit.setImage(req.getImage());
		twit.setVideo(req.getVideo());
		twit.setUser(user);
		twit.setReply(true);
		twit.setTwit(false);
		twit.setReplyFor(replyFor);
		
		Twit savedReply=twitRepository.save(twit);
		replyFor.getReplyTwits().add(savedReply);
		twitRepository.save(replyFor);
		
		return replyFor;
	}

	@Override
	public List<Twit> getUserTwits(User user) {
		return twitRepository.findByReTwitUsersContainsOrUser_idAndIsTwitTrueOrderByCreatedAtDesc(user, user.getId());
	}

	@Override
	public List<Twit> getUserLikedTwits(User user) {
		return twitRepository.findByLikesUser_id(user.getId());
	}

}
