package com.felarca.ootp.Repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.felarca.ootp.domain.Pitching;
@Deprecated
public interface PitchingRepository extends JpaRepository<Pitching, Integer>
{	
    // @Query("select new com.felarca.ootp.domain.HittingLine(s.id as cid, s.title as title, sum(s.g) as games, sum(s.ab) as ab) " + " from Hitting h " + " where id is not null and g is not null group by s.id, s.title")
    // public List<HittingLine> getHitters();
	
	List<Pitching> findAllByIpGreaterThanEqual(long ip, Sort sort );
	Pitching findOneByCid(int cid);

}