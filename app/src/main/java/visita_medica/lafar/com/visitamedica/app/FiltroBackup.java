package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import visita_medica.lafar.com.visitamedica.R;

public class FiltroBackup extends Activity {

    /////////////////////////////FECHA
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;


    Button calendario,  filtro_fecha, mandar_backup;
    EditText resultado_calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_backup);

        filtro_fecha=(Button)this.findViewById(R.id.btn_sincronizar_por_fecha);
        calendario=(Button)this.findViewById(R.id.pickDate);
        resultado_calendario=(EditText)this.findViewById(R.id.txt_fecha_filtro);
        filtrar_por_fecha();
    }

    public void filtrar_por_fecha()
    {
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        displayDate();

        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);
            }
        });

        filtro_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent resultIntent = new Intent();
                resultIntent.putExtra("fecha", resultado_calendario.getText().toString());




                setResult(Activity.RESULT_OK, resultIntent);
                finish();

            }
        });

    }

    private void displayDate() {

        if(Integer.toString(mDay).length()==1 && Integer.toString(mMonth+1).length()==1)
        {
            resultado_calendario.setText(
                    new StringBuilder()
                            .append(mYear).append("-")
                            .append("0").append(mMonth + 1).append("-")
                            .append("0").append(mDay)
            );

        }
        else if (Integer.toString(mDay).length()==2 && Integer.toString(mMonth+1).length()==1)
        {
            resultado_calendario.setText(
                    new StringBuilder()
                            .append(mYear).append("-")
                            .append("0").append(mMonth + 1).append("-")
                            .append(mDay)
            );

        }

        else if (Integer.toString(mDay).length()==1 && Integer.toString(mMonth+1).length()==2)
        {    resultado_calendario.setText(
                new StringBuilder()
                        .append(mYear).append("-")
                        .append(mMonth + 1).append("-")
                        .append("0").append(mDay));
        }
        else if (Integer.toString(mDay).length()==2 && Integer.toString(mMonth+1).length()==2)
        {    resultado_calendario.setText(
                new StringBuilder()
                        .append(mYear).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mDay));
        }
        else
        {
            System.out.println("La fecha esta mal");
        }

    }



    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    displayDate();
                }
            };
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);


        }
        return null;
    }

}
