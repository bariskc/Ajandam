package com.ajanda.bariskoc.ajanda;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ajanda.bariskoc.ajanda.R;
import com.ajanda.bariskoc.ajanda.base.BaseActivity;
import com.ajanda.bariskoc.ajanda.model.Gorev;
import com.ajanda.bariskoc.ajanda.tool.MyRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends BaseActivity {

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private ArrayList<Gorev> result;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("yapilacaklar");

        result = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.match_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.ana_swiperrefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listeyiDoldur();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        listeyiDoldur();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void listeyiDoldur() {

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("yapilacaklar");
        final Query query = myRef.orderByChild("tarih");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                result = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    result.add(snapshot.getValue(Gorev.class));
                }

                MyRecyclerAdapter adapter = new MyRecyclerAdapter(MainActivity.this, result);
                recyclerView.setAdapter(adapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                Collections.reverse(result);
                query.removeEventListener(this);
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            Toast.makeText(this, "Ekleme.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), AddTaskActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
