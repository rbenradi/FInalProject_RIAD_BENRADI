package fr.esilv.finalproject_riad_benradi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapater extends RecyclerView.Adapter<MyAdapater.MyHolder> {

    Context c;
    private List<KitsuSearchData> results; //This list create a list of array which parameter define in our model class
    private OnItemListener mOnItemListener;

    public MyAdapater(List<KitsuSearchData> results,OnItemListener onItemListener) {
        this.results = results;
        this.mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null); //This line inflate our row

        return new MyHolder(view,mOnItemListener); //this will return our view to holder class
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        KitsuSearchData kitsuSearchData = results.get(position);
        Attributes kitsu_attributes = kitsuSearchData.getAttributes();
        holder.myTitle.setText(kitsu_attributes.getTitles().getEn());
        holder.mySynopsis.setText(kitsu_attributes.getSynopsis());
        Glide.with(holder.itemView.getContext()).load(kitsu_attributes.getPosterImage().getLarge()).into(holder.myImageView);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView myImageView;
        private RelativeLayout card;
        private TextView myTitle, mySynopsis;
        private OnItemListener onItemListener;

        public MyHolder(@NonNull View itemView,OnItemListener onItemListener) {
            super(itemView);

            this.myImageView = itemView.findViewById(R.id.imageView);
            this.myTitle = itemView.findViewById(R.id.title);
            this.mySynopsis = itemView.findViewById(R.id.synopsis);
            this.onItemListener = onItemListener;
            this.card = itemView.findViewById(R.id.card);
            this.card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener{
        void onItemClick(int position);
    }

}




