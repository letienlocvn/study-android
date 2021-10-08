package com.example.se1414_day11_database2.daos;

import android.os.Environment;

import com.example.se1414_day11_database2.dtos.StudentDTO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public List<StudentDTO> loadFromRAW(InputStream is) throws Exception {
        List<StudentDTO> result = new ArrayList<>();
        BufferedReader br = null;
        InputStreamReader isr = null;

        try {
            String s = "";
            StudentDTO dto = null;
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            while ((s = br.readLine()) != null) {
                String[] tmp = s.split("-");
                dto = new StudentDTO(tmp[0], tmp[1], Float.parseFloat(tmp[2]));
                result.add(dto);
            }
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public void saveToInternal(FileOutputStream fos, List<StudentDTO> listStudent) throws Exception {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            String result = "";
            for (StudentDTO dto : listStudent) {
                result += dto.toString() + "\n";
            }
            osw.write(result);
            osw.flush();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<StudentDTO> loadFromInternal(FileInputStream fis) throws Exception {
        List<StudentDTO> listStudent = new ArrayList<>();

        try {
            String s = "";
            StudentDTO dto = null;
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            while ((s = br.readLine()) != null) {
                String[] temp = s.split("-");
                dto = new StudentDTO(temp[0], temp[1], Float.parseFloat(temp[2]));
                listStudent.add(dto);
            }
        } finally {

        }

        return listStudent;
    }

    public void saveToExternal(List<StudentDTO> listStudent) throws Exception {
        File sdCard = Environment.getExternalStorageDirectory();
        String realPath = sdCard.getAbsolutePath();
        File directory = new File(realPath + "/MyFiles");
        directory.mkdir();
        //Co tinh dat giong nhau
        File file = new File(directory, "loclt.txt");
        FileOutputStream fos = new FileOutputStream(file);
        String result = "";
        for (StudentDTO dto : listStudent) {
            result += dto.toString() + "\n";
        }
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        osw.write(result);
        osw.flush();
    }

    public List<StudentDTO> loadFromExternal() throws Exception {
        List<StudentDTO> listStudent = new ArrayList<>();
        File sdCard = Environment.getExternalStorageDirectory();
        String realPath = sdCard.getAbsolutePath();
        File directory = new File(realPath + "/MyFiles");
        File file = new File(directory, "loclt.txt");
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        StudentDTO dto = null;
        String s = "";
        while ((s = br.readLine()) != null) {
            String[] temp = s.split("-");
            dto = new StudentDTO(temp[0], temp[1], Float.parseFloat(temp[2]));
            listStudent.add(dto);
        }
        return listStudent;

    }


}
