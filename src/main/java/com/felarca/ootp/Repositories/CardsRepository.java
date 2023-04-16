package com.felarca.ootp.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.felarca.ootp.domain.Cards;

public interface CardsRepository extends JpaRepository<Cards, Integer> {
	
	@Query(value="select case when bats = 1 then 'right' when bats = 2 then 'left' when bats = 3 then 'switch' else 'bats' end from Cards s where CardID = :cid", nativeQuery=true)
	public String bats(@Param("cid") int cid);
	//Positions
	@Query(value="select PosRatingC from Cards s where CardID = :cid", nativeQuery=true)
	public Integer playsc(@Param("cid") int cid);
	@Query(value="select PosRating1B from Cards s where CardID = :cid", nativeQuery=true)
	public Integer plays1b(@Param("cid") int cid);
	@Query(value="select PosRating2B from Cards s where CardID = :cid", nativeQuery=true)
	public Integer plays2b(@Param("cid") int cid);
	@Query(value="select PosRating3B from Cards s where CardID = :cid", nativeQuery=true)
	public Integer plays3b(@Param("cid") int cid);
	@Query(value="select PosRatingSS from Cards s where CardID = :cid", nativeQuery=true)
	public Integer playsss(@Param("cid") int cid);
	@Query(value="select PosRatingRF from Cards s where CardID = :cid", nativeQuery=true)
	public Integer playsrf(@Param("cid") int cid);
	@Query(value="select PosRatingCF from Cards s where CardID = :cid", nativeQuery=true)
	public Integer playscf(@Param("cid") int cid);
	@Query(value="select PosRatingLF from Cards s where CardID = :cid", nativeQuery=true)
	public Integer playslf(@Param("cid") int cid);
}

