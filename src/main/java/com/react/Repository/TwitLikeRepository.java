package com.react.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.react.Model.TwitLike;

@Repository
public interface TwitLikeRepository extends JpaRepository<TwitLike, Long>{

	@Query("Select tl From TwitLike tl Where tl.user.id= :userId And tl.twit.id=:twitId")
	public TwitLike isLikeExist(Long userId,Long twitId);
	
	@Query("Select tl From TwitLike tl Where tl.twit.id=:twitId")
	public List<TwitLike> findLikeByTwitId(Long twitId);
}
