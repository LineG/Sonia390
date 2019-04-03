package com.fsck.k9.fragment;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fsck.k9.R;
import com.fsck.k9.activity.ContactList;
import com.fsck.k9.firebasedb.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends ArrayAdapter<Contact> {

   
    private Context mContext;
    private List<Contact> contactList;


    public ContactListAdapter(@NonNull Context context, ArrayList<Contact> list){
        super(context, 0 , list);
        mContext = context;
        contactList = list;
    }


    @NonNull
    @Override
    public View getView(@NonNull int position, View convertView, @NonNull ViewGroup parent) {

        ContactHolder holder = new ContactHolder();

        if (convertView == null){

            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_contact_list_view,
                    null);

            holder.contactName = convertView.findViewById(R.id.contact_list_name);
            holder.contactEmail = convertView.findViewById(R.id.contact_list_email);

        }

        Contact contact = getItem(position);
        String name = contact.getName()+" "+contact.getLastName();
        holder.contactName.setText(name);
        holder.contactEmail.setText(contact.getEmail());

        return convertView;

    }


}
