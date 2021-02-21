package sarathmolathoti.blogspot.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText player1Name,player2Name;
    private Button startGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1Name = (EditText) findViewById(R.id.p1);
        player2Name = (EditText) findViewById(R.id.p2);
        startGame = (Button) findViewById(R.id.startGameBtn);
       // exitGame = (Button) findViewById(R.id.exitGameBtn);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String p1_name = player1Name.getText().toString();
                String p2_name = player2Name.getText().toString();

                if(TextUtils.isEmpty(p1_name) || TextUtils.isEmpty(p2_name)){
                    Toast.makeText(MainActivity.this, "Please Enter Your Names", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent gameActivity = new Intent(MainActivity.this,GameActivity.class);
                    gameActivity.putExtra("player1Name", player1Name.getText().toString());
                    gameActivity.putExtra("player2Name", player2Name.getText().toString());
                    startActivity(gameActivity);
                }
            }
        });
    }
}
