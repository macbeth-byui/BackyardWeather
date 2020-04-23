package macbeth.backyardweather.model;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper Class for working with Firebase Login (Singleton Class)
 */
public class FirebaseLoginHelper {

    private static FirebaseLoginHelper instance = null;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private List<FirebaseLoginListener> listeners;

    private FirebaseLoginHelper() {
        user = null;
        mAuth = FirebaseAuth.getInstance();
        listeners = new ArrayList<>();
    }

    public static FirebaseLoginHelper getInstance() {
        if (instance == null) {
            instance = new FirebaseLoginHelper();
        }
        return instance;
    }

    /**
     * Create a new email/password account in Firebase and login in automatically.  If we are
     * already logged in, then no action is taken.
     * @param email
     * @param password
     */
    public void createAccount(Activity activity, String email, String password) {
        if (user != null) {
            notifyLoginSuccess();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, (fbTask) -> {
                        if (fbTask.isSuccessful()) {
                            user = mAuth.getCurrentUser();
                            notifyLoginSuccess();
                        } else {
                            user = null;
                            notifyLoginFailed();
                        }
                    });
        }
    }

    /**
     * Login via email and password.  If they are already logged in then no action is taken.
     * @param email
     * @param password
     */
    public void login(Activity activity, String email, String password) {
        if (user != null) {
            notifyLoginSuccess();
        } else {
            // Sign into Firebase and notify client with the results
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, (fbTask) -> {
                if (fbTask.isSuccessful()) {
                    user = mAuth.getCurrentUser();
                    notifyLoginSuccess();
                } else {
                    user = null;
                    notifyLoginFailed();
                }
            });
        }
    }

    /** Log out of Firebase and notify the client of the results.  Will automatically deregister
     * all firebase requests.
     */
    public void logoff() {
        mAuth.signOut();
        user = null;
        notifyLogoffComplete();
    }

    /**
     * Get the user email for the logged in user
     * @return
     */
    public String getUserEmail() {
        if (user != null) {
            return user.getEmail();
        }
        return null;
    }

    /**
     * Get the user id (UID) for the logged in user.  This is used for constructing
     * database path's.
     * @return
     */
    public String getUserID() {
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

    /**
     * Determine if someone is logged in.
     * @return
     */
    public boolean isLoggedIn() {
        return user != null;
    }

    public void registerListener(FirebaseLoginListener listener) {
        listeners.add(listener);
        if (isLoggedIn()) {
            listener.loginSuccess();
        }
    }

    public void unregisterListener(FirebaseLoginListener listener) {
        listeners.remove(listener);
    }

    private void notifyLoginSuccess() {
        for (FirebaseLoginListener listener : listeners) {
            listener.loginSuccess();
        }
    }

    private void notifyLoginFailed() {
        for (FirebaseLoginListener listener : listeners) {
            listener.loginFailed();
        }
    }

    private void notifyLogoffComplete() {
        for (FirebaseLoginListener listener : listeners) {
            listener.logoffComplete();
        }
    }

}

