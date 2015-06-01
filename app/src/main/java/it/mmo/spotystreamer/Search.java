package it.mmo.spotystreamer;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import it.mmo.spotystreamer.adapters.ArtistAdapter;
import it.mmo.spotystreamer.model.Artist;
import it.mmo.spotystreamer.utils.UrlGetter;


public class Search extends ActionBarActivity {
    Context that;
    ArtistAdapter artistAdapter;
    List<Artist> artistList = new ArrayList<Artist>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        that = this;
        EditText editText = (EditText)findViewById(R.id.searchbar);
        ListView listView = (ListView)findViewById(R.id.list);
        artistAdapter = new ArtistAdapter(this, R.layout.artist_item, artistList);
        listView.setAdapter(artistAdapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             //get the new data from spotify
                String query = charSequence.toString();
                String full_query = "http://ws.spotify.com/search/1/artist.json?q="+query;
                new ArtistGetter().execute(full_query);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class ArtistGetter extends AsyncTask<String, Void, List<Artist>> {

        @Override
        protected List<Artist> doInBackground(String... strings) {
            try {
                String in = new UrlGetter(strings[0]).get().getText();
                JsonObject jo = new JsonParser().parse(in).getAsJsonObject();

                if (jo.getAsJsonObject("info").get("num_results").getAsInt() == 0){
                    Toast.makeText(that, "Sorry, no artist found...", Toast.LENGTH_LONG);
                }
                else {

                    Gson gson = new Gson();
                    for (JsonElement jitem : jo.getAsJsonArray("artists")) {
                        if (jitem.isJsonObject()) {
                            artistList.add(gson.fromJson(jitem, Artist.class));
                        }
                    }
                    artistAdapter.notifyDataSetChanged();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return artistList;
        }
    }
}
