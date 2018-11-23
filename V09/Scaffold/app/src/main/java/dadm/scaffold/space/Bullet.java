package dadm.scaffold.space;

import android.util.Log;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

public class Bullet extends Sprite {

    private double speedFactor;

    private SpaceShipPlayer parent;

    public Bullet(GameEngine gameEngine) {
        super(gameEngine, R.drawable.bullet, BodyType.Circular);

        speedFactor = gameEngine.pixelFactor * -300d / 1000d;
    }

    @Override
    public void startGame() {
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor * elapsedMillis;
        if (positionY < -mHeight) {
            removeObject(gameEngine);
        }
    }


    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - mWidth / 2;
        positionY = initPositionY - mHeight / 2;
        parent = parentPlayer;
    }

    public void removeObject(GameEngine gameEngine){
        gameEngine.removeGameObject(this);
        // And return it to the pool
        parent.releaseBullet(this);

    }

    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

        if (otherObject instanceof Enemy) {    // Remove both from the game (and return them to their pools)

            removeObject(gameEngine);
            Enemy e = (Enemy) otherObject;
            e.removeObject(gameEngine);
            parent.score++;
            parent.checkWin(gameEngine);

        }
    }

}
