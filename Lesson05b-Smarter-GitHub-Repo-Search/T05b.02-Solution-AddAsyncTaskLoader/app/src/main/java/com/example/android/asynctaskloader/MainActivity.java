/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.asynctaskloader;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.asynctaskloader.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

// MainActivity LoaderManager.LoaderCallbacks<String>  implement ediliyor.
public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String> {

    /* Görüntülenen URL 'i kaydetmek ve geri yüklemek için bir sabit  */
    private static final String SEARCH_QUERY_URL_EXTRA = "query";




    /*
     * Yükleyicinin benzersiz bir şekilde tanımlandığı ID numarası.
     */
    private static final int GITHUB_SEARCH_LOADER = 22;

    private EditText mSearchBoxEditText;

    private TextView mUrlDisplayTextView;
    private TextView mSearchResultsTextView;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Gösterilecek View Elemanları //
        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        // Gösterilecek View Elemanları SON //

        if (savedInstanceState != null) {
            String queryUrl = savedInstanceState.getString(SEARCH_QUERY_URL_EXTRA);

            mUrlDisplayTextView.setText(queryUrl);

        }


        /*
         * Loader Yükleme kısmı - onCreate En Altına
         */
        getSupportLoaderManager().initLoader(GITHUB_SEARCH_LOADER, null, this);
    }

    /**
     * This method retrieves the search text from the EditText, constructs the
     * URL (using {@link NetworkUtils}) for the github repository you'd like to find, displays
     * that URL in a TextView, and finally request that an AsyncTaskLoader performs the GET request.
     */
    private void makeGithubSearchQuery() {
        String githubQuery = mSearchBoxEditText.getText().toString(); // Kullanıcının girdiği Query metni


      // Eğer hiçbir şey girilmediyse kontrolü
        if (TextUtils.isEmpty(githubQuery)) {
            mUrlDisplayTextView.setText("No query entered, nothing to search for.");
            return;
        }

        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery); // girilen query metni ile bir url oluşturuluyor.
        mUrlDisplayTextView.setText(githubSearchUrl.toString()); // Textview içinde o url gösteriliyor.




        Bundle queryBundle = new Bundle();

        queryBundle.putString(SEARCH_QUERY_URL_EXTRA, githubSearchUrl.toString()); // Oluşturulan URL Bundle'da tutuluyor.

        /*
         * Now that we've created our bundle that we will pass to our Loader, we need to decide
         * if we should restart the loader (if the loader already existed) or if we need to
         * initialize the loader (if the loader did NOT already exist).
         *
         * We do this by first store the support loader manager in the variable loaderManager.
         * All things related to the Loader go through through the LoaderManager. Once we have a
         * hold on the support loader manager, (loaderManager) we can attempt to access our
         * githubSearchLoader. To do this, we use LoaderManager's method, "getLoader", and pass in
         * the ID we assigned in its creation. You can think of this process similar to finding a
         * View by ID. We give the LoaderManager an ID and it returns a loader (if one exists). If
         * one doesn't exist, we tell the LoaderManager to create one. If one does exist, we tell
         * the LoaderManager to restart it.
         */

        LoaderManager loaderManager = getSupportLoaderManager(); // LoaderManager

        Loader<String> githubSearchLoader = loaderManager.getLoader(GITHUB_SEARCH_LOADER); // İlgili loader

        if (githubSearchLoader == null) { // Eğer loader boş ise yani oluşturulmadıysa
            loaderManager.initLoader(GITHUB_SEARCH_LOADER, queryBundle, this);
        } else { // eğer varolan bir loader var ise
            loaderManager.restartLoader(GITHUB_SEARCH_LOADER, queryBundle, this);
        }
    }


    private void showJsonDataView() {

        mErrorMessageDisplay.setVisibility(View.INVISIBLE);

        mSearchResultsTextView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {

        mSearchResultsTextView.setVisibility(View.INVISIBLE);

        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
    //Loader başlatıldığında yükleme nesnesi gösterilir ek olarak args kontrol edilir.
    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<String>(this) {


            @Override
            protected void onStartLoading() {


                /* Eğer Bundle boş ise boş dönecek  */
                if (args == null) {
                    return;
                }


                /*
                 * Eğer Bundle dolu ise Yükleme nesnesi gösterilecek ve Loader başlatılacak.
                 */
                mLoadingIndicator.setVisibility(View.VISIBLE);


                forceLoad();
            }

            // COMPLETED (9) Override loadInBackground
            @Override
            public String loadInBackground() {

                // COMPLETED (10) Get the String for our URL from the bundle passed to onCreateLoader
                /* Extract the search query from the args using our constant */
                String searchQueryUrlString = args.getString(SEARCH_QUERY_URL_EXTRA);

                // COMPLETED (11) If the URL is null or empty, return null
                /* If the user didn't enter anything, there's nothing to search for */
                if (searchQueryUrlString == null || TextUtils.isEmpty(searchQueryUrlString)) {
                    return null;
                }

                // COMPLETED (12) Copy the try / catch block from the AsyncTask's doInBackground method
                /* Parse the URL from the passed in String and perform the search */
                try {
                    URL githubUrl = new URL(searchQueryUrlString);
                    String githubSearchResults = NetworkUtils.getResponseFromHttpUrl(githubUrl);
                    return githubSearchResults;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    // Yükleme bittiğinde
    @Override
    public void onLoadFinished(Loader<String> loader, String data) {


        /* Yükleme işlemi bittiğinde Yükleme nesnesi gizlenecek r. */
        mLoadingIndicator.setVisibility(View.INVISIBLE);


        /*
         * Eğer sonuç boş ise errorMessage Göster değilse sonucu ekrana yazdır ve showJsonDataView metodunu çağır.
         */
        if (null == data) {
            showErrorMessage();
        } else {
            mSearchResultsTextView.setText(data);
            showJsonDataView();
        }
    }

    // COMPLETED (16) Override onLoaderReset as it is part of the interface we implement, but don't do anything in this method
    @Override
    public void onLoaderReset(Loader<String> loader) {

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            makeGithubSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String queryUrl = mUrlDisplayTextView.getText().toString();
        outState.putString(SEARCH_QUERY_URL_EXTRA, queryUrl);


    }
}