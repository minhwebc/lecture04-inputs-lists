package edu.uw.inputlistdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        //....

        //model
        ArrayList<String> data = new ArrayList<String>();
        //view
        //See XML!


        //controller
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.txtItem, data);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);








//        Button searchButton = (Button) findViewById(R.id.btnSearch);
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.v(TAG, "You clicked me!");
//            }
//        });
    }

    public void handleButtonSearch( View v){
        Log.v(TAG, "Click from the XML");

        EditText searchQuery = (EditText)findViewById(R.id.txtSearch);
        String text = searchQuery.getText().toString();

        Log.v(TAG, "You searched for " + text);
        MovieDownloadTask myTask = new MovieDownloadTask();
        myTask.execute(text);

    }

    public class MovieDownloadTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String ... params){

            String[] result = MovieDownloader.downloadMovieData(params[0]);
            for(String movie : result){
                Log.v(TAG, movie);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String[] movies) {
            super.onPostExecute(movies);

            if(movies != null){
                adapter.clear();
                for(String movie : movies){
                    adapter.add(movie);
                }
            }
        }
    }

}
