package com.felarca.ootp.Repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.felarca.ootp.domain.Hitter;
import com.felarca.ootp.domain.Record;
import com.felarca.ootp.domain.dao.Stats72;
import com.felarca.ootp.domain.results.CardTournamentResult;

@Repository
public interface Stats72Repository extends JpaRepository<Stats72, Integer> {
/*
    @Query("select new com.felarca.ootp.domain.results.CardTournamentResult(s.cid as cid, 'fix this' as card, group_concat(s.pos) AS pos, avg(s.ovr) as ovr, sum(s.pa) as pa, sum(s.b_pitches) as b_pitches, sum(s.singles) as singles, sum(s.doubles) as doubles, sum(s.triples) as triples, sum(s.hr) as hr, sum(s.bb) as bb, sum(s.ibb) as ibb, sum(s.hp) as hp, sum(s.innings) as ip, sum(s.er) as er, sum(s.p_singles) as p_singles, sum(s.p_doubles) as p_doubles, sum(s.p_triples) as p_triples, sum(s.p_homeruns) as p_homeruns, sum(s.p_bb) as p_bb, sum(s.p_ibb) as p_ibb, sum(s.p_hp) as p_hp, sum(s.ab) as ab, sum(s.sb) as sb, sum(s.cs) as cs, sum(k) as k, coalesce(sum(g),0) as g, coalesce(sum(gs),0) as gs, sum(p_games) as p_games, sum(p_gamesstarted) as p_gamesstarted, sum(pi) as p_pitches, sum(fb) as p_fb, sum(gb) as p_gb, coalesce(sum(p_runs),0) as p_runs) "
    + " from com.felarca.ootp.domain.dao.Stats72 s " + " where s.cid = :cid and tournament_type = :tournament_type and function('str_to_date', s.date, '%Y-%m-%d %H:%i:%s.%f') BETWEEN :after AND :before group by s.cid")
    public CardTournamentResult getSumByTypeTime(@Param("cid") int cid, @Param("tournament_type") String tournament_type, @Param("before") LocalDateTime before, @Param("after") LocalDateTime after);
*/
	@Query("select new com.felarca.ootp.domain.results.CardTournamentResult(999 as cid, 'Meta' as card, 'POS' as pos, 99.99 as ovr, sum(s.pa) as pa, sum(s.b_pitches) as b_pitches, sum(s.singles) as singles, sum(s.doubles) as doubles, sum(s.triples) as triples, sum(s.hr) as hr, sum(s.bb) as bb, sum(s.ibb) as ibb, sum(s.hp) as hp, sum(s.innings) as ip, sum(s.er) as er, sum(s.p_singles) as p_singles, sum(s.p_doubles) as p_doubles, sum(s.p_triples) as p_triples, sum(s.p_homeruns) as p_homeruns, sum(s.p_bb) as p_bb, sum(s.p_ibb) as p_ibb, sum(s.p_hp) as p_hp, sum(s.ab) as ab, sum(s.sb) as sb, sum(s.cs) as cs, sum(k) as k, coalesce(sum(g),0) as g, coalesce(sum(gs),0) as gs, sum(p_games) as p_games, sum(p_gamesstarted) as p_gamesstarted, sum(pi) as p_pitches, sum(fb) as p_fb, sum(gb) as p_gb, coalesce(sum(p_runs),0) as p_runs)"
			+ " from com.felarca.ootp.domain.dao.Stats72 s " + " where tournament_type = :tournament_type and function('str_to_date', s.date, '%Y-%m-%d %H:%i:%s.%f') BETWEEN :after AND :before group by tournament_type")
	public CardTournamentResult getMetaResult(@Param("tournament_type") String tournament_type, @Param("before") LocalDateTime before, @Param("after") LocalDateTime after);
//TEST
@Query("select new com.felarca.ootp.domain.Hitter(999 as cid, 'Meta' as card, 'POS' AS pos, 99.99 as ovr, sum(s.pa) as pa, sum(s.b_pitches) as b_pitches, sum(s.singles) as singles, sum(s.doubles) as doubles, sum(s.triples) as triples, sum(s.hr) as hr, sum(s.bb) as bb, sum(s.ibb) as ibb, sum(s.hp) as hp, sum(s.innings) as ip, sum(s.er) as er, sum(s.p_singles) as p_singles, sum(s.p_doubles) as p_doubles, sum(s.p_triples) as p_triples, sum(s.p_homeruns) as p_homeruns, sum(s.p_bb) as p_bb, sum(s.p_ibb) as p_ibb, sum(s.p_hp) as p_hp, sum(s.ab) as ab, sum(s.sb) as sb, sum(s.cs) as cs, sum(k) as k, coalesce(sum(g),0) as g, coalesce(sum(gs),0) as gs, sum(p_games) as p_games, sum(p_gamesstarted) as p_gamesstarted, sum(pi) as p_pitches, sum(fb) as p_fb, sum(gb) as p_gb, coalesce(sum(p_runs),0) as p_runs) "
+ " from Stats72 s " + " where tournament_type = :tournament_type and function('str_to_date', s.date, '%Y-%m-%d %H:%i:%s.%f') BETWEEN :after AND :before group by tournament_type")
public Hitter getMetaHitter(@Param("tournament_type") String tournament_type, @Param("before") LocalDateTime before, @Param("after") LocalDateTime after);




	@Query("select new com.felarca.ootp.domain.Record(s.tm, sum(s.w), sum(s.l), sum(s.r), sum(s.p_runs)) from com.felarca.ootp.domain.dao.Stats72 s where s.tournament_type = :tournament_type and s.tm = :team and function('str_to_date', s.date, '%Y-%m-%d %H:%i:%s.%f') BETWEEN :after AND :before group by s.tm")
	public Record getRecord(@Param("tournament_type") String tournament_type, @Param("team") String team, @Param("before") LocalDateTime before,
			@Param("after") LocalDateTime after);

	@Query("select new com.felarca.ootp.domain.Record(s.tm, sum(s.w), sum(s.l), sum(s.r), sum(s.p_runs)) from com.felarca.ootp.domain.dao.Stats72 s where s.tournament_type = :tournament_type and function('str_to_date', s.date, '%Y-%m-%d %H:%i:%s.%f') BETWEEN :after AND :before group by s.tm order by sum(s.w)/(sum(s.w)+sum(s.l)) desc")
	public List<Record> getTopTeams(@Param("tournament_type") String tournament_type, @Param("before") LocalDateTime before,
			@Param("after") LocalDateTime after);
	
}