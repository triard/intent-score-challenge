package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.opengl.ETC1;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ScorerActivity extends AppCompatActivity {
    private EditText playerName;
    private boolean isHome;
    private int homeScoreVal, awayScoreVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorer);

        Bundle bundle = getIntent().getExtras();
        playerName = findViewById(R.id.editText);
        isHome = bundle.getBoolean("isHome");
        if (isHome){
            homeScoreVal = bundle.getInt("homeScore");
        }else{
            awayScoreVal = bundle.getInt("awayScore");
        }
    }

    public void handlePlayerName(View view) {
        String name = playerName.getText().toString();
        if(!name.isEmpty()){
            Intent i = new Intent();
            if (isHome){
                homeScoreVal++;
                i.putExtra("homeScorer", name);
                i.putExtra("homeScore", homeScoreVal);
                setResult(1, i);
            }else{
                awayScoreVal++;
                i.putExtra("awayScorer", name);
                i.putExtra("awayScore", awayScoreVal);
                setResult(2, i);
            }
            finish();
        }else{
            Toast.makeText(this, "Fill player name!", Toast.LENGTH_SHORT).show();
        }
    }
}
