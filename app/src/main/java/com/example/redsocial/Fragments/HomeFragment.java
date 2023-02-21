package com.example.redsocial.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.example.redsocial.R;
import com.example.redsocial.activities.MainActivity;
import com.example.redsocial.activities.PostActivity;
import com.example.redsocial.adapters.PostsAdapter;
import com.example.redsocial.models.Publicacion;
import com.example.redsocial.providers.PostProvider;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.redsocial.providers.AuthProvider;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment
{
    View mView;
    FloatingActionButton mFab;
    Toolbar toolbar;
    AuthProvider authProvider;
    RecyclerView recyclerView;
    PostProvider postProvider;
    PostsAdapter postsAdapter;
    public HomeFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        mFab = mView.findViewById(R.id.iconoAdd);
        toolbar = mView.findViewById(R.id.toolBar);
        recyclerView = mView.findViewById(R.id.recyclerViewHome);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Publicaciones");
        setHasOptionsMenu(true);
        authProvider = new AuthProvider();
        postProvider = new PostProvider();
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarVentana();
            }
        });
        return mView;
    }
    public void mostrarVentana()
    {
        Intent i = new Intent(getContext(), PostActivity.class);
        startActivity(i);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        Query query = postProvider.getAll();
        FirestoreRecyclerOptions<Publicacion> options = new FirestoreRecyclerOptions.Builder<Publicacion>().setQuery(query, Publicacion.class).build();
        postsAdapter = new PostsAdapter(options, getContext());
        recyclerView.setAdapter(postsAdapter);
        postsAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        postsAdapter.stopListening();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == R.id.itemLogout)
        {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout()
    {
        authProvider.logout();
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
