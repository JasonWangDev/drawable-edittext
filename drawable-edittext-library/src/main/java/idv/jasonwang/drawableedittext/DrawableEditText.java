package idv.jasonwang.drawableedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

/**
 *支援設置顯示圖片大小的 EditText
 *
 * 使用 EditText 需要搭配圖片並控制圖片大小時，原生的 EditText 沒有設置 Drawable 長寬大小相關的屬性，雖然可
 * 以透過 layer-list 來設置圖片 (drawable 屬性) 以及圖片大小 (width、height 屬性) 解決此問題，但其中的 width、
 * height 兩個屬性僅支援 API 22 (含)以上，在舊版的設備執行時會產生相容性問題，無法依照設定的屬性改變大小。
 *
 * 在使用此類別在時，XML 中只需要使用自訂的 DrawableLef、DrawableRight 屬性即可顯示指定的資源圖片，使用
 * DrawableWidth、DrawableHeight 即可設置顯示圖片的大小，其餘像 padding、margin 等屬性皆支援原生屬性，另外
 * 也支援動態更改屬性功能。
 *
 * 支援 API： 17~25
 *
 * Created by jason on 2017/5/11.
 */
public class DrawableEditText extends android.support.v7.widget.AppCompatEditText {

    private Drawable[] drawables = {null,   // Left
                                    null,   // Top
                                    null,   // Right
                                    null};  // Bottom

    private int[][] drawableSize = {{0, 0}, // Left (width, height)
                                    {0, 0}, // Top (width, height)
                                    {0, 0}, // Right (width, height)
                                    {0, 0}};// Bottom (width, height)


    public DrawableEditText(Context context) {
        super(context);

        init(context, null);
        setDrawable();
    }

    public DrawableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
        setDrawable();
    }

    public DrawableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
        setDrawable();
    }


    @Override
    protected void onDetachedFromWindow() {
        release();

        super.onDetachedFromWindow();
    }


    public void setDrawableLeft(int res) {
        setDrawableLeft(ContextCompat.getDrawable(getContext(), res));
    }

    public void setDrawableTop(int res) {
        setDrawableTop(ContextCompat.getDrawable(getContext(), res));
    }

    public void setDrawableRight(int res) {
        setDrawableRight(ContextCompat.getDrawable(getContext(), res));
    }

    public void setDrawableBottom(int res) {
        setDrawableBottom(ContextCompat.getDrawable(getContext(), res));
    }

    public void setDrawableLeft(Drawable res) {
        drawables[0] = res;

        setDrawable();
    }

    public void setDrawableTop(Drawable res) {
        drawables[1] = res;

        setDrawable();
    }

    public void setDrawableRight(Drawable res) {
        drawables[2] = res;

        setDrawable();
    }

    public void setDrawableBottom(Drawable res) {
        drawables[3] = res;

        setDrawable();
    }

    public void setDrawableLeftSize(int width, int height) {
        drawableSize[0][0] = width;
        drawableSize[0][1] = height;

        setDrawable();
    }

    public void setDrawableTopSize(int width, int height) {
        drawableSize[1][0] = width;
        drawableSize[1][1] = height;

        setDrawable();
    }

    public void setDrawableRightSize(int width, int height) {
        drawableSize[2][0] = width;
        drawableSize[2][1] = height;

        setDrawable();
    }

    public void setDrawableBottomSize(int width, int height) {
        drawableSize[3][0] = width;
        drawableSize[3][1] = height;

        setDrawable();
    }


    /**
     *
     *  經由 XML 建構此元件時， 會讀取相關屬性參數
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        if (attrs == null)
            return;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableEditText);

        drawables[0] = typedArray.getDrawable(R.styleable.DrawableEditText_drawableLeft);
        drawables[1] = typedArray.getDrawable(R.styleable.DrawableEditText_drawableTop);
        drawables[2] = typedArray.getDrawable(R.styleable.DrawableEditText_drawableRight);
        drawables[3] = typedArray.getDrawable(R.styleable.DrawableEditText_drawableBottom);

        // 沒有設置圖片時大小預設 0，有設置圖片但沒有設置大小時預設對應解析度的圖片的原始大小
        drawableSize[0][0] = drawables[0] != null ? typedArray.getDimensionPixelSize(R.styleable.DrawableEditText_drawableLeftWidth, drawables[0].getIntrinsicWidth()) : 0;
        drawableSize[0][1] = drawables[0] != null ? typedArray.getDimensionPixelSize(R.styleable.DrawableEditText_drawableLeftHeight, drawables[0].getIntrinsicHeight()) : 0;

        drawableSize[1][0] = drawables[1] != null ? typedArray.getDimensionPixelSize(R.styleable.DrawableEditText_drawableTopWidth, drawables[1].getIntrinsicWidth()) : 0;
        drawableSize[1][1] = drawables[1] != null ? typedArray.getDimensionPixelSize(R.styleable.DrawableEditText_drawableTopHeight, drawables[1].getIntrinsicHeight()) : 0;

        drawableSize[2][0] = drawables[2] != null ? typedArray.getDimensionPixelSize(R.styleable.DrawableEditText_drawableRightWidth, drawables[2].getIntrinsicWidth()) : 0;
        drawableSize[2][1] = drawables[2] != null ? typedArray.getDimensionPixelSize(R.styleable.DrawableEditText_drawableRightHeight, drawables[2].getIntrinsicHeight()) : 0;

        drawableSize[3][0] = drawables[3] != null ? typedArray.getDimensionPixelSize(R.styleable.DrawableEditText_drawableBottomWidth, drawables[3].getIntrinsicWidth()) : 0;
        drawableSize[3][1] = drawables[3] != null ? typedArray.getDimensionPixelSize(R.styleable.DrawableEditText_drawableBottomHeight, drawables[3].getIntrinsicHeight()) : 0;

        // 參數物件使用完畢後必定釋放資源
        typedArray.recycle();
    }

    /**
     *
     * 根據屬性設置 EdiText 個四方位的圖片
     */
    private void setDrawable() {
        // 設置四個方位的圖片大小
        for(int i = 0, count = drawables.length ; i < count ; i++)
        {
            if (drawables[i] != null)
                drawables[i].setBounds(0, 0, drawableSize[i][0], drawableSize[i][1]);
        }

        // 設置四個方位的圖片 (Null 表示不顯示)
        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    private void release() {
        try
        {
            for (Drawable drawable : drawables)
            {
                if (drawable != null)
                    drawable.setCallback(null);
            }
        }
        catch (Exception e) { }
    }

}
