package nju.wjw.service.util;

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
}
