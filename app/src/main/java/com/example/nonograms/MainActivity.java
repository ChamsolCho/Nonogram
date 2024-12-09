package com.example.nonograms;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    AlertDialog alertDialog1;
    AlertDialog alertDialog2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        setContentView(tableLayout);

        TableRow tableRow = new TableRow(this);
        TextView life = new TextView(this);
        TextView lifeNum = new TextView(this);

        life.setText("Life : ");
        lifeNum.setText("3");

        tableLayout.addView(tableRow);
        tableRow.addView(life);
        tableRow.addView(lifeNum);



        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(120, 120);
        layoutParams.setMargins(5, 5, 5, 5);

        TableRow.LayoutParams textLayoutParams = new TableRow.LayoutParams(100, 100);
        textLayoutParams.setMargins(10, 0, -10, 0);

        Cell[][] buttons = new Cell[5][5];
        TextView[][] verticalTextViews = new TextView[3][5];
        TextView[][] horizontalTextViews = new TextView[5][3];


        ToggleButton toggleButton = new ToggleButton(this);
        toggleButton.setTextOn("BLACK SQAURE");
        toggleButton.setTextOff("X");
        toggleButton.setChecked(true);


        AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage("Game over")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "확인", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();

        AlertDialog alertDialog2 = new AlertDialog.Builder(this)
                .setTitle("Game Clear")
                .setMessage("Game Clear")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "확인", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();



        // (Vertical TextViews)
        for (int i = 0; i < 3; i++) {
            TableRow verticalTextRow = new TableRow(this);
            tableLayout.addView(verticalTextRow);

            for (int j = 0; j < 8; j++) {
                if (j < 3) { // 앞쪽에 빈 공백을 추가
                    TextView gap = new TextView(this);
                    gap.setText("   ");
                    gap.setLayoutParams(textLayoutParams);
                    verticalTextRow.addView(gap);
                } else { // 실제 Vertical TextViews
                    int colIndex = j - 3; // 버튼 배열과 일치시키기 위해 인덱스 조정
                    verticalTextViews[i][colIndex] = new TextView(this);
                    verticalTextViews[i][colIndex].setText("0");
                    verticalTextViews[i][colIndex].setLayoutParams(textLayoutParams);
                    verticalTextViews[i][colIndex].setPadding(10, 10, 10, 10);
                    verticalTextRow.addView(verticalTextViews[i][colIndex]);
                }
            }
        }

        // (Horizontal TextViews + Buttons)
        for (int i = 0; i < 5; i++) {
            TableRow textAndButtonRow = new TableRow(this);
            tableLayout.addView(textAndButtonRow);

            // Horizontal TextViews
            for (int j = 0; j < 3; j++) {
                horizontalTextViews[i][j] = new TextView(this);
                horizontalTextViews[i][j].setText("0");
                horizontalTextViews[i][j].setLayoutParams(textLayoutParams);
                horizontalTextViews[i][j].setPadding(10, 10, 10, 10);
                textAndButtonRow.addView(horizontalTextViews[i][j]);
            }

            // Buttons
            for (int j = 0; j < 5; j++) {
                buttons[i][j] = new Cell(this);
                buttons[i][j].setLayoutParams(layoutParams);

                boolean isBlack = new Random().nextBoolean();
                buttons[i][j].updateBlackSqaure(isBlack);

                if (isBlack) {
                    buttons[i][j].setText("");
                    buttons[i][j].setTag(true);
                } else {
                    buttons[i][j].setText("");
                    buttons[i][j].setTag(false);
                }

                textAndButtonRow.addView(buttons[i][j]);
            }
        }

        // Calculate Vertical TextViews
        for (int col = 0; col < 5; col++) {
            int firstColCount = 0, secondColCount = 0, thirdColCount = 0;
            int currentColStreak = 0, streakColCount = 0;

            for (int row = 0; row < 5; row++) {
                if ((boolean) buttons[row][col].getTag()) {
                    currentColStreak++;
                } else {
                    if (currentColStreak > 0) {
                        streakColCount++;
                        if (streakColCount == 1) firstColCount = currentColStreak;
                        else if (streakColCount == 2) secondColCount = currentColStreak;
                        else if (streakColCount == 3) thirdColCount = currentColStreak;
                        currentColStreak = 0;
                    }
                }
            }

            if (currentColStreak > 0) {
                streakColCount++;
                if (streakColCount == 1) firstColCount = currentColStreak;
                else if (streakColCount == 2) secondColCount = currentColStreak;
                else if (streakColCount == 3) thirdColCount = currentColStreak;
            }

            verticalTextViews[0][col].setText(String.valueOf(firstColCount));
            verticalTextViews[1][col].setText(String.valueOf(secondColCount));
            verticalTextViews[2][col].setText(String.valueOf(thirdColCount));
        }

        // Calculate Horizontal TextViews
        for (int i = 0; i < 5; i++) {
            int firstRowCount = 0, secondRowCount = 0, thirdRowCount = 0;
            int currentRowStreak = 0, streakRowCount = 0;

            for (int j = 0; j < 5; j++) {
                if ((boolean) buttons[i][j].getTag()) {
                    currentRowStreak++;
                } else {
                    if (currentRowStreak > 0) {
                        streakRowCount++;
                        if (streakRowCount == 1) firstRowCount = currentRowStreak;
                        else if (streakRowCount == 2) secondRowCount = currentRowStreak;
                        else if (streakRowCount == 3) thirdRowCount = currentRowStreak;
                        currentRowStreak = 0;
                    }
                }
            }

            if (currentRowStreak > 0) {
                streakRowCount++;
                if (streakRowCount == 1) firstRowCount = currentRowStreak;
                else if (streakRowCount == 2) secondRowCount = currentRowStreak;
                else if (streakRowCount == 3) thirdRowCount = currentRowStreak;
            }

            horizontalTextViews[i][0].setText(String.valueOf(firstRowCount));
            horizontalTextViews[i][1].setText(String.valueOf(secondRowCount));
            horizontalTextViews[i][2].setText(String.valueOf(thirdRowCount));
        }


        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                toggleButton.setText("BLACK SQUARE");
            } else {
                toggleButton.setText("X");
            }
        });

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                buttons[i][j].setOnClickListener(v -> {
                    if (toggleButton.isChecked()) {
                        if(!(boolean) v.getTag()){
                            int currentLife = Integer.parseInt(lifeNum.getText().toString());
                            if(currentLife > 0 ){
                                lifeNum.setText(String.valueOf(currentLife-1));
                                Toast.makeText(this, "Life -1", Toast.LENGTH_SHORT).show();
                            }
                            if(lifeNum.getText().toString().equals("0")){
                                alertDialog1.show();

                                for(int x= 0; x < 5; x++){
                                    for(int y = 0; y < 5; y++){
                                        buttons[x][y].setEnabled(false);
                                    }
                                }
                        }
                            return;
                        }
                        if(((Cell) v).markBlackSquare()){
                            if (Cell.numMarkedSquares == Cell.numBlackSquares) {
                                alertDialog2.show();

                                for(int x= 0; x < 5; x++){
                                    for(int y = 0; y < 5; y++){
                                        buttons[x][y].setEnabled(false);
                                    }
                                }
                            }
                        }

                    } else {
                        ((Cell) v).toggleX();
                    }
                });
            }
        }


        tableLayout.addView(toggleButton);
    }
}
