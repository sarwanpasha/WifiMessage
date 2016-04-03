package upm.Upload_User_Detail;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import upm.Upload_User_Detail.Registration.DatabaseHelperClass;
//import com.example.root.com.example.root.Registration.Login;
//import com.example.root.giveblood.R;
//import com.example.root.giveblood.Select_Donor_Or_Receptor_3;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import upm.Registration.DatabaseHelperClass;
import upm.Registration.Login;
import upm.userinterfaces.MainActivity;
import upm.userinterfaces.R;

public class Upload_Detail extends Activity implements AdapterView.OnItemSelectedListener {

    EditText Donor_name,blood_group,phone_Number,city,Home_address;
    Button Upload_Image,Submit_Data;
    TextView Uploadstatus;

    private static int RESULT_LOAD_CAMERA_IMAGE = 21;
    private static int RESULT_LOAD_GALLERY_IMAGE = 11;

    private File cameraImageFile;
    private String mCurrentPhotoPath;
    public static int ImageStatus=0;


    public String get_Donor_name,get_blood_group,get_phone_Number,get_city,get_Home_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__detail);

        checkLoginDetail();

        Parse.initialize(this, "Iiav8rG2tGa1ExUQZiuYEC0c3I54v1d29RSS1tZS", "W5GaHHTdR18tEK2SGmDgFWcdPOD1savHG4zwS4e1");

        Donor_name = (EditText) findViewById(R.id.et_Name);
        // blood_group = (EditText) findViewById(R.id.et_Blood_Group);
        phone_Number = (EditText) findViewById(R.id.et_Phone_Number);
        city = (EditText) findViewById(R.id.et_City);
        Home_address = (EditText) findViewById(R.id.et_Address);

        Upload_Image = (Button) findViewById(R.id.btn_Upload);
        Submit_Data = (Button) findViewById(R.id.btn_Submit);

        Uploadstatus = (TextView) findViewById(R.id.tvGet_Upload_Status);

        /////Spinner////////
        Spinner spinner = (Spinner) findViewById(R.id.et_Blood_Group);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("A+");
        categories.add("A-");
        categories.add("B+");
        categories.add("B-");
        categories.add("AB+");
        categories.add("AB-");
        categories.add("O+");
        categories.add("O-");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        /////Spinner///////

        Upload_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChooseFromForImage();

            }
        });

        Submit_Data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_Donor_name = Donor_name.getText().toString();
                //  get_blood_group = blood_group.getText().toString();
                get_phone_Number = phone_Number.getText().toString();
                get_city = city.getText().toString();
                get_Home_address = Home_address.getText().toString();

                if (get_Donor_name.equals("") || get_blood_group.equals("") || get_phone_Number.equals("") ||
                        get_city.equals("")|| get_Home_address.equals("")) {
                    //  notificationValidation();
                    showMessage();
                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                    if (TextUtils.isEmpty(Donor_name.getText().toString())) {
                        Donor_name.setError("Please Fill Name Field");
                        return;
                    } else if (TextUtils.isEmpty(blood_group.getText().toString())) {
                        blood_group.setError("Please Fill Blood group Field");
                        return;
                    } else if (TextUtils.isEmpty(phone_Number.getText().toString())) {
                        phone_Number.setError("Please Fill Phone Number Field");
                        return;
                    } else if (TextUtils.isEmpty(city.getText().toString())) {
                        city.setError("Please Fill CITY Field");
                        return;
                    }else if (TextUtils.isEmpty(Home_address.getText().toString())) {
                        Home_address.setError("Please Fill ADDRESS Field");
                        return;
                    }
                } else {
                    UploadingDataToParse();
                }
            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        get_blood_group = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //  Toast.makeText(parent.getContext(), "Selected: " + get_blood_group, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    public void checkLoginDetail(){
        boolean isChecked;
        SharedPreferences settings1 = getSharedPreferences("PREFS_NAME", 0);
        isChecked = settings1.getBoolean("isChecked", false);
        if (!isChecked) {

            DatabaseHelperClass setUserName=new DatabaseHelperClass();
            String user=null;
            user=setUserName.getUsername();
            if(user==null){
                startActivity(new Intent(Upload_Detail.this, Login.class));
                finish();
            }

        }


    }
    protected void onDestroy()
    {
        super.onDestroy();
        //  updateUserStatus(false);
        DatabaseHelperClass setUserName=new DatabaseHelperClass();
        setUserName.setUsername(null);
    }
    private void dialogChooseFromForImage(){

        final CharSequence[] items={"From Gallery","From Camera"};

        AlertDialog.Builder chooseDialog =new AlertDialog.Builder(this);
        chooseDialog.setTitle("Pick your choice").setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (items[which].equals("From Gallery")) {
                    try {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, RESULT_LOAD_GALLERY_IMAGE);
                    } catch (Exception ex) {
                        Log.d("Error Pasha", ex.getMessage());
                        Toast.makeText(getBaseContext(), "Error1:" + ex, Toast.LENGTH_LONG).show();
                    }

                } else {

                    try {

                        File photoFile = createImageFile();
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        startActivityForResult(cameraIntent, RESULT_LOAD_CAMERA_IMAGE);

                    } catch (IOException e) {
                        Log.d("Error Pasha", e.getMessage());
                        e.printStackTrace();
                        Toast.makeText(getBaseContext(), "Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        chooseDialog.show();
    }
    private File createImageFile () throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";

        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File folder = new File(storageDir.getAbsolutePath() + "/PlayIOFolder");

        if (!folder.exists()) {
            folder.mkdir();
        }

        cameraImageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                folder      /* directory */
        );
        Toast.makeText(getApplicationContext(),"File Name: " +imageFileName+".jpg" ,Toast.LENGTH_SHORT).show();
        return cameraImageFile;
    }
    private byte[] readInFileforImage(String path) throws IOException {

        byte[] data = null;
        File file = new File(path);
        InputStream input_stream = new BufferedInputStream(new FileInputStream(file));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        data = new byte[16384]; // 16K
        int bytes_read;

        while ((bytes_read = input_stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytes_read);
        }

        input_stream.close();
        return buffer.toByteArray();
    }
    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == RESULT_LOAD_GALLERY_IMAGE && null != data) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mCurrentPhotoPath = cursor.getString(columnIndex);
                ImageStatus = 1;
                cursor.close();

            } else if (requestCode == RESULT_LOAD_CAMERA_IMAGE) {
                ImageStatus = 1;
                mCurrentPhotoPath = cameraImageFile.getAbsolutePath();
            }

            if (ImageStatus == 1) {
                File image = new File(mCurrentPhotoPath);
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
                // imgPhoto.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "Image Selected successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void UploadingDataToParse(){
        final ProgressDialog progressDialog = new ProgressDialog(Upload_Detail.this,R.style.AppBaseTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait while Data is uploading");
        progressDialog.show();
        Uploadstatus.setText("Please Wait while Data is uploading");
        Toast.makeText(getApplicationContext(),"Uploading"  ,Toast.LENGTH_SHORT).show();

        byte[] image = null;
        try {
            image = readInFileforImage(mCurrentPhotoPath);
        } catch(Exception e) {
            e.printStackTrace();
            //   Log.d("Error Pasha", e.getMessage());
            // Toast.makeText(getApplicationContext(),"Error For Image: "+e.getMessage()  ,Toast.LENGTH_SHORT).show();
        }

        // Create a New Class called "BloodBank" in Parse
        ParseObject UploadValue = new ParseObject("BloodBank");

        if(ImageStatus==1) {
            // Create the ParseFile
            ParseFile  Imagefile = new ParseFile("picturePath", image);
            // Upload the image into Parse Cloud
            Imagefile.saveInBackground();
            // Create a column named "Image" and set the string
            UploadValue.put("Image", "picturePath");
            // Create a column named "ImageFile" and insert the image
            UploadValue.put("ImageFile", Imagefile);
        }
        if(get_Donor_name!=null){
            UploadValue.put("Donor_Name", get_Donor_name);
        }
        if(get_blood_group!=null){
            UploadValue.put("Blood_Group", get_blood_group);
        }
        if(get_phone_Number!=null){
            UploadValue.put("Phone_Number", get_phone_Number);
        }
        if(get_city!=null){
            UploadValue.put("City", get_city);
        }
        if(get_Home_address!=null){
            UploadValue.put("Address", get_Home_address);
        }
        // Create the class and the columns
        UploadValue.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Uploadstatus.setText("Data Is Uploaded successfully");
                    ImageStatus=0;
                    Toast.makeText(getBaseContext(), "Done!", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();

                    notification();
                } else {
                    Uploadstatus.setText( "Error saving: " + e.getMessage());
                    Toast.makeText(getApplicationContext(),
                            "Error saving: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void notification() {
        //  mp.setLooping(true);
        //mp.start();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Acknowledgement"); // Set Alert dialog title
        // here
        alert.setMessage("Data Uploaded Successfully,");

        alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent i = new Intent(Upload_Detail.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        alert.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();

    }
    public void showMessage()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Error");
        builder.setMessage("Please Fill All fields");
        builder.show();
    }
    public void onBackPressed() {
        Intent i = new Intent(Upload_Detail.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
