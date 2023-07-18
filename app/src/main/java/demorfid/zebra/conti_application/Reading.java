package demorfid.zebra.conti_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Reading extends AppCompatActivity {

    private Button eNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        eNext = findViewById(R.id.btnNext2);

        eNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        // Neue Aktivität hinzufügen
                        Intent intent = new Intent(Reading.this, Report.class);
                        startActivity(intent);
                    }
        });
    }
}