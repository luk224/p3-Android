package dadm.scaffold.space;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;

public class ParallaxBackground extends GameObject {

    private final Bitmap mBitmap;
    private final double mPixelFactor;
    private final double mSpeedY;
    private final int mImageWidth, mImageHeight,mScreenHeight,mScreenWidth,mTargetWidth;
    private double mPositionY;
    private final Matrix mMatrix = new Matrix();
    private Rect mSrcRect, mDstRect;

    public ParallaxBackground(GameEngine gameEngine, double speed,
                              int drawableResId) {
        Drawable spriteDrawable = gameEngine.getContext().getResources()
                .getDrawable(drawableResId);

        mBitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
        mPixelFactor = gameEngine.pixelFactor;

        mSpeedY = speed * mPixelFactor / 1000d;

        mImageHeight = (int) (spriteDrawable.getIntrinsicHeight() * mPixelFactor);
        mImageWidth = (int) (spriteDrawable.getIntrinsicWidth() * mPixelFactor);

        mScreenHeight = gameEngine.height;
        mScreenWidth = gameEngine.width;

        mTargetWidth = Math.min(mImageWidth, mScreenWidth);
        mSrcRect = new Rect();
        mDstRect = new Rect();
    }


    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        mPositionY += mSpeedY * elapsedMillis;

    }
    @Override
    public void onDraw(Canvas canvas) {
        efficientDraw(canvas);
        /*
        if (mPositionY > 0) {
            mMatrix.reset();
            mMatrix.postScale((float) (mPixelFactor),
                    (float) (mPixelFactor));
            mMatrix.postTranslate(0, (float) (mPositionY - mImageHeight));
            canvas.drawBitmap(mBitmap, mMatrix, null);
        }
        mMatrix.reset();
        mMatrix.postScale((float) (mPixelFactor),
                (float) (mPixelFactor));
        mMatrix.postTranslate(0, (float) mPositionY);
        canvas.drawBitmap(mBitmap, mMatrix, null);
        if (mPositionY > mScreenHeight) {
            mPositionY -= mImageHeight;
        }*/
    }
    private void efficientDraw(Canvas canvas) {
        if (mPositionY < 0) {

            mSrcRect.set(0,
                    (int) (-mPositionY/mPixelFactor),
                    (int) (mTargetWidth/mPixelFactor),
                    (int) ((mScreenHeight - mPositionY)/mPixelFactor));
            mDstRect.set(0,
                    0,
                    (int) mTargetWidth,
                    (int) mScreenHeight);
            canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, null);
        }
        else {
            mSrcRect.set(0,
                    0,
                    (int) (mTargetWidth/mPixelFactor),
                    (int) ((mScreenHeight - mPositionY) / mPixelFactor));
            mDstRect.set(0,
                    (int) mPositionY,
                    (int) mTargetWidth,
                    (int) mScreenHeight);
            canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, null);
            // We need to draw the previous block
            mSrcRect.set(0,
                    (int) ((mImageHeight - mPositionY) / mPixelFactor),
                    (int) (mTargetWidth/mPixelFactor),
                    (int) (mImageHeight/mPixelFactor));
            mDstRect.set(0,
                    0,
                    (int) mTargetWidth,
                    (int) mPositionY);
            canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, null);
        }
        if (mPositionY > mScreenHeight) {
            mPositionY -= mImageHeight;
        }
    }

}
