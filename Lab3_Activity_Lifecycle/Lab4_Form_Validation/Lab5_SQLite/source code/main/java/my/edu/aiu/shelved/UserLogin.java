package my.edu.aiu.shelved;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserLogin extends AppCompatActivity {

    private EditText editUsername;
    private EditText editPassword;
    private Button btnLogin;
    private Button btnClear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnClear = findViewById(R.id.btnClear);

        btnLogin.setOnClickListener(UserLogin.this);
        btnClear.setOnClickListener(UserLogin.this);
    }
    @Override
    public void onClick(View v){
        if(v.getId() == R.id.btnLogin){
            editUsername.getText();
            editPassword.getText();

            String sampleLogin = "admin@aiu.edu.my";
            String samplePassword = "admin1234";

            if(isValidEmail(editUsername.getText().toString()) == false){
                editUSername.setError("Invalid Email Address";
                Log.i("Message", "Invalid Email Address");
                return;
            }

            if(editUsername.getText().toString().equals(sampleLogin) && editPassword.getText().toString().equals(samplePassword)){
                Log.i("Message", "Login Successfully");
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent()
            }
        }
    }
    //E-mail checking validation using regural expression
    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z8-9+_.-]+@aiu.edu.my$";
        return email != null && email.matches(emailRegex);
    }
}