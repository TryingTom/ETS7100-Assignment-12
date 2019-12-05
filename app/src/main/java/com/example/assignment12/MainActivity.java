package com.example.assignment12;

import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import static com.example.assignment12.R.color.white;

public class MainActivity extends AppCompatActivity{

    View view;
    private GestureDetectorCompat myGestureDetector;
    private String TAG = "MainActivity";
    boolean isBlack = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (View) findViewById(R.id.view);

        myGestureDetector = new GestureDetectorCompat(this, new GestureListner());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        myGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class GestureListner extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            // get max velocity of the system
            float maxFlingVelocity = ViewConfiguration.get(MainActivity.this).getScaledMaximumFlingVelocity();


            // making the numbers absolute because it's easier to check them, also it wasn't specified to include the negative velocity
            String xMuutosRed = Integer.toHexString(Math.abs((int) ((255 / maxFlingVelocity) * velocityX)));
            String yMuutosBlue = Integer.toHexString(Math.abs((int) ((255 / maxFlingVelocity) * velocityY)));

            // make sure that the change is in form 0x00 rather than 0x0 or something. Because that would cause an error
            if (xMuutosRed.length() == 1){
                xMuutosRed = 0 + xMuutosRed;
            }
            if (yMuutosBlue.length() == 1){
                yMuutosBlue = 0 + yMuutosBlue;
            }

            Toast.makeText(MainActivity.this, "x fling:" + xMuutosRed + "\ny fling:" + yMuutosBlue, Toast.LENGTH_SHORT).show();

            String colorString ="#" + xMuutosRed + "00" + yMuutosBlue;

            view.setBackgroundColor(Color.parseColor(colorString));
            isBlack = false;
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        // sometimes takes two double taps from one double tap, though it could be just my phone (Can't test on simulator because it broke)
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            if(isBlack){
                view.setBackgroundResource(white);
                Toast.makeText(MainActivity.this, "DoubleTap now white", Toast.LENGTH_SHORT).show();
                isBlack = false;
            }else{
                view.setBackgroundResource(R.color.black);
                Toast.makeText(MainActivity.this, "DoubleTap now black", Toast.LENGTH_SHORT).show();
                isBlack = true;
            }
            return super.onDoubleTapEvent(e);
        }
    }

}
