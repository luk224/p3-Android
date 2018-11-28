package dadm.scaffold.engine;

import android.content.Context;

import java.util.List;

import dadm.scaffold.input.InputController;
import dadm.scaffold.input.JoystickInputController;

public interface GameView {

    void draw();

    void setGameObjects(List<GameObject> gameObjects);

    int getWidth();

    int getHeight();

    int getPaddingLeft();

    int getPaddingRight();

    int getPaddingTop();

    int getPaddingBottom();

    Context getContext();
}
