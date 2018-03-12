package visita_medica.lafar.com.visitamedica.app;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

import java.text.DecimalFormat;

import visita_medica.lafar.com.visitamedica.R;
import visita_medica.lafar.com.visitamedica.funciones.Funciones;
import visita_medica.lafar.com.visitamedica.shared_preferences.SharedPreferencesP;

/**
 * The simplest possible example of using AndroidPlot to plot some data.
 */
public class MiCobertura extends Activity
{

    private TextView donutSizeTextView;
    private SeekBar donutSizeSeekBar;

    private PieChart pie;

    private Segment s1;
    private Segment s2;
    private Segment s3;

    /*
    BUNDLE BUNDLE BUNDLE
     */
    String mes,  anhio;
    String s_positivas, s_negativas, s_faltantes, s_total;
    TextView positivas,negativas,  faltantes,  total, titulo_mes_anhio;
    Integer i_positivas, i_negativas, i_faltantes, i_total;
    Double d_positivas,  d_negativas, d_faltantes, d_total;
    Double porcentaje_positivas, porcentaje_negativas, porcentaje_faltantes,porcentaje_total;

    Bundle bundle;

    Funciones fun = new Funciones();

    DecimalFormat f = new DecimalFormat("##.00");


    SharedPreferencesP fps=  new SharedPreferencesP();

    ImageView volver;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cobertura);

        bundle=getIntent().getExtras();
        s_positivas=bundle.getString("positivas");
        s_negativas=bundle.getString("negativas");
        s_faltantes=bundle.getString("faltantes");
        s_total=bundle.getString("total");
        mes=bundle.getString("mes");
        anhio=bundle.getString("anhio");



        d_positivas=fun.convertir_de_string_a_double(s_positivas);
        d_negativas=fun.convertir_de_string_a_double(s_negativas);
        d_faltantes=fun.convertir_de_string_a_double(s_faltantes);
        d_total=fun.convertir_de_string_a_double(s_total);



        i_positivas=fun.convertir_de_double_a_integer(d_positivas);
        i_negativas=fun.convertir_de_double_a_integer(d_negativas);
        i_faltantes=fun.convertir_de_double_a_integer(d_faltantes);
        i_total=fun.convertir_de_double_a_integer(d_total);


       porcentaje_total=1.00*100.00;
       porcentaje_positivas=(d_positivas/d_total)*100.00;
       porcentaje_negativas=(d_negativas/d_total)*100.00;
       porcentaje_faltantes=(d_faltantes/d_total)*100.00;




        positivas=(TextView)this.findViewById(R.id.lbl_positivas);
        positivas.setText(s_positivas);

        negativas=(TextView)this.findViewById(R.id.lbl_negativas);
        negativas.setText(s_negativas);

        faltantes=(TextView)this.findViewById(R.id.lbl_faltantes);
        faltantes.setText(s_faltantes);

        total=(TextView)this.findViewById(R.id.lbl_total);
        total.setText(s_total);


        titulo_mes_anhio=(TextView)this.findViewById(R.id.lbl_titulo_mes_y_anhio);
        titulo_mes_anhio.setText(getString(R.string.cobertura_del_mes)+" "+ fps.Consultar_si_hay_registro(MiCobertura.this)+" "+ mes + " "+ getString(R.string.cobertura_del_anhio)+" "+anhio);


        // initialize our XYPlot reference:
        pie = (PieChart) findViewById(R.id.mySimplePieChart);



        // detect segment clicks:
        pie.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PointF click = new PointF(motionEvent.getX(), motionEvent.getY());
                if(pie.getPieWidget().containsPoint(click)) {
                    Segment segment = pie.getRenderer(PieRenderer.class).getContainingSegment(click);
                    if(segment != null) {
                        System.out.println("Clicked Segment: " + segment.getTitle());
                    }
                }
                return false;
            }
        });


        volver= (ImageView)this.findViewById(R.id.btn_volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        s1 = new Segment(getString(R.string.positivas)+"\n"+f.format(porcentaje_positivas)+"%" , i_positivas);
        s2 = new Segment(getString(R.string.negativas)+"\n"+f.format(porcentaje_negativas)+"%", i_negativas);
        s3 = new Segment(getString(R.string.faltantes)+"\n"+f.format(porcentaje_faltantes)+"%", i_faltantes);

        EmbossMaskFilter emf = new EmbossMaskFilter(new float[]{1, 1, 1}, 0.4f, 10, 8.2f);

        SegmentFormatter sf1 = new SegmentFormatter();
        sf1.configure(getApplicationContext(), R.xml.pie_segment_formatter1);

        sf1.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf2 = new SegmentFormatter();
        sf2.configure(getApplicationContext(), R.xml.pie_segment_formatter2);

        sf2.getFillPaint().setMaskFilter(emf);

        SegmentFormatter sf3 = new SegmentFormatter();
        sf3.configure(getApplicationContext(), R.xml.pie_segment_formatter3);

        sf3.getFillPaint().setMaskFilter(emf);




        pie.addSeries(s1, sf1);
        pie.addSeries(s2, sf2);
        pie.addSeries(s3, sf3);

        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBackgroundPaint().setColor(Color.TRANSPARENT);
    }

   /* protected void updateDonutText() {
        donutSizeTextView.setText(donutSizeSeekBar.getProgress() + "%");
    }*/
}