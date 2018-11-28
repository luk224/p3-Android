package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class Enemy extends Sprite {

    private double mSpeedX, mSpeedY;
    private final double mSpeed;
    private final GameController mController;


    public Enemy(GameController gameController, GameEngine gameEngine) {
        //super(gameEngine.getContext(), R.drawable.enemy, gameEngine.pixelFactor); //Este es del libro pero no funciona.
        super(gameEngine, R.drawable.enemy,BodyType.Circular);
        mSpeed = 200d * pixelFactor / 1000d;
        System.out.println("SpeedY ENEMY _________________________"+mSpeed);
        mController = gameController;
    }

    @Override
    public void startGame() {
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionX += mSpeedX * elapsedMillis;
        positionY += mSpeedY * elapsedMillis;

        //Log.i("Velocidad modulo:"," "+Math.sqrt(mSpeedX*mSpeedX+mSpeedY*mSpeedY));
        //Check of the sprite goes out of the screen    if (mPositionY > gameEngine.mHeight) {      // Return to the pool      gameEngine.removeGameObject(this);      mController.returnToPool(this);    }
        //Sistema de coordenadas: https://stackoverflow.com/questions/11483345/how-do-android-screen-coordinates-work

        if(positionY > gameEngine.height + mHeight){ //Se sale por abajo
            removeObject(gameEngine);
        }else if(positionX > gameEngine.width-mWidth){ //Se sale por la derecha
            removeObject(gameEngine);
        }else if(positionX < mWidth){ //Se sale por la izquierda
            removeObject(gameEngine);
        }
    }
    public void removeObject(GameEngine gameEngine){
        gameEngine.removeGameObject(this);
        // And return it to the pool
        mController.releaseEnemy(this);
    }

    public void init(GameEngine gameEngine) {
        Double angle = gameEngine.mRandom.nextDouble() * Math.PI / 3d - Math.PI / 6d;
        mSpeedX = mSpeed * Math.sin(angle);
        mSpeedY = mSpeed * Math.cos(angle);

        positionX = gameEngine.mRandom.nextInt(gameEngine.width / 2) + gameEngine.width / 4;
        positionY = -mHeight;
    }
}
