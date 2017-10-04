package com.ajanda.bariskoc.ajanda;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ajanda.bariskoc.ajanda.base.BaseActivity;
import com.ajanda.bariskoc.ajanda.model.Gorev;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AddTaskActivity extends BaseActivity {

    EditText txtKonu, txtNot;
    TextView txtTarih, txtSaatAdd, txtKategori;
    Button btnKaydet, datePicker, timepicker;
    Date seciliTarih;
    String[] spinnerList = {"İş", "Okul", "Eğlence", "Günlük", "Diğer"};
    String seciliGun, gun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        txtKonu = (EditText) findViewById(R.id.txtKonu);
        txtNot = (EditText) findViewById(R.id.txtNot);
        txtTarih = (TextView) findViewById(R.id.txtTarihAdd);
        //txtKategori= (TextView) findViewById(R.id.txtKategori);
        txtSaatAdd = (TextView) findViewById(R.id.txtSaatAdd);
        btnKaydet = (Button) findViewById(R.id.btnAddKayit);
        timepicker = (Button) findViewById(R.id.timepicker);
        datePicker = (Button) findViewById(R.id.datepicker);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddTaskActivity.this, android.R.layout.simple_dropdown_item_1line, spinnerList);
        final MaterialBetterSpinner spinner = (MaterialBetterSpinner) findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker;
                datePicker = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        seciliTarih = new Date(year - 1900, monthOfYear, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
                        txtTarih.setText(simpleDateFormat.format(seciliTarih));

                        SimpleDateFormat simpledateformat2 = new SimpleDateFormat("EEEE");
                        Date date = new Date(year, monthOfYear, dayOfMonth - 1);
                        String dayOfWeek = simpledateformat2.format(date);
                        seciliGun = dayOfWeek;
                    }
                }, year, month, day);
                datePicker.setTitle("Tarih Seçiniz");
                datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Seç", datePicker);
                datePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", datePicker);
                datePicker.show();
            }
        });
        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txtSaatAdd.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Saat Seçiniz");
                mTimePicker.show();
            }
        });

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gorev ajanda = new Gorev();
                ajanda.setKonu(txtKonu.getText().toString());
                ajanda.setSaat(txtSaatAdd.getText().toString());

                ajanda.setDetay(txtNot.getText().toString());
                ajanda.setTarih(txtTarih.getText().toString());
                ajanda.setKategori(spinner.getText().toString());
                ajanda.setGun(seciliGun);
                HashMap<String, Object> gorevTarihi = new HashMap<>();
                gorevTarihi.put("tarih", txtTarih.getText());
                mAuth = FirebaseAuth.getInstance();
                user = mAuth.getCurrentUser();
                ajanda.setEkleyen(user.getUid());
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference().child("yapilacaklar");
                myRef.child(ajanda.getId()).setValue(ajanda);
                Toast.makeText(AddTaskActivity.this, "Kayıt başarıyla eklendi.", Toast.LENGTH_SHORT).show();
                //hideProgressDialog();
                finish();
            }
        });
    }

}
