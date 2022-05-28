package com.aram_201572.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.aram_201572.Api.ApiClient;
import com.aram_201572.Api.ApiInterface;
import com.aram_201572.R;
import com.aram_201572.Utils.SharedPrefsHelper;
import com.aram_201572.Models.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirebaseActivity extends AppCompatActivity {

    ApiInterface apiService;
    List<Student> students=new ArrayList<Student>();
    EditText edtxtId,edtxtName,edtxtSurname,edtxtFatherName, edtxtNationalId;
    Button btnAdd,btnUpdate,btnGetRecord,btnGetAllRecords,btnDelete;
    DatePicker dateDob;
    RadioGroup rgGender;
    TextView txtWeather;

    @Override
    protected void onResume() {
        super.onResume();
        String city = SharedPrefsHelper.getCity(FirebaseActivity.this);
        getWeather("http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=76b4f5fc92f432d9fc68dffc349aa1cf&units=metric");

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        getAllStudentsFromFirebase();
        edtxtId= findViewById(R.id.edtxtId);
        edtxtName= findViewById(R.id.edtxtName);
        dateDob= findViewById(R.id.dateDob);
        edtxtNationalId= findViewById(R.id.edtxtNationalId);
        edtxtFatherName= findViewById(R.id.edtxtFatherName);
        edtxtSurname= findViewById(R.id.edtxtSurname);
        rgGender= findViewById(R.id.rgGender);
        btnAdd= findViewById(R.id.btnAdd);
        btnDelete= findViewById(R.id.btnDelete);
        btnUpdate= findViewById(R.id.btnUpdate);
        btnGetAllRecords= findViewById(R.id.btnGetAll);
        btnGetRecord= findViewById(R.id.btnGetRecord);
        txtWeather= findViewById(R.id.txtWeather);



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtxtId.getText().toString();
                String gender = getGender();
                String dob = getDob();
                String name = edtxtName.getText().toString();
                String surname = edtxtSurname.getText().toString();
                String fName = edtxtFatherName.getText().toString();
                String nationalId = edtxtNationalId.getText().toString();
                if(!id.equalsIgnoreCase("")){
                    String firebaseId =  getFirebaseIdForStudent(id);
                    if(!firebaseId.equals("")){
                        if(!gender.equalsIgnoreCase("") && !dob.equalsIgnoreCase("") && !id.equalsIgnoreCase("") && !name.equalsIgnoreCase("")
                                && !surname.equalsIgnoreCase("") && !fName.equalsIgnoreCase("") && !nationalId.equalsIgnoreCase("")){
                            Student student = new Student( id,name,surname,fName,nationalId,dob,gender);
                            updateStudentByIdFromFirebase(firebaseId,student);
                        }
                        else{
                            Toasty.error(FirebaseActivity.this, "Please enter valid values", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                    else{
                        Toasty.error(FirebaseActivity.this, "Student Id does not exist!", Toast.LENGTH_SHORT, true).show();
                     }
                }
                else{
                    Toasty.error(FirebaseActivity.this, "Please enter a valid id", Toast.LENGTH_SHORT, true).show();
                 }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = edtxtId.getText().toString();
                if(!id.equalsIgnoreCase("")){
                    String firebaseId =  getFirebaseIdForStudent(id);
                    if(!firebaseId.equalsIgnoreCase("")){
                        deleteStudentByIdFromFirebase(firebaseId);
                    }
                    else{
                        Toasty.error(FirebaseActivity.this, "Student Id does not exist!", Toast.LENGTH_SHORT, true).show();

                    }
                }
                else{
                    Toasty.error(FirebaseActivity.this, "Please enter a valid id", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

        btnGetAllRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FirebaseActivity.this, FirebaseListActivity.class);
                startActivity(intent);

            }
        });

        btnGetRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = edtxtId.getText().toString();
                if(!id.equalsIgnoreCase("")){
                    getStudentByIdFromFirebase(id);
                }
                else{
                    Toasty.error(FirebaseActivity.this, "Please enter a valid id", Toast.LENGTH_SHORT, true).show();

                }

            }
        });



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String gender = getGender();
                String dob = getDob();
                String id = edtxtId.getText().toString();
                String name = edtxtName.getText().toString();
                String surname = edtxtSurname.getText().toString();
                String fName = edtxtFatherName.getText().toString();
                String nationalId = edtxtNationalId.getText().toString();

                if(!gender.equalsIgnoreCase("") && !dob.equalsIgnoreCase("") && !id.equalsIgnoreCase("") && !name.equalsIgnoreCase("")
                   && !surname.equalsIgnoreCase("") && !fName.equalsIgnoreCase("") && !nationalId.equalsIgnoreCase("")){
                    Student student = new Student( id,name,surname,fName,nationalId,dob,gender);
                    addStudentToFirebase(student);
                }
                else{
                    Toasty.error(FirebaseActivity.this, "Please enter valid values", Toast.LENGTH_SHORT, true).show();
              }
            }
        });


    }

    String getGender(){
        int selectedRB = rgGender.getCheckedRadioButtonId();
        if(selectedRB==R.id.rdbMale){
            return "Male";
        }
        else if(selectedRB==R.id.rdbFemale){
            return "Female";
        }
        return "";
    }
    String getDob(){
        int day = dateDob.getDayOfMonth();
        int month = dateDob.getMonth() + 1;
        int year = dateDob.getYear();
        return day+"-"+month+"-"+year;
    }
    String getFirebaseIdForStudent(String id){
        for(int i=0; i<students.size(); i++){
            if(students.get(i).getId().equals(id)){
                return  students.get(i).getDocumentId();
            }
        }
        return "";
    }
    void getAllStudentsFromFirebase(){
        students.clear();
        Call<ResponseBody> call = apiService.getStudents();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {

                if(response.body()!=null) {

                    try {
                        String responseBody = response.body().string();
                        if(responseBody.equals("null")){
                            return;
                        }
                        if(responseBody.contains("[")){
                            JSONArray jsonArr = new JSONArray(responseBody);
                            if(responseBody.contains("null")){
                                JSONObject jsonBody = jsonArr.getJSONObject(1);
                                Student student  =getStudentFromJson(jsonBody);
                                student.setDocumentId(String.valueOf(1));
                                students.add(student);
                            }
                            else{
                                for (int i = 0; i < jsonArr.length(); i++) {
                                    JSONObject jsonBody = jsonArr.getJSONObject(i);
                                    Student student  =getStudentFromJson(jsonBody);
                                    student.setDocumentId(String.valueOf(i));
                                    students.add(student);
                                }
                            }

                        }
                        else{
                            JSONObject  jsonResponse = new JSONObject(responseBody);
                            Iterator<String> keys = jsonResponse.keys();
                            if( keys.hasNext() ){
                                String key = (String)keys.next(); // First key in your json object
                                if(jsonResponse.has(key)){
                                    JSONObject jsonBody= jsonResponse.getJSONObject(key);
                                    Student student  =getStudentFromJson(jsonBody);
                                    student.setDocumentId(key);
                                    if(!students.contains(student)){
                                        students.add(student);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toasty.error(FirebaseActivity.this, "Error while getting students from firebase", Toast.LENGTH_SHORT, true).show();
            }
        });
    }
    void getStudentByIdFromFirebase(String id){
        Call<ResponseBody> call = apiService.getStudentById("\"id\"","\""+id+"\"");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {

                if(response.body()!=null) {
                    try {
                        JSONObject  jsonResponse = new JSONObject(response.body().string());
                        Iterator<String> keys = jsonResponse.keys();
                        if( keys.hasNext() ){
                            String key = (String)keys.next(); // First key in your json object
                            if(jsonResponse.has(key)){
                                JSONObject jsonBody= jsonResponse.getJSONObject(key);
                                Student student  =getStudentFromJson(jsonBody);
                                student.setDocumentId(key);
                                showPopup(student);
                                populateStudentFields(student);
                            }
                        }

                    } catch (Exception e) {
                        Toasty.error(FirebaseActivity.this, "Error while getting student from firebase", Toast.LENGTH_SHORT, true).show();

                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toasty.error(FirebaseActivity.this, "Error while getting student from firebase", Toast.LENGTH_SHORT, true).show();


            }
        });
    }
    void deleteStudentByIdFromFirebase(String id){
        Call<ResponseBody> call = apiService.deleteStudent(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {

                if(response.body()!=null) {
                    if(response.code()==200){
                        Toasty.success(FirebaseActivity.this, "Student deleted!", Toast.LENGTH_SHORT, true).show();
                        clearStudentFields();
                        getAllStudentsFromFirebase();
                    }
                    else{
                        Toasty.error(FirebaseActivity.this, "Error while deleting Student!", Toast.LENGTH_SHORT, true).show();
                   }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasty.error(FirebaseActivity.this, "Error while deleting Student!", Toast.LENGTH_SHORT, true).show();

            }
        });
    }
    void updateStudentByIdFromFirebase(String id,Student student){
        Call<ResponseBody> call = apiService.updateStudents(id,student);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {

                if(response.body()!=null) {
                    if(response.code()==200){
                        Toasty.success(FirebaseActivity.this, "Student updated!", Toast.LENGTH_SHORT, true).show();
                        getAllStudentsFromFirebase();
                    }
                    else{
                        Toasty.error(FirebaseActivity.this, "Error while updating Student!", Toast.LENGTH_SHORT, true).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasty.error(FirebaseActivity.this, "Error while updating Student!", Toast.LENGTH_SHORT, true).show();

            }
        });
    }
    Student getStudentFromJson(JSONObject jsonBody){
        Student student = null;
        try{
            String nationalId =jsonBody.getString("nationalId");
            String surname= jsonBody.getString("surname");
            String name =jsonBody.getString("name");
            String id= jsonBody.getString("id");
            String gender =jsonBody.getString("gender");
            String fName= jsonBody.getString("fatherName");
            String dob =jsonBody.getString("dob");
            student = new Student(id,name,surname,fName,nationalId,dob,gender);
        }
        catch (Exception e){
            e.getMessage();
        }
        return student;
    }
    void showPopup(Student student){
        StringBuffer buffer = new StringBuffer();
        buffer.append("ID: " + student.getId() + "\n");
        buffer.append("Name: " + student.getName() + "\n");
        buffer.append("Surname: " + student.getSurname() + "\n");
        buffer.append("Father's Name: " + student.getFatherName() + "\n");
        buffer.append("National ID: " + student.getNationalId() + "\n");
        buffer.append("Date of Birth: " + student.getDob() + "\n");
        buffer.append("Gender: " + student.getGender() + "\n");

        AlertDialog.Builder builder = new AlertDialog.Builder(FirebaseActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Student Information");
        builder.setMessage(buffer.toString());
        builder.show();

    }
    void populateStudentFields(Student student){

        edtxtSurname.setText(student.getSurname());
        edtxtFatherName.setText(student.getFatherName());
        edtxtNationalId.setText(student.getNationalId());
        edtxtName.setText(student.getName());
        edtxtId.setText(student.getId());
        String[] date = student.getDob().split("-");
        int month=Integer.valueOf(date[1])-1;
        dateDob.updateDate(Integer.valueOf(date[2]),month,Integer.valueOf(date[0]));
        rgGender.check(R.id.rdbFemale);
        if(student.getGender().equals("Male")){
            rgGender.check(R.id.rdbMale);
        }
    }
    void clearStudentFields(){
        edtxtSurname.setText("");
        edtxtFatherName.setText("");
        edtxtNationalId.setText("");
        edtxtName.setText("");
        edtxtId.setText("");
        rgGender.clearCheck();
    }
    void addStudentToFirebase(Student student){
        students.add(student);
        Call<ResponseBody> call = apiService.addStudents(students);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toasty.success(FirebaseActivity.this, "Student added!", Toast.LENGTH_SHORT, true).show();
                clearStudentFields();
                getAllStudentsFromFirebase();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasty.error(FirebaseActivity.this, "Failed adding Student, Try again!", Toast.LENGTH_SHORT, true).show();
            }
        });
    }
    public void getWeather(String url){
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONObject jsonMain = response.getJSONObject("main");
                double temp = jsonMain.getDouble("temp");
                double humid = jsonMain.getDouble("humidity");
                txtWeather.setText("Temperature: "+String.valueOf(temp)+"°C\nHumidity: "+String.valueOf(humid));

            }
            catch (JSONException e){
                e.printStackTrace();

            }
        }, error -> Log.d("Student", error.getMessage()));
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);
    }
}