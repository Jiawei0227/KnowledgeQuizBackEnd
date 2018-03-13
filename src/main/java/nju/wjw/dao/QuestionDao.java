package nju.wjw.dao;

import nju.wjw.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Jerry Wang on 13/03/2018.
 */
public interface QuestionDao extends JpaRepository<Question, Long> {
}
