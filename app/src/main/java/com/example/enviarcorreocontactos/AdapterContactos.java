package com.example.enviarcorreocontactos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterContactos extends RecyclerView.Adapter<AdapterContactos.ContactoViewHolder> implements PopupMenu.OnMenuItemClickListener {

    List<Contacto> contactos;
    String email = "";
    Context context;
    View v;

    public AdapterContactos(List<Contacto> contactos, Context context) {
        this.contactos = contactos;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ContactoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactoViewHolder holder, int position) {
        final Contacto contacto = contactos.get(position);
        holder.tvNombre.setText(contacto.getNombre());
        holder.tvNumero.setText(contacto.getNumero());
        holder.tvEmail.setText(contacto.getEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = contacto.getEmail();
                showPopup(holder.itemView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_correo);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.enviar:
                sendMail(email);
                return true;
            default:
                return true;
        }
    }

    private void sendMail(String email) {

        String[] TO = {email};
        String[] CC = {""};

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto: "));

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Asunto");

        String title = "Mandar este mail con...";

        Intent chooser = Intent.createChooser(emailIntent, title);
        if (emailIntent.resolveActivity(context.getPackageManager()) != null){
            context.startActivity(chooser);
        }

    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNombre, tvNumero, tvEmail;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvNumero = itemView.findViewById(R.id.tvNumero);
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }
    }
}

