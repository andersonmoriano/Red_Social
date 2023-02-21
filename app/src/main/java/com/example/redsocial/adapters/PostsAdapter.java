package com.example.redsocial.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redsocial.R;
import com.example.redsocial.models.Publicacion;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

public class PostsAdapter extends FirestoreRecyclerAdapter<Publicacion, PostsAdapter.ViewHolder>
{
    Context context;

    public PostsAdapter(FirestoreRecyclerOptions<Publicacion> options, Context context)
    {
        super(options);
        this.context = context;
    }
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Publicacion publicacion) {
        holder.txtTitulo.setText(publicacion.getNombre());
        holder.txtDescripcion.setText(publicacion.getDescripcion());
        if (publicacion.getImage1() != null) {
            if (!publicacion.getImage1().isEmpty()) {
                Picasso.with(context).load(publicacion.getImage1()).into(holder.ivPublicacion);
            }
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vistatarjeta_publicacion, parent, false);
        return new ViewHolder(view);
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtTitulo;
        TextView txtDescripcion;
        ImageView ivPublicacion;

        public ViewHolder(View view)
        {
            super(view);
            txtTitulo = view.findViewById(R.id.txtTituloPublicacion);
            txtDescripcion = view.findViewById(R.id.txtDescripcionPublicacion);
            ivPublicacion = view.findViewById(R.id.ivPublicacionCard);
        }
    }
}
