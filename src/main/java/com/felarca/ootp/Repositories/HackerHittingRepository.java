package com.felarca.ootp.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.felarca.ootp.domain.HackerHitting;
import com.felarca.ootp.domain.Pitching;

public interface HackerHittingRepository extends JpaRepository<HackerHitting, Integer>
{	
    // @Query("select new com.felarca.ootp.domain.HittingLine(s.id as cid, s.title as title, sum(s.g) as games, sum(s.ab) as ab) " + " from Hitting h " + " where id is not null and g is not null group by s.id, s.title")
    // public List<HittingLine> getHitters();
	
	HackerHitting findOneByCid(int cid);

}