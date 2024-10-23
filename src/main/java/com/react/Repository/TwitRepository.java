package com.react.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.react.Model.Twit;
import com.react.Model.User;

@Repository
public interface TwitRepository extends JpaRepository<Twit, Long>{

	List<Twit> findAllByIsTwitTrueOrderByCreatedAtDesc();
	
	List<Twit> findByReTwitUsersContainsOrUser_idAndIsTwitTrueOrderByCreatedAtDesc(User user,Long userId);
	
	List<Twit> findByLikesContainingOrderByCreatedAtDesc(User user);
	
	@Query("select t from Twit t join t.likes l where l.user.id=:userId")
	List<Twit> findByLikesUser_id(Long userId);
	
	
}
