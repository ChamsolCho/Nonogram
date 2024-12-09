package com.example.nonograms;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public class Cell extends AppCompatButton {

    boolean blackSquare = false;
    boolean checked = false;
    static int numBlackSquares = 0;
    static int numMarkedSquares = 0;

    public Cell(@NonNull Context context) {
        super(context);

        setBackgroundResource(R.drawable.cell_shape);

    }

    public boolean isBlackSquare(){
        return blackSquare;

    }

    public static int getNumBlackSquares(){
        return numBlackSquares;
    }

    public void updateBlackSqaure(boolean isBlack){
        if(isBlack && !blackSquare){
            numBlackSquares++;
        }else if (!isBlack && blackSquare){
            numBlackSquares--;
        }
        blackSquare = isBlack;
    }

    public boolean markBlackSquare(){
        if (blackSquare && !checked) { // 이미 체크되지 않은 경우에만
            setBackgroundResource(R.drawable.cell_pressed_shape);
            numMarkedSquares++; // 마킹된 블랙 스퀘어 개수 증가
            checked = true; // 체크 상태로 표시
            return true; // 성공적으로 마킹
        }
        return false;
    }

    public boolean toggleX() {
        if(checked==false){
            setBackgroundResource(R.drawable.cell_shape);
            setText("X");
            return checked = true;
        } else{
            setBackgroundResource(R.drawable.cell_shape);
            setText("");
            return checked = false;
        }
    }


}
