package dadm.scaffold.space;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;
import dadm.scaffold.engine.ScreenGameObject;

public class GameController extends GameObject {
    long mCurrentMillis = 0;
    int mEnemiesSpawned = 0;
    int TIME_BETWEEN_ENEMIES = 500;
    int INITIAL_ENEMY_POOL_AMOUNT = 10;
    List<Enemy> mEnemyPool = new ArrayList<Enemy>();

    @Override
    public void startGame() {

    }


    public GameController(GameEngine gameEngine){

        initEnemyPool(gameEngine);
    }


    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        mCurrentMillis+= elapsedMillis;
        long waveTimestamp = mEnemiesSpawned*TIME_BETWEEN_ENEMIES;
        if(mCurrentMillis>waveTimestamp){//Spawn new enemy
            Enemy e =getEnemy();
            if(e==null){
                return;
            }
            e.init(gameEngine);
            gameEngine.addGameObject(e);
            mEnemiesSpawned++; //Hacer solo si remove no ha devuelto null
        }
    }

    @Override
    public void onDraw(Canvas canvas) {

    }
    private void initEnemyPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_ENEMY_POOL_AMOUNT; i++) {
            mEnemyPool.add(new Enemy(this,gameEngine));
        }
    }

    private Enemy getEnemy() {
        if (mEnemyPool.isEmpty()) {
            return null;
        }
        return mEnemyPool.remove(0);
    }

    void releaseEnemy(Enemy enemy) {
        mEnemyPool.add(enemy);
    }
}
