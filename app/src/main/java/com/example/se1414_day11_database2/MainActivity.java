package com.example.se1414_day11_database2;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se1414_day11_database2.daos.StudentAdapter;
import com.example.se1414_day11_database2.daos.StudentDAO;
import com.example.se1414_day11_database2.dtos.StudentDTO;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se1414_day11_database2.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView txtTitle;
    private ListView listViewStudent;
    private StudentAdapter adapter;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private static final int MY_WRITE_EXTERNAL_STORAGE = 6789;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //Hoi ung dung xin quyen chua
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //Co quyen roi
        } else {
            //Chua co quyen
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void loadDataFromRAW(MenuItem item) {
        txtTitle = findViewById(R.id.txtID);
        listViewStudent = findViewById(R.id.listViewStudent);
        adapter = new StudentAdapter();
        try {
            StudentDAO dao = new StudentDAO();
            InputStream inputStream = getResources().openRawResource(R.raw.data);
            List<StudentDTO> result = dao.loadFromRAW(inputStream);
            adapter.setListStudent(result);
            listViewStudent.setAdapter(adapter);
            txtTitle.setText("List  student from RAW");
            listViewStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    StudentDTO dto = (StudentDTO) listViewStudent.getItemAtPosition(position);
                    Toast.makeText(MainActivity.this, dto.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToSaveDataFromRawToInternal(MenuItem item) {
        try {
            StudentDAO dao = new StudentDAO();
            InputStream is = getResources().openRawResource(R.raw.data);
            List<StudentDTO> listStudent = dao.loadFromRAW(is);
            FileOutputStream fos = openFileOutput("loclt.txt", MODE_PRIVATE);
            dao.saveToInternal(fos, listStudent);
            Toast.makeText(this, "Save Internal Sucess", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToLoadDataFromInternal(MenuItem item) {
        txtTitle = findViewById(R.id.txtID);
        listViewStudent = findViewById(R.id.listViewStudent);
        adapter = new StudentAdapter();
        try {
            StudentDAO dao = new StudentDAO();
            FileInputStream fis = openFileInput("loclt.txt");
            List<StudentDTO> result = dao.loadFromInternal(fis);
            adapter.setListStudent(result);
            listViewStudent.setAdapter(adapter);
            txtTitle.setText("List  student from Internal");
            listViewStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    StudentDTO dto = (StudentDTO) listViewStudent.getItemAtPosition(position);
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("dto", dto);
                    intent.putExtra("action", "update");
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToCreate(View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("action", "create");
        startActivity(intent);
    }


    public void clickToSaveDataFromInternalToExternal(MenuItem item) {
        try {
            StudentDAO dao = new StudentDAO();
            FileInputStream fis = openFileInput("loclt.txt");
            List<StudentDTO> listStudent = dao.loadFromInternal(fis);
            dao.saveToExternal(listStudent);
            Toast.makeText(this, "Save SD Card Success", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToLoadDataFromExternal(MenuItem item) {
        txtTitle = findViewById(R.id.txtID);
        listViewStudent = findViewById(R.id.listViewStudent);
        adapter = new StudentAdapter();
        try {
            StudentDAO dao = new StudentDAO();
            FileInputStream fis = openFileInput("loclt.txt");
            List<StudentDTO> result = dao.loadFromExternal();
            adapter.setListStudent(result);
            listViewStudent.setAdapter(adapter);
            txtTitle.setText("List  student from SD card");
            listViewStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    StudentDTO dto = (StudentDTO) listViewStudent.getItemAtPosition(position);
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("dto", dto);
                    intent.putExtra("action", "update");
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void clickToRestoreDataFromExternal(MenuItem item) {
        try {
            //Diem denm
            File sdCard = Environment.getExternalStorageDirectory();
            String realPath = sdCard.getAbsolutePath();
            String desDir = realPath + "/MyFiles";
            File directory = new File(desDir);
//            if (!directory.exists()) {
//                directory.mkdir(); // vi tri ko can khoi tao
//            }

            //Diem di
            String dataPath = "/data/data/" + this.getPackageName() + "/files"; //url internal
            File dataDir = new File(dataPath);
            //Lay tat ca cac file;
            //2
            File[] listFile = directory.listFiles();
            if (listFile != null) {
                for (int i = 0; i < listFile.length; i++) {
                    File file = listFile[i];
                    MyUtils utils = new MyUtils();
                    //3
                    utils.copyFile(file.getAbsolutePath(), dataPath + "/" + file.getName());
                }
                Toast.makeText(this, "Restore success", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickToBackupDataToExternal(MenuItem item) {
        try {
            //Diem denm
            File sdCard = Environment.getExternalStorageDirectory();
            String realPath = sdCard.getAbsolutePath();
            String desDir = realPath + "/MyFiles";
            File directory = new File(desDir);
            if (!directory.exists()) {
                directory.mkdir();
            }

            //Diem di
            String dataPath = "/data/data/" + this.getPackageName() + "/files"; //url internal
            File dataDir = new File(dataPath);
            //Lay tat ca cac file;
            File [] listFile = dataDir.listFiles();
            if (listFile != null) {
                for (int i = 0; i < listFile.length; i++) {
                    File file = listFile[i];
                    MyUtils utils = new MyUtils();
                    utils.copyFile(file.getAbsolutePath(), desDir + "/" + file.getName());
                }
                Toast.makeText(this, "Backup success", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}