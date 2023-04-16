package com.felarca.ootp.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.felarca.ootp.domain.Stats2;

public interface Stats2Repository extends JpaRepository<Stats2, Integer> {
	
	@Query(value="select cid from stats2 s where cid = :cid", nativeQuery=true)
	public Integer owned(@Param("cid") int cid);
}