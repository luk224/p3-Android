package dadm.scaffold.space;

import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;



public class SpaceShipPlayer extends Sprite {

    public int health = 100;
    public int score = 0;


    private static final int INITIAL_BULLET_POOL_AMOUNT = 6,INITIAL_SPECIAL_BULLET_POOL_AMOUNT = 1;//numero de disparos (3 sprites cada uno)
    private static final long TIME_BETWEEN_BULLETS = 500,TIME_BETWEEN_SPECIAL_BULLETS=2000, IMMORTAL_TIME= 5000;

    List<Bullet> bullets = new ArrayList<Bullet>();
    List<SpecialBullet> specialBullets = new ArrayList<SpecialBullet>();


    private long timeSinceLastFire, timeSinceLastSpecialFire, timeOfLastImmortalState;
    private int maxX;
    private int maxY;
    private double speedFactor;
    private long startTime = 0L;
    private int scorePerKill = 1;


    public SpaceShipPlayer(GameEngine gameEngine, int ship_selected){

        super(gameEngine, ship_selected, BodyType.Circular);
        speedFactor = pixelFactor * 200d / 1000d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - mWidth;
        maxY = gameEngine.height - mHeight;

        initBulletPool(gameEngine);
        initSpecialBulletPool(gameEngine);
        timeOfLastImmortalState = 0;//1970
        startTime = SystemClock.uptimeMillis();

    }

    private void initSpecialBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_SPECIAL_BULLET_POOL_AMOUNT; i++) {
            specialBullets.add(new SpecialBullet(gameEngine,-30));
            specialBullets.add(new SpecialBullet(gameEngine,0));
            specialBullets.add(new SpecialBullet(gameEngine,30));

        }
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
    private SpecialBullet getSpecialBullet() {
        if (specialBullets.isEmpty()) {
            return null;
        }
        return specialBullets.remove(0);
    }

    void releaseBullet(Bullet bullet) {
        bullets.add(bullet);
    }
    void releaseSpecialBullet(SpecialBullet bullet) {
        specialBullets.add(bullet);
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
        if ( gameEngine.theInputController.isFiring  && (timeSinceLastSpecialFire > TIME_BETWEEN_SPECIAL_BULLETS)) {
            if(specialBullets.size()<3)//Si no hay 3 balas especiales, corta, no dispares.
                return;
            for(int i = 0; i<3;i++) {
                SpecialBullet b = getSpecialBullet();
                if (b == null) {
                    return;
                }
                b.init(this, positionX + mWidth / 2, positionY);
                gameEngine.addGameObject(b);
            }
            timeSinceLastSpecialFire = 0;
        }
        else {
            timeSinceLastSpecialFire += elapsedMillis;
        }


    }

    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        //Si no sigo siendo inmortal:
        if(timeOfLastImmortalState+IMMORTAL_TIME <System.currentTimeMillis() ){
            if (otherObject instanceof Enemy) {    // Remove both from the game (and return them to their pools)
                health -=10;
                if(health <=0){
                    gameEngine.removeGameObject(this);
                    gameEngine.finishGame();
                    ((ScaffoldActivity)gameEngine.mainActivity).endGameScreen(false,score,timer());
                }

                Enemy e = (Enemy) otherObject;
                e.removeObject(gameEngine);
            }
        }

    }
    public void scoreUp(){
        score +=scorePerKill;
    }

    public void win(GameEngine gameEngine){

        gameEngine.finishGame();
        ((ScaffoldActivity)gameEngine.mainActivity).endGameScreen(true,score,timer());

    }
    public int timer(){
        long timeInMil = SystemClock.uptimeMillis()-startTime;
        return (int) (timeInMil/1000);
    }

    public void healthUp(){
        health =100;
    }
    public void immortal(){
        timeOfLastImmortalState = System.currentTimeMillis();
    }


    public void powerGun(){
        scorePerKill++;
    }

}
