package com.polamr.rssexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by MacMiniCD on 27/11/15.
 */
public class MovieDetails extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        tv=(TextView)findViewById(R.id.textView);

        Bundle bundle=getIntent().getExtras();
        String id=bundle.getString("id");
        tv.setText(id);

    }
}
