package it.mmo.spotystreamer.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.List;

import it.mmo.spotystreamer.model.Artist;

/**
 * Created by asus on 31/05/2015.
 */
public class ArtistAdapter extends ArrayAdapter<Artist> implements ListAdapter {

    public ArtistAdapter(Context context, int resource, List<Artist> objects) {
        super(context, resource, objects);
    }


}
