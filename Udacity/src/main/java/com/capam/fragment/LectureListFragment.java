/*
package com.capam.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.capam.database.LectureProvider;
import com.capam.database.UdacityDB;

*/
/**
 * Created by acer on 7/12/13.
 *//*

public class LectureListFragment  extends ListFragment implements
            LoaderManager.LoaderCallbacks<Cursor> {
        private OnTutSelectedListener tutSelectedListener;
        private static final int TUTORIAL_LIST_LOADER = 0x01;

        private SimpleCursorAdapter adapter;

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            String projection[] = { UdacityDB.KEY_TITLE };
            Cursor tutorialCursor = getActivity().getContentResolver().query(
                    Uri.withAppendedPath(LectureProvider.CONTENT_URI,
                            String.valueOf(id)), projection, null, null, null);
            if (tutorialCursor.moveToFirst()) {
                String tutorialUrl = tutorialCursor.getString(0);
                tutSelectedListener.onTutSelected(tutorialUrl);
            }
            tutorialCursor.close();
            l.setItemChecked(position, true);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            String[] uiBindFrom = { UdacityDB.KEY_TITLE };
            int[] uiBindTo = { R.id.title };

            getLoaderManager().initLoader(TUTORIAL_LIST_LOADER, null, this);

            adapter = new SimpleCursorAdapter(
                    getActivity().getApplicationContext(), R.layout.list_item,
                    null, uiBindFrom, uiBindTo,
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

            setListAdapter(adapter);
            setHasOptionsMenu(true);
        }

        public interface OnTutSelectedListener {
            public void onTutSelected(String tutUrl);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            try {
                tutSelectedListener = (OnTutSelectedListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnTutSelectedListener");
            }
        }

        // options menu

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.options_menu, menu);

            // refresh menu item
            Intent refreshIntent = new Intent(
                    getActivity().getApplicationContext(),
                    LectureService.class);
            refreshIntent
                    .setData(Uri
                            .parse("http://feeds.feedburner.com/mobile-tuts-summary?format=xml"));

            MenuItem refresh = menu.findItem(R.id.refresh_option_item);
            refresh.setIntent(refreshIntent);

         */
/*   // pref menu item
            Intent prefsIntent = new Intent(getActivity().getApplicationContext(),
                    TutListPreferencesActivity.class);

            MenuItem preferences = menu.findItem(R.id.settings_option_item);
            preferences.setIntent(prefsIntent);*//*

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.refresh_option_item:
                    getActivity().startService(item.getIntent());
                    break;
                case R.id.settings_option_item:
                    getActivity().startActivity(item.getIntent());
                    break;
            }
            return true;
        }

        // LoaderManager.LoaderCallbacks<Cursor> methods

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            String[] projection = { UdacityDB.KEY_ROWID, UdacityDB.KEY_TITLE };

            CursorLoader cursorLoader = new CursorLoader(getActivity(),
                    LectureProvider.CONTENT_URI, projection, null, null, null);
            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            adapter.swapCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            adapter.swapCursor(null);
        }

}
*/
