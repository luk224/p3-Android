package dadm.scaffold.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;

import dadm.scaffold.R;
import dadm.scaffold.space.SpaceShipPlayer;

public class PlayerScoreUI extends GameObject{

    private final float textWidth;
    private final float textHeight;

    private Paint paint;
    private int draws;
    private float framesPerSecond;
    private SpaceShipPlayer spaceShipPlayer;
    private String scoreText = "";

    Typeface tf ;

    private String framesPerSecondText = "";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public PlayerScoreUI (GameEngine gameEngine, SpaceShipPlayer spaceShipPlayer) {
        this.spaceShipPlayer = spaceShipPlayer;
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        textHeight = (float) (40 * gameEngine.pixelFactor);
        textWidth = (float) (120 * gameEngine.pixelFactor);
        paint.setTextSize(textHeight / 2);
        this.scoreText = gameEngine.getContext().getString(R.string.score);
        this.tf = gameEngine.getContext().getResources().getFont(R.font.ampersand);
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDraw(Canvas canvas) {



        paint.setColor(Color.argb(0.5f,0f,0f,0f));
        canvas.drawRect(0, textHeight, textWidth,(int) (textHeight * 2), paint);
        paint.setColor(Color.RED);
        paint.setTextSize(60f);
        paint.setTypeface(tf);
        canvas.drawText(scoreText+" "+Integer.toString(spaceShipPlayer.score), textWidth / 2, (int) ( textHeight + 3*textHeight/4 ), paint);
        draws++;
    }



}
