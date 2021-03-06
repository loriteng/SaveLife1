package es.esy.mobilehost.android.savelife;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class GalleryDetail extends AppCompatActivity {
    ImageView selectedImage;
    TextView selectedText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallerydetail);

        selectedImage = (ImageView) findViewById(R.id.selectedImage); // init a ImageView
        Intent intent = getIntent(); // get Intent which we set from Previous Activity
        selectedImage.setImageResource(intent.getIntExtra("image", 0)); // get image from Intent and set it in ImageView
        selectedText =(TextView) findViewById(R.id.selectedText);
        selectedText.setText(intent.getIntExtra("text",0));

    }
}
