package com.google.firebase.quickstart.database;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.quickstart.database.models.Alarm;
import com.google.firebase.quickstart.database.models.Entry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.PointValue;

public class EntryListActivity extends DrawerActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private EntryAdapter mAdapter;
    private FloatingActionButton fab;
    private Context context;
    private FloatingActionButton fabChart;
    private FloatingActionButton fabMail;
    private String fileName= "data.csv";
    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_alarm_list__);

        LinearLayout contentFrameLayout = (LinearLayout) findViewById(R.id.content); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_entry_list__, contentFrameLayout);

        context = this;
        super.setDrawer(false);

        setTitle(R.string.mesures);

        mRecyclerView = (RecyclerView) findViewById(R.id.all_entry_recyclerview);

        fab = findViewById(R.id.fab_new_entry);
        fabChart = findViewById(R.id.fab_chart);
        fabMail = findViewById(R.id.fab_mail);


        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EntryActivity.class);
                intent.putExtra(context.getString(R.string.ENTRY_EXTRA_MESSAGE), 12548964);
                context.startActivity(intent);
            }
        });

        fabChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChartActivity.class);
                context.startActivity(intent);
            }
        });

        fabMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //firebaseUser.
                 String columnString =   "\"Créneau\",\"Taux Glycemie\",\"Pression Arterielle\",\"Acetone\",\"Date\"\n";
                List<Entry> entries = DataSupport.findAll(Entry.class);
                int size = entries.size();
                String dataString = "";

                for(int i=0; i<size;i++)
                {
                    entries.get(i).getTauxGlycemie();
                    dataString += "\"" + entries.get(i).getCreneau()+ "\",\"" + entries.get(i).getTauxGlycemie()+ "\",\"" + entries.get(i).getPressionArterielle() + "\",\"" + entries.get(i).getAcetone()
                            + "\",\"" + entries.get(i).getTime() + "\"";
                }


                String combinedString = columnString + "\n" + dataString;


                if (askPermissionAndWriteFile()){
                     writeFile(combinedString);
                    File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);
                    //Uri contentUri = FileProvider.getUriForFile(context, "com.mydomain.fileprovider", filelocation);
                    Uri path = Uri.fromFile(filelocation);
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
// set the type to 'email'
                    emailIntent .setType("vnd.android.cursor.dir/email");
// the attachment
                    emailIntent .putExtra(Intent.EXTRA_STREAM, path);

                    emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
// the mail subject
                    emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Liste des mesures");
                    startActivity(Intent.createChooser(emailIntent , "Envoie email..."));
                }

        /*    Uri u1  =   null;
            u1  =   Uri.fromFile(file);

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Person Details");
sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
sendIntent.setType("text/html");
            startActivity(sendIntent);*/




            }
        });

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        List<Entry> entryList = DataSupport.findAll(Entry.class);
        mAdapter = new EntryAdapter(entryList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // fetch updated data
        mAdapter = new EntryAdapter(DataSupport.findAll(Entry.class), this);
        mRecyclerView.setAdapter(mAdapter);
    }



    private boolean askPermissionAndWriteFile() {
        boolean canWrite = this.askPermission(REQUEST_ID_WRITE_PERMISSION,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //
        if (canWrite) {
            return true;
        }
        return false;
    }

    private void askPermissionAndReadFile() {
        boolean canRead = this.askPermission(REQUEST_ID_READ_PERMISSION,
                android.Manifest.permission.READ_EXTERNAL_STORAGE);
        //
        if (canRead) {
            this.readFile();
        }
    }

    // With Android Level >= 23, you have to ask the user
    // for permission with device (For example read/write data on the device).
    private boolean askPermission(int requestId, String permissionName) {
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            // Check if we have permission
            int permission = ActivityCompat.checkSelfPermission(this, permissionName);


            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return false;
            }
        }
        return true;
    }


    // When you have the request results
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        // Note: If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0) {
            switch (requestCode) {
                case REQUEST_ID_READ_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        readFile();
                    }
                }
                case REQUEST_ID_WRITE_PERMISSION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //writeFile();
                    }
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Permission Cancelled!", Toast.LENGTH_SHORT).show();
        }
    }


    private void writeFile(String data) {

        File extStore = Environment.getExternalStorageDirectory();
        // ==> /storage/emulated/0/note.txt
        String path = extStore.getAbsolutePath();
        Log.i("ExternalStorageDemo", "Save to: " + path);


        try {
            File myFile = new File(path,fileName);
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);
            myOutWriter.close();
            fOut.close();

            Toast.makeText(getApplicationContext(), fileName + " saved", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFile() {

        File extStore = Environment.getExternalStorageDirectory();
        // ==> /storage/emulated/0/note.txt
        String path = extStore.getAbsolutePath() + "/" + fileName;
        Log.i("ExternalStorageDemo", "Read file: " + path);

        String s = "";
        String fileContent = "";
        try {
            File myFile = new File(path);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(
                    new InputStreamReader(fIn));

            while ((s = myReader.readLine()) != null) {
                fileContent += s + "\n";
            }
            myReader.close();

            //this.textView.setText(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), fileContent, Toast.LENGTH_LONG).show();
    }

    private void listExternalStorages() {
        StringBuilder sb = new StringBuilder();

        sb.append("Data Directory: ").append("\n - ")
                .append(Environment.getDataDirectory().toString()).append("\n");

        sb.append("Download Cache Directory: ").append("\n - ")
                .append(Environment.getDownloadCacheDirectory().toString()).append("\n");

        sb.append("External Storage State: ").append("\n - ")
                .append(Environment.getExternalStorageState().toString()).append("\n");

        sb.append("External Storage Directory: ").append("\n - ")
                .append(Environment.getExternalStorageDirectory().toString()).append("\n");

        sb.append("Is External Storage Emulated?: ").append("\n - ")
                .append(Environment.isExternalStorageEmulated()).append("\n");

        sb.append("Is External Storage Removable?: ").append("\n - ")
                .append(Environment.isExternalStorageRemovable()).append("\n");

        sb.append("External Storage Public Directory (Music): ").append("\n - ")
                .append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).toString()).append("\n");

        sb.append("Download Cache Directory: ").append("\n - ")
                .append(Environment.getDownloadCacheDirectory().toString()).append("\n");

        sb.append("Root Directory: ").append("\n - ")
                .append(Environment.getRootDirectory().toString()).append("\n");

        Log.i("ExternalStorageDemo", sb.toString());
        //this.textView.setText(sb.toString());
    }
}
