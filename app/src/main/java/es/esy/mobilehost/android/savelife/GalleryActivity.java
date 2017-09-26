package es.esy.mobilehost.android.savelife;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class GalleryActivity extends MenuActivity {

    GridView simpleGrid;

    int photoList[] ={R.drawable.p0, R.drawable.p1, R.drawable.p2,
    R.drawable.p3, R.drawable.p4, R.drawable.p5,
    R.drawable.p6, R.drawable.p7, R.drawable.p8,
    R.drawable.p9, R.drawable.p10, R.drawable.p11,
    R.drawable.p12, R.drawable.p13, R.drawable.p14,
    R.drawable.p15, R.drawable.p16, R.drawable.p17,
    R.drawable.p18, R.drawable.p19};

    int info[] = {R.string.logo0,R.string.logo1, R.string.logo2, R.string.logo3, R.string.logo4,
            R.string.logo5, R.string.logo6, R.string.logo7, R.string.logo8, R.string.logo9,
            R.string.logo10, R.string.logo11, R.string.logo12, R.string.logo13,
            R.string.logo14,R.string.logo15, R.string.logo16, R.string.logo17,
            R.string.logo18, R.string.logo19};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        simpleGrid = (GridView) findViewById(R.id.simpleGridView); // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView
        GalleryAdapter customAdapter = new GalleryAdapter(getApplicationContext(), photoList);
        simpleGrid.setAdapter(customAdapter);
        // implement setOnItemClickListener event on GridView
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity
                Intent intent = new Intent(GalleryActivity.this, GalleryDetail.class);
                intent.putExtra("image", photoList[position]); // put image data in Intent
                intent.putExtra("text", info[position]); // put image info data in Intent
                startActivity(intent); // start Intent
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}


