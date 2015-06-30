package pl.mslawin.ocadofm.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by mslawin on 18.04.15.
 */

@DatabaseTable
public class Game {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Team team1;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Team team2;

    @DatabaseField
    private int goalsTeam1;

    @DatabaseField
    private int goalsTeam2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public int getGoalsTeam1() {
        return goalsTeam1;
    }

    public void setGoalsTeam1(int goalsTeam1) {
        this.goalsTeam1 = goalsTeam1;
    }

    public int getGoalsTeam2() {
        return goalsTeam2;
    }

    public void setGoalsTeam2(int goalsTeam2) {
        this.goalsTeam2 = goalsTeam2;
    }

    @Override
    public String toString() {
        return team1.getName() + " " + goalsTeam1 + " : " +
                goalsTeam2 + " " + team2.getName();
    }
}