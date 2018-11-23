package dadm.scaffold.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import dadm.scaffold.space.SpaceShipPlayer;

public class PlayerScoreUI extends GameObject{

    private final float textWidth;
    private final float textHeight;

    private Paint paint;
    private int draws;
    private float framesPerSecond;
    private SpaceShipPlayer spaceShipPlayer;

    private String framesPerSecondText = "";

    public PlayerScoreUI (GameEngine gameEngine, SpaceShipPlayer spaceShipPlayer) {
        this.spaceShipPlayer = spaceShipPlayer;
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        textHeight = (float) (25 * gameEngine.pixelFactor);
        textWidth = (float) (100* gameEngine.pixelFactor);
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
        canvas.drawRect(0, textHeight, textWidth,(int) (textHeight * 2), paint);
        paint.setColor(Color.RED);
        canvas.drawText("Score: "+Integer.toString(spaceShipPlayer.score), textWidth / 2, (int) ( textHeight + 3*textHeight/4 ), paint);
        draws++;
    }



}
