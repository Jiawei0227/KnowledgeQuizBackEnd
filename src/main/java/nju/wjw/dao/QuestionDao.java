package nju.wjw.dao;

import nju.wjw.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Jerry Wang on 13/03/2018.
 */
public interface QuestionDao extends JpaRepository<Question, Long> {

    @Query("select q.id from Question q where q.difficulty = ?1")
    public List<Long> searchRandomQuestionIds(String difficulty);

}
