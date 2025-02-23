package com.example.numad25fa_qianli;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.snackbar.Snackbar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactHolder> {
    List<Contact> contacts;
    Context context;
    RecyclerView recyclerView;

    public ContactsAdapter(Context context, List<Contact> contacts, RecyclerView recyclerView) {
        this.context = context;
        this.contacts = contacts;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2,parent,false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.contactName.setText(contact.getName());
        holder.contactPhone.setText(contact.getPhone());

//        Tap on function
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + contact.getPhone()));
                view.getContext().startActivity(intent);
            }
        });

//        Edit and delete
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete or Edit")
                    .setMessage("Do you want to edit or delete?")
                    .setPositiveButton("Edit", (dialog, which) -> showEditDialog(position))
                    .setNegativeButton("Delete", (dialog, which) -> {
                        contacts.remove(position);
                        notifyItemRemoved(position);
                        Snackbar.make(recyclerView, "Contact deleted", Snackbar.LENGTH_SHORT).show();
                    })
                    .setNeutralButton("Cancel", null)
                    .show();
            return true;
        });
    }


//    Edit function
private void showEditDialog(int position) {
    Contact contact = contacts.get(position);
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle("Edit Contact");

    View view = LayoutInflater.from(context).inflate(R.layout.contact_dialog, null);
    builder.setView(view);

    EditText editFirstName = view.findViewById(R.id.contact_firstname);
    EditText editLastName = view.findViewById(R.id.contact_lastname);
    EditText editPhone = view.findViewById(R.id.contact_phone);

    String[] nameParts = contact.getName().split(" ");
    editFirstName.setText(nameParts.length > 0 ? nameParts[0] : "");
    editLastName.setText(nameParts.length > 1 ? nameParts[1] : "");
    editPhone.setText(contact.getPhone());

    builder.setPositiveButton("Save", (dialog, which) -> {
        String fullName = editFirstName.getText().toString().trim() + " " + editLastName.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();

        if (!fullName.trim().isEmpty() && !phone.trim().isEmpty()) {
            contact.setName(fullName);
            contact.setPhone(phone);
            notifyItemChanged(position);
            Snackbar.make(recyclerView, "Contact updated", Snackbar.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Please enter a valid name and valid phone number", Toast.LENGTH_SHORT).show();
        }
    });

    builder.setNegativeButton("Cancel", null);
    builder.show();
}

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ContactHolder extends RecyclerView.ViewHolder {
        TextView contactName;
        TextView contactPhone;

        public ContactHolder(View itemView) {
            super(itemView);
            contactName = (TextView) itemView.findViewById(android.R.id.text1);
            contactPhone = (TextView) itemView.findViewById(android.R.id.text2);
        }
    }
}
