package com.link2loyalty.bwigomdlib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.adapters.FaqAdapter;
import com.link2loyalty.bwigomdlib.interfaces.FaqRecyclerViewOnItemClickListener;
import com.link2loyalty.bwigomdlib.models.FaqModel;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public class FaqActivity extends AppCompatActivity {

    private static final String THE_ANSWER = "com.link2loyalty.bwigo.activities.THE_ANSWER";
    private String title = "Preguntas frecuentes";
    private Toolbar myToolbar;
    /*
        Declarar instancias globales
    */
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    // Inicializar Animes
    List<FaqModel> items;

    private Context mContext;
    private User mUser;

    private ValorMultitenancy mMultitenancy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        mContext = getApplicationContext();

        mUser = new User(mContext);
        mMultitenancy = mUser.getMultitenancy();
        findViews();
        configToolbar();
        loadQuestions();
    }

    private void loadQuestions() {


        try {
            getQuestionsFromServer();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*
        items.add(new FaqModel(1, "??Pregunta 1 ... a123123?", "respuesta 1"));
        items.add(new FaqModel(2, "??Pregunta 2 ... a123123?", "respuesta 2"));
        items.add(new FaqModel(3, "??Pregunta 3 ... a123123?", "respuesta 3"));
        items.add(new FaqModel(4, "??Pregunta 4 ... a123123?", "respuesta 4"));
        items.add(new FaqModel(5, "??Pregunta 5 ... a123123?", "respuesta 5"));
        */
        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new FaqAdapter(items , new FaqRecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {

                Intent intent = new Intent(FaqActivity.this, AnswerActivity.class);
                intent.putExtra(THE_ANSWER, items.get(position).getAnswer());
                startActivity(intent);


            }
        });
        recycler.setAdapter(adapter);


    }

    private void getQuestionsFromServer() throws JSONException {
        Gson gson = new Gson();

        //TODO hacer la peticion al servidor para traer las preguntas y respuestas


        String json =
                "{ " +
                " \"faqs\": " +
                "    [" +
                "       { \"id\":1 , \"question\":\"??C??mo funciona Bwigo Mobile?\" , \"answer\":\"Bwigo mobile es la plataforma m??vil de compra inteligente que reconoce tu consumo. A trac??s de tu aplicaci??n descubre descuentos y beneficios exclusivos, los cuales te llegar??n a trav??s de una notificaci??n, inform??ndote sobre los establecimientos a tu alrededor que tienen descuentos para ti. Es f??cil de dirigirte a la sucursal y redime tu cup??n. Es facil de usar: descubre, comparte, redime.\" }, " +
                "       { \"id\":2 , \"question\":\"??En donde puedo redimir mis cupones de Bwigo Mobile?\" , \"answer\":\"Tu aplicaci??n te notificar?? los establecimientos en los que tienes beneficios. Tambi??n puedes ingresar  a tu aplicaci??n, dirigete al menu y selecciona el mapa para ver los beneficios a tu alrededor.\" }, " +
                        "       { \"id\":3 , \"question\":\"??C??mo puedo inscribirme a Bwigo Mobile?\" , \"answer\":\"Si no tienes tu p??liza para ingresar a la aplici??n comunicate con tu aseguradora para que te la puedan proporcionar. Si no eres beneficiario de los programas afiliados a Bwigo Mobile por el momento no podr??s ingresar a la aplicaci??n, hasta el 2018 cuando se encuentre disponible en las tiensas de Apple Store y Google Play abiertas al p??blico en general.\" }," +
                        "       { \"id\":3 , \"question\":\"??Porqu?? no pude redimir mi cup??n?\" , \"answer\":\"Cada sucursal ofrece un descuento o un beneficio especial para ti. Una vez que ya redimiste tu cupon, por medio del lector de c??digo QR no podras hacer nuevamente uso de ??l ya que el mismo sistema lo 'desactiva' al momento de hacer la redenci??n, sin embargo puedes dirigirte a otra sucursal y hacer uso del cupon de esa sucursal en espec??fico. \" }," +
                        "       { \"id\":3 , \"question\":\"??Donde puedo ver mis cupones?\" , \"answer\":\"Si abres la aplicaci??n para consultar alguno de los cupones que tenemos para ti puedes guardarlo en la secci??n de  'mis cupones' y consultarlo y/o redimirlo m??s tarde.\" }," +
                        "       { \"id\":3 , \"question\":\"??Qu?? pasa si no me validaron mi cup??n en el establecimiento?\" , \"answer\":\"En Bwigo Mobile queremos que tengas la mejor experiencia, por eso tenemos garantia de reembolso, si el establecimiento no te hace valido tu cup??n te devolvemos el monto proporcional al descuento de la promoci??n. La garant??a del reembolso procede solo si se tiene el ticket de la compra, los cuales nos deberas compartir (im??genes del cupon y ticket) via correo electr??nico a: contacto@bwigo.com (es nesesario que coincida la sucursal del establecimiento del cup??n con el ticket de compra para hacer valida la garantia) \" }," +
                        "       { \"id\":3 , \"question\":\"??Porqu?? no puedo ingresar a mi aplicaci??n?\" , \"answer\":\"En Bwigo Mobile queremos proporcionar una experiencia sencilla y ??til. Por tanto, de vez en cuando, examinamos detenidamente como nuestros miembros est??n usando nuestros productos y los actualizamos debidamente. Bas??ndonos en eso y nuestra supervisi??n del uso movil de nuestros miembros, en adelante solo admitiremos iOS 9.0 y Android Jelly Bean (4.3 / API nivel 18) o versiones m??s nuevas. Solicitamos a los miembros que utilizan la aplicaci??n movil de Bwigo Mobile que actualicen a dicha versi??n para obtener una mejor experiencia.\" }," +
                        "       { \"id\":3 , \"question\":\"??Porqu?? no pude acceder al siguiente nivel?\" , \"answer\":\"Recuerda que en Bwigo Mobile mientras mas hagas uso de tus cupones, m??s ganas. Para alcanzar el siguiente nivel deber??s canjear un determinado n??mero de cupones en un determinado lapso de tiempo establecido.Para ascender del nivel b??sico abronce, deberas redimir 5 cupones en un mes, pra acseder de nivel bronce a nivel plata deber??s redimir 10 cupones en un mes, para acseder de nivel plata a oro deber??s redimir un total de 25 cupones en 2 meses. Al ascender de nivel podr??s desbloquear servicios exclusivos que en Bwigo Mobile tenemos para ti.\" }" +
                "    ]"+
                "}";

        JSONObject jObject = new JSONObject(json);

        items = Arrays.asList(gson.fromJson(String.valueOf(jObject.getJSONArray("faqs")), FaqModel[].class));




    }

    private void configToolbar() {
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Config multitenancy
        if( mMultitenancy.getColpri() != null ){
            myToolbar.setBackgroundColor(Color.parseColor( "#"+mMultitenancy.getColpri() ));
            //myToolbar.setTitle( mMultitenancy.getDes() );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor( "#"+mMultitenancy.getColosc() ));
            }
        }
    }

    private void findViews(){
        myToolbar = findViewById(R.id.tb_faq);
    }

}
