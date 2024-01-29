package com.felarca.ootp.Repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.felarca.ootp.domain.Card;
import com.felarca.ootp.domain.Cards;
import com.felarca.ootp.domain.TierPosition;
import com.felarca.ootp.domain.results.RawPoint;

public interface CardsRepository extends JpaRepository<Cards, Integer> {
	//Base Card
	@Query("select new com.felarca.ootp.domain.Card(CardID, fn, ln, Stamina, StuffvL, ControlvL, pHRvL, pBABIPvL, StuffvR, ControlvR, pHRvR, pBABIPvR, Throws, ifr, OFRange, ratingC, rating1B,rating2B,rating3B,ratingSS,ratingRF,ratingCF,ratingLF,ContactvL,GapvL,PowervL,EyevL,KsvL,BABIPvL, ContactvR,GapvR,PowervR,EyevR,KsvR,BABIPvR, Overall, Throws, Bats ) from com.felarca.ootp.domain.Cards c where CardID = :cid")
	public Card getCard(@Param("cid") long cid);

	//Card Type
	@Query(value="select case when CardType = 1 then 'Live' when CardType = 2 then 'NeL' when CardType = 3 then 'RookSens' when CardType = 4 then 'Legend' when CardType = 7 then 'SnapShot' when CardType = 8 then 'Unsung' when CardType = 9 then 'Hardware' when CardType = 10 then 'Topps' else CardType end from cards s where CardID = :cid", nativeQuery=true)
	public String type(@Param("cid") int cid);
	// Handed
	@Query(value="select case when bats = 1 then 'right' when bats = 2 then 'left' when bats = 3 then 'switch' else 'bats' end from cards s where CardID = :cid", nativeQuery=true)
	public String bats(@Param("cid") int cid);
	@Query(value="select case when throws = 1 then 'RHP' when throws = 2 then 'LHP' else throws end from cards s where CardID = :cid", nativeQuery=true)
	public String handed(@Param("cid") int cid);
	// Name
	@Query(value="select CONCAT(fn,' ', ln) from cards s where CardID = :cid", nativeQuery=true)
	public String name(@Param("cid") int cid);

	//Positions
	@Query(value="select ratingC from cards s where CardID = :cid", nativeQuery=true)
	public Integer playsc(@Param("cid") int cid);
	@Query(value="select rating1B from cards s where CardID = :cid", nativeQuery=true)
	public Integer plays1b(@Param("cid") int cid);
	@Query(value="select rating2B from cards s where CardID = :cid", nativeQuery=true)
	public Integer plays2b(@Param("cid") int cid);
	@Query(value="select rating3B from cards s where CardID = :cid", nativeQuery=true)
	public Integer plays3b(@Param("cid") int cid);
	@Query(value="select ratingSS from cards s where CardID = :cid", nativeQuery=true)
	public Integer playsss(@Param("cid") int cid);
	@Query(value="select ratingRF from cards s where CardID = :cid", nativeQuery=true)
	public Integer playsrf(@Param("cid") int cid);
	@Query(value="select ratingCF from cards s where CardID = :cid", nativeQuery=true)
	public Integer playscf(@Param("cid") int cid);
	@Query(value="select ratingLF from cards s where CardID = :cid", nativeQuery=true)
	public Integer playslf(@Param("cid") int cid);
	@Query(value="select InfieldRange from cards s where CardID = :cid", nativeQuery=true)
	public Integer ifrange(@Param("cid") int cid);	
	@Query(value="select OFRange from cards s where CardID = :cid", nativeQuery=true)
	public Integer ofrange(@Param("cid") int cid);	



	//Stamina
	@Query(value="select Stamina from cards s where CardID = :cid", nativeQuery=true)
	public Integer stamina(@Param("cid") int cid);

	//Hitting profiles
	@Query(value="select KsvR + BABIPvR from cards s where CardID = :cid", nativeQuery=true)
	public Integer vra(@Param("cid") int cid);
	@Query(value="select EyevR + PowervR from cards s where CardID = :cid", nativeQuery=true)
	public Integer vrp(@Param("cid") int cid);
	@Query(value="select AvoidKvL + BABIPvL from cards s where CardID = :cid", nativeQuery=true)
	public Integer vla(@Param("cid") int cid);
	@Query(value="select EyevL + PowervL from cards s where CardID = :cid", nativeQuery=true)
	public Integer vlp(@Param("cid") int cid);

	//Pitcher stuff
	@Query(value="select pBABIP from cards s where CardID = :cid", nativeQuery=true)
	public Integer pbabip(@Param("cid") int cid);
	@Query(value="select pHR from cards s where CardID = :cid", nativeQuery=true)
	public Integer phr(@Param("cid") int cid);
	
	@Query(value="select StuffvL from cards s where CardID = :cid", nativeQuery=true)
	public Integer svl(@Param("cid") int cid);
	@Query(value="select ControlvL from cards s where CardID = :cid", nativeQuery=true)
	public Integer cvl(@Param("cid") int cid);
	@Query(value="select pBABIPvL from cards s where CardID = :cid", nativeQuery=true)
	public Integer pbvl(@Param("cid") int cid);
	@Query(value="select pHRvL from cards s where CardID = :cid", nativeQuery=true)
	public Integer phrvl(@Param("cid") int cid);
	@Query(value="select StuffvR from cards s where CardID = :cid", nativeQuery=true)
	public Integer svr(@Param("cid") int cid);
	@Query(value="select ControlvR from cards s where CardID = :cid", nativeQuery=true)
	public Integer cvr(@Param("cid") int cid);
	@Query(value="select pBABIPvR from cards s where CardID = :cid", nativeQuery=true)
	public Integer pbvr(@Param("cid") int cid);
	@Query(value="select pHRvR from cards s where CardID = :cid", nativeQuery=true)
	public Integer phrvr(@Param("cid") int cid);



	@Query(value="select (StuffvL + ControlvL + pHRvL + pBABIPvL) - (StuffvR + ControlvR + pHRvR + pBABIPvR) from cards s where CardID = :cid", nativeQuery=true)
	public Integer pitcherSplits(@Param("cid") int cid);

	@Query("select new com.felarca.ootp.domain.TierPosition(tier, Position, count(Position) ) from com.felarca.ootp.domain.Cards group by tier, Position")
	public List<TierPosition> getCardTierPosition();

	@Query("select new com.felarca.ootp.domain.TierPosition(c.tier, c.Position, count(c.Position)) from com.felarca.ootp.domain.Stats57 s, com.felarca.ootp.domain.Cards c where s.cid = c.CardID and s.tournament_type = 'PerfectDraft' and s.tm = :team group by c.tier, c.Position")
	public List<TierPosition> getTeamCardTierPosition(@Param("team") String team);

	// Special queries
	@Query("select new com.felarca.ootp.domain.TierPosition(tier, Position, count(Position) ) from com.felarca.ootp.domain.Cards where InfieldRange = 80 group by tier, Position")
	public List<TierPosition> getSSDefenders();


	//Get Points list
	@Query("select new com.felarca.ootp.domain.results.RawPoint((c.PowervL + c.PowervR)/2 as p, sum(s.hr), sum(s.pa) as pa) from com.felarca.ootp.domain.dao.Stats72 s, com.felarca.ootp.domain.Cards c where c.CardID=s.cid and s.tournament_type = 'bronze16' and pa > 100 group by p order by p")
	public List<RawPoint> getPoints();

	@Query("select new com.felarca.ootp.domain.results.RawPoint((c.EyevL + c.EyevR)/2 as p, sum(s.bb), sum(s.pa) as pa2) from com.felarca.ootp.domain.dao.Stats72 s, com.felarca.ootp.domain.Cards c where c.CardID=s.cid and s.tournament_type = 'bronze16' and pa > 1 group by p order by p")
	public List<RawPoint> getEyePoints();

	@Query("select new com.felarca.ootp.domain.results.RawPoint((c.PowervL + c.PowervR)/2 as p, sum(s.hr), sum(s.pa) as pa) from com.felarca.ootp.domain.dao.Stats72 s, com.felarca.ootp.domain.Cards c where c.CardID=s.cid and s.tournament_type = 'bronze16' and pa > 100 group by p order by p")
	public List<RawPoint> getHomerunPoints();

	@Query("select new com.felarca.ootp.domain.results.RawPoint((c.KsvL + c.KsvR)/2 as p, sum(s.so), sum(s.pa) as pa) from com.felarca.ootp.domain.dao.Stats72 s, com.felarca.ootp.domain.Cards c where c.CardID=s.cid and s.tournament_type = 'bronze16' and pa > 100 group by p order by p")
	public List<RawPoint> getKPoints();

	@Query("select new com.felarca.ootp.domain.results.RawPoint((c.BABIPvL + c.BABIPvR)/2 as p, ( sum(s.singles) + sum(s.doubles) + sum(s.triples) ), (sum(s.pa) - (sum(s.hr) + sum(s.so) + sum(s.bb) + sum(s.ibb) + sum(s.sf))) as pa) from com.felarca.ootp.domain.dao.Stats72 s, com.felarca.ootp.domain.Cards c where c.CardID=s.cid and s.tournament_type = 'bronze16' and pa > 100 group by p order by p")
	public List<RawPoint> getBabipPoints();

	@Query("select new com.felarca.ootp.domain.results.RawPoint((c.GapvL + c.GapvR)/2 as p, sum(s.doubles), (sum(s.singles)+sum(s.doubles)+sum(s.triples)) as pa) from com.felarca.ootp.domain.dao.Stats72 s, com.felarca.ootp.domain.Cards c where c.CardID=s.cid and s.tournament_type = 'bronze16' and pa > 100 group by p order by p")
	public List<RawPoint> getDoublePoints();

	@Query("select new com.felarca.ootp.domain.results.RawPoint((c.GapvL )/2 as p, sum(s.triples), sum(s.pa) as pa) from com.felarca.ootp.domain.dao.Stats72 s, com.felarca.ootp.domain.Cards c where c.CardID=s.cid and s.tournament_type = 'bronze16' and pa > 10 group by p order by p")
	public List<RawPoint> getTriplePoints();

	//Splits
	@Query(value ="select sum(s.PI) from stats72 s, cards c where c.CardID=s.cid and s.tournament_type='bronze16' and c.throws=:hand", nativeQuery=true)
	public Integer getPitches(@Param("hand") int hand);

}

