package visita_medica.lafar.com.visitamedica.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import visita_medica.lafar.com.visitamedica.ImagesAndDrawing.CanvasView;
import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.funciones.Funciones;

public class CapturaFirma extends Activity {

    private CanvasView customCanvas;
    Button btn_borrar_canvas, btn_guardar_canvas;
    LinearLayout ly_firma;
    String foto;
    Bundle bundle;
    String nombre_foto;
    Integer contador_borrar_firma=0;
    Funciones fun= new Funciones();
    Integer ancho;
    Double ancho_real_decimal;
    Integer ancho_real;


    TextView leyenda1,leyenda2,  leyenda3;

    //////////////Fecha y hora

    private Calendar fechaYhora = Calendar.getInstance();
    SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String fecha_actual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_captura_firma);

        fecha_actual = fecha.format(fechaYhora.getTime());

        leyenda1 =(TextView)this.findViewById(R.id.leyenda1);
        leyenda1.setText(getString(R.string.leyenda_legal_firma)+"\n"+getString(R.string.fecha_generacion)+": " + fecha_actual );

        leyenda2 =(TextView)this.findViewById(R.id.leyenda2);
        leyenda2.setText(getString(R.string.leyenda_legal_firma)+"\n"+getString(R.string.fecha_generacion)+": "  + fecha_actual );

        leyenda3 =(TextView)this.findViewById(R.id.leyenda3);
        leyenda3.setText(getString(R.string.leyenda_legal_firma)+"\n"+getString(R.string.fecha_generacion)+": "  + fecha_actual );

        ancho= fun.get_width(CapturaFirma.this);
        ancho_real_decimal= fun.convertir_de_integer_a_double(ancho)*0.9;
        ancho_real=fun.convertir_de_double_a_integer(ancho_real_decimal);

        getWindow().setLayout(ancho_real,WindowManager.LayoutParams.WRAP_CONTENT);

        bundle=getIntent().getExtras();
        nombre_foto=bundle.getString("nombre_foto");

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);

        btn_borrar_canvas=(Button)this.findViewById(R.id.btn_borrar_firma);
        btn_borrar_canvas.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contador_borrar_firma=contador_borrar_firma+1;
                if(contador_borrar_firma>3)
                {
                Toast.makeText(CapturaFirma.this,getString(R.string.sobrepaso_tres_intentos),Toast.LENGTH_SHORT).show();
                finish();
                }
                else
                {
                customCanvas.clearCanvas();
                }

            }
        });

        btn_guardar_canvas=(Button)this.findViewById(R.id.btn_guardar_firma);
        btn_guardar_canvas.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i=  new Intent(CapturaFirma.this,AuxiliarCapturaFirma.class);
                startActivityForResult(i, 1);


            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {


            case 1:
                    screen_shot();

            break;
        }

    }





    public void screen_shot()
    {
        ly_firma=(LinearLayout)this.findViewById(R.id.ly_firma);
        whatsapp(CapturaFirma.this, ly_firma);





        Toast.makeText(CapturaFirma.this,  getString(R.string.firma_guardada), Toast.LENGTH_SHORT).show();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("ruta_imagen",foto );
        setResult(Activity.RESULT_OK, resultIntent);
        finish();


    }

    Bitmap bitmap;
    public void  whatsapp(Activity act, View v)
    {
        bitmap = takeScreenshot(v);
        saveBitmap(bitmap);

    }

    public Bitmap takeScreenshot(View view) {
        view.getRootView();
        view.setDrawingCacheEnabled(true);
        return view.getDrawingCache();
    }



    public void saveBitmap(Bitmap bitmap) {

        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath() + "/VISMED");
        if (!directory.isDirectory())
            directory.mkdirs();
        foto = Environment.getExternalStorageDirectory()+ "/VISMED/" +nombre_foto+ ".debbie_zuleta";
        File fot = new File(foto);

        FileOutputStream fos;
        try {

            if (fot != null) {
                fos = new FileOutputStream(fot);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

}
