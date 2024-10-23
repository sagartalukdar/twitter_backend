package com.react.Util;

import com.react.Model.Twit;
import com.react.Model.User;

public class UserUtil {

	public final static boolean isReqUser(User reqUser,User user) {
		if(user.getId().equals(reqUser.getId())) {
			return true;
		}
		return false;
	}
	
	public final static boolean isFollowedByReqUser(User reqUser,User user) {
		if(reqUser.getFollowings().contains(user)) {
			return true;
		}
		return false;
	}
	
}
