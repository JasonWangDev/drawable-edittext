package idv.jasonwang.widgetutils.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import idv.jasonwang.widgetutils.R;

/**
 *支援設置顯示圖片大小的 TextView
 *
 * 使用 TextView 需要搭配圖片並控制圖片大小時，原生的 TextView 沒有設置 Drawable 長寬大小相關的屬性，雖然可
 * 以透過 layer-list 來設置圖片 (drawable 屬性) 以及圖片大小 (width、height 屬性) 解決此問題，但其中的 width、
 * height 兩個屬性僅支援 API 22 (含)以上，在舊版的設備執行時會產生相容性問題，無法依照設定的屬性改變大小。
 *
 * 設置圖片時，在 XML 中可以選擇各別設置顯示四個方位的圖片與大小，如果沒有指定對應方位的圖片大小時，
 * 預設大小為圖片實際的尺寸，在程式碼中，必須先設置圖片大小，才能設置顯示的圖片資源，且只能選擇設置其
 * 中一個方位，或是一次設置四個方位，如果直接設置顯示的圖片資源，其大小預設為最後一次指定的圖片大小。
 *
 * 支援 API： 17~25
 *
 * Created by jason on 2017/5/11.
 */
public class DrawableTextView extends android.support.v7.widget.AppCompatTextView {

    private int[][] drawableSize = {{-1, -1}, // Left (width, height)
                                    {-1, -1}, // Top (width, height)
                                    {-1, -1}, // Right (width, height)
                                    {-1, -1}};// Bottom (width, height)


    public DrawableTextView(Context context) {
        super(context);

        init(context, null);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }


    public void setDrawableLeftSize(int width, int height) {
        setDrawableSize(new int[]{width, height},
                        null,
                        null,
                        null);
    }

    public void setDrawableTopSize(int width, int height) {
        setDrawableSize(null,
                        new int[]{width, height},
                        null,
                        null);
    }

    public void setDrawableRightSize(int width, int height) {
        setDrawableSize(null,
                        null,
                        new int[]{width, height},
                        null);
    }

    public void setDrawableBottomSize(int width, int height) {
        setDrawableSize(null,
                        null,
                        null,
                        new int[]{width, height});
    }

    public void setDrawableSize(int[] sizeLeft, int[] sizeTop, int[] sizeRight, int[] sizeBottom) {
        if (sizeLeft != null && sizeLeft.length >= 2)
        {
            drawableSize[0][0] = sizeLeft[0];
            drawableSize[0][1] = sizeLeft[1];
        }
        else
        {
            drawableSize[0][0] = -1;
            drawableSize[0][1] = -1;
        }

        if (sizeTop != null && sizeTop.length >= 2)
        {
            drawableSize[1][0] = sizeTop[0];
            drawableSize[1][1] = sizeTop[1];
        }
        else
        {
            drawableSize[1][0] = -1;
            drawableSize[1][1] = -1;
        }

        if (sizeRight != null && sizeRight.length >= 2)
        {
            drawableSize[2][0] = sizeRight[0];
            drawableSize[2][1] = sizeRight[1];
        }
        else
        {
            drawableSize[2][0] = -1;
            drawableSize[2][1] = -1;
        }

        if (sizeBottom != null && sizeBottom.length >= 2)
        {
            drawableSize[3][0] = sizeBottom[0];
            drawableSize[3][1] = sizeBottom[1];
        }
        else
        {
            drawableSize[3][0] = -1;
            drawableSize[3][1] = -1;
        }
    }

    public void setDrawableLeft(int res) {
        setDrawable(ContextCompat.getDrawable(getContext(), res),
                    null,
                    null,
                    null);
    }

    public void setDrawableTop(int res) {
        setDrawable(null,
                    ContextCompat.getDrawable(getContext(), res),
                    null,
                    null);
    }

    public void setDrawableRight(int res) {
        setDrawable(null,
                    null,
                    ContextCompat.getDrawable(getContext(), res),
                    null);
    }

    public void setDrawableBottom(int res) {
        setDrawable(null,
                    null,
                    null,
                    ContextCompat.getDrawable(getContext(), res));
    }

    public void setDrawable(int resLeft, int resTop, int resRight, int resBottom) {
        setDrawable(ContextCompat.getDrawable(getContext(), resLeft),
                    ContextCompat.getDrawable(getContext(), resTop),
                    ContextCompat.getDrawable(getContext(), resRight),
                    ContextCompat.getDrawable(getContext(), resBottom));
    }

    public void setDrawableLeft(Drawable res) {
        setDrawable(res, null, null, null);
    }

    public void setDrawableTop(Drawable res) {
        setDrawable(null, res, null, null);
    }

    public void setDrawableRight(Drawable res) {
        setDrawable(null, null, res, null);
    }

    public void setDrawableBottom(Drawable res) {
        setDrawable(null, null, null, res);
    }

    /**
     *
     * 根據屬性設置並顯示 EdiText 個四方位的圖片
     */
    public void setDrawable(Drawable drawableLeft, Drawable drawableTop, Drawable drawableRight, Drawable drawableBottom) {
        if (drawableLeft != null && drawableSize[0][0] > 0 && drawableSize[0][1] > 0)
            drawableLeft.setBounds(0, 0,drawableSize[0][0], drawableSize[0][1]);

        if (drawableTop != null && drawableSize[1][0] > 0 && drawableSize[1][1] > 0)
            drawableTop.setBounds(0, 0,drawableSize[1][0], drawableSize[1][1]);

        if (drawableRight != null && drawableSize[2][0] > 0 && drawableSize[2][1] > 0)
            drawableRight.setBounds(0, 0,drawableSize[2][0], drawableSize[2][1]);

        if (drawableBottom != null && drawableSize[3][0] > 0 && drawableSize[3][1] > 0)
            drawableBottom.setBounds(0, 0,drawableSize[3][0], drawableSize[3][1]);

        setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
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

        Drawable[] drawables = {typedArray.getDrawable(R.styleable.DrawableEditText_drawableLeft),
                                typedArray.getDrawable(R.styleable.DrawableEditText_drawableTop),
                                typedArray.getDrawable(R.styleable.DrawableEditText_drawableRight),
                                typedArray.getDrawable(R.styleable.DrawableEditText_drawableBottom)};

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

        setDrawable(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

}
