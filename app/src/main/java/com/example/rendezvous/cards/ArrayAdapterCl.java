package com.example.rendezvous.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.rendezvous.R;

import java.util.List;

public class ArrayAdapterCl extends ArrayAdapter<Cards> {

    Context context;

    public ArrayAdapterCl(Context context, int resourceId, List<Cards> items) {
        super(context, resourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Cards cardItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView budget = (TextView) convertView.findViewById(R.id.budget);
        ImageView mNeedImage = (ImageView) convertView.findViewById(R.id.needImage);
        ImageView mGiveImage = (ImageView) convertView.findViewById(R.id.giveImage);

        name.setText(cardItem.getName());
        budget.setText(cardItem.getBudget());

        //need image
        if (cardItem.getNeed().equals("Dupa"))
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.poland));
        else if (cardItem.getNeed().equals("Test"))
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ukraine));
        else if (cardItem.getNeed().equals("Mexican"))
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.mexico));
        else if (cardItem.getNeed().equals("German"))
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.germany));
        else if (cardItem.getNeed().equals("American"))
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.usa));
        else if (cardItem.getNeed().equals("Japanese"))
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.japan));
        else if (cardItem.getNeed().equals("Spanish"))
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.spain));
        else if (cardItem.getNeed().equals("Chinese"))
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.china));
        else if (cardItem.getNeed().equals("French"))
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.france));
        else
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.forbidden));

        //food image
        if (cardItem.getGive().equals("Polish"))
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.poland));
        else if (cardItem.getGive().equals("Ukrainian"))
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ukraine));
        else if (cardItem.getGive().equals("Mexican"))
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.mexico));
        else if (cardItem.getGive().equals("Dupa"))
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.germany));
        else if (cardItem.getGive().equals("American"))
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.usa));
        else if (cardItem.getGive().equals("Japanese"))
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.japan));
        else if (cardItem.getGive().equals("Spanish"))
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.spain));
        else if (cardItem.getGive().equals("Chinese"))
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.china));
        else if (cardItem.getGive().equals("French"))
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.france));
        else
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.forbidden));

        switch (cardItem.getProfileImageUrl()) {
            case "default":
                Glide.with(convertView.getContext()).load(R.drawable.profile).into(image);
                break;
            default:
                Glide.clear(image);
                Glide.with(convertView.getContext()).load(cardItem.getProfileImageUrl()).into(image);
                break;
        }
        return convertView;
    }
}
