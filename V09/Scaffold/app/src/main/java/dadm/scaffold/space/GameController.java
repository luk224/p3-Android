package dadm.scaffold.space;

import android.graphics.Canvas;
import android.widget.Space;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;
import dadm.scaffold.engine.ScreenGameObject;

public class GameController extends GameObject {
    SpaceShipPlayer spaceShipPlayer;
    long mCurrentMillis = 0;

    //////
    //Enemies:
    //////
    int mEnemiesSpawned = 0;
    int TIME_BETWEEN_ENEMIES = 500;
    int INITIAL_ENEMY_POOL_AMOUNT = 10;
    List<Enemy> mEnemyPool = new ArrayList<Enemy>();
    int MAX_ENEMIES = 50; //Cuando salgan estos enemigos se acaba la partida.


    //////
    //Items
    //////

    int mItemsSpawned = 0;
    int TIME_BETWEEN_ITEMS = 5000;
    int INITIAL_ITEM_POOL_AMOUNT = 20;
    List<Item> mItemPool = new ArrayList<Item>();


    @Override
    public void startGame() {

    }


    public GameController(GameEngine gameEngine, SpaceShipPlayer spaceShipPlayer){
        this.spaceShipPlayer =spaceShipPlayer;
        initEnemyPool(gameEngine);
        initItemPool(gameEngine);
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
            if(mEnemiesSpawned >=MAX_ENEMIES)
                spaceShipPlayer.win(gameEngine);


        }

        long waveTimestampItems = mItemsSpawned*TIME_BETWEEN_ITEMS;
        if(mCurrentMillis>waveTimestampItems){//Spawn new enemy
            Item i = getItem();
            if(i==null){
                return;
            }
            i.init(gameEngine);
            gameEngine.addGameObject(i);
            mItemsSpawned++; //Hacer solo si remove no ha devuelto null
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
    private void initItemPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_ITEM_POOL_AMOUNT; i++) {
            int type = gameEngine.mRandom.nextInt(3);
            mItemPool.add(new Item(this,type,gameEngine));
        }
    }



    private Enemy getEnemy() {
        if (mEnemyPool.isEmpty()) {
            return null;
        }
        return mEnemyPool.remove(0);
    }

    private Item getItem() {
        if (mItemPool.isEmpty()) {
            return null;
        }
        return mItemPool.remove(0);
    }

    void releaseEnemy(Enemy enemy) {
        mEnemyPool.add(enemy);
    }
    void releaseItem(Item item) {
        mItemPool.add(item);
    }
}
