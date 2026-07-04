package com.uth.appfrima;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private SignatureAdapter adapter;
    private List<Signature> signatureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewSignatures);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadSignatures();
    }

    private void loadSignatures() {
        signatureList = dbHelper.getAllSignatures();
        adapter = new SignatureAdapter(signatureList, this::showDeleteDialog);
        recyclerView.setAdapter(adapter);
    }

    private void showDeleteDialog(Signature signature, int position) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_title)
                .setMessage(R.string.delete_message)
                .setPositiveButton(R.string.delete_confirm, (dialog, which) -> {
                    dbHelper.deleteSignature(signature.getId());
                    signatureList.remove(position);
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(ListActivity.this, R.string.delete_success, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.delete_cancel, null)
                .show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
