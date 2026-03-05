package com.aby.mvvm_app.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.aby.mvvm_app.R;
import com.aby.mvvm_app.model.Client;
import com.aby.mvvm_app.viewmodel.ClientViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ClientViewModel vm;
    private ArrayAdapter<Client> adapter;

    private EditText etNom, etVille;
    private Button btnAdd, btnUpdate;
    private ListView lv;

    private long selectedId = -1;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        // liaison XML
        etNom = findViewById(R.id.etNom);
        etVille = findViewById(R.id.etVille);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        lv = findViewById(R.id.lvClients);

        // ViewModel
        vm = new ViewModelProvider(this).get(ClientViewModel.class);

        // adapter ListView
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>()
        );

        lv.setAdapter(adapter);

        // observer LiveData
        vm.getClients().observe(this, data -> {
            adapter.clear();
            if (data != null) adapter.addAll(data);
            adapter.notifyDataSetChanged();
        });

        // -------- AJOUTER --------
        btnAdd.setOnClickListener(v -> {

            String nom = etNom.getText().toString().trim();
            String ville = etVille.getText().toString().trim();

            if (nom.isEmpty() || ville.isEmpty()) {
                Toast.makeText(this, "Champs vides !", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean ok = vm.addClient(nom, ville);

            if (ok) {
                Toast.makeText(this, "Client ajouté", Toast.LENGTH_SHORT).show();
                etNom.setText("");
                etVille.setText("");
            } else {
                Toast.makeText(this, "Insertion échouée", Toast.LENGTH_SHORT).show();
            }
        });

        // -------- CLICK LISTVIEW → REMPLIR EDITTEXT --------
        lv.setOnItemClickListener((parent, view, position, id) -> {

            Client c = adapter.getItem(position);

            if (c != null) {
                selectedId = c.id;

                etNom.setText(c.nom);
                etVille.setText(c.ville);
            }
        });

        // -------- MODIFIER --------
        btnUpdate.setOnClickListener(v -> {

            if (selectedId == -1) {
                Toast.makeText(this, "Sélectionnez un client", Toast.LENGTH_SHORT).show();
                return;
            }

            String nom = etNom.getText().toString().trim();
            String ville = etVille.getText().toString().trim();

            if (nom.isEmpty() || ville.isEmpty()) {
                Toast.makeText(this, "Champs vides !", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean ok = vm.updateClient(selectedId, nom, ville);

            if (ok) {
                Toast.makeText(this, "Client modifié", Toast.LENGTH_SHORT).show();

                etNom.setText("");
                etVille.setText("");

                selectedId = -1;
            } else {
                Toast.makeText(this, "Modification échouée", Toast.LENGTH_SHORT).show();
            }
        });

        // -------- SUPPRIMER (LONG CLICK) --------
        lv.setOnItemLongClickListener((parent, view, position, id) -> {

            Client c = adapter.getItem(position);

            if (c == null) {
                Toast.makeText(this, "Client introuvable", Toast.LENGTH_SHORT).show();
                return true;
            }

            boolean ok = vm.deleteClient(c.id);

            if (ok) {
                Toast.makeText(this, "Client supprimé", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Suppression échouée", Toast.LENGTH_SHORT).show();
            }

            return true;
        });

    }
}