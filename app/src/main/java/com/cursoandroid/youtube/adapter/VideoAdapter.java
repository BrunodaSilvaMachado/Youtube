package com.cursoandroid.youtube.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cursoandroid.youtube.R;
import com.cursoandroid.youtube.model.Item;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private final List<Item> videoList;
    private final Context context;
    private OnItemClickListener<Item> itemClick;

    public VideoAdapter(Context context, List<Item> videoList) {
        this.videoList = videoList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_video,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VideoViewHolder holder, int position) {
        Item video = videoList.get(position);
        String urlCapa = video.snippet.thumbnails.high.url;
        //String urlCapaCanal = video.snippet.thumbnails.high.url;
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
        Picasso.get().load(urlCapa).into(holder.capa);
        holder.capaCanal.setImageResource(R.mipmap.ic_launcher_round);
        //Picasso.get().load(urlCapaCanal).into(holder.capaCanal);
        holder.titulo.setText(video.snippet.title);
        holder.data.setText(DateFor.format(video.snippet.publishedAt));
        holder.itemView.setOnClickListener(l->itemClick.onItemClick(video));
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public void setOnItemClickListener(OnItemClickListener<Item> itemClick) {
        this.itemClick = itemClick;
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder{
        TextView titulo;
        TextView descricao;
        TextView data;
        ImageView capa;
        CircleImageView capaCanal;
        public VideoViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.tvTitulo);
            //descricao = itemView.findViewById();
            data = itemView.findViewById(R.id.tvDate);
            capa = itemView.findViewById(R.id.ivCapa);
            capaCanal = itemView.findViewById(R.id.ivCapaCanal);

        }
    }
}
