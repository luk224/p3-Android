package dadm.scaffold.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.input.InputController;
import dadm.scaffold.input.JoystickInputController;

public class StandardGameView extends View implements GameView {

    private List<GameObject> gameObjects;
    private JoystickInputController joystick;

    public StandardGameView(Context context) {
        super(context);
        this.gameObjects = new ArrayList<GameObject>();
    }

    public StandardGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.gameObjects = new ArrayList<GameObject>();
    }

    public StandardGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.gameObjects = new ArrayList<GameObject>();
    }

    @Override
    public void draw() {
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        synchronized (gameObjects) {
            int numObjects = gameObjects.size();
            for (int i = 0; i < numObjects; i++) {
                gameObjects.get(i).onDraw(canvas);
            }
        }
    }

    public void setGameObjects(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }
    public  void setJoystick(InputController joystick){ this.joystick = (JoystickInputController) joystick; }
}
