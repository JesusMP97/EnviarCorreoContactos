package com.example.enviarcorreocontactos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    String nombre, telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getData();
        initComponents();
    }

    private void getData() {
        Intent intent = getIntent();
        nombre = intent.getStringExtra("nombre");
        telefono = intent.getStringExtra("telefono");
    }

    private void initComponents() {
        recyclerView = findViewById(R.id.recyclerView);

        GridLayoutManager lim = new GridLayoutManager(this, 2);
        lim.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lim);

        AdapterContactos adapter = new AdapterContactos(getListaContactos(this), this);
        recyclerView.setAdapter(adapter);
    }

    public List<Contacto> getListaContactos(Context context){

        ArrayList<Contacto> contactosList = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        String[] PROJECTION = new String[] {
                ContactsContract.RawContacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Email.DATA,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Photo.CONTACT_ID };
        String order = "CASE WHEN "
                + ContactsContract.Contacts.DISPLAY_NAME
                + " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
                + ContactsContract.Contacts.DISPLAY_NAME
                + ", "
                + ContactsContract.CommonDataKinds.Email.DATA
                +", "
                + ContactsContract.CommonDataKinds.Phone.DATA1
                + " COLLATE NOCASE";
        String filter = ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE '' AND "+ ContactsContract.CommonDataKinds.Phone.NUMBER+ " NOT LIKE ''";
        Cursor cur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, filter, null, order);
        Cursor cur2 = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, filter, null, order);
        if (cur.moveToFirst() && cur2.moveToFirst()) {
            do {
                String name = cur.getString(1);
                String emlAddr = cur.getString(2);
                String tel = cur2.getString(3);
                if (name.contains(nombre) || tel.equals(telefono) ) {
                    Contacto contacto = new Contacto();
                    contacto.setNombre(name);
                    contacto.setEmail(emlAddr);
                    contacto.setNumero(tel);

                    contactosList.add(contacto);
                }
            } while (cur.moveToNext() && cur2.moveToNext());
        }

        cur.close();
        return contactosList;
    }

}
