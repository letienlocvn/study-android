package com.example.se1414_day11_database2.daos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.se1414_day11_database2.R;
import com.example.se1414_day11_database2.dtos.StudentDTO;

import java.util.List;

public class StudentAdapter extends BaseAdapter {
    private List<StudentDTO> listStudent;

    public List<StudentDTO> getListStudent() {
        return listStudent;
    }

    public void setListStudent(List<StudentDTO> listStudent) {
        this.listStudent = listStudent;
    }

    public StudentAdapter() {
    }

    @Override
    public int getCount() {
        return listStudent.size();
    }

    @Override
    public Object getItem(int i) {
        return listStudent.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            convertView =  inflater.inflate(R.layout.item, viewGroup, false);
        }

        TextView txtID = convertView.findViewById(R.id.txtID);
        TextView txtView = convertView.findViewById(R.id.txtName);
        TextView txtMark = convertView.findViewById(R.id.txtMark);
        StudentDTO dto = listStudent.get(i);
        txtID.setText(dto.getId());
        txtView.setText(dto.getName());
        txtMark.setText(dto.getMark() + "");
        return convertView;
    }
}
