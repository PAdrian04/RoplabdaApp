package com.example.roplabdabajnoksag;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static androidx.core.app.ActivityCompat.requestPermissions;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MatchListActivity extends AppCompatActivity {

    //elpocsékoltam 12 órát arra hogy működjön a kilistázás, de tovább már nem bírom. Feladtam...
//elpocsékoltam 12 órát arra hogy működjön a kilistázás, de tovább már nem bírom. Feladtam...
//elpocsékoltam 12 órát arra hogy működjön a kilistázás, de tovább már nem bírom. Feladtam...
//elpocsékoltam 12 órát arra hogy működjön a kilistázás, de tovább már nem bírom. Feladtam...
//elpocsékoltam 12 órát arra hogy működjön a kilistázás, de tovább már nem bírom. Feladtam...

    private final static String TAG = MatchListActivity.class.getName();

    private int tag=1;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestrore;
    private CollectionReference mMatches;


    private FrameLayout redCircle;
    private TextView contentTextView;


    private RecyclerView mRecyclerView;
    private ArrayList<MatchItem> mLista;
    private MatchItemAdapter mAdapter;
    private int gridNumber = 1;
    private int cartItems = 0;
    private int queryLimit = 10;
    private boolean viewRow = false;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            Log.d(TAG, "Autentikált felhasználó!");
        } else {
            Log.d(TAG, "Nem autentikált felhasználó!");
            finish();
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,gridNumber));
        mLista = new ArrayList<>();

        mAdapter = new MatchItemAdapter(this,mLista);
        mRecyclerView.setAdapter(mAdapter);

        mFirestrore = FirebaseFirestore.getInstance();
        mMatches = mFirestrore.collection("Matches");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        queryData();

        
    }

    private void queryData() {

        mLista.clear();

        //mItems.whereEqualTo();
        mMatches.orderBy("cartedCount", Query.Direction.DESCENDING)
                .limit(queryLimit)
                .addSnapshotListener((snap, err) ->  {
            for (QueryDocumentSnapshot document : snap) {
                MatchItem item = document.toObject(MatchItem.class);
                mLista.add(item);
            }

            if(mLista.size() == 0) {
                intializeData();
                queryData();
            }
            //initializeDate();
            mAdapter.notifyDataSetChanged();

        });
    }

    private void intializeData() {
        List<Task<DocumentReference>> tasks = new ArrayList<>();

        String[] matchesList = getResources().getStringArray(R.array.match_item_names);
        String[] matchesInfos= getResources().getStringArray(R.array.match_item_desc);
        String[] matchesPrices= getResources().getStringArray(R.array.match_prices);

        TypedArray matchesImageResource = getResources().obtainTypedArray(R.array.match_images);

        //mLista.clear();

        for (int i = 0; i < matchesList.length; i++) {
            mMatches.add(new MatchItem(
                    matchesList[i],
                    matchesInfos[i],
                    matchesPrices[i],
                    matchesImageResource.getResourceId(i,0)));
        }
        Tasks.whenAllSuccess(tasks).addOnSuccessListener(v -> queryData());
        matchesImageResource.recycle();



        //mAdapter.notifyDataSetChanged();



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.match_list_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.logout_button) {
            Log.d(TAG, "Logout clicked!");
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.setting_button) {
            Log.d(TAG, "Setting clicked!");
            FirebaseAuth.getInstance().signOut();
            finish();
            return true;
        }else if (id == R.id.open_camera_button) {
            //openCamera();
            return true;
        }else if (id == R.id.june_matches_button) {
            //showJuneMatches();
            return true;
        }else if (id == R.id.all_matches) {
           //allMatches();
            return true;
        }else if (id == R.id.cart) {
            Log.d(TAG, "Cart clicked!");
            return true;
        } else if (id == R.id.view_selector) {
            if (viewRow) {
                changeSpanCount(item, R.drawable.view_grid, 1);
            } else {
                changeSpanCount(item, R.drawable.view_row, 2);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeSpanCount(MenuItem item, int drawableId, int spanCount) {
        viewRow = !viewRow;
        item.setIcon(drawableId);
        GridLayoutManager layoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
        layoutManager.setSpanCount(spanCount);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem alertMenuItem = menu.findItem(R.id.cart);
        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();

        redCircle = (FrameLayout) rootView.findViewById(R.id.view_alert_red_circle);
        contentTextView = (TextView) rootView.findViewById(R.id.view_alert_count_textview);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(alertMenuItem);
            }
        });

        return super.onPrepareOptionsMenu(menu);
    }



}
