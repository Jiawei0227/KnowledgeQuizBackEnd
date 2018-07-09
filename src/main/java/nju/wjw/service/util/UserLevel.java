package nju.wjw.service.util;

import nju.wjw.vo.SelfRankingVO;

/**
 * Created by Jerry Wang on 16/03/2018.
 */
public enum UserLevel {

    STAR(3,1,1),
    MOON(1,3,1),
    SUN(1,1,3);

    UserLevel(int easy, int normal, int hard){
        this.easy = easy;
        this.normal = normal;
        this.hard = hard;
    }

    int easy;
    int normal;
    int hard;

    public int getEasy() {
        return easy;
    }

    public int getNormal() {
        return normal;
    }

    public int getHard() {
        return hard;
    }

    public static SelfRankingVO getLevel(int totalScore){
        SelfRankingVO selfRankingVO = new SelfRankingVO();
        UserLevel userLevel = null;
        int star = 0;
        if(totalScore < 1000) {
            userLevel = UserLevel.STAR;
            if(totalScore < 100)
                star = 0;
            else if(totalScore < 300)
                star = 1;
            else if (totalScore < 600)
                star = 2;
            else if (totalScore < 1000)
                star = 3;
        }else if (totalScore < 2000) {
            userLevel = UserLevel.MOON;
            if(totalScore < 1100)
                star = 0;
            else if(totalScore < 1300)
                star = 1;
            else if (totalScore < 1600)
                star = 2;
            else if (totalScore < 2000)
                star = 3;
        }else{
            userLevel = UserLevel.SUN;
            if(totalScore < 2100)
                star = 0;
            else if(totalScore < 2300)
                star = 1;
            else if (totalScore < 2600)
                star = 2;
            else
                star = 3;
        }
        selfRankingVO.setStar(star);
        selfRankingVO.setRank(userLevel.name());
        return selfRankingVO;

    }
}
