package demorfid.zebra.conti_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Report extends AppCompatActivity {

    private Button eNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        eNext = findViewById(R.id.btnBack);

        eNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Neue Aktivität hinzufügen
                Intent intent = new Intent(Report.this, Reading.class);
                startActivity(intent);
            }
        });
    }
}