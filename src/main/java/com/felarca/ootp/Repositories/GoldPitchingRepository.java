package com.felarca.ootp.Repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.felarca.ootp.domain.GoldPitching;
import com.felarca.ootp.domain.Pitching;

public interface GoldPitchingRepository extends JpaRepository<GoldPitching, Integer>
{	
    // @Query("select new com.felarca.ootp.domain.HittingLine(s.id as cid, s.title as title, sum(s.g) as games, sum(s.ab) as ab) " + " from Hitting h " + " where id is not null and g is not null group by s.id, s.title")
    // public List<HittingLine> getHitters();
	
	List<GoldPitching> findAllByIpGreaterThanEqual(long ip, Sort sort );
	GoldPitching findOneByCid(int cid);

}