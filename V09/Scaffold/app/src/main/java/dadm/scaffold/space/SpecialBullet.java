package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

public class SpecialBullet  extends Sprite {
    private double mSpeed;

    private double mSpeedX, mSpeedY;
    private SpaceShipPlayer parent;
    private int direction;

    public SpecialBullet(GameEngine gameEngine,int direction){ //0º is Up
        super(gameEngine, R.drawable.bullet, BodyType.Circular);//TODO cambiar sprite
        rotation = -direction;
        this.direction= direction;
        mSpeed = gameEngine.pixelFactor * - 300d / 1000d;
    }

    @Override
    public void startGame() {
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {


        positionX += mSpeedX * elapsedMillis;
        positionY += mSpeedY * elapsedMillis;


        if(positionY < 0){ //Se sale por arriba
            removeObject(gameEngine);
        }else if(positionX > gameEngine.width-mWidth){ //Se sale por la derecha
            removeObject(gameEngine);
        }else if(positionX < mWidth){ //Se sale por la izquierda
            removeObject(gameEngine);
        }
    }


    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - mWidth / 2;
        positionY = initPositionY - mHeight / 2;
        parent = parentPlayer;

        mSpeedX = mSpeed * Math.sin(Math.toRadians(direction));
        mSpeedY = mSpeed *Math.cos(Math.toRadians(direction));


    }

    public void removeObject(GameEngine gameEngine){
        gameEngine.removeGameObject(this);
        // And return it to the pool
        parent.releaseSpecialBullet(this);

    }

    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

        if (otherObject instanceof Enemy) {    // Remove both from the game (and return them to their pools)

            removeObject(gameEngine);
            Enemy e = (Enemy) otherObject;
            e.removeObject(gameEngine);
            parent.score++;


        }
    }
}
