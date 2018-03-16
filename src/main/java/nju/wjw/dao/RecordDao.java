package nju.wjw.dao;

import nju.wjw.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Jerry Wang on 15/03/2018.
 */
public interface RecordDao extends JpaRepository<Record, Long> {

    Record findByOpenId(String openId);

    List<Record> findFirst50ByOrderByScoreDesc();


}
