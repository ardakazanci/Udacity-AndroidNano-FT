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

public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.NumberViewHolder> {

    private static final String TAG = GreenAdapter.class.getSimpleName(); // LOG Belirteci

    private int mNumberItems;

    /**
     *
     *
     * @param numberOfItems Listede görüntülecek öğe sayısı
     */
    public GreenAdapter(int numberOfItems) { // Tıklama olayları için hangi viewholder ' a tıklandığını belirten position bilgisi
        mNumberItems = numberOfItems;
    }

    /**
     *
     * Bu metot yeterli miktarda viewHolder oluşturacaktır.
     *
     *
     * @param viewGroup ViewHolders'ın içerdiği ViewGroup
     * @param viewType  View Tipi - Özel tip olarak layout olarak belirlendi . int olarak çağırılacak
     * @return - Teker teker görünüm dönrüecek. NumberViewHolder isminde
     */
    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.number_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        return viewHolder;
    }

    /**
     *Gösterilecek verinin doğru pozisyonda gösterilmesini sağlar.
     *
     * @param holder   : İlgili pozisyona göre doğru içeriğin güncelleştirilmesini sağlar.
     * @param position Adapter içerisinden alınan pozisyona bilgisi
     */
    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

    /**
     * Görüntülenecek öğe sayısı
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    /**
     * Cache of the children views for a list item.
     */
    class NumberViewHolder extends RecyclerView.ViewHolder {

        // Will display the position in the list, ie 0 through getItemCount() - 1
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
