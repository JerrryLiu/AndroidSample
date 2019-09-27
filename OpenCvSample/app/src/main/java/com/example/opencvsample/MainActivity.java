package com.example.opencvsample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * <pre>
 *     @author : liudongbing
 *     @e-mail : lvdongbing@orbbec.com
 *     @time   : 2019/9/27
 *     @desc   :
 *     version: 1.0
 * </pre>
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private Button mGrayButton, mColorButtton;
    private ImageView mImageView;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        mGrayButton = findViewById(R.id.gray_btn);
        mColorButtton = findViewById(R.id.color_btn);

        mImageView = findViewById(R.id.imageView);

        mGrayButton.setOnClickListener(this);
        mColorButtton.setOnClickListener(this);

        showImage();


    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.gray_btn:
                showGray();
                break;
            case R.id.color_btn:
                showImage();
                break;
            default:
                break;
        }

    }

    private void showImage(){
        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.pic);
        mImageView.setImageBitmap(mBitmap);
    }

    private void showGray(){
        int bitmapWidth = mBitmap.getWidth();
        int bitmapHeight = mBitmap.getHeight();

        int[] piexls = new int[bitmapHeight*bitmapWidth];

        mBitmap.getPixels(piexls,0,bitmapWidth,0,0,bitmapWidth,bitmapHeight);

        int[] resultData = bitmap2Gray(piexls,bitmapWidth,bitmapHeight);

        Bitmap resultIamge = Bitmap.createBitmap(bitmapWidth,bitmapHeight,Bitmap.Config.ARGB_8888);
        resultIamge.setPixels(resultData,0,bitmapWidth,0,0,bitmapWidth,bitmapHeight);
        mImageView.setImageBitmap(resultIamge);
    }

    public native int[] bitmap2Gray(int[] piexls, int bitmapWidth, int bitmapHeight) ;
}
