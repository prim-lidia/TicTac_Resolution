package pmdm.ieselcaminas.org.tictac_resolution;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    class Player {
        public String name;
        public String symbol;

        public Player(String name, String symbol){
            this.name = name;
            this.symbol = symbol;
        }
    }
    enum StateOfGame {
        Playing, Draw, Winner;
    }
    public static final int NUM_ROWS_COLUMNS = 3;

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Button [][] buttons;
    private Boolean gameOver;
    private StateOfGame stateOfGame;
    private int numberOfMoves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1 = new Player("1", "X");
        player2 = new Player("2", "O");

        gameOver = false;
        currentPlayer = player1;
        stateOfGame = StateOfGame.Playing;
        numberOfMoves = 0;

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        gridLayout.setRowCount(NUM_ROWS_COLUMNS);
        gridLayout.setColumnCount(NUM_ROWS_COLUMNS);

        addButtons (gridLayout);
        displayTurn();
    }

    private void addButtons(GridLayout gridLayout) {
        buttons = new Button[NUM_ROWS_COLUMNS][NUM_ROWS_COLUMNS];

        for(int row =0; row<NUM_ROWS_COLUMNS; row++) {
            for (int col=0; col<NUM_ROWS_COLUMNS; col++) {
                buttons[row][col] = new Button(this, null, android.R.attr.buttonStyleSmall);
                gridLayout.addView(buttons[row][col]);
                addListenerToButton(buttons[row][col]);
            }
        }
    }

    private void addListenerToButton(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (!(stateOfGame == StateOfGame.Playing)) {return;}

                if ( button.getText().equals("")) {
                    button.setText(currentPlayer.symbol);
                    numberOfMoves++;
                    stateOfGame = checkWinner();
                    if (stateOfGame != StateOfGame.Playing){
                        displayEndOfGame();
                    } else {
                        changePlayer();
                        displayTurn();
                    }
                }
            }
        });
    }

    private void displayEndOfGame() {
        TextView textView = findViewById(R.id.textTurn);

        if(stateOfGame == StateOfGame.Draw ) {
            textView.setText("DRAW");
        }else {
            if (stateOfGame == StateOfGame.Winner) {
                textView.setText("Winner: "+currentPlayer.name+ " (" + currentPlayer.symbol+")");
            }
        }

    }

    private StateOfGame checkWinner() {
        boolean winner = checkRowsWinner();
        if(!winner) {
            winner = checkColsWinner();
        }
        if(!winner) {
            winner = checkDiagonalLeftToRight();
        }
        if(!winner) {
            winner = checkDiagonalRightToLeft();
        }
        if(!winner) {
            if(numberOfMoves == 9){
                return StateOfGame.Draw;
            }
        }
        if (winner) {
            return StateOfGame.Winner;
        }
        return StateOfGame.Playing;
    }

    private boolean checkDiagonalRightToLeft() {
        int countSymbols = 0;
        for (int i = 0; i<NUM_ROWS_COLUMNS; i++){
            if(buttons[i][NUM_ROWS_COLUMNS-i-1].getText().equals(currentPlayer.symbol)){
                countSymbols++;
            }
        }
        if( countSymbols==NUM_ROWS_COLUMNS ){
            return true;
        }
        return false;
    }

    private boolean checkDiagonalLeftToRight() {
        int countSymbols = 0;
        for (int i = 0; i<NUM_ROWS_COLUMNS; i++){
            if(buttons[i][i].getText().equals(currentPlayer.symbol)){
                countSymbols++;
            }
        }
        if( countSymbols==NUM_ROWS_COLUMNS ){
            return true;
        }
        return false;
    }

    private boolean checkColsWinner() {
        int countSymbols;
        for (int col=0; col<NUM_ROWS_COLUMNS; col++){
            countSymbols = 0;
            for (int row = 0; row<NUM_ROWS_COLUMNS; row++){
                if(buttons[row][col].getText().equals(currentPlayer.symbol)){
                    countSymbols++;
                }
            }
            if( countSymbols==NUM_ROWS_COLUMNS ){
                return true;
            }
        }
        return false;
    }

    private boolean checkRowsWinner() {
        int countSymbols;
        for (int row = 0; row<NUM_ROWS_COLUMNS; row++){
            countSymbols = 0;
            for (int col=0; col<NUM_ROWS_COLUMNS; col++){
                if(buttons[row][col].getText().equals(currentPlayer.symbol)){
                    countSymbols++;
                }
            }
            if( countSymbols==NUM_ROWS_COLUMNS ){
                return true;
            }
        }
        return false;
    }


    private void changePlayer() {
        if (currentPlayer == player1){
            currentPlayer = player2;
        }else {
            currentPlayer = player1;
        }
    }

    private void displayTurn(){
        TextView textView = findViewById(R.id.textTurn);

        textView.setText("Turn player "+ currentPlayer.name + "  Symbol: "+currentPlayer.symbol);

    }


}
