package dadm.scaffold.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import dadm.scaffold.space.SpaceShipPlayer;

public class PlayerHealthUI extends GameObject {

    private final float textWidth;
    private final float textHeight;

    private Paint paint;
    private int draws;
    private float framesPerSecond;
    private SpaceShipPlayer spaceShipPlayer;

    private String framesPerSecondText = "";

    public PlayerHealthUI (GameEngine gameEngine, SpaceShipPlayer spaceShipPlayer) {
        this.spaceShipPlayer = spaceShipPlayer;
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        textHeight = (float) (25 * gameEngine.pixelFactor);
        textWidth = (float) (50 * gameEngine.pixelFactor);
        paint.setTextSize(textHeight / 2);
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }

    @Override
    public void onDraw(Canvas canvas) {



        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, textWidth,(int) (textHeight), paint);
        paint.setColor(Color.RED);
        canvas.drawText(Integer.toString(spaceShipPlayer.health)+"%", textWidth / 2, (int) ( 3*textHeight / 4), paint);
        draws++;
    }

}
