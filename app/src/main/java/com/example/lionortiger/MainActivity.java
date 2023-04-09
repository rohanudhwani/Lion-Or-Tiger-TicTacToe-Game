package com.example.lionortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum Player {
        ONE, TWO, No
    }

    Player currentPlayer = Player.ONE;

    Player[] playerChoices = new Player[9];

    int[][] winnerRowsColumns = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private boolean gameOver = false;

    private Button btnReset;

    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int index = 0; index < playerChoices.length; index++){
            playerChoices[index] = Player.No;
        }

        btnReset = findViewById(R.id.btnReset);
        gridLayout = findViewById(R.id.gridLayout);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTheGame();
            }
        });

    }

    public void imageViewIsTapped(View imageView){
        ImageView tappedImageView = (ImageView) imageView;
        int tiTag = Integer.parseInt(tappedImageView.getTag().toString());

        if (playerChoices[tiTag] == Player.No && gameOver == false) {

            tappedImageView.setTranslationX(-2000);

            playerChoices[tiTag] = currentPlayer;

            if (currentPlayer == Player.ONE) {
                tappedImageView.setImageResource(R.drawable.lion);
                tappedImageView.animate().translationXBy(2000).alpha(1).rotation(3600).setDuration(1000);
                currentPlayer = Player.TWO;

            } else if (currentPlayer == Player.TWO) {

                tappedImageView.setImageResource(R.drawable.tiger);
                tappedImageView.animate().translationXBy(2000).alpha(1).rotation(3600).setDuration(1000);
                currentPlayer = Player.ONE;

            }

//            Toast.makeText(this, tappedImageView.getTag() + "", Toast.LENGTH_SHORT).show();

            for (int[] winnerColumns : winnerRowsColumns) {
                if (playerChoices[winnerColumns[0]] == playerChoices[winnerColumns[1]] && playerChoices[winnerColumns[1]] == playerChoices[winnerColumns[2]] && playerChoices[winnerColumns[0]] != Player.No) {

                    btnReset.animate().alpha(0);
                    btnReset.setVisibility(View.VISIBLE);
                    btnReset.animate().alpha(1).setDuration(2000);
                    gameOver = true;
                    String winner = "";
                    if (currentPlayer == Player.ONE) {
                        winner = "Player 2";
                    } else if (currentPlayer == Player.TWO) {
                        winner = "Player 1";
                    }

                    Toast.makeText(this, winner + " is the winner!", Toast.LENGTH_LONG).show();
                }
            }

        }

    }

    //Reset Game Function
    private void resetTheGame(){

        for (int index = 0; index < gridLayout.getChildCount(); index++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(index);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0f);
        }

        currentPlayer = Player.ONE;

        for (int index = 0; index < playerChoices.length; index++){
            playerChoices[index] = Player.No;
        }

        gameOver = false;

        btnReset.setVisibility(View.INVISIBLE);

    }

}