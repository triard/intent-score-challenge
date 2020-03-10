package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private TextView scoreText, winnerText, scorerNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scoreText = findViewById(R.id.textView2);
        winnerText = findViewById(R.id.textView3);
        scorerNameText = findViewById(R.id.textView4);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            scoreText.setText(bundle.getString("score"));
            winnerText.setText(bundle.getString("winner"));
            scorerNameText.setText(bundle.getString("scorer"));
        }
    }
}
