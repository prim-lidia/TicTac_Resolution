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
    public static final int NUM_ROWS_COLUMNS = 3;

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Button [][] buttons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1 = new Player("1", "X");
        player2 = new Player("2", "O");
        currentPlayer = player1;

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
                button.setText(currentPlayer.symbol);
                changePlayer();
                displayTurn();
            }
        });
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
