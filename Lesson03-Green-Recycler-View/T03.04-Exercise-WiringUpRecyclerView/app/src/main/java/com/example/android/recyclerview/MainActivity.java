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
package com.example.android.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    // TODO (COMPLETED) Create a private static final int called NUM_LIST_ITEMS and set it equal to 100

    // TODO (COMPLETED) Create a GreenAdapter variable called mAdapter
    // TODO (COMPLETED) Create a RecyclerView variable called mNumbersList


    private static final  int NUM_LIST_ITEMS = 100; // Görüntülenecek verisi sayısı.
    GreenAdapter mAdater; // Adapter değişkeni.
    RecyclerView mNumbersList;  // Numberlist değişkeni.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO (COMPLETED) Use findViewById to store a reference to the RecyclerView in mNumbersList

        mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);


        // TODO (COMPLETED) Create a LinearLayoutManager variable called layoutManager

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        // TODO (COMPLETED) Use setLayoutManager on mNumbersList with the LinearLayoutManager we created above
        mNumbersList.setLayoutManager(linearLayoutManager);
        // TODO (COMPLETED) Use setHasFixedSize(true) to designate that the contents of the RecyclerView won't change an item's size
        // RecyclerView öğesinin içeriğinin bir öğenin boyutunu değiştirmeyeceğini belirtmek için setHasFixedSize (true) kullanın.

        mNumbersList.setHasFixedSize(true);
        // TODO (COMPLETED) Store a new GreenAdapter in mAdapter and pass it NUM_LIST_ITEMS
        mAdater = new GreenAdapter(NUM_LIST_ITEMS);
        // TODO (COMPLETED) Set the GreenAdapter you created on mNumbersList

        mNumbersList.setAdapter(mAdater);
    }
}
