package org.example.CW;

import java.io.Serializable;
import java.util.Date;

public class Game implements Serializable, Comparable<Game> {
    public String gamer;
    public Integer raiting;
    public Date gameDate;

    @Override
    public int compareTo(Game o) {
        return o.raiting.compareTo(this.raiting);//Сортировка по убыванию рейтинга
    }
}