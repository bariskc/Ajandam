package com.ajanda.bariskoc.ajanda.tool;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajanda.bariskoc.ajanda.R;
import com.ajanda.bariskoc.ajanda.model.Gorev;
import com.ajanda.bariskoc.ajanda.model.Header;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by bariskoc on 2.10.2017.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int STANDART_MENU_ITEM = 0;
    public static final int AD_ITEM = 1;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private List<Gorev> list;
    private List<Header> listDay;
    private Context context;
    //private ArrayList<Gorev> gorevArrayList;


    public MyRecyclerAdapter(Context context, List<Gorev> list) {
        this.context = context;
        //this.list = list;

        this.list = new ArrayList<>();
        for (int i = 0; i<list.size(); i++){
            //this.list.add(new Gorev("dateSep", liste.keySet().toString()));

                this.list.add(list.get(i));


        }

        /*Map<String, List<Gorev>> liste = new HashMap<>();

        for (int i = 0; i <= list.size(); i++) {
            String current = list.get(i).getTarih();
            for (int k = 0; k < list.size(); k++) {
                if (list.get(k).getTarih().equals(current)) {
                    liste.put(list.get(i).getTarih(), list.get(k));
                    //this.list.add(list.get(k));
                }
            }

            /*if (i != 0) {
                if (list.get(i).getTarih().equals(list.get(i - 1).getTarih())) {
                    if (i == list.size()) {
                        continue;
                    } else {
                        i++;
                    }

                }
            }*/
            /*if (i == list.size()) {
                continue;
            }*/

            /*this.list.add(new Gorev("dateSep", list.get(i).getTarih(), list.get(i).getGun()));

            for (int k = 0; k < list.size(); k++) {
                if (list.get(k).getTarih().equals(current)) {
                    this.list.add(list.get(k));
                }*/

            /*if (!list.get(i).getTarih().equals(current)) {
                if (count == 0) {
                    this.list.add(new Gorev("dateSep", list.get(i).getTarih(), list.get(i).getGun()));
                    count++;
                    current = list.get(i + 1).getTarih();
                } else if (count != 0) {
                    this.list.add(list.get(i));


                }

            }*/
            //this.list.add(list.get(i));
       /* }
        for (int i = 0; i<liste.size(); i++){
            this.list.add(new Gorev("dateSep", liste.keySet().toString()));
            for (int k = 0 ; k< liste.get(i).size(); k++){
                this.list.add(list.get(k));
            }

        }*/

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case STANDART_MENU_ITEM:
                View vv = inflater.inflate(R.layout.card_item, parent, false);
                viewHolder = new MyViewHolder(vv);
                break;
            case AD_ITEM:
                View v2 = inflater.inflate(R.layout.date_separator, parent, false);
                viewHolder = new ViewHolderDateSep(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case STANDART_MENU_ITEM:
                final MyViewHolder viewHolder = (MyViewHolder) holder;
                Gorev match = list.get(position);

                //viewHolder.txt_category.setText(match.getKategori());
                viewHolder.txt_title.setText(match.getKonu());
                viewHolder.txt_description.setText(match.getDetay());
                viewHolder.txt_time.setText(match.getSaat());
                String kategori = match.getKategori();

                if (kategori.equals("Okul")) {
                    viewHolder.txt_category.setBackgroundColor(Color.rgb(226, 189, 8));
                } else if (kategori.equals("İş")) {
                    viewHolder.txt_category.setBackgroundColor(Color.rgb(10, 143, 226));
                } else if (kategori.equals("Eğlence")) {
                    viewHolder.txt_category.setBackgroundColor(Color.rgb(118, 10, 226));
                } else if (kategori.equals("Günlük")) {
                    viewHolder.txt_category.setBackgroundColor(Color.rgb(226, 11, 11));
                } else if (kategori.equals("Diğer")) {
                    viewHolder.txt_category.setBackgroundColor(Color.rgb(226, 9, 110));
                }


                holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo contextMenuInfo) {
                        menu.add(holder.getAdapterPosition(), 0, 0, "Sil");
                    }
                });
                break;
            case AD_ITEM:
                final ViewHolderDateSep viewHolderDateSep = (ViewHolderDateSep) holder;
                //Header tarih = listDay.get(position);
                Gorev gorev = list.get(position);
                viewHolderDateSep.txt_day.setText(gorev.getGun());
                viewHolderDateSep.txt_date.setText(gorev.getTarih());
                break;
            default:
                break;
        }
    }


    @Override
    public int getItemViewType(int position) {

        //return (position % 5 == 1) ? AD_ITEM : STANDART_MENU_ITEM;
        if (list.get(position).getId().equals("dateSep"))
            return AD_ITEM;
        return STANDART_MENU_ITEM;


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txt_category, txt_time, txt_title, txt_description;


        public MyViewHolder(View itemView) {
            super(itemView);
            txt_category = itemView.findViewById(R.id.txt_category);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_description = itemView.findViewById(R.id.txt_description);

        }

        @Override
        public void onClick(View view) {

        }
    }

    public static class ViewHolderDateSep extends RecyclerView.ViewHolder {
        public TextView txt_day, txt_date;

        public ViewHolderDateSep(View view) {
            super(view);
            txt_day = view.findViewById(R.id.txt_day);
            txt_date = view.findViewById(R.id.txt_date);
        }
    }

}
