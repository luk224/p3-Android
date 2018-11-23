package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;



public class SpaceShipPlayer extends Sprite {

    public int health = 100;
    public int score = 0;
    public int FINAL_SCORE = 10;

    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    private static final long TIME_BETWEEN_BULLETS = 250;
    List<Bullet> bullets = new ArrayList<Bullet>();
    private long timeSinceLastFire;

    private int maxX;
    private int maxY;
    private double speedFactor;


    public SpaceShipPlayer(GameEngine gameEngine){
        super(gameEngine, R.drawable.ship_2,BodyType.Circular);
        speedFactor = pixelFactor * 100d / 1000d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - mWidth;
        maxY = gameEngine.height - mHeight;

        initBulletPool(gameEngine);
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine));
        }
    }

    private Bullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    void releaseBullet(Bullet bullet) {
        bullets.add(bullet);
    }


    @Override
    public void startGame() {
        positionX = maxX / 2;
        positionY = maxY / 2;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        // Get the info from the inputController
        updatePosition(elapsedMillis, gameEngine.theInputController);
        checkFiring(elapsedMillis, gameEngine);
    }

    private void updatePosition(long elapsedMillis, InputController inputController) {
        positionX += speedFactor * inputController.horizontalFactor * elapsedMillis;
        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > maxX) {
            positionX = maxX;
        }
        positionY += speedFactor * inputController.verticalFactor * elapsedMillis;
        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        automaticFire(elapsedMillis,gameEngine);
        specialFire(elapsedMillis,gameEngine);

    }
    private void automaticFire(long elapsedMillis, GameEngine gameEngine){
        if ( timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            Bullet bullet = getBullet();
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX + mWidth/2, positionY);
            gameEngine.addGameObject(bullet);
            timeSinceLastFire = 0;
        }
        else {
            timeSinceLastFire += elapsedMillis;
        }
    }

    private void specialFire(long elapsedMillis, GameEngine gameEngine){
        //TODO Pulsar el boton de disparo: gameEngine.theInputController.isFiring
        //TODO poner nuevo timelastSpecialFire
    }

    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

        if (otherObject instanceof Enemy) {    // Remove both from the game (and return them to their pools)
            health -=10;
            if(health <=0){
                gameEngine.removeGameObject(this);
                gameEngine.finishGame();
                ((ScaffoldActivity)gameEngine.mainActivity).endGameScreen(false,score,0);//TODO contar tiempo.
            }

            Enemy e = (Enemy) otherObject;
            e.removeObject(gameEngine);
        }
    }

    public void checkWin(GameEngine gameEngine){
        if(score >=FINAL_SCORE){
            gameEngine.finishGame();
            ((ScaffoldActivity)gameEngine.mainActivity).endGameScreen(true,score,0);//TODO contar tiempo.
        }
    }

}
