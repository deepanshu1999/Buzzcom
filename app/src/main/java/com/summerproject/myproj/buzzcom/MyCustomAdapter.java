package com.summerproject.myproj.buzzcom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import at.blogc.android.views.ExpandableTextView;

public class MyCustomAdapter extends ArrayAdapter<Feed>{
    int resid;

    int layoutpallete=R.layout.pallete1;
    int layoutpallete_image=R.layout.pallete_with_image;
    int layoutquestion=R.layout.question_without_image;
    int layoutquestion_image=R.layout.question_with_image;
    Context context;
    ArrayList<Feed> arr;

    public MyCustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Feed> objects) {
        super(context, resource,objects);
        this.resid=resource;
        this.arr=objects;
        this.context=context;

    }
    @Override
    public int getCount() {
        return arr.size();
    }

    @Nullable
    @Override
    public Feed getItem(int position) {
        return arr.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Feed holder = getItem(position);
        if (convertView == null) {
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             if(holder.getStatus()== Feed.Status.Post_Imageless)
                convertView= layoutInflater.inflate(layoutpallete, parent, false);
             else if(holder.getStatus()==Feed.Status.Post_Image)
                 convertView= layoutInflater.inflate(layoutpallete_image, parent, false);
             else if(holder.getStatus()== Feed.Status.Question_Imageless)
                 convertView= layoutInflater.inflate(layoutquestion, parent, false);
             else if(holder.getStatus()==Feed.Status.Question_Image)
                 convertView= layoutInflater.inflate(layoutquestion_image, parent, false);
             else

            Log.d("TAG", "getView: Hello! I am a newly inflated view. position: " + position);
        } else
            Log.d("TAG", "getView: Hello! I was just recycled. Green Environment :). position: " + position);
        if(holder.getStatus()== Feed.Status.Post_Imageless)
        {
            ImageView img=convertView.findViewById(R.id.icon1);
            img.setImageBitmap(holder.getUploader_icon());
            TextView tv0=convertView.findViewById(R.id.labelname);
            tv0.setText(holder.getUploadername());
            TextView tv1=convertView.findViewById(R.id.threadname);
            tv1.setText("Posted In "+holder.getThreadname());
           final ExpandableTextView tv2=convertView.findViewById(R.id.maintext);
            tv2.setText(holder.getMaintext());
            TextView tv3=convertView.findViewById(R.id.readmore);
            tv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv2.expand();
                }
            });
            TextView tv4=convertView.findViewById(R.id.liketext);
            tv4.setText(Integer.toString(holder.getNo_of_likes())+" Likes");
            TextView tv5=convertView.findViewById(R.id.commenttext);
            tv5.setText(Integer.toString(holder.getNo_of_comments())+" Comments");
        }
        else if(holder.getStatus()==Feed.Status.Post_Image){
            ImageView img=convertView.findViewById(R.id.pallete_with_image_icon1);
            img.setImageBitmap(holder.getUploader_icon());
            TextView tv0=convertView.findViewById(R.id.pallete_with_image_labelname);
            tv0.setText(holder.getUploadername());
            TextView tv1=convertView.findViewById(R.id.pallete_with_image_threadname);
            tv1.setText("Posted In "+holder.getThreadname());
            final ExpandableTextView tv2=convertView.findViewById(R.id.pallete_with_image_maintext);
            tv2.setText(holder.getMaintext());
            ImageView im1=convertView.findViewById(R.id.pallete_with_image_main_text_image);
            im1.setImageBitmap(holder.getImage());
            TextView tv3=convertView.findViewById(R.id.pallete_with_image_readmore);
            tv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv2.expand();
                }
            });
            TextView tv4=convertView.findViewById(R.id.pallete_with_image_liketext);
            tv4.setText(Integer.toString(holder.getNo_of_likes())+" Likes");
            TextView tv5=convertView.findViewById(R.id.pallete_with_image_commenttext);
            tv5.setText(Integer.toString(holder.getNo_of_comments())+" Comments");
        }

        else if(holder.getStatus()== Feed.Status.Question_Imageless){
            ImageView img=convertView.findViewById(R.id.question_without_image_icon1);
            img.setImageBitmap(holder.getUploader_icon());
            TextView tv0=convertView.findViewById(R.id.question_without_image_labelname);
            tv0.setText(holder.getUploadername());
            TextView tv1=convertView.findViewById(R.id.question_without_image_threadname);
            tv1.setText("Posted In "+holder.getThreadname());
            final ExpandableTextView tv2=convertView.findViewById(R.id.question_without_image_maintext);
            tv2.setText(holder.getQuestion());
            TextView tv3=convertView.findViewById(R.id.question_without_image_readmore);
            tv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv2.expand();
                }
            });
            TextView tv4=convertView.findViewById(R.id.question_without_image_liketext);
            tv4.setText(Integer.toString(holder.getNo_of_likes())+" Support");
            TextView tv5=convertView.findViewById(R.id.question_without_image_commenttext);
            tv5.setText(Integer.toString(holder.getNo_of_comments())+" Answer");
        }

        else if(holder.getStatus()==Feed.Status.Question_Image){
            ImageView img=convertView.findViewById(R.id.question_with_image_icon1);
            img.setImageBitmap(holder.getUploader_icon());
            TextView tv0=convertView.findViewById(R.id.question_with_image_labelname);
            tv0.setText(holder.getUploadername());
            TextView tv1=convertView.findViewById(R.id.question_with_image_threadname);
            tv1.setText("Posted In "+holder.getThreadname());
            final ExpandableTextView tv2=convertView.findViewById(R.id.question_with_image_maintext);
            tv2.setText(holder.getQuestion());
            ImageView im1=convertView.findViewById(R.id.question_with_image_main_text_image);
            im1.setImageBitmap(holder.getImage());
            TextView tv3=convertView.findViewById(R.id.question_with_image_readmore);
            tv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv2.expand();
                }
            });
            TextView tv4=convertView.findViewById(R.id.question_with_image_liketext);
            tv4.setText(Integer.toString(holder.getNo_of_likes())+" Likes");
            TextView tv5=convertView.findViewById(R.id.question_with_image_commenttext);
            tv5.setText(Integer.toString(holder.getNo_of_comments())+" Comments");
        }
        return convertView;
    }
}



//Continue with firebase
