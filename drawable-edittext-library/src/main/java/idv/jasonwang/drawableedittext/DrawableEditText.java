package idv.jasonwang.drawableedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 *支援圖片顯示的 EditText
 *
 * 使用 EditText 需要搭配圖片並控制圖片大小時，原生的 EditText 沒有設置 Drawable 長寬大小相關的屬性，雖然可
 * 以透過 layer-list 來設置圖片 (drawable 屬性) 以及圖片大小 (width、height 屬性) 解決此問題，但其中的 width、
 * height 兩個屬性僅支援 API 22 (含)以上，在舊版的設備執行時會產生相容性問題，無法依照設定的屬性改變大小。
 *
 * 在使用此類別在時，XML 中只需要使用自訂的 DrawableLef、DrawableRight 屬性即可顯示指定的資源圖片，使用
 * DrawableWidth、DrawableHeight 即可設置顯示圖片的大小，其餘像 padding、margin 等屬性皆支援原生屬性。
 *
 * 支援 API： 17~25
 *
 * Created by jason on 2017/5/11.
 */
public class DrawableEditText extends android.support.v7.widget.AppCompatEditText {

    public DrawableEditText(Context context) {
        super(context);

        init(context, null);
    }

    public DrawableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public DrawableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        drawable(context, attrs);
    }

    private void drawable(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableEditText);
        Drawable image;

        if (null != typedArray.getDrawable(R.styleable.DrawableEditText_drawableLeft))
        {
            image = typedArray.getDrawable(R.styleable.DrawableEditText_drawableLeft);
            int imageWidth = typedArray.getDimensionPixelSize(R.styleable.DrawableEditText_drawableWidth, image.getIntrinsicWidth());
            int imageHeight = typedArray.getDimensionPixelSize(R.styleable.DrawableEditText_drawableHeight, image.getIntrinsicHeight());
            image.setBounds(0, 0, imageWidth, imageHeight);
            setCompoundDrawables(image, null, null, null);
        }
        else if (null != typedArray.getDrawable(R.styleable.DrawableEditText_drawableRight))
        {
            image = typedArray.getDrawable(R.styleable.DrawableEditText_drawableRight);
            int imageWidth = typedArray.getDimensionPixelSize(R.styleable.DrawableEditText_drawableWidth, image.getIntrinsicWidth());
            int imageHeight = typedArray.getDimensionPixelSize(R.styleable.DrawableEditText_drawableHeight, image.getIntrinsicHeight());
            image.setBounds(0, 0, imageWidth, imageHeight);
            setCompoundDrawables(null, null, image, null);
        }

        typedArray.recycle();
    }

}
