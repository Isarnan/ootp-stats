package com.felarca.ootp.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.felarca.ootp.domain.Cards;

public interface CardsRepository extends JpaRepository<Cards, Integer> {
	//Card Type
	@Query(value="select case when CardType = 1 then 'Live' when CardType = 2 then 'NeL' when CardType = 3 then 'RookSens' when CardType = 4 then 'Legend' when CardType = 7 then 'SnapShot' when CardType = 8 then 'Unsung' when CardType = 9 then 'Hardware' when CardType = 10 then 'Topps' else CardType end from Cards s where CardID = :cid", nativeQuery=true)
	public String type(@Param("cid") int cid);
	// Handed
	@Query(value="select case when bats = 1 then 'right' when bats = 2 then 'left' when bats = 3 then 'switch' else 'bats' end from Cards s where CardID = :cid", nativeQuery=true)
	public String bats(@Param("cid") int cid);
	@Query(value="select case when throws = 1 then 'RHP' when throws = 2 then 'LHP' else throws end from Cards s where CardID = :cid", nativeQuery=true)
	public String handed(@Param("cid") int cid);
	// Name
	@Query(value="select CONCAT(FirstName,' ', LastName) from Cards s where CardID = :cid", nativeQuery=true)
	public String name(@Param("cid") int cid);

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
	
	//Stamina
	@Query(value="select Stamina from Cards s where CardID = :cid", nativeQuery=true)
	public Integer stamina(@Param("cid") int cid);

	//Hitting profiles
	@Query(value="select KsvR + BABIPvR from Cards s where CardID = :cid", nativeQuery=true)
	public Integer vra(@Param("cid") int cid);
	@Query(value="select EyevR + PowervR from Cards s where CardID = :cid", nativeQuery=true)
	public Integer vrp(@Param("cid") int cid);
	@Query(value="select AvoidKvL + BABIPvL from Cards s where CardID = :cid", nativeQuery=true)
	public Integer vla(@Param("cid") int cid);
	@Query(value="select EyevL + PowervL from Cards s where CardID = :cid", nativeQuery=true)
	public Integer vlp(@Param("cid") int cid);

	//Pitcher stuff
	@Query(value="select pBABIP from Cards s where CardID = :cid", nativeQuery=true)
	public Integer pbabip(@Param("cid") int cid);
	@Query(value="select pHR from Cards s where CardID = :cid", nativeQuery=true)
	public Integer phr(@Param("cid") int cid);
}

