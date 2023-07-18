package demorfid.zebra.conti_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ScanOrder extends AppCompatActivity {

    private EditText eOrderNr;

    private Button eLogout;
    private Button eNext;


    //Hardgecodete Personalnummer 1234 zum Login
    private final String Username = "1234";

    boolean isValid = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_order);

        eLogout = findViewById(R.id.btnLogout);
        eNext = findViewById(R.id.btnNext);
        eOrderNr = findViewById(R.id.etOrderNr);

        eNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputOrderNr = eOrderNr.getText().toString();

                if(inputOrderNr.isEmpty() || inputOrderNr.isEmpty())
                {
                    Toast.makeText(ScanOrder.this, "Füge eine Auftragsnummer ein", Toast.LENGTH_SHORT).show();
                } else{

                    // Dieser Abschnitt ist für die Validierung einer falschen Eingabe
                    isValid = validate(inputOrderNr);

                    if(!isValid){

                        Toast.makeText(ScanOrder.this, "Ungültige Auftragsnummer", Toast.LENGTH_SHORT).show();

                        // Dieser Abschnitt ist für die Eingabe eines richtigen Passwortes
                    }else{
                        Toast.makeText(ScanOrder.this, "Auftrag erfolgreich bestätigt", Toast.LENGTH_SHORT).show();

                        // Neue Aktivität hinzufügen
                        Intent intent = new Intent(ScanOrder.this, Reading.class);
                        startActivity(intent);
                    }
                }
            }
        });

        eLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        // Neue Aktivität hinzufügen
                        Intent intent = new Intent(ScanOrder.this, Login.class);
                        startActivity(intent);
                    }
        });
    }

    private boolean validate(String name){

        if(name.equals(Username)){
            return true;
        }

        return false;
    }
}