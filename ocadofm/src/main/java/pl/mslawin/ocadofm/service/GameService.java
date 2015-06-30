package pl.mslawin.ocadofm.service;

import android.content.Context;

import com.google.common.collect.Sets;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.mslawin.ocadofm.dao.DatabaseHelper;
import pl.mslawin.ocadofm.domain.Game;
import pl.mslawin.ocadofm.domain.Team;
import pl.mslawin.ocadofm.view.RunningGameActivity;

/**
 * Created by mslawin on 18.04.15.
 */
public class GameService {

    private DatabaseHelper databaseHelper;

    public void recordTeams(String teamName, Set<String> members, Context context) throws SQLException {
        initHelper(context);

        Team team = new Team();
        team.setName(teamName);
        team.setMembers(Sets.newHashSet(members));
        databaseHelper.getTeamDao().create(team);
    }

    public List<Team> getTeams(Context context) throws SQLException {
        initHelper(context);

        return databaseHelper.getTeamDao().queryForAll();
    }

    public Game recordGame(String team1Name, String team2Name, Context context, int goalsTeam1,
                           int goalsTeam2) throws SQLException {
        initHelper(context);

        Dao<Team, Integer> teamDao = databaseHelper.getTeamDao();
        Team team1 = teamDao.queryForEq("name", team1Name).get(0);
        Team team2 = teamDao.queryForEq("name", team2Name).get(0);

        Game game = new Game();
        game.setTeam1(team1);
        game.setTeam2(team2);
        game.setGoalsTeam1(goalsTeam1);
        game.setGoalsTeam2(goalsTeam2);
        databaseHelper.getGameDao().create(game);

        return game;
    }

    public List<Game> getGames(Context context) throws SQLException {
        initHelper(context);
        return databaseHelper.getGameDao().queryForAll();
    }

    public void deleteGame(Context context, Game game) throws SQLException {
        initHelper(context);
        databaseHelper.getGameDao().delete(game);
    }

    public void resetData(Context context) throws SQLException {
        initHelper(context);

        databaseHelper.getGameDao().deleteBuilder().delete();
        databaseHelper.getTeamDao().deleteBuilder().delete();
    }

    private void initHelper(Context context) {
        if (databaseHelper == null || !databaseHelper.isOpen()) {
            databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
    }
}