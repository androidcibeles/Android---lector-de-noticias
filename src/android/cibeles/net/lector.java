package android.cibeles.net;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

public class lector extends Activity {

	private static final String SOAP_ACTION = "urn:server#servicios_iphone"; //OK
    private static final String METHOD_NAME = "servicios_iphone"; //OK
    private static final String NAMESPACE = "urn:server"; //OK
    private static final String URL = "http://www.sportcartagena.es/servicios_web_iphone_v2/soap/servicios_v2.php";
    
    String id_device;
    String os_version;
    private static final String OS = "Android";
    
    TextView tv;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getDeviceInfo();
        this.verportada(tv);
    }
	
	public void verfotos(View view) {
		setContentView(R.layout.fotos);
		
		Drawable d = findViewById(R.id.buttonfotos2).getBackground();  
        PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);  
        d.setColorFilter(filter); 
	 }
	
	public void verarticulo(View view) {
		setContentView(R.layout.articulo);
		Drawable d = findViewById(R.id.buttonarticulo4).getBackground();  
        PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);  
        d.setColorFilter(filter);  
	 }
	
	public void vercalendario(View view) {
		setContentView(R.layout.calendario);
		Drawable d = findViewById(R.id.buttoncalendario3).getBackground();  
        PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);  
        d.setColorFilter(filter);  
	 }
	
	public void verportada(View view) {
		setContentView(R.layout.main);
		Drawable d = findViewById(R.id.buttonportada1).getBackground();  
        PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);  
        d.setColorFilter(filter);
        tv = (TextView)findViewById(R.id.titulares);
        tv.setText(this.makePetition());
        
	 }
	
public String makePetition() {
		
		SoapObject Request = new SoapObject (NAMESPACE,METHOD_NAME);
        Request.addProperty("funcion_y_parametros","2####000129735192####iphone####2.2####traePortada####n");
        
        /* 1) traePortada
         * 2) traeNoticia (incluir nro de noticia)
         * 3) traeAniosHemeroteca (nada como dato) se puede harcodear, siempre igual
         * 4) traeFechasHemeroteca (año como dato)
         * 5) traetitulos	("---n" para ultima edicion, "---XX-XX-XXXX espesifica")
         * 6) traeCategorias (n pide fecha en formato "XX-XX-XXXX" o "n" para actual )
         * 7) traeFotos	"									"
         * 
         * */
        
        
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        soapEnvelope.setOutputSoapObject(Request);
        
        AndroidHttpTransport aht = new AndroidHttpTransport(URL);
        String respuesta = "";
        try
        {
        	aht.call(SOAP_ACTION,soapEnvelope);
        	SoapPrimitive resultString = (SoapPrimitive)soapEnvelope.getResponse();
        	respuesta = resultString.toString();
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	
        	respuesta="error en el web service";
        } 
			
        respuesta= respuesta.replace( "<respuesta>","");
        respuesta= respuesta.replace( "</respuesta>","");
        respuesta= respuesta.replace( "####","\n");
        respuesta= respuesta.replace( "@#@","\n\n\n");
        //String delims = "[ .,?!]+";
        //String[] tokens = respuesta.split(delims);
        
        
        
		return respuesta;
	}

	/* Funcion Para obtener los datos del dispositivo*/
	public void getDeviceInfo() {
		TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
		this.id_device = tm.getDeviceId();
	
		String version_android = android.os.Build.VERSION.RELEASE;
		this.os_version = Uri.encode(version_android);
	}	
	
}