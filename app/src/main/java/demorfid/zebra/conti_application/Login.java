package demorfid.zebra.conti_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zebra.rfid.api3.*;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity implements RfidEventsListener {

    private EditText ePersonalNr;
    private Button eLogin;
    private TextView eAttemptsInfo;

    // Hardgecodete Personalnummer 1234 zum Login
    private final String Username = "1234";

    boolean isValid = false;
    private int counter = 5;

    // URL für die POST-Anfrage an Postman
    private final String POSTMAN_URL = "https://www.postman.com/blue-sunset-388313";

    private RFIDReader rfidReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ePersonalNr = findViewById(R.id.etPersonalNr);
        eLogin = findViewById(R.id.btnOk);
        eAttemptsInfo = findViewById(R.id.tvAttemptsInfo);

        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputPersonalNr = ePersonalNr.getText().toString();

                if (inputPersonalNr.isEmpty()) {
                    Toast.makeText(Login.this, "Füge deine Personalnummer ein", Toast.LENGTH_SHORT).show();
                } else {
                    isValid = validate(inputPersonalNr);

                    if (!isValid) {
                        counter--;
                        Toast.makeText(Login.this, "Ungültige Eingabe", Toast.LENGTH_SHORT).show();
                        eAttemptsInfo.setText("Erlaubte Versuche: " + counter);

                        if (counter == 0) {
                            eLogin.setEnabled(false);
                        }
                    } else {
                        Toast.makeText(Login.this, "Login erfolgreich", Toast.LENGTH_SHORT).show();

                        sendPostRequestToPostman(Username);

                        // Neue Aktivität hinzufügen
                        Intent intent = new Intent(Login.this, ScanOrder.class);
                        startActivity(intent);
                    }
                }
            }
        });

        try {
            // RFIDReader-Instanz erstellen
            rfidReader = new RFIDReader();

            // RFID-Verbindung herstellen
            rfidReader.connect();

            // RFID-Ereignislistener festlegen
            rfidReader.Events.addEventsListener(this);

            // RFID-Reader konfigurieren (je nach den Anforderungen des RFID SDKs)
            // ...

            // RFID-Reader aktivieren
            rfidReader.Events.setAttachTagDataWithReadEvent(false);
            rfidReader.Actions.Inventory.perform();

        } catch (Exception e) {
            e.printStackTrace();
            // Fehlermeldung anzeigen oder protokollieren
        }
    }

//    @Override
    public void eventReadNotify(RfidReadEvents rfidReadEvents) {
        // RFID-Leseereignis behandeln
        // ...

        // Beispielcode zum Abrufen der Tag-Daten aus dem Ereignisobjekt (nur als Beispiel, muss an dein RFID SDK angepasst werden):
//        RFIDTagData[] tagDataArray = rfidReadEvents.getTags();
//        if (tagDataArray != null && tagDataArray.length > 0) {
//            for (RFIDTagData tagData : tagDataArray) {
//                String tagID = tagData.getTagID();
                // Weitere Verarbeitung der Tag-Daten hier durchführen
//            }
//        }
  }


    @Override
    public void eventStatusNotify(RfidStatusEvents rfidStatusEvents) {
        // RFID-Statusereignis behandeln
        // ...

        // Beispielcode aus dem Tutorial zum Verarbeiten des Statusereignisses:
        if (rfidStatusEvents.StatusEventData.getStatusEventType() == STATUS_EVENT_TYPE.DISCONNECTION_EVENT) {
            // RFID-Verbindung getrennt
//        } else if (rfidStatusEvents.StatusEventData.getStatusEventType() == STATUS_EVENT_TYPE.AVAILABILITY_EVENT) {
            // RFID-Verfügbarkeit geändert
        }
    }

    private boolean validate(String name) {
        return name.equals(Username);
    }

    private void sendPostRequestToPostman(String username) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // JSON-Daten für die POST-Anfrage erstellen
                    String jsonBody = "{\"message\": \"Benutzer hat sich angemeldet\", \"username\": \"" + username + "\"}";

                    // OkHttpClient initialisieren
                    OkHttpClient client = new OkHttpClient();

                    // Anfrage erstellen
                    Request request = new Request.Builder()
                            .url(POSTMAN_URL)
                            .post(RequestBody.create(MediaType.parse("application/json"), jsonBody))
                            .build();

                    // Anfrage senden
                    Response response = client.newCall(request).execute();

                    // Antwort verarbeiten
                    if (response.isSuccessful()) {
                        // Erfolgreiche Antwort erhalten
                        String responseBody = response.body().string();
                        // Weitere Verarbeitung der Antwort hier durchführen
                    } else {
                        // Fehlerhafte Antwort erhalten
                        // Fehlerbehandlung hier durchführen
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Fehlermeldung anzeigen oder protokollieren
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // RFID-Verbindung trennen und aufräumen
        try {
            if (rfidReader != null && rfidReader.isConnected()) {
                rfidReader.Actions.Inventory.stop();
                rfidReader.disconnect();
                rfidReader.Events.removeEventsListener(this);
                rfidReader = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Weitere RFID-Ereignislistener-Methoden implementieren, wenn erforderlich
}
