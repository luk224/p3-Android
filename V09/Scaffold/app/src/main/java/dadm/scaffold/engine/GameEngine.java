package dadm.scaffold.engine;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dadm.scaffold.input.InputController;
import dadm.scaffold.space.GameController;

public class GameEngine {


    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private List<GameObject> objectsToAdd = new ArrayList<GameObject>();
    private List<GameObject> objectsToRemove = new ArrayList<GameObject>();
    private List<ScreenGameObject> mCollisionableObjects = new ArrayList<ScreenGameObject>();

    private UpdateThread theUpdateThread;
    private DrawThread theDrawThread;
    public InputController theInputController;
    private final GameView theGameView;

    public int width;
    public int height;
    public double pixelFactor;
    public Random mRandom;

    public Activity mainActivity;


    public GameEngine(Activity activity, GameView gameView) {
        mainActivity = activity;
        mRandom = new Random();

        theGameView = gameView;
        theGameView.setGameObjects(this.gameObjects);
        this.width = theGameView.getWidth()
                - theGameView.getPaddingRight() - theGameView.getPaddingLeft();
        this.height = theGameView.getHeight()
                - theGameView.getPaddingTop() - theGameView.getPaddingTop();

        this.pixelFactor = this.height / 400d;


    }

    public void setTheInputController(InputController inputController) {
        theInputController = inputController;
    }

    public void startGame() {
        // Stop a game if it is running
        stopGame();

        // Setup the game objects
        int numGameObjects = gameObjects.size();
        for (int i = 0; i < numGameObjects; i++) {
            gameObjects.get(i).startGame();
        }

        // Start the update thread
        theUpdateThread = new UpdateThread(this);
        theUpdateThread.start();

        // Start the drawing thread
        theDrawThread = new DrawThread(this);
        theDrawThread.start();
    }

    public void stopGame() {
        if (theUpdateThread != null) {
            theUpdateThread.stopGame();
        }
        if (theDrawThread != null) {
            theDrawThread.stopGame();
        }
    }

    public void finishGame(){
        if (theUpdateThread != null) {
            theUpdateThread.finishGame();
        }
        if (theDrawThread != null) {
            theDrawThread.finishGame();
        }
    }

    public void pauseGame() {
        if (theUpdateThread != null) {
            theUpdateThread.pauseGame();
        }
        if (theDrawThread != null) {
            theDrawThread.pauseGame();
        }
    }

    public void resumeGame() {
        if (theUpdateThread != null) {
            theUpdateThread.resumeGame();
        }
        if (theDrawThread != null) {
            theDrawThread.resumeGame();
        }
    }

    public void addGameObject(GameObject gameObject) {
        if (gameObject instanceof ScreenGameObject) {
            ScreenGameObject sgo = (ScreenGameObject) gameObject;
            if (sgo.mBodyType != ScreenGameObject.BodyType.None) {
                mCollisionableObjects.add(sgo);
            }
        }
        if (isRunning()) {
            objectsToAdd.add(gameObject);
        } else {
            gameObjects.add(gameObject);
        }


        mainActivity.runOnUiThread(gameObject.onAddedRunnable);
    }

    public void removeGameObject(GameObject gameObject) {
        objectsToRemove.add(gameObject);
        mainActivity.runOnUiThread(gameObject.onRemovedRunnable);
    }

    public void onUpdate(long elapsedMillis) {
        int numGameObjects = gameObjects.size();
        for (int i = 0; i < numGameObjects; i++) {

            gameObjects.get(i).onUpdate(elapsedMillis, this);
        }

        checkCollisions(this);
        synchronized (gameObjects) {
            while (!objectsToRemove.isEmpty()) {
                GameObject go = objectsToRemove.get(0);
                if(go instanceof ScreenGameObject){
                    ScreenGameObject sgo = (ScreenGameObject) go;
                    if (sgo.mBodyType != ScreenGameObject.BodyType.None) {
                       mCollisionableObjects.remove(sgo);
                    }
                }
                gameObjects.remove(objectsToRemove.remove(0));
            }
            while (!objectsToAdd.isEmpty()) {
                gameObjects.add(objectsToAdd.remove(0));
            }
        }
    }

    public void onDraw() {
        theGameView.draw();
    }

    public boolean isRunning() {
        return theUpdateThread != null && theUpdateThread.isGameRunning();
    }

    public boolean isPaused() {
        return theUpdateThread != null && theUpdateThread.isGamePaused();
    }

    public Context getContext() {
        return theGameView.getContext();
    }

    private void checkCollisions(GameEngine gameEngine) {
        int numObjects = mCollisionableObjects.size();
        //Log.i("Colisionables: ",""+numObjects);
        for (int i = 0; i < numObjects; i++) {
            ScreenGameObject objectA = mCollisionableObjects.get(i);
            for (int j = i + 1; j < numObjects; j++) {
                ScreenGameObject objectB = mCollisionableObjects.get(j);
                if (objectA.checkCollision(objectB)) {
                    objectA.onCollision(gameEngine, objectB);
                    objectB.onCollision(gameEngine, objectA);
                }
            }
        }
    }
}
