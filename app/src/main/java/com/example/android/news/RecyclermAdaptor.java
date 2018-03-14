package com.example.android.news;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.getColor;
import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Justas on 2/22/2018.
 */

public class RecyclermAdaptor extends RecyclerView.Adapter<RecyclermAdaptor.MyViewHolder> {
    Context mcontext;
    SubscribedList subscribedList;
    ArrayList<String> curento = new ArrayList<>();
    ArrayList<browseObject> marrayList = new ArrayList<>();
    RecyclermAdaptor(Context context,ArrayList<browseObject> arrayList)
    {
        marrayList= arrayList;
        mcontext = context;
        subscribedList= new SubscribedList(mcontext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browselistitem,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.navigateto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, soucearticles.class);
                intent.putExtra("souceId", marrayList.get(position).getMid());
                intent.putExtra("souceName", marrayList.get(position).getMname());
                mcontext.startActivity(intent);

            }
        });
        holder.navigateto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String o="";
                for(int i=0; i<subscribedList.getValues().size(); i++)
                    o+=subscribedList.getValues().get(i)+", ";
                Toast.makeText(mcontext, ""+o, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curento = subscribedList.getNames();
                if(!curento.contains(marrayList.get(position).getMname())) {
                    if(subscribedList.size()==20)
                    {
                        Toast.makeText(mcontext, "Maximum of 20 subscriptions reached", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        subscribedList.put(marrayList.get(position).getMname(), marrayList.get(position).getMid());
                        holder.button.setBackgroundColor(ResourcesCompat.getColor(mcontext.getResources(), R.color.colorAccent, null));
                        holder.minusbutton.setBackgroundColor(ResourcesCompat.getColor(mcontext.getResources(), R.color.removeselected,null));
                    }
                }
            }
        });
        if(subscribedList.getNames().contains(marrayList.get(position).getMname()))
        {
            holder.button.setBackgroundColor(ResourcesCompat.getColor(mcontext.getResources(), R.color.colorAccent, null));
            holder.minusbutton.setBackgroundColor(ResourcesCompat.getColor(mcontext.getResources(), R.color.removeselected,null));
        }
        else {
            holder.button.setBackgroundColor(ResourcesCompat.getColor(mcontext.getResources(), R.color.notselected, null));
            holder.minusbutton.setBackgroundColor(ResourcesCompat.getColor(mcontext.getResources(), R.color.removeNOTselected,null));
        }
        holder.category.setText(mcontext.getString(R.string.cate) + " " + marrayList.get(position).getMcategory());
        holder.des.setText(marrayList.get(position).getMdescription());
        holder.title.setText(marrayList.get(position).getMname());
        holder.language.setText(mcontext.getString(R.string.language) + " " + marrayList.get(position).getMlanguage());
        holder.country.setText(mcontext.getString(R.string.countr) + " " + marrayList.get(position).getMlanguage());

        holder.button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                subscribedList.clearList();
                return false;
            }
        });
        holder.minusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curento = subscribedList.getNames();
                if(curento.contains(marrayList.get(position).getMname())) {
                    subscribedList.remove(marrayList.get(position).getMname(), marrayList.get(position).getMid());
                    holder.button.setBackgroundColor(ResourcesCompat.getColor(mcontext.getResources(), R.color.notselected, null));
                    holder.minusbutton.setBackgroundColor(ResourcesCompat.getColor(mcontext.getResources(), R.color.removeNOTselected,null));
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return  marrayList == null ? 0 : marrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView des,title,category,language,country;
        LinearLayout navigateto;
        ImageButton button,minusbutton;

        public MyViewHolder(View itemView) {
            super(itemView);
            navigateto = itemView.findViewById(R.id.navigateto);
            button = itemView.findViewById(R.id.subscribe);
            title = itemView.findViewById(R.id.name);
            des = itemView.findViewById(R.id.descripton);
            category = itemView.findViewById(R.id.category);
            language = itemView.findViewById(R.id.language);
            country = itemView.findViewById(R.id.country);
            minusbutton = itemView.findViewById(R.id.unsub);
        }
    }
}
