package com.aby.mvvm_app.viewmodel;


import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aby.mvvm_app.data.ClientDbHelper;
import com.aby.mvvm_app.model.Client;

import java.util.List;




// ViewModel : joue le rôle d'intermédiaire entre la Vue (Activity) et la Base (SQLite)
public class ClientViewModel extends AndroidViewModel {

    // Référence vers la base SQLite (ClientDbHelper)
    private ClientDbHelper db;

    // LiveData : liste observable de clients
    // Toute modification mettra à jour automatiquement la Vue.
    private MutableLiveData<List<Client>> clients = new MutableLiveData<>();


    // Constructeur : reçoit l'application
    // On initialise la base SQLite ici + on charge les données au démarrage
    public ClientViewModel(Application app) {
        super(app);
        db = new ClientDbHelper(app);  // accès BD
        loadClients();                 // charge liste depuis SQLite
    }

    // Permet à l'Activity d'observer la liste des clients
    public LiveData<List<Client>> getClients() {
        return clients;
    }

    // Charge la liste depuis SQLite et met à jour le LiveData
    // Automatiquement, l'écran sera mis à jour.
    public void loadClients() {
        clients.setValue(db.getAllClients());
    }

    // Ajouter un client dans SQLite
    public boolean addClient(String nom, String ville) {

        // insertClient retourne l'id inséré ou -1 si erreur
        long id = db.insertClient(nom, ville);

        if (id != -1) {
            // si insertion ok → nous rechargeons la liste,
            // ce qui va mettre à jour la Vue automatiquement.
            loadClients();
            return true;
        }

        return false; // insertion échouée
    }

    // Supprimer un client par id
    public boolean deleteClient(long id) {

        // deleteClient retourne true si suppression réussie
        boolean ok = db.deleteClient(id);

        if (ok) {
            // On recharge la liste après suppression
            loadClients();
        }

        return ok;
    }
    public boolean updateClient(long id, String nom, String ville) {
        boolean ok = db.updateClient(id, nom, ville);
        if (ok) loadClients(); // recharge la liste
        return ok;
    }
}