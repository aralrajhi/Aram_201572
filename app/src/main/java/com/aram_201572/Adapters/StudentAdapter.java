package com.aram_201572.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aram_201572.Models.Student;
import com.aram_201572.R;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {
	Context _context;
	public List<Student> _students;
	public StudentAdapter(Context context, List<Student> students) {
		super(context, 0, students);
		_context = context;
		_students = students;

	}

	@Override
	public int getViewTypeCount() {
		return  1;
	}

	@Override
	public int getItemViewType(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		final Student student = getItem(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_student, parent, false);
			holder.txtId = convertView.findViewById(R.id.txtId);
			holder.txtNatId = convertView.findViewById(R.id.txtNationalId);
			holder.txtName = convertView.findViewById(R.id.txtName);
			holder.txtFName= convertView.findViewById(R.id.txtfName);
			holder.txtSurname = convertView.findViewById(R.id.txtSurname);
			holder.txtGender = convertView.findViewById(R.id.txtGender);
			holder.txtDob = convertView.findViewById(R.id.txtDob);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txtId.setText(student.getId());
		holder.txtName.setText(student.getName());
		holder.txtNatId.setText(student.getNationalId());
		holder.txtFName.setText(student.getFatherName());
		holder.txtSurname.setText(student.getSurname());
		holder.txtGender.setText(student.getGender());
		holder.txtDob.setText(student.getDob());

		return convertView;
	}
	private class ViewHolder {
		TextView txtId;
		TextView txtName;
		TextView txtFName;
		TextView txtSurname;
		TextView txtDob;
		TextView txtGender;
		TextView txtNatId;
	}

}