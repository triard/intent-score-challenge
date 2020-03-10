package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MatchActivity extends AppCompatActivity {
    private TextView homeTeam, awayTeam, homeScore, awayScore;
    private ImageView homeLogo, awayLogo;
    private Uri homeLogoUri, awayLogoUri;
    private Bitmap homeBitmap, awayBitmap;
    private boolean isHome = true;
    private int homeScoreVal, awayScoreval;
    private String homeScorer = "", awayScorer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        homeTeam = findViewById(R.id.txt_home);
        awayTeam = findViewById(R.id.txt_away);
        homeScore = findViewById(R.id.score_home);
        awayScore = findViewById(R.id.score_away);

        homeLogo = findViewById(R.id.home_logo);
        awayLogo = findViewById(R.id.away_logo);

        Bundle bundle = getIntent().getExtras();

        homeLogoUri = Uri.parse(bundle.getString("homeUri"));
        awayLogoUri = Uri.parse(bundle.getString("awayUri"));

        if (bundle != null){
            homeTeam.setText(bundle.getString("homeTeam"));
            awayTeam.setText(bundle.getString("awayTeam"));
            try {
                homeBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), homeLogoUri);
                awayBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), awayLogoUri);
                homeLogo.setImageBitmap(homeBitmap);
                awayLogo.setImageBitmap(awayBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "Blank bundle", Toast.LENGTH_SHORT).show();
        }

        //TODO
        //1.Menampilkan detail match sesuai data dari main activity
        //2.Tombol add score menambahkan memindah activity ke scorerActivity dimana pada scorer activity di isikan nama pencetak gol
        //3.Dari activity scorer akan mengirim kembali ke activity matchactivity otomatis nama pencetak gol dan skor bertambah +1
        //4.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang beserta nama pencetak gol ke ResultActivity, jika seri di kirim text "Draw",
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0){
            return;
        }

        if (requestCode == 1){
            homeScorer = data.getStringExtra("homeScorer") + "\n " + homeScorer;
            homeScoreVal = data.getIntExtra("homeScore", 0);
            homeScore.setText(String.valueOf(homeScoreVal));
        }else if(requestCode == 2){
            awayScorer = data.getStringExtra("awayScorer") + "\n " + awayScorer;
            awayScoreval =  data.getIntExtra("awayScore", 0);
            awayScore.setText(String.valueOf(awayScoreval));
        }
    }

    public void handelHomeScore(View view) {
        isHome = true;
        Intent i = new Intent(MatchActivity.this, ScorerActivity.class);
        i.putExtra("isHome", isHome);
        i.putExtra("homeScore", homeScoreVal);
        startActivityForResult(i, 1);
    }

    public void handleAwayScore(View view) {
        isHome = false;
        Intent i = new Intent(MatchActivity.this, ScorerActivity.class);
        i.putExtra("isHome", isHome);
        i.putExtra("awayScore", awayScoreval);
        startActivityForResult(i, 2);
    }

    public void handleResult(View view) {
        String winner = "", score = "", scorerName = "";
        if (homeScoreVal > awayScoreval){
            winner = "The winner is " + homeTeam.getText().toString();
            score = "Score \n" + homeScoreVal + " - " + awayScoreval;
            scorerName = "Scorer : \n" + homeScorer;
        }else if (homeScoreVal < awayScoreval){
            winner = "The winner is " + awayTeam.getText().toString();
            score = "Score \n" + homeScoreVal + " - " + awayScoreval;
            scorerName = "Scorer : \n" + awayScorer;
        }else{
            winner = "Draw";
            score = "Score \n" + homeScoreVal + " - " + awayScoreval;
            scorerName = "";
        }
        Intent i = new Intent(MatchActivity.this, ResultActivity.class);
        i.putExtra("winner", winner);
        i.putExtra("score", score);
        i.putExtra("scorer", scorerName);
        startActivity(i);
    }
}
