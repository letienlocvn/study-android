package com.example.se1414_day11_database2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se1414_day11_database2.daos.StudentDAO;
import com.example.se1414_day11_database2.dtos.StudentDTO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private EditText editId, editName, editMark;
    private String action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        editId = findViewById(R.id.editID);
        editName = findViewById(R.id.editName);
        editMark = findViewById(R.id.editMark);
        Intent intent = getIntent();
        action = intent.getStringExtra("action");
        if (action.equals("update")){
            StudentDTO dto = (StudentDTO) intent.getSerializableExtra("dto");
            editId.setText(dto.getId());
            editName.setText(dto.getName());
            editMark.setText(dto.getMark() + "");
        }
    }

    public void clickToSave(View view) {
        try {
            String id = editId.getText().toString();
            String name = editName.getText().toString();
            Float mark = Float.parseFloat(editMark.getText().toString());
            StudentDAO dao = new StudentDAO();
            //Read file
            FileInputStream fis = openFileInput("loclt.txt");
            //Load file
            List<StudentDTO> listStudent = dao.loadFromInternal(fis);
            if (action.equals("create")) {
                StudentDTO dto = new StudentDTO(id, name, mark);
                listStudent.add(dto);
            } else if(action.equals("update")) {
                for (StudentDTO dto: listStudent) {
                    if (dto.getId().equals(id)){
                        dto.setName(name);
                        dto.setMark(mark);
                        break;
                    }
                }
            }

            //OutputFile
            FileOutputStream fos = openFileOutput("loclt.txt", MODE_PRIVATE);
            dao.saveToInternal(fos, listStudent);
            Toast.makeText(this, "Save internal Success", Toast.LENGTH_SHORT).show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}