package sarathmolathoti.blogspot.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView player1Score,player2Score;
    private Button resetBtn;
    private Button[][] btns=new Button[3][3];

    private int player1Points=0,player2Points=0;
    private int roundCount=0;
    private Boolean player1Tern=true;
    private String Player1Name,Player2Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initViews();

        Player1Name=getIntent().getStringExtra("player1Name");
        Player2Name=getIntent().getStringExtra("player2Name");
        player1Score.setText(Player1Name+"(X): 0");
        player2Score.setText(Player2Name+"(0): 0");

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();

            }
        });
    }

    private void initViews() {

        player1Score=(TextView) findViewById(R.id.p1_score);
        player2Score=(TextView) findViewById(R.id.p2_score);
        resetBtn=(Button) findViewById(R.id.resetBtn);

        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                String id_name="btn_"+i+j; //btn_00 ,btn_01,btn_02
                int btnId=this.getResources().getIdentifier(id_name,"id",getPackageName());
                btns[i][j]=(Button) findViewById(btnId); //findViewById(R.id.btn_00);
                btns[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (player1Tern)
        {
            ((Button)v).setText("X");
            ((Button)v).setTextColor(this.getResources().getColor(R.color.colorAccent));
            ((Button)v).setEnabled(false);
        }else
        {
            ((Button)v).setText("O");
            ((Button)v).setTextColor(this.getResources().getColor(R.color.colorPrimary));
            v.setEnabled(false);
        }
        roundCount++;

        if (checkForWin())
        {
            if (player1Tern)
            {
                player1Wins();
            }else
            {
                player2Wins();
            }
        }else if(roundCount==9)
        {
            draw();
        }else
        {
            player1Tern = !player1Tern;
        }

    }

    private void draw() {
        //Toast.makeText(this,"Match draw", Toast.LENGTH_SHORT).show();
        askForAnotherGame("Match Draw");
    }

    private void player2Wins() {
        player2Points++;
       // Toast.makeText(this, Player2Name+" wins", Toast.LENGTH_SHORT).show();
        updatePointText();
        askForAnotherGame(Player2Name+" Won");
    }



    private void player1Wins() {
        player1Points++;
         //Toast.makeText(this, Player1Name +" wins", Toast.LENGTH_SHORT).show();
        updatePointText();
        askForAnotherGame(Player1Name+" Won");
    }

    private void resetBoard() {

        for (int i=0;i<3;i++)
        {
            for (int j=0;j<3;j++)
            {
                btns[i][j].setText("");
                btns[i][j].setEnabled(true);
            }
        }
        roundCount=0;
        player1Tern=true;
    }

    private void updatePointText() {
        player1Score.setText(Player1Name+":"+player1Points);
        player2Score.setText(Player2Name+":"+player2Points);

    }

    private boolean checkForWin() {
        String field[][]=new String[3][3];
        for (int i=0;i<3;i++)
        {
            for (int j=0;j<3;j++)
            {
                field[i][j]=btns[i][j].getText().toString();
            }
        }
        //comparing columns while changing rows
        for (int i=0;i<3;i++)
        {
            //        00       =         01    &&      00      =           02  row1
            //        10       =         11    &&      10      =           12  row2
            //        00       =         01    &&      00      =           02   row3
            if (field[i][0].equals(field[i][1])&&field[i][0].equals(field[i][2])&&!(field[i][0].equals("")))
            {
                return true;
            }
        }
        for (int i=0;i<3;i++) //comparing rows while changing columns
        {
            if (field[0][i].equals(field[1][i])&&field[0][i].equals(field[2][i])&&!(field[0][i].equals("")))
            {
                return true;
            }
        }
        //comparing \ diagonal
        if( field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && (!field[0][0].equals("")))
        {
            return true;
        }

        //comparing reverse diagonal
        if( field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && (!field[0][2].equals("")))
        {
            return true;
        }
        return false;
    }

    public void resetGame()
    {
        player1Points=0;
        player2Points=0;
        resetBoard();
        updatePointText();
    }


    private void askForAnotherGame(String msg)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(GameActivity.this);
        builder.setTitle(msg);
        builder.setMessage("Play Again??");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetBoard();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(GameActivity.this,MainActivity.class));
            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();


    }

}
