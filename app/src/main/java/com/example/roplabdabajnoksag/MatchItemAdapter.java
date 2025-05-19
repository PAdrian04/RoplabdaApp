package com.example.roplabdabajnoksag;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MatchItemAdapter extends RecyclerView.Adapter<MatchItemAdapter.viewHolder> implements Filterable {
    //elpocsékoltam 12 órát arra hogy működjön a kilistázás, de tovább már nem bírom. Feladtam...
//elpocsékoltam 12 órát arra hogy működjön a kilistázás, de tovább már nem bírom. Feladtam...
//elpocsékoltam 12 órát arra hogy működjön a kilistázás, de tovább már nem bírom. Feladtam...
//elpocsékoltam 12 órát arra hogy működjön a kilistázás, de tovább már nem bírom. Feladtam...
//elpocsékoltam 12 órát arra hogy működjön a kilistázás, de tovább már nem bírom. Feladtam...
    private final static String TAG = MatchItemAdapter.class.getName();
    private ArrayList<MatchItem> lista;
    private ArrayList<MatchItem> listaegesz;
    private Context mcontext;
    private int lastPostition = -1;
    public MatchItemAdapter(Context context, ArrayList<MatchItem> itemsdata){
        this.lista = itemsdata;
        this.listaegesz = itemsdata;
        this.mcontext = context;
    }
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(mcontext).inflate(R.layout.list_match, parent, false));
    }

    @Override
    public void onBindViewHolder(MatchItemAdapter.viewHolder holder, int position) {
        MatchItem currentMatch = lista.get(position);

        holder.bindTo(currentMatch);

        if(holder.getBindingAdapterPosition()> lastPostition){
            Animation animation = AnimationUtils.loadAnimation(mcontext,R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPostition = holder.getBindingAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public Filter getFilter() {
        return matchFilter;
    }
    private Filter matchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            ArrayList<MatchItem> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0) {
                results.count = listaegesz.size();
                results.values = listaegesz;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(MatchItem item : listaegesz) {
                    if(item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            lista = (ArrayList) results.values;
            notifyDataSetChanged();
        }
    };
    class viewHolder extends RecyclerView.ViewHolder{
        private TextView titleText;
        private TextView infoText;
        private TextView priceText;
        private ImageView matchImage;
        public viewHolder(View itemView){
            super(itemView);

            titleText = itemView.findViewById(R.id.matchTitle);
            infoText = itemView.findViewById(R.id.matchTime);
            priceText = itemView.findViewById(R.id.price);
            matchImage = itemView.findViewById(R.id.matchImage);

            itemView.findViewById(R.id.add_to_cart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: asdasd");
                }
            });
        }

        public void bindTo(MatchItem currentMatch) {
            titleText.setText(currentMatch.getName());
            infoText.setText(currentMatch.getInfo());
            priceText.setText(currentMatch.getPrice());

            Glide.with(mcontext).load(currentMatch.getImageResource()).into(matchImage);
        }
    }
}
