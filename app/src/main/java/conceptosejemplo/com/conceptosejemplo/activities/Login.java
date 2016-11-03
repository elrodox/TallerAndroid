package conceptosejemplo.com.conceptosejemplo.activities;

/**
 * Created by paulo on 02/11/2016.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import conceptosejemplo.com.conceptosejemplo.R;

import static conceptosejemplo.com.conceptosejemplo.R.layout.activity_login;


public class Login extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login);
        Button buttonIngresar;
        buttonIngresar = (Button) findViewById(R.id.buttonIngresar);
        buttonIngresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String usuario = ((EditText)findViewById(R.id.txtUser)).getText().toString();
                String password = ((EditText)findViewById(R.id.txtPass)).getText().toString();
                if (usuario.equals("admin")&& password.equals("1234")){
                    Intent nuevoform = new Intent(Login.this,Admin.class);
                    startActivity(nuevoform);

                }
                else{
                    if (usuario.equals("usuario")&& password.equals("1234")){
                        Intent nuevoform = new Intent(Login.this,MainActivity.class);
                        startActivity(nuevoform);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Usuario Incorrecto o Contraseña",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
