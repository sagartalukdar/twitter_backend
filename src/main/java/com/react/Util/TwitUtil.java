package com.react.Util;

import com.react.Model.Twit;
import com.react.Model.TwitLike;
import com.react.Model.User;

public class TwitUtil {

	public final static boolean isLikedByReqUser(User ReqUser,Twit twit) {
		for(TwitLike like:twit.getLikes()) {
			if(like.getUser().getId().equals(ReqUser.getId())) {
				return true;
			}
		}
		return false;
	}
	
	public final static boolean isReTwitedByReqUser(User reqUser,Twit twit) {
		for(User user:twit.getReTwitUsers()) {
			if(user.getId().equals(reqUser.getId())) {
				return true;
			}
		}
		return false;
	}
}
