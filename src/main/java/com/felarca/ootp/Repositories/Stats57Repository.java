package com.felarca.ootp.Repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.felarca.ootp.domain.Hitter;
import com.felarca.ootp.domain.Pitching;
import com.felarca.ootp.domain.Record;
import com.felarca.ootp.domain.Stats57;

public interface Stats57Repository extends JpaRepository<Stats57, Integer> {
	/*
	 * @Query("select new com.felarca.ootp.domain.Hitter(s.cid as cid, s.title as card, group_concat(s.pos) AS pos, avg(s.ovr) as ovr, sum(s.pa) as pa, sum(s.b_pitches) as b_pitches, sum(s.singles) as singles, sum(s.doubles) as doubles, sum(s.triples) as triples, sum(s.hr) as hr, sum(s.bb) as bb, sum(s.ibb) as ibb, sum(s.hp) as hp, sum(s.innings) as ip, sum(s.er) as er, sum(s.p_singles) as p_singles, sum(s.p_doubles) as p_doubles, sum(s.p_triples) as p_triples, sum(s.p_homeruns) as p_homeruns, sum(s.p_bb) as p_bb, sum(s.p_ibb) as p_ibb, sum(s.p_hp) as p_hp, sum(s.ab) as ab, sum(s.sb) as sb, sum(s.cs) as cs) "
	 * + " from Stats57 s " +
	 * " where s.cid = ?1 and tournament_type = ?2 group by s.cid, s.title") public
	 * Hitter getHitter(int cid, String tournament_type);
	 * 
	 * @Query("select new com.felarca.ootp.domain.Hitter(s.cid as cid, s.title as card, group_concat(s.pos) AS pos, avg(s.ovr) as ovr, sum(s.pa) as pa, sum(s.b_pitches) as b_pitches, sum(s.singles) as singles, sum(s.doubles) as doubles, sum(s.triples) as triples, sum(s.hr) as hr, sum(s.bb) as bb, sum(s.ibb) as ibb, sum(s.hp) as hp, sum(s.innings) as ip, sum(s.er) as er, sum(s.p_singles) as p_singles, sum(s.p_doubles) as p_doubles, sum(s.p_triples) as p_triples, sum(s.p_homeruns) as p_homeruns, sum(s.p_bb) as p_bb, sum(s.p_ibb) as p_ibb, sum(s.p_hp) as p_hp, sum(s.ab) as ab, sum(s.sb) as sb, sum(s.cs) as cs) "
	 * + " from Stats57 s " +
	 * " where s.cid = :cid and tournament_type = :tournament_type and datediff(curdate(),function('str_to_date', s.date, '%Y-%m-%d %H:%i:%s.%f'))<= :since and s.tm = 'Dark Web Hackers' group by s.cid, s.title"
	 * ) public Hitter getHackerHitter(@Param("cid") int
	 * cid, @Param("tournament_type") String tournament_type, @Param("since") int
	 * since);
	 */
	
	@Query("select new com.felarca.ootp.domain.Hitter(s.cid as cid, s.title as card, group_concat(s.pos) AS pos, avg(s.ovr) as ovr, sum(s.pa) as pa, sum(s.b_pitches) as b_pitches, sum(s.singles) as singles, sum(s.doubles) as doubles, sum(s.triples) as triples, sum(s.hr) as hr, sum(s.bb) as bb, sum(s.ibb) as ibb, sum(s.hp) as hp, sum(s.innings) as ip, sum(s.er) as er, sum(s.p_singles) as p_singles, sum(s.p_doubles) as p_doubles, sum(s.p_triples) as p_triples, sum(s.p_homeruns) as p_homeruns, sum(s.p_bb) as p_bb, sum(s.p_ibb) as p_ibb, sum(s.p_hp) as p_hp, sum(s.ab) as ab, sum(s.sb) as sb, sum(s.cs) as cs, sum(k) as k, coalesce(sum(g),0) as g, coalesce(sum(gs),0) as gs, sum(p_games) as p_games, sum(p_gamesstarted) as p_gamesstarted) "
			+ " from Stats57 s "
			+ " where s.cid = :cid and tournament_type = :tournament_type and function('str_to_date', s.date, '%Y-%m-%d %H:%i:%s.%f') BETWEEN :after AND :before and s.tm = :team group by s.cid, s.title")
	public Hitter getTeamHitter(@Param("cid") int cid, @Param("tournament_type") String tournament_type, @Param("team") String team, @Param("before") LocalDateTime before, @Param("after") LocalDateTime after);
	
	@Query("select new com.felarca.ootp.domain.Hitter(s.cid as cid, s.title as card, group_concat(s.pos) AS pos, avg(s.ovr) as ovr, sum(s.pa) as pa, sum(s.b_pitches) as b_pitches, sum(s.singles) as singles, sum(s.doubles) as doubles, sum(s.triples) as triples, sum(s.hr) as hr, sum(s.bb) as bb, sum(s.ibb) as ibb, sum(s.hp) as hp, sum(s.innings) as ip, sum(s.er) as er, sum(s.p_singles) as p_singles, sum(s.p_doubles) as p_doubles, sum(s.p_triples) as p_triples, sum(s.p_homeruns) as p_homeruns, sum(s.p_bb) as p_bb, sum(s.p_ibb) as p_ibb, sum(s.p_hp) as p_hp, sum(s.ab) as ab, sum(s.sb) as sb, sum(s.cs) as cs, sum(k) as k, coalesce(sum(g),0) as g, coalesce(sum(gs),0) as gs, sum(p_games) as p_games, sum(p_gamesstarted) as p_gamesstarted) "
			+ " from Stats57 s " + " where s.cid = :cid and tournament_type = :tournament_type and function('str_to_date', s.date, '%Y-%m-%d %H:%i:%s.%f') BETWEEN :after AND :before group by s.cid, s.title")
	public Hitter getHitter(@Param("cid") int cid, @Param("tournament_type") String tournament_type, @Param("before") LocalDateTime before, @Param("after") LocalDateTime after);
	
	@Query("select new com.felarca.ootp.domain.Hitter(999 as cid, 'Meta' as card, 'POS' AS pos, 99.99 as ovr, sum(s.pa) as pa, sum(s.b_pitches) as b_pitches, sum(s.singles) as singles, sum(s.doubles) as doubles, sum(s.triples) as triples, sum(s.hr) as hr, sum(s.bb) as bb, sum(s.ibb) as ibb, sum(s.hp) as hp, sum(s.innings) as ip, sum(s.er) as er, sum(s.p_singles) as p_singles, sum(s.p_doubles) as p_doubles, sum(s.p_triples) as p_triples, sum(s.p_homeruns) as p_homeruns, sum(s.p_bb) as p_bb, sum(s.p_ibb) as p_ibb, sum(s.p_hp) as p_hp, sum(s.ab) as ab, sum(s.sb) as sb, sum(s.cs) as cs, sum(k) as k, coalesce(sum(g),0) as g, coalesce(sum(gs),0) as gs, sum(p_games) as p_games, sum(p_gamesstarted) as p_gamesstarted) "
			+ " from Stats57 s " + " where tournament_type = :tournament_type and function('str_to_date', s.date, '%Y-%m-%d %H:%i:%s.%f') BETWEEN :after AND :before group by tournament_type")
	public Hitter getMetaHitter(@Param("tournament_type") String tournament_type, @Param("before") LocalDateTime before, @Param("after") LocalDateTime after);
	
	@Query("select new com.felarca.ootp.domain.Hitter(999 as cid, 'Meta' as card, 'POS' AS pos, 99.99 as ovr, sum(s.pa) as pa, sum(s.b_pitches) as b_pitches, sum(s.singles) as singles, sum(s.doubles) as doubles, sum(s.triples) as triples, sum(s.hr) as hr, sum(s.bb) as bb, sum(s.ibb) as ibb, sum(s.hp) as hp, sum(s.innings) as ip, sum(s.er) as er, sum(s.p_singles) as p_singles, sum(s.p_doubles) as p_doubles, sum(s.p_triples) as p_triples, sum(s.p_homeruns) as p_homeruns, sum(s.p_bb) as p_bb, sum(s.p_ibb) as p_ibb, sum(s.p_hp) as p_hp, sum(s.ab) as ab, sum(s.sb) as sb, sum(s.cs) as cs, sum(k) as k, coalesce(sum(g),0) as g, coalesce(sum(gs),0) as gs, sum(p_games) as p_games, sum(p_gamesstarted) as p_gamesstarted) "
			+ " from Stats57 s " + " where tournament_type = :tournament_type and function('str_to_date', s.date, '%Y-%m-%d %H:%i:%s.%f') BETWEEN :after AND :before and s.tm = :team group by tournament_type")
	public Hitter getMetaTeamHitter(@Param("tournament_type") String tournament_type, @Param("team") String team, @Param("before") LocalDateTime before, @Param("after") LocalDateTime after);
	
	/** Lists **/
	// Before adding my collection
	// @Query("select new com.felarca.ootp.domain.Hitter(s.cid as cid, s.title as card, group_concat(s.pos) AS pos, avg(s.ovr) as ovr, sum(s.pa) as pa, sum(s.b_pitches) as b_pitches, sum(s.singles) as singles, sum(s.doubles) as doubles, sum(s.triples) as triples, sum(s.hr) as hr, sum(s.bb) as bb, sum(s.ibb) as ibb, sum(s.hp) as hp, sum(s.innings) as ip, sum(s.er) as er, sum(s.p_singles) as p_singles, sum(s.p_doubles) as p_doubles, sum(s.p_triples) as p_triples, sum(s.p_homeruns) as p_homeruns, sum(s.p_bb) as p_bb, sum(s.p_ibb) as p_ibb, sum(s.p_hp) as p_hp, sum(s.ab) as ab, sum(s.sb) as sb, sum(s.cs) as cs, sum(k) as k, coalesce(sum(g),0) as g, coalesce(sum(gs),0) as gs, sum(p_games) as p_games, sum(p_gamesstarted) as p_gamesstarted) "
	// 		+ " from Stats57 s " + " where tournament_type = ?1 and (s.pa > 0 or s.ip > 0) group by s.cid, s.title")
	// public List<Hitter> getHittersList(String tournamentType);

	@Query("select new com.felarca.ootp.domain.Hitter(s.cid as cid, c.cid as owned, s.title as card, group_concat(s.pos) AS pos, avg(s.ovr) as ovr, sum(s.pa) as pa, sum(s.b_pitches) as b_pitches, sum(s.singles) as singles, sum(s.doubles) as doubles, sum(s.triples) as triples, sum(s.hr) as hr, sum(s.bb) as bb, sum(s.ibb) as ibb, sum(s.hp) as hp, sum(s.innings) as ip, sum(s.er) as er, sum(s.p_singles) as p_singles, sum(s.p_doubles) as p_doubles, sum(s.p_triples) as p_triples, sum(s.p_homeruns) as p_homeruns, sum(s.p_bb) as p_bb, sum(s.p_ibb) as p_ibb, sum(s.p_hp) as p_hp, sum(s.ab) as ab, sum(s.sb) as sb, sum(s.cs) as cs, sum(k) as k, coalesce(sum(g),0) as g, coalesce(sum(gs),0) as gs, sum(p_games) as p_games, sum(p_gamesstarted) as p_gamesstarted) "
	+ " from Stats57 s left join Stats2 c on s.cid=c.cid" + " where tournament_type = ?1 and (s.pa > 0 or s.ip > 0) group by s.cid, s.title")
	public List<Hitter> getHittersList(String tournamentType);

	@Query("select new com.felarca.ootp.domain.Hitter(s.cid as cid, s.title as card, group_concat(s.pos) AS pos, avg(s.ovr) as ovr, sum(s.pa) as pa, sum(s.b_pitches) as b_pitches, sum(s.singles) as singles, sum(s.doubles) as doubles, sum(s.triples) as triples, sum(s.hr) as hr, sum(s.bb) as bb, sum(s.ibb) as ibb, sum(s.hp) as hp, sum(s.innings) as ip, sum(s.er) as er, sum(s.p_singles) as p_singles, sum(s.p_doubles) as p_doubles, sum(s.p_triples) as p_triples, sum(s.p_homeruns) as p_homeruns, sum(s.p_bb) as p_bb, sum(s.p_ibb) as p_ibb, sum(s.p_hp) as p_hp, sum(s.ab) as ab, sum(s.sb) as sb, sum(s.cs) as cs, sum(k) as k, coalesce(sum(g),0) as g, coalesce(sum(gs),0) as gs, sum(p_games) as p_games, sum(p_gamesstarted) as p_gamesstarted) "
			+ " from Stats57 s "
			+ " where tournament_type = :tournament_type and function('str_to_date', s.date, '%Y-%m-%d %H:%i:%s.%f') BETWEEN :after AND :before and (s.pa > 0 or s.ip > 0) group by s.cid, s.title")
	public List<Hitter> getHittersList(@Param("tournament_type") String tournament_type, @Param("before") LocalDateTime before,
			@Param("after") LocalDateTime after);

		
	@Query("select new com.felarca.ootp.domain.Hitter(s.cid as cid, s.title as card, group_concat(s.pos) AS pos, avg(s.ovr) as ovr, sum(s.pa) as pa, sum(s.b_pitches) as b_pitches, sum(s.singles) as singles, sum(s.doubles) as doubles, sum(s.triples) as triples, sum(s.hr) as hr, sum(s.bb) as bb, sum(s.ibb) as ibb, sum(s.hp) as hp, sum(s.innings) as ip, sum(s.er) as er, sum(s.p_singles) as p_singles, sum(s.p_doubles) as p_doubles, sum(s.p_triples) as p_triples, sum(s.p_homeruns) as p_homeruns, sum(s.p_bb) as p_bb, sum(s.p_ibb) as p_ibb, sum(s.p_hp) as p_hp, sum(s.ab) as ab, sum(s.sb) as sb, sum(s.cs) as cs, sum(k) as k, coalesce(sum(g),0) as g, coalesce(sum(gs),0) as gs, sum(p_games) as p_games, sum(p_gamesstarted) as p_gamesstarted) "
			+ " from Stats57 s " + " where tournament_type = ?1 and (s.pa > 0 or s.ip > 0) and s.tm = 'Dark Web Hackers' group by s.cid, s.title")
	public List<Hitter> getHackerHittersList(String tournamentType);

	@Query("select new com.felarca.ootp.domain.Hitter(s.cid as cid, s.title as card, group_concat(s.pos) AS pos, avg(s.ovr) as ovr, sum(s.pa) as pa, sum(s.b_pitches) as b_pitches, sum(s.singles) as singles, sum(s.doubles) as doubles, sum(s.triples) as triples, sum(s.hr) as hr, sum(s.bb) as bb, sum(s.ibb) as ibb, sum(s.hp) as hp, sum(s.innings) as ip, sum(s.er) as er, sum(s.p_singles) as p_singles, sum(s.p_doubles) as p_doubles, sum(s.p_triples) as p_triples, sum(s.p_homeruns) as p_homeruns, sum(s.p_bb) as p_bb, sum(s.p_ibb) as p_ibb, sum(s.p_hp) as p_hp, sum(s.ab) as ab, sum(s.sb) as sb, sum(s.cs) as cs, sum(k) as k, coalesce(sum(g),0) as g, coalesce(sum(gs),0) as gs, sum(p_games) as p_games, sum(p_gamesstarted) as p_gamesstarted) "
			+ " from Stats57 s "
			+ " where tournament_type = :tournament_type and function('str_to_date', s.date, '%Y-%m-%d %H:%i:%s.%f') BETWEEN :after AND :before and (s.pa > 0 or s.ip > 0) and s.tm = 'Dark Web Hackers' group by s.cid, s.title")
	public List<Hitter> getHackerHittersList(@Param("tournament_type") String tournament_type, @Param("before") LocalDateTime before,
			@Param("after") LocalDateTime after);
	
	@Query("select new com.felarca.ootp.domain.Hitter(s.cid as cid, s.title as card, group_concat(s.pos) AS pos, avg(s.ovr) as ovr, sum(s.pa) as pa, sum(s.b_pitches) as b_pitches, sum(s.singles) as singles, sum(s.doubles) as doubles, sum(s.triples) as triples, sum(s.hr) as hr, sum(s.bb) as bb, sum(s.ibb) as ibb, sum(s.hp) as hp, sum(s.innings) as ip, sum(s.er) as er, sum(s.p_singles) as p_singles, sum(s.p_doubles) as p_doubles, sum(s.p_triples) as p_triples, sum(s.p_homeruns) as p_homeruns, sum(s.p_bb) as p_bb, sum(s.p_ibb) as p_ibb, sum(s.p_hp) as p_hp, sum(s.ab) as ab, sum(s.sb) as sb, sum(s.cs) as cs, sum(k) as k, coalesce(sum(g),0) as g, coalesce(sum(gs),0) as gs, sum(p_games) as p_games, sum(p_gamesstarted) as p_gamesstarted) "
			+ " from Stats57 s "
			+ " where tournament_type = :tournament_type and function('str_to_date', s.date, '%Y-%m-%d %H:%i:%s.%f') BETWEEN :after AND :before and (s.pa > 0 or s.ip > 0) and s.tm = :team group by s.cid, s.title")
	public List<Hitter> getTeamHittersList(@Param("tournament_type") String tournament_type, @Param("team") String team, @Param("before") LocalDateTime before,
			@Param("after") LocalDateTime after);
	
	@Query("select new com.felarca.ootp.domain.Hitter(s.cid as cid, s.tm as card, group_concat(s.pos) AS pos, avg(s.ovr) as ovr, sum(s.pa) as pa, sum(s.b_pitches) as b_pitches, sum(s.singles) as singles, sum(s.doubles) as doubles, sum(s.triples) as triples, sum(s.hr) as hr, sum(s.bb) as bb, sum(s.ibb) as ibb, sum(s.hp) as hp, sum(s.innings) as ip, sum(s.er) as er, sum(s.p_singles) as p_singles, sum(s.p_doubles) as p_doubles, sum(s.p_triples) as p_triples, sum(s.p_homeruns) as p_homeruns, sum(s.p_bb) as p_bb, sum(s.p_ibb) as p_ibb, sum(s.p_hp) as p_hp, sum(s.ab) as ab, sum(s.sb) as sb, sum(s.cs) as cs, sum(k) as k, coalesce(sum(g),0) as g, coalesce(sum(gs),0) as gs, sum(p_games) as p_games, sum(p_gamesstarted) as p_gamesstarted) "
			+ " from Stats57 s "
			+ " where tournament_type = :tournament_type and function('str_to_date', s.date, '%Y-%m-%d %H:%i:%s.%f') BETWEEN :after AND :before and (s.pa > 0 or s.ip > 0) and s.cid = :cid group by s.tm")
	public List<Hitter> getHitterByTeamList(@Param("tournament_type") String tournament_type, @Param("cid") int cid, @Param("before") LocalDateTime before,
			@Param("after") LocalDateTime after);
	
	/** META **/

	// @Query("select new com.felarca.ootp.domain.Record(s.tm, sum(s.w), sum(s.l))
	// from Stats57 s where s.tournament_type = :tournament_type and s.tm = :team
	// and datediff(:after,function('str_to_date', s.date, '%Y-%m-%d %H:%i:%s.%f'))
	// > 0 and datediff(:before,function('str_to_date', s.date, '%Y-%m-%d
	// %H:%i:%s.%f')) < 0 group by s.tm")
	@Query("select new com.felarca.ootp.domain.Record(s.tm, sum(s.w), sum(s.l), sum(s.r), sum(s.p_runs)) from Stats57 s where s.tournament_type = :tournament_type and s.tm = :team and function('str_to_date', s.date, '%Y-%m-%d %H:%i:%s.%f') BETWEEN :after AND :before group by s.tm")
	public Record getRecord(@Param("tournament_type") String tournament_type, @Param("team") String team, @Param("before") LocalDateTime before,
			@Param("after") LocalDateTime after);

	@Query("select new com.felarca.ootp.domain.Record(s.tm, sum(s.w), sum(s.l), sum(s.r), sum(s.p_runs)) from Stats57 s where s.tournament_type = :tournament_type and function('str_to_date', s.date, '%Y-%m-%d %H:%i:%s.%f') BETWEEN :after AND :before group by s.tm order by sum(s.w)/(sum(s.w)+sum(s.l)) desc")
	public List<Record> getTopTeams(@Param("tournament_type") String tournament_type, @Param("before") LocalDateTime before,
			@Param("after") LocalDateTime after);
	
	/** Utility **/
	@Query(value="select s.title from stats57 s where cid = :cid limit 1", nativeQuery=true)
	public String getCard(@Param("cid") int cid);
	
	
	
}