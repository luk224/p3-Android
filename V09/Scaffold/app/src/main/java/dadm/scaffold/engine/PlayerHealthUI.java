package dadm.scaffold.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import dadm.scaffold.R;
import dadm.scaffold.space.SpaceShipPlayer;

public class PlayerHealthUI extends GameObject {

    private final float textWidth;
    private final float textHeight;
    private final String healthText;
    private final Typeface tf;

    private Paint paint;
    private int draws;
    private float framesPerSecond;
    private SpaceShipPlayer spaceShipPlayer;

    private String framesPerSecondText = "";

    public PlayerHealthUI (GameEngine gameEngine, SpaceShipPlayer spaceShipPlayer) {
        this.spaceShipPlayer = spaceShipPlayer;
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        textHeight = (float) (40 * gameEngine.pixelFactor);
        textWidth = (float) (120 * gameEngine.pixelFactor);
        paint.setTextSize(textHeight / 2);
        this.healthText = gameEngine.getContext().getString(R.string.health);
        this.tf = gameEngine.getContext().getResources().getFont(R.font.ampersand);
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }

    @Override
    public void onDraw(Canvas canvas) {



        paint.setColor(Color.argb(0.5f,0f,0f,0f));
        canvas.drawRect(0, 0, textWidth,(int) (textHeight), paint);
        paint.setColor(Color.RED);
        paint.setTypeface(tf);
        paint.setTextSize(60f);
        canvas.drawText(healthText+" "+Integer.toString(spaceShipPlayer.health)+"%", textWidth / 2, (int) ( 3*textHeight / 4), paint);
        draws++;
    }

}
