package dadm.scaffold.space;

import android.util.Log;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

class Item extends Sprite {

    private ItemType iT;
    private double speedFactor;
    private final GameController mController;
    private int drawableRes;
    public static int[] itemImages = {R.drawable.item_health, R.drawable.item_immortal, R.drawable.item_powergun};
    private SpaceShipPlayer ssp;

    public enum ItemType {
        HealthUp,
        PowerGun,//Da mas score
        Immortal
    }


    public Item(GameController gameController, int type, GameEngine gameEngine) {
        super(gameEngine, itemImages[type], BodyType.Circular);
        iT = selectType(type);

        speedFactor = gameEngine.pixelFactor * 100d / 1000d;
        mController = gameController;
        //this.ssp = ssp;
    }

    private ItemType selectType(int type) {
        ItemType iT;
        switch (type) {
            case 0:
                iT = ItemType.HealthUp;
                break;
            case 1:
                iT = ItemType.Immortal;
                break;
            case 2:
                iT = ItemType.PowerGun;
                break;
            default:
                iT = ItemType.Immortal;
                Log.i("selecttype of Item", "Ha entrado en default.");
                break;


        }
        return iT;
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor * elapsedMillis;
        if (positionY > gameEngine.height + mHeight) {
            removeObject(gameEngine);
        }
    }

    public void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        // And return it to the pool
        mController.releaseItem(this);
    }

    public void init(GameEngine gameEngine) {
        positionX = gameEngine.mRandom.nextInt(gameEngine.width - mWidth);
        positionY = -mHeight;
    }

    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

        if (otherObject instanceof SpaceShipPlayer) {    // Remove both from the game (and return them to their pools)

            removeObject(gameEngine);
            SpaceShipPlayer e = (SpaceShipPlayer) otherObject;
            switch (iT) {
                case HealthUp:
                        e.healthUp();
                    break;
                case Immortal:
                        e.immortal();
                    break;

                case PowerGun:
                        e.powerGun();
                    break;
                default:
                    break;
            }


        }
    }

}
