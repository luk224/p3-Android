package dadm.scaffold.engine;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.List;

public abstract class ScreenGameObject extends GameObject {
    protected double positionX;
    protected double positionY;
    protected int mHeight;
    protected int mWidth;
    protected int mRadius;
    protected BodyType mBodyType;
    public enum BodyType {
        None,
        Circular,
        Rectangular
    }




    public boolean checkCollision(ScreenGameObject otherObject) {
         return    checkCircularCollision(otherObject);

    }

    public void onCollision(GameEngine gameEngine, ScreenGameObject sgo) {

    }
    private boolean checkCircularCollision(ScreenGameObject other) {
        double distanceX = (positionX + mWidth /2) - (other.positionX +
                other.mWidth /2);
        double distanceY = (positionY + mHeight /2) - (other.positionY +
                other.mHeight /2);
        double squareDistance = distanceX*distanceX +
                distanceY*distanceY;
        double collisionDistance = (mRadius + other.mRadius);
        if(squareDistance <= collisionDistance*collisionDistance){
            return  true;
        }else
            return  false;
    }



}
