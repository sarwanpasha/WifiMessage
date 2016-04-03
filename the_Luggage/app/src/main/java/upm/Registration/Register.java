package upm.Registration;

import android.app.ProgressDialog;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//import com.example.root.SQLITE_Local_Database.AndroidSQLiteTutorialActivity;
//import com.example.root.giveblood.R;
//import com.example.root.giveblood.Select_Donor_Or_Receptor_3;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import upm.SQL_Lite_Local_Database.AndroidSQLiteTutorialActivity;
import upm.userinterfaces.MainActivity;
import upm.userinterfaces.R;

public class Register extends CustomActivity
{

    /** The username EditText. */
    private EditText user;

    /** The password EditText. */
    private EditText pwd;

    /** The email EditText. */
    private EditText email,bloodgroup,city;

    /* (non-Javadoc)
     * @see com.chatt.custom.CustomActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTouchNClick(R.id.btnReg);

        user = (EditText) findViewById(R.id.user);
        pwd = (EditText) findViewById(R.id.pwd);
        email = (EditText) findViewById(R.id.email);
        bloodgroup = (EditText) findViewById(R.id.bloodgroup);
        city = (EditText) findViewById(R.id.city);

    }

    /* (non-Javadoc)
     * @see com.chatt.custom.CustomActivity#onClick(android.view.View)
     */
    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        Parse.initialize(this, "Iiav8rG2tGa1ExUQZiuYEC0c3I54v1d29RSS1tZS", "W5GaHHTdR18tEK2SGmDgFWcdPOD1savHG4zwS4e1");

        String u = user.getText().toString();
        String p = pwd.getText().toString();
        String e = email.getText().toString();
        String b = bloodgroup.getText().toString();
        String c = city.getText().toString();

        AndroidSQLiteTutorialActivity local_Database=new AndroidSQLiteTutorialActivity();
        local_Database.name=u;
        local_Database.bloodgroup=b;

        if (u.length() == 0 || p.length() == 0 || e.length() == 0|| b.length() == 0|| c.length() == 0)
        {
            Utils.showDialog(this, R.string.err_fields_empty);
            return;
        }
        final ProgressDialog dia = ProgressDialog.show(this, null,
                getString(R.string.alert_wait));

        final ParseUser pu = new ParseUser();
        pu.setEmail(e);
        pu.setPassword(p);
        pu.setUsername(u);
        pu.signUpInBackground(new SignUpCallback() {

            @Override
            public void done(ParseException e) {
                dia.dismiss();
                if (e == null) {
//                    UserList.user = pu;
                    startActivity(new Intent(Register.this, MainActivity.class));
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Utils.showDialog(
                            Register.this,
                            getString(R.string.err_singup) + " "
                                    + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

    }
}
