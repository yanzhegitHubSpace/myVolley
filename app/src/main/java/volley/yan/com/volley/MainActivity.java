package volley.yan.com.volley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import volley.yan.com.volley.http.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Volley volley = new Volley();
        volley.login();
    }
}
