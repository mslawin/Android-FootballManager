package pl.mslawin.ocadofm.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import pl.mslawin.ocadofm.R;
import pl.mslawin.ocadofm.domain.Game;
import pl.mslawin.ocadofm.domain.Team;
import pl.mslawin.ocadofm.service.GameService;

public class RunningGameActivity extends ActionBarActivity {

    private static final Logger logger = Logger.getLogger(RunningGameActivity.class.getName());

    private EditText team1Score;
    private EditText team2Score;
    private Spinner team1;
    private Spinner team2;

    private ArrayAdapter<Game> listAdapter;
    private List<Game> games = new ArrayList<Game>();

    private final GameService gameService = new GameService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_game);

        TextView teamAMembers = (TextView) findViewById(R.id.gameRunTeamA);
        TextView teamBMembers = (TextView) findViewById(R.id.gameRunTeamB);
        TextView teamCMembers = (TextView) findViewById(R.id.gameRunTeamC);

        ListView gamesList = (ListView) findViewById(R.id.gamesList);
        try {
            List<Team> teams = gameService.getTeams(this);
            teamAMembers.setText(Html.fromHtml(getTeamDescription(teams.get(0))));
            teamBMembers.setText(Html.fromHtml(getTeamDescription(teams.get(1))));
            teamCMembers.setText(Html.fromHtml(getTeamDescription(teams.get(2))));

            games.addAll(gameService.getGames(this));

            listAdapter = new ArrayAdapter<Game>(this, android.R.layout.simple_list_item_1,
                    android.R.id.text1, games);
            gamesList.setAdapter(listAdapter);
            gamesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RunningGameActivity.this);
                    builder.setMessage(R.string.newGame_confirm_delete);
                    builder.setCancelable(true);
                    builder.setNegativeButton(R.string.newGame_cancel_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.setPositiveButton(R.string.newGame_confirm_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteGame(position);
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            });
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Unable to get teams", e);
        }
    }

    private void deleteGame(int position) {
        Game game = listAdapter.getItem(position);
        try {
            gameService.deleteGame(this, game);
            games.remove(game);
            listAdapter.notifyDataSetChanged();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Unable to record a game", e);
            Toast.makeText(this, getString(R.string.errorDeletingGame), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.run_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        super.onOptionsItemSelected(menuItem);
        switch (menuItem.getItemId()) {
            case R.id.menu_newGame:
                openNewGamePopup();
                return true;
            case R.id.menu_finishGame:
                finishGames();
                return true;
            default:
                return false;
        }
    }

    private void openNewGamePopup() {
        LayoutInflater layoutInflater = LayoutInflater.from(RunningGameActivity.this);
        View promptView = layoutInflater.inflate(R.layout.new_game_popup, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.newGame_popup_title));
        builder.setCancelable(true);
        builder.setView(promptView);
        builder.setPositiveButton(getString(R.string.newGame_ok_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addNewGame();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(getString(R.string.newGame_cancel_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        team1 = (Spinner) promptView.findViewById(R.id.team1);
        team2 = (Spinner) promptView.findViewById(R.id.team2);
        team1Score = (EditText) promptView.findViewById(R.id.team1_score);
        team2Score = (EditText) promptView.findViewById(R.id.team2_score);
        alertDialog.show();
    }

    private void addNewGame() {
        try {
            Game game = gameService.recordGame("Team " + team1.getSelectedItem(), "Team " + team2.getSelectedItem(), this,
                    Integer.parseInt(team1Score.getText().toString()), Integer.parseInt(team2Score.getText().toString()));
            games.add(game);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Unable to record a game", e);
            Toast.makeText(this, getString(R.string.errorRecordingGame), Toast.LENGTH_SHORT).show();
        }
    }

    private void finishGames() {
    }

    private String getTeamDescription(Team team) {
        StringBuilder sb = new StringBuilder();
        for (String member : team.getMembers()) {
            sb.append(member)
                    .append(", ");
        }
        String members = sb.toString();
        return "<b>" + team.getName() + ":</b> " + members.substring(0, members.length() - 1);
    }
}