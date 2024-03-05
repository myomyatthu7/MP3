package app.mmt.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddData extends AppCompatActivity implements View.OnClickListener {
    EditText etId,etName,etEmail,etPhno;
    Button btnSave;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);
        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhno = findViewById(R.id.etPhno);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onClick(View v) {
        String id = etId.getText().toString();
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String phon = etPhno.getText().toString();

        User user = new User(name,email,phon);
        databaseReference.child("User").child(name).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
