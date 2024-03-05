package app.mmt.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.services.docs.v1.Docs;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main extends AppCompatActivity {
    ArrayList<User>list;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        FloatingActionButton fbtnAdd = findViewById(R.id.fbtnAdd);
        RecyclerView dataRecView = findViewById(R.id.dataRecView);
        SearchView dataSearch = findViewById(R.id.dataSearch);
        fbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main.this,AddData.class));
            }
        });
        list = new ArrayList<>();
        User defaultuser1 = new User("Su Su","susu@gmail.com","09111");
        User defaultuser2 = new User("Nu Nu","nunu@gmail.com","09222");
        User defaultuser3 = new User("Hla Hla","hlahla@gmail.com","09333");
        list.add(defaultuser1);
        list.add(defaultuser2);
        list.add(defaultuser3);
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        myAdapter = new MyAdapter(Main.this,list);
        dataRecView.setLayoutManager(new LinearLayoutManager(Main.this));
        dataRecView.setAdapter(myAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(Main.this, String.valueOf(snapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
                if (snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User user = dataSnapshot.getValue(User.class);
                        list.add(user);
                    }
                } else {
                    Toast.makeText(Main.this, "No Data", Toast.LENGTH_SHORT).show();
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
