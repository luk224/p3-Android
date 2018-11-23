package dadm.scaffold.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public abstract class Sprite extends ScreenGameObject {

    protected double rotation;

    protected double pixelFactor;

    private final Bitmap bitmap;

    private final Matrix matrix = new Matrix();

    protected Sprite (GameEngine gameEngine, int drawableRes, BodyType bt) {

        Resources r = gameEngine.getContext().getResources();
        Drawable spriteDrawable = r.getDrawable(drawableRes);

        this.pixelFactor = gameEngine.pixelFactor;

        this.mHeight = (int) (spriteDrawable.getIntrinsicHeight() * this.pixelFactor);
        this.mWidth = (int) (spriteDrawable.getIntrinsicWidth() * this.pixelFactor);
        this.mBodyType=bt;
        this.mRadius = Math.max(mHeight, mWidth)/2;


        this.bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
    }

    @Override
    public void onDraw(Canvas canvas) {


        //////////////////////////////
        //Debug que dibuja el circulo. //TODO quitar el debug de pintado.
        Paint mPaint= new Paint();
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(
                (int) (positionX + mWidth / 2),
                (int) (positionY + mHeight / 2),
                (int) mRadius,
                mPaint);
        //////////////////////////////


        if (positionX > canvas.getWidth()
                || positionY > canvas.getHeight()
                || positionX < - mWidth
                || positionY < - mHeight) {
            return;
        }
        matrix.reset();
        matrix.postScale((float) pixelFactor, (float) pixelFactor);
        matrix.postTranslate((float) positionX, (float) positionY);
        matrix.postRotate((float) rotation, (float) (positionX + mWidth/2), (float) (positionY + mHeight/2));
        canvas.drawBitmap(bitmap, matrix, null);

    }
}
