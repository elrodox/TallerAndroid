package conceptosejemplo.com.conceptosejemplo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import conceptosejemplo.com.conceptosejemplo.R;
import conceptosejemplo.com.conceptosejemplo.state.FailedConnectionState;
import conceptosejemplo.com.conceptosejemplo.state.SuccessConnectionState;

public class UserConnection extends AppCompatActivity {

    private boolean connectionResult = false;
    private LinearLayout connectionInfoContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection_state);

        connectionInfoContainer = (LinearLayout)findViewById(R.id.connection_info_container);

        SuccessConnectionState successConnectionState = new SuccessConnectionState(getApplicationContext());
        FailedConnectionState failedConnectionState = new FailedConnectionState(getApplicationContext());

        try{
            connectionResult = getIntent().getExtras().getBoolean("connectionBoolean");
        }catch (NullPointerException nPE){
            nPE.getMessage();
        }

        if(connectionResult){
            connectionInfoContainer.addView(successConnectionState.getView());
        }else {
            connectionInfoContainer.addView(failedConnectionState.getView());
        }
    }

}
