package pl.mslawin.ocadofm.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import pl.mslawin.ocadofm.domain.Game;
import pl.mslawin.ocadofm.domain.Team;

/**
 * Created by mslawin on 18.04.15.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "ocadofm.db";
    private static final int DATABASE_VERSION = 1;
    private static final Logger logger = Logger.getLogger(DatabaseHelper.class.getName());

    private Dao<Team, Integer> teamDao;
    private Dao<Game, Integer> gameDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Team.class);
            TableUtils.createTable(connectionSource, Game.class);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Unable to create tables", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public Dao<Team, Integer> getTeamDao() throws SQLException {
        if (teamDao == null) {
            teamDao = getDao(Team.class);
        }
        return teamDao;
    }

    public Dao<Game, Integer> getGameDao() throws SQLException {
        if (gameDao == null) {
            gameDao = getDao(Game.class);
        }
        return gameDao;
    }
}
