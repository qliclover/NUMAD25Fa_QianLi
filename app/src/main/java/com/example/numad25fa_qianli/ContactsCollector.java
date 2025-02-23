package com.example.numad25fa_qianli;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ContactsCollector extends AppCompatActivity {
    RecyclerView recyclerView;
    ContactsAdapter adapter;
    ArrayList<Contact> contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_collector);

        recyclerView = findViewById(R.id.recycler_view_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactsList = new ArrayList<>();

        adapter = new ContactsAdapter(this, contactsList, recyclerView);

        recyclerView.setAdapter(adapter);

        FloatingActionButton fabBtn = findViewById(R.id.fab_btn);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
            }
        });
    }

    private void addContact() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Contact");

        View showDialog = LayoutInflater.from(this).inflate(R.layout.contact_dialog, null);
        builder.setView(showDialog);

        final EditText editFirstName = showDialog.findViewById(R.id.contact_firstname);
        final EditText editLastName = showDialog.findViewById(R.id.contact_lastname);
        final EditText editPhone = showDialog.findViewById(R.id.contact_phone);

        builder.setPositiveButton("Add", null);
        builder.setNegativeButton("Cancel", null);

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String firstName = editFirstName.getText().toString().trim();
            String lastName = editLastName.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();

            if (!firstName.isEmpty() && !lastName.isEmpty() && !phone.isEmpty()) {
                contactsList.add(new Contact(firstName, lastName, phone));
                int newPosition = contactsList.size() - 1;
                adapter.notifyItemInserted(newPosition);
            }
            dialog.dismiss();
        });
    }
}