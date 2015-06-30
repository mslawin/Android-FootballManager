package pl.mslawin.ocadofm.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.common.collect.Sets;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import pl.mslawin.ocadofm.R;
import pl.mslawin.ocadofm.domain.Team;
import pl.mslawin.ocadofm.service.GameService;


public class StartGameActivity extends Activity implements View.OnClickListener {

    private static final Logger logger = Logger.getLogger(StartGameActivity.class.getName());

    private AutoCompleteTextView teamAMember1;
    private AutoCompleteTextView teamAMember2;
    private AutoCompleteTextView teamAMember3;
    private AutoCompleteTextView teamAMember4;

    private AutoCompleteTextView teamBMember1;
    private AutoCompleteTextView teamBMember2;
    private AutoCompleteTextView teamBMember3;
    private AutoCompleteTextView teamBMember4;

    private AutoCompleteTextView teamCMember1;
    private AutoCompleteTextView teamCMember2;
    private AutoCompleteTextView teamCMember3;
    private AutoCompleteTextView teamCMember4;

    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        initControls();
    }

    private void initControls() {
        teamAMember1 = (AutoCompleteTextView) findViewById(R.id.teamAMember1);
        teamAMember2 = (AutoCompleteTextView) findViewById(R.id.teamAMember2);
        teamAMember3 = (AutoCompleteTextView) findViewById(R.id.teamAMember3);
        teamAMember4 = (AutoCompleteTextView) findViewById(R.id.teamAMember4);

        teamBMember1 = (AutoCompleteTextView) findViewById(R.id.teamBMember1);
        teamBMember2 = (AutoCompleteTextView) findViewById(R.id.teamBMember2);
        teamBMember3 = (AutoCompleteTextView) findViewById(R.id.teamBMember3);
        teamBMember4 = (AutoCompleteTextView) findViewById(R.id.teamBMember4);

        teamCMember1 = (AutoCompleteTextView) findViewById(R.id.teamCMember1);
        teamCMember2 = (AutoCompleteTextView) findViewById(R.id.teamCMember2);
        teamCMember3 = (AutoCompleteTextView) findViewById(R.id.teamCMember3);
        teamCMember4 = (AutoCompleteTextView) findViewById(R.id.teamCMember4);

        saveButton = (Button) findViewById(R.id.startGame);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        GameService gameService = new GameService();
        try {
            List<Team> teams = gameService.getTeams(this);
            if (teams == null || teams.isEmpty()) {
                gameService.recordTeams(getString(R.string.newGame_teamA), Sets.newHashSet(
                        teamAMember1.getText().toString(), teamAMember2.getText().toString(),
                        teamAMember3.getText().toString(), teamAMember4.getText().toString()), this);
                gameService.recordTeams(getString(R.string.newGame_teamB), Sets.newHashSet(
                        teamBMember1.getText().toString(), teamBMember2.getText().toString(),
                        teamBMember3.getText().toString(), teamBMember4.getText().toString()), this);
                gameService.recordTeams(getString(R.string.newGame_teamC), Sets.newHashSet(
                        teamCMember1.getText().toString(), teamCMember2.getText().toString(),
                        teamCMember3.getText().toString(), teamCMember4.getText().toString()), this);
            }
            startGameActivity();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creating teams", e);
            Toast.makeText(this, getString(R.string.errorCreatingTeams), Toast.LENGTH_SHORT).show();
        }
    }

    private void startGameActivity() {
        Intent intent = new Intent(this, RunningGameActivity.class);
        startActivity(intent);
    }
}