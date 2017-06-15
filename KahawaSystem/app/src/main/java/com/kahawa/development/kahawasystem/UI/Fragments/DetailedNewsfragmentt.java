package com.kahawa.development.kahawasystem.UI.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.kahawa.development.kahawasystem.R;
import com.kahawa.development.kahawasystem.UI.Activities.CustomVolleyRequest;


/**
 * Created by Mwatha on 29-Aug-16.
 */
public class DetailedNewsfragmentt extends DialogFragment {
    View view;
    private Context context;
    private ImageLoader loadImage;
    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout to use as dialog or embedded fragment
        view=inflater.inflate(R.layout.detailednews, container, false);
        TextView tvTitle=(TextView) view.findViewById(R.id.detailedNewstitle);
        TextView tvContent=(TextView)view.findViewById(R.id.detailedcontent);
        TextView tvDate=(TextView) view.findViewById(R.id.detaileddate);
        NetworkImageView imageView=(NetworkImageView) view.findViewById(R.id.detailedicon);


        String title= getArguments().getString("title");
        String date= getArguments().getString("date");
        String content= getArguments().getString("content");
        String imageurl=getArguments().getString("url");

        //setting the values

        loadImage = CustomVolleyRequest.getInstance(context).getImageLoader();
        loadImage.get(imageurl, ImageLoader.getImageListener(imageView, R.drawable.ic_holder, android.R.drawable.ic_dialog_alert));

        tvTitle.setText(title);
        tvContent.setText(content);
        tvDate.setText(date);
        imageView.setImageUrl(imageurl,loadImage);

        //return the view that is going to be inflated in the fragment
        return view;
}

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
