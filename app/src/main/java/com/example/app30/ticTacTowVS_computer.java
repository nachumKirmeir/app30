package com.example.app30;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ticTacTowVS_computer extends AppCompatActivity implements View.OnClickListener {

    Button[][] btns;
    Button btnResetGame;
    TextView tvWIn;
    Button btnHomePage;
    int counter = 0;
    int numWinUser;
    int numWinComputer;
    TextView tvScore;
    Button btnRestartScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_tow_vs_computer);

        tvScore = findViewById(R.id.tvScore);
        tvWIn = (TextView) findViewById(R.id.tvWin);
        tvWIn.setVisibility(View.INVISIBLE);
        btnResetGame = (Button) findViewById(R.id.btnResetGame);
        btnResetGame.setOnClickListener(this);
        btnResetGame.setVisibility(View.INVISIBLE);
        btnHomePage = findViewById(R.id.btnHomePage);
        btnHomePage.setOnClickListener(this);
        btnRestartScore = findViewById(R.id.btnRestartScore);
        btnRestartScore.setOnClickListener(this);


        btns = new Button[3][3];
        btns[0][0] = (Button) findViewById(R.id.btn0);
        btns[0][1] = (Button) findViewById(R.id.btn1);
        btns[0][2] = (Button) findViewById(R.id.btn2);
        btns[1][0] = (Button) findViewById(R.id.btn3);
        btns[1][1] = (Button) findViewById(R.id.btn4);
        btns[1][2] = (Button) findViewById(R.id.btn5);
        btns[2][0] = (Button) findViewById(R.id.btn6);
        btns[2][1] = (Button) findViewById(R.id.btn7);
        btns[2][2] = (Button) findViewById(R.id.btn8);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                btns[i][j].setOnClickListener(this);
        }
        updateBoardScore();
    }

    @Override
    public void onClick(View v) {
        if(v == btnResetGame){
            reset();
        }
        else if(v == btnHomePage){
            Intent intent = new Intent(ticTacTowVS_computer.this, MainActivity.class);
            startActivity(intent);
        }
        else if(v == btnRestartScore){//I will open a dialog that will make sure that the user want to delete the score points
            openNewDialog();
        }
        else {
            if(isWin() == -1)
                userTurn(v);
        }
    }

    public void userTurn(View v)
    {
        Button btn = (Button) v;
        if(btn.getText().toString().length() == 0) {
            btn.setText("O");
            counter++;
            if (doweHaveAwinner()) {
                return;
            } else if(counter != 9){
                computerTurn();
                doweHaveAwinner();
            }
        }
    }
    public boolean doweHaveAwinner()
    {
        if (isWin() != -1) {
            if (isWin() == 1)
            {
                tvWIn.setText("The User Win (O)");
            }
            else if (isWin() == 2)
            {
                tvWIn.setText("The Computer Win (X)");
            }
            saveWinner(isWin());// maybe i need to add "this"
            btnResetGame.setVisibility(View.VISIBLE);
            tvWIn.setVisibility(View.VISIBLE);
            return true;

        }
        else if (counter > 8)
        {
            tvWIn.setVisibility(View.VISIBLE);
            tvWIn.setText("No One Win");
            btnResetGame.setVisibility(View.VISIBLE);
            return false;
        }
        return false;
    }
    public int isWin()
    {
        if (counter > 4) {
            int j = 0;
            for (int i = 0; i < 3; i++) {
                if (btns[i][j].getText().toString().length() > 0 && btns[i][j].getText().toString().equals(btns[i][j + 1].getText().toString()) && btns[i][j].getText().toString().equals(btns[i][j + 2].getText().toString())) {
                    if (btns[i][j].getText().toString().equals("O"))
                        return 1;//0 win  - odd counting
                    return 2;//x win
                } else if (btns[j][i].getText().toString().length() > 0 && btns[j][i].getText().toString().equals(btns[j + 1][i].getText().toString()) && btns[j][i].getText().toString().equals(btns[j + 2][i].getText().toString())) {
                    if (btns[j][i].getText().toString().equals("O"))
                        return 1;//user win - even win
                    return 2;
                }

            }//end of for
            //checking diagonal
            if (btns[0][0].getText().toString().length() > 0 && btns[0][0].getText().toString().equals(btns[1][1].getText().toString()) && btns[0][0].getText().toString().equals(btns[2][2].getText().toString())) {
                if (btns[0][0].getText().toString().equals("O"))
                    return 1;//0 win  - odd counting
                return 2;//x win
            }
            if (btns[0][2].getText().toString().length() > 0 && btns[0][2].getText().toString().equals(btns[1][1].getText().toString()) && btns[0][2].getText().toString().equals(btns[2][0].getText().toString())) {
                if (btns[1][1].getText().toString().equals("O"))
                    return 1;//o win  - odd counting
                return 2;
            }
        }
        return -1;//no one win
    }



    public void computerTurn()
    {
        counter++;
        boolean isBestPlayMadeMove;
        isBestPlayMadeMove = bestPlay();
        if(!isBestPlayMadeMove) {
            if(btns[1][1].getText().toString().length() == 0){
                btns[1][1].setText("X");//the middle is a great place to start
                return;
            }
            else if(counter == 2){
                btns[0][0].setText("X");
                return;
            }
            Random random = new Random();
            boolean find = false;
            while (!find) {
                int col = random.nextInt(10) % 3;
                int row = random.nextInt(10) % 3;
                if (btns[row][col].getText().toString().equals("")) {
                    btns[row][col].setText("X");
                    find = true;
                }
            }//end of while
        }
    }



    //this algorithm will put x in the best position
    public boolean bestPlay(){

        int j = 0;
        for(int i = 0; i < 3; i++){

            //check for two in row from left to right
            if(btns[i][j].getText().toString().length() != 0 && btns[i][j].getText().toString().equals(btns[i][j + 1].getText().toString()) && btns[i][j + 2].getText().toString().length() == 0){
                btns[i][j + 2].setText("X");
                return true;
            }
            //check for two in column from top to bottom
            else if(btns[j][i].getText().toString().length() != 0 && btns[j][i].getText().toString().equals(btns[j + 1][i].getText().toString())&& btns[j + 2][i].getText().toString().length() == 0){
                btns[j + 2][i].setText("X");
                return true;
            }
            //check for two in row from right to left
            else if(btns[i][j + 2].getText().toString().length() != 0 && btns[i][j + 2].getText().toString().equals(btns[i][j + 1].getText().toString()) && btns[i][j].getText().toString().length() == 0){
                btns[i][j].setText("X");
                return true;
            }
            //check for two in column from bottom to top
            else if(btns[j + 2][i].getText().toString().length() != 0 && btns[j + 2][i].getText().toString().equals(btns[j + 1][i].getText().toString())&& btns[j][i].getText().toString().length() == 0){
                btns[j][i].setText("X");
                return true;
            }
            //check for two in row try put X in the middle
            else if(btns[i][j].getText().toString().length() != 0 && btns[i][j].getText().toString().equals(btns[i][j + 2].getText().toString()) && btns[i][j + 1].getText().toString().length() == 0){
                btns[i][j + 1].setText("X");
                return true;
            }
            //check for two in collum try put X in the middle
            else if(btns[j][i].getText().toString().length() != 0 && btns[j][i].getText().toString().equals(btns[j + 2][i].getText().toString()) && btns[j + 1][i].getText().toString().length() == 0){
                btns[j + 1][i].setText("X");
                return true;
            }

        }
        //check in the diagonal by order
        if(btns[0][0].getText().toString().length() != 0 && btns[0][0].getText().toString().equals(btns[1][1].getText().toString()) && btns[2][2].getText().toString().length()== 0){
            btns[2][2].setText("X");
            return true;
        }
        //check in the diagonal without order
        else if(btns[0][0].getText().toString().length() != 0 && btns[0][0].getText().toString().equals(btns[2][2].getText().toString()) && btns[1][1].getText().toString().length()== 0){
            btns[1][1].setText("X");
            return true;
        }
        //check in the diagonal by order from left
        else if(btns[0][2].getText().toString().length() != 0 && btns[0][2].getText().toString().equals(btns[1][1].getText().toString()) && btns[2][0].getText().toString().length()== 0){
            btns[2][0].setText("X");
            return true;
        }
        //check in the diagonal without order from left
        else if(btns[0][2].getText().toString().length() != 0 && btns[0][2].getText().toString().equals(btns[2][0].getText().toString()) && btns[1][1].getText().toString().length()== 0){
            btns[1][1].setText("X");
            return true;
        }
        //diagonal from bottom right
        else if(btns[2][0].getText().toString().length() != 0 && btns[2][0].getText().toString().equals(btns[1][1].getText().toString()) && btns[0][2].getText().toString().length()== 0){
            btns[0][2].setText("X");
            return true;
        }
        //diagonal from bottom left
        else if(btns[2][2].getText().toString().length() != 0 && btns[2][2].getText().toString().equals(btns[1][1].getText().toString()) && btns[0][0].getText().toString().length()== 0){
            btns[0][0].setText("X");
            return true;
        }
        return false;
    }



    public void reset()
    {
        counter = 0;
        btnResetGame.setVisibility(View.INVISIBLE);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                btns[i][j].setText("");
        }
        tvWIn.setVisibility(View.INVISIBLE);
    }

    public void saveWinner(int numWinner){

        SharedPreferences sp = getSharedPreferences("score", 0);
        SharedPreferences.Editor editor = sp.edit();
        numWinUser = sp.getInt("userScore", 0);
        numWinComputer = sp.getInt("computerScore", 0);
        if(numWinner == 1){ //the user O win
            numWinUser++;
        }
        else if(numWinner == 2){//the computer win X
            numWinComputer++;
        }
        editor.putInt("userScore", numWinUser);
        editor.putInt("computerScore", numWinComputer);
        editor.commit();
        updateBoardScore();
    }

    public void updateBoardScore(){
        SharedPreferences sp = getSharedPreferences("score", 0);
        numWinUser = sp.getInt("userScore", 0);
        numWinComputer = sp.getInt("computerScore", 0);
        tvScore.setText("Points User: " + numWinUser + " ---- Points Computer: " + numWinComputer);
    }

    public void openNewDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Score");
        builder.setMessage("Are You Sure You Want To Delete The Scores From The App?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new HandleAlertDialogListener());
        builder.setNegativeButton("No", new HandleAlertDialogListener());
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public class HandleAlertDialogListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which == -1){
                SharedPreferences sp = getSharedPreferences("score", 0);
                SharedPreferences.Editor editor = sp.edit();
                numWinUser = 0;
                numWinComputer = 0;
                editor.putInt("userScore" , 0);
                editor.putInt("computerScore", 0);
                editor.commit();
                updateBoardScore();
                Toast.makeText(ticTacTowVS_computer.this, "The score is deleted", Toast.LENGTH_SHORT).show();
            }
            else if(which == -2){
                Toast.makeText(ticTacTowVS_computer.this, "You didn't deleted the score" + which, Toast.LENGTH_SHORT).show();
            }
        }

    }
}