package macbeth.backyardweather.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface FirebaseLoginListener {

    public void loginSuccess();
    public void loginFailed();
    public void logoffComplete();
}
