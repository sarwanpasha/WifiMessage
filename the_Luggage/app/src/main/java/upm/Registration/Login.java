package upm.Registration;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.root.Upload_User_Detail.Upload_Detail;
//import com.example.root.giveblood.R;
//import com.example.root.giveblood.Select_Donor_Or_Receptor_3;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import upm.userinterfaces.MainActivity;
import upm.userinterfaces.R;

public class Login extends CustomActivity
{

    /** The username edittext. */
    private EditText user;

    /** The password edittext. */
    private EditText pwd;

    /* (non-Javadoc)
     * @see com.chatt.custom.CustomActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTouchNClick(R.id.btnLogin);
        setTouchNClick(R.id.btnReg);

        user = (EditText) findViewById(R.id.user);
        pwd = (EditText) findViewById(R.id.pwd);

        CheckBox keeplog = (CheckBox) findViewById(R.id.checkBox1);
        //  boolean isChecked = false;

        keeplog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
                SharedPreferences.Editor editor = settings.edit();
                //  editor.putBoolean("isChecked", isChecked);
                editor.putBoolean("isChecked", true);
                editor.commit();
            }
        });
    }

    /* (non-Javadoc)
     * @see com.chatt.custom.CustomActivity#onClick(android.view.View)
     */
    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        final DatabaseHelperClass KeepDataRecord =new DatabaseHelperClass();


        if (v.getId() == R.id.btnReg)
        {
            startActivityForResult(new Intent(this, Register.class), 10);
        }
        else
        {
            SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
            SharedPreferences.Editor editor = settings.edit();
            //  editor.putBoolean("isChecked", isChecked);
            editor.putBoolean("isChecked", true);
            editor.commit();

            // Parse.initialize(this, "Tz3LAjkQdYlIg7ig700MQHPTSPQYAIykHfY8NhmO", "WtIHCldN6ZO23VAKdBgrPEvqM0UfGqH7wKZUsGhr");
            Parse.initialize(this, "Iiav8rG2tGa1ExUQZiuYEC0c3I54v1d29RSS1tZS", "W5GaHHTdR18tEK2SGmDgFWcdPOD1savHG4zwS4e1");


            String u = user.getText().toString();
            String p = pwd.getText().toString();
            if (u.length() == 0 || p.length() == 0)
            {
                Utils.showDialog(this, R.string.err_fields_empty);
                return;
            }
            final ProgressDialog dia = ProgressDialog.show(this, null,
                    getString(R.string.alert_wait));
            ParseUser.logInInBackground(u, p, new LogInCallback() {

                @Override
                public void done(ParseUser pu, ParseException e) {
                    dia.dismiss();
                    if (pu != null) {
                        KeepDataRecord.setUsername(pu.toString());
                        // Toast.makeText(getBaseContext(), "Username = " + pu, Toast.LENGTH_LONG).show();
                        Toast.makeText(getBaseContext(), "Login Successfull", Toast.LENGTH_LONG).show();
//                        UserList.user = pu;
                        //  startActivity(new Intent(Login.this, Upload_Detail.class));
                        startActivity(new Intent(Login.this, MainActivity.class));

                        finish();
                    } else {
                        Utils.showDialog(
                                Login.this,
                                getString(R.string.err_login) + " "
                                        + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK)
            finish();

    }
}
