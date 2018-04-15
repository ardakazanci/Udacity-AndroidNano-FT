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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// RecyclerView'den extend edilecek Bu sınıf içerisinde yer alan ViewHolder ' ı generic tip olarak aldı.
public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.NumberViewHolder> {

    private static final String TAG = GreenAdapter.class.getSimpleName(); // LOG Etiketi - Hangi Dosyada hata olduğunu belirtmek için

    private int mNumberItems; // Listede görüntülecek öğe sayısını ifade eder.


    // MainActivity ' den gelen Gösterilecek öğe sayısı burada eşlenecektir.
    public GreenAdapter(int numberOfItems) {
        mNumberItems = numberOfItems;
    }
    /**
     *
     *Bu, her yeni ViewHolder oluşturulduğunda çağrılır. Bu, RecyclerView olduğunda gerçekleşir düzenlenmiştir.
     * Ekranı doldurmak ve kaydırmaya izin vermek için yeterli ViewHolders oluşturulacaktır
     *
     * @param viewGroup Viewholders'ın içerdiği viewGroup
     * @param viewType : Özel olarak oluşturmuş olduğumuz stil dosyası - int değer alır.
     * @return Her liste öğesi için görünümü tutan viewHolder döndürür.
     */

    // Görünümlerin bireysel oluşturulması ve şişirilme işlemi .
    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext(); // Contexdt alır.
        int layoutIdForListItem = R.layout.number_list_item; // Şişirme özel görünüm biçimi
        LayoutInflater inflater = LayoutInflater.from(context); // Layout şişirme işlemi .
        boolean shouldAttachToParentImmediately = false; // son parametre false olmalı.

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        return viewHolder;
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    /**
     * Bir liste öğesi için çocukların görüşlerinin önbelleği.
     */
    class NumberViewHolder extends RecyclerView.ViewHolder {


        TextView listItemNumberView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews and set an onClickListener to listen for clicks. Those will be handled in the
         * onClick method below.
         * @param itemView The View that you inflated in
         *                 {@link GreenAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public NumberViewHolder(View itemView) {
            super(itemView);

            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
        }

        /**
         * A method we wrote for convenience. This method will take an integer as input and
         * use that integer to display the appropriate text within a list item.
         * @param listIndex Position of the item in the list
         */
        void bind(int listIndex) {
            listItemNumberView.setText(String.valueOf(listIndex));
        }
    }
}
