package com.example.berkeyanilmaz.biberapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import static com.google.zxing.BarcodeFormat.QR_CODE;

public class MainActivity extends AppCompatActivity {

    int QR_IMAGE_HEIGHT = 1000;
    int QR_IMAGE_WIDTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button button = (Button) findViewById(R.id.qrbutton);

        button.setOnClickListener(new View.OnClickListener() {
            // Convert Pepper logo to Bitmap

            @Override
            public void onClick(View view) {
                //TODO: QR Generator
                String textToEncode = "                       155295662";

                //Find screen size
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);


                try {
                    QRCodeWriter writer = new QRCodeWriter();
                    Log.d("Info", "Creating BitMatrix");

                    // BitMatrix to Bitmap
                    BitMatrix encoded = writer.encode(textToEncode, QR_CODE, QR_IMAGE_WIDTH, QR_IMAGE_HEIGHT);
                    //Get bitmaps to combine
                    Bitmap qr = toBitmap(encoded);
                    Bitmap pepper = BitmapFactory.decodeResource(getResources(), R.drawable.pepper_mini);
                    Bitmap smaller = Bitmap.createScaledBitmap(pepper, 300, 180, false);

                    Bitmap combined = combineBitmaps(qr, smaller);

                    ImageView qr_image = (ImageView) findViewById(R.id.imageView);
                    qr_image.setImageBitmap(combined);



                } catch (WriterException e) {
                    //TODO: Auto-generated exception
                    e.printStackTrace();
                }


            }
        });
    }

    public Bitmap combineBitmaps(Bitmap qr, Bitmap overlay) {
        Bitmap combined = Bitmap.createBitmap(qr.getWidth(), qr.getHeight(), qr.getConfig());
        Canvas canvas = new Canvas(combined);

        canvas.drawBitmap(qr, new Matrix(), null);

        int centreX = 350;
        int centreY = 410;

        canvas.drawBitmap(overlay, centreX, centreY, null);

        return combined;
    }

    public Bitmap toBitmap(BitMatrix matrix) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();

        Bitmap qrBmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                qrBmp.setPixel(x, y, matrix.get(x,y) ? Color.BLACK : Color.WHITE);
            }
        }
        return  qrBmp;
    }


}
