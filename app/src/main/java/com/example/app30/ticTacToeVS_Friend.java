package com.example.app30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ticTacToeVS_Friend extends AppCompatActivity implements View.OnClickListener {

    Button[][] buttons;
    Button btnResetGame;
    TextView tvWin;
    Button btnHomePage;
    int counter = 0;//the counter will help to decide whose turn it is

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe_vs_friend);

        btnResetGame = findViewById(R.id.btnResetGame);
        tvWin = findViewById(R.id.tvWin);
        tvWin.setVisibility(View.INVISIBLE);
        btnResetGame.setOnClickListener(this);
        btnResetGame.setVisibility(View.INVISIBLE);
        btnHomePage = findViewById(R.id.btnHomePage);
        btnHomePage.setOnClickListener(this);

        buttons = new Button[3][3];
        buttons[0][0] = (Button) findViewById(R.id.btn0);
        buttons[0][1] = (Button) findViewById(R.id.btn1);
        buttons[0][2] = (Button) findViewById(R.id.btn2);
        buttons[1][0] = (Button) findViewById(R.id.btn3);
        buttons[1][1] = (Button) findViewById(R.id.btn4);
        buttons[1][2] = (Button) findViewById(R.id.btn5);
        buttons[2][0] = (Button) findViewById(R.id.btn6);
        buttons[2][1] = (Button) findViewById(R.id.btn7);
        buttons[2][2] = (Button) findViewById(R.id.btn8);
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                buttons[i][j].setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == btnResetGame){
            this.reset();
        }
        else if(v == btnHomePage){
            Intent intent = new Intent(ticTacToeVS_Friend.this, MainActivity.class);
            startActivity(intent);
        }

        else{
            Button btn = (Button) v;
            if(btn.getText().toString().length() == 0 && isWin() == -1) {
                if (counter % 2 == 0) {
                    btn.setText("X");//user
                } else {
                    btn.setText("O");//computer
                }
                counter++;
                int numberIsWin = isWin();
                if (numberIsWin != -1) {
                    if (numberIsWin == 2)
                        tvWin.setText("X Win The Game");
                    else if (numberIsWin == 1)
                        tvWin.setText("O Win Thw Game");
                    btnResetGame.setVisibility(View.VISIBLE);
                    tvWin.setVisibility(View.VISIBLE);
                } else if (counter >= 9) {
                    tvWin.setVisibility(View.VISIBLE);
                    tvWin.setText("No One Win");
                    btnResetGame.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    //the function will check if their is a winner
    public int isWin() {
        if (counter > 4) {
            int j = 0;
            for (int i = 0; i < 3; i++) {
                if (buttons[i][j].getText().toString().length() > 0 && buttons[i][j].getText().toString().equals(buttons[i][j + 1].getText().toString()) && buttons[i][j].getText().toString().equals(buttons[i][j + 2].getText().toString())) {
                    if (buttons[i][j].getText().toString().equals("O"))
                        return 1;//0 win  - odd counting
                    return 2;//x win
                } else if (buttons[j][i].getText().toString().length() > 0 && buttons[j][i].getText().toString().equals(buttons[j + 1][i].getText().toString()) && buttons[j][i].getText().toString().equals(buttons[j + 2][i].getText().toString())) {
                    if (buttons[j][i].getText().toString().equals("O"))
                        return 1;//computer win - even win
                    return 2;//user Win
                }
            }
            //checking diagonal
            if (buttons[0][0].getText().toString().length() > 0 && buttons[0][0].getText().toString().equals(buttons[1][1].getText().toString()) && buttons[0][0].getText().toString().equals(buttons[2][2].getText().toString())) {
                if (buttons[0][0].getText().toString().equals("O"))
                    return 1;//0 win  - odd counting - computer
                return 2;//x win user
            }
            if (buttons[0][2].getText().toString().length() > 0 && buttons[0][2].getText().toString().equals(buttons[1][1].getText().toString()) && buttons[0][2].getText().toString().equals(buttons[2][0].getText().toString())) {
                if (buttons[0][2].getText().toString().equals("O"))
                    return 1;//o win  - odd counting
                return 2;
            }
        }
        return -1;
    }

    public void reset(){
        tvWin.setVisibility(View.INVISIBLE);
        btnResetGame.setVisibility(View.INVISIBLE);
        counter = 0;
        for(int i = 0; i<buttons.length;i++)
            for(int j = 0; j<buttons[i].length;j++)
                buttons[i][j].setText("");
    }

}