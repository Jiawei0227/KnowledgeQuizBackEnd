package nju.wjw.dao;

import nju.wjw.entity.WeekRanking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Jerry Wang on 19/04/2018.
 */
public interface WeekRankingDao extends JpaRepository<WeekRanking, Long> {

    WeekRanking getByOpenIdAndYearAndWeek(String openId, int year, int week);

    List<WeekRanking> findFirst10ByYearAndWeekOrderByTotalScoreDesc(int year, int week);
}
