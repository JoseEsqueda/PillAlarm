package help.com.miadmimedico;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Receta extends AppCompatActivity {

    ClaseMedicamentoReceta medicamentoReceta = new ClaseMedicamentoReceta();
    ClaseUsuario usuario = new ClaseUsuario();

    FirebaseAuth mAuth;
    DatabaseReference baseDatos;

    ExpandableListView listaReceta;
    AdaptadorTipoReceta adaptadorTipoReceta;
    ArrayList<String> lista_tipo_receta;
    Map<String, ArrayList<String>> lista_medicamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);

        Toolbar toolbar;

        mAuth = FirebaseAuth.getInstance();
        baseDatos = FirebaseDatabase.getInstance().getReference();

        usuario.setIdUsuario(mAuth.getCurrentUser().getUid());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listaReceta = (ExpandableListView) findViewById(R.id.listaReceta);

        lista_tipo_receta = new ArrayList<>();
        lista_medicamento = new HashMap<>();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Receta");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Principal.class));
                finish();
            }
        });

        listarTipoReceta();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.agregar:
                Intent intent = new Intent(Receta.this, AgregarMedicamentoReceta.class);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }

    private void listarTipoReceta() {
        final ArrayList<String> cardiologo = new ArrayList<>();
        final ArrayList<String> urologo = new ArrayList<>();
        final ArrayList<String> dermatologo = new ArrayList<>();
        final ArrayList<String> oncologo = new ArrayList<>();
        final ArrayList<String> neurologo = new ArrayList<>();
        final ArrayList<String> psiquiatria = new ArrayList<>();
        final ArrayList<String> medicoFamiliar = new ArrayList<>();
        final ArrayList<String> id_medicamento_urologo = new ArrayList<>();
        final ArrayList<String> id_medicamento_dermatologo = new ArrayList<>();
        final ArrayList<String> id_medicamento_cardiologo = new ArrayList<>();
        final ArrayList<String> id_medicamento_oncologo = new ArrayList<>();
        final ArrayList<String> id_medicamento_neurologo = new ArrayList<>();
        final ArrayList<String> id_medicamento_psiquiatria = new ArrayList<>();
        final ArrayList<String> id_medicamento_medicoFamiliar = new ArrayList<>();
        HashMap<String, ArrayList<String>> id_medicamento_receta = new HashMap<>();

        baseDatos.child("receta").child(usuario.getIdUsuario()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cardiologo.clear();
                id_medicamento_cardiologo.clear();
                dermatologo.clear();
                id_medicamento_dermatologo.clear();
                urologo.clear();
                id_medicamento_urologo.clear();
                oncologo.clear();
                id_medicamento_oncologo.clear();
                neurologo.clear();
                id_medicamento_neurologo.clear();
                psiquiatria.clear();
                id_medicamento_psiquiatria.clear();
                medicoFamiliar.clear();
                id_medicamento_medicoFamiliar.clear();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                        medicamentoReceta.setNombreMedicamento((objSnapshot.child("medicamento").getValue().toString()));
                        medicamentoReceta.setViaAdministracion(objSnapshot.child("viaAdministracion").getValue().toString());
                        medicamentoReceta.setCantidadPorcion(objSnapshot.child("cantidadPorcion").getValue().toString());
                        medicamentoReceta.setTipoPorcion(objSnapshot.child("tipoPorcion").getValue().toString());
                        medicamentoReceta.setIntervaloHora(objSnapshot.child("intervaloHora").getValue().toString());
                        medicamentoReceta.setCantidadMedicamento(objSnapshot.child("cantidadMedicamento").getValue().toString());
                        medicamentoReceta.setDias(objSnapshot.child("dias").getValue().toString());

                        // SE ORDENAN POR TIPO DE RECETA
                        if (objSnapshot.child("tipoReceta").getValue().toString().equals("Cardi??logo")) {
                            id_medicamento_cardiologo.add((objSnapshot.child("idMedicamento").getValue().toString()));
                            cardiologo.add("Medicamento: " + medicamentoReceta.getNombreMedicamento() + "\n" + "V??a de administraci??n: "
                                    + medicamentoReceta.getViaAdministracion() + "\n" + "Porci??n: " + medicamentoReceta.getCantidadPorcion()
                                    + medicamentoReceta.getTipoPorcion() + "\n" + "Cantidad: " + medicamentoReceta.getCantidadMedicamento()
                                    + "\n" + "Cada " + medicamentoReceta.getIntervaloHora() + " horas por " + medicamentoReceta.getDias() + " d??as");
                        }
                        else if (objSnapshot.child("tipoReceta").getValue().toString().equals("Dermat??logo")) {
                            id_medicamento_dermatologo.add((objSnapshot.child("idMedicamento").getValue().toString()));
                            dermatologo.add("Medicamento: " + medicamentoReceta.getNombreMedicamento() + "\n" + "V??a de administraci??n: "
                                    + medicamentoReceta.getViaAdministracion() + "\n" + "Porci??n: " + medicamentoReceta.getCantidadPorcion()
                                    + medicamentoReceta.getTipoPorcion() + "\n" + "Cantidad:" + medicamentoReceta.getCantidadMedicamento()
                                    + "\n" + "Cada " + medicamentoReceta.getIntervaloHora() + " horas por " + medicamentoReceta.getDias() + " d??as");
                        }
                        else if (objSnapshot.child("tipoReceta").getValue().toString().equals("Ur??logo")) {
                            id_medicamento_urologo.add((objSnapshot.child("idMedicamento").getValue().toString()));
                            urologo.add("Medicamento: " + medicamentoReceta.getNombreMedicamento() + "\n" + "V??a de administraci??n: "
                                    + medicamentoReceta.getViaAdministracion() + "\n" + "Porci??n: " + medicamentoReceta.getCantidadPorcion()
                                    + medicamentoReceta.getTipoPorcion() + "\n" + "Cantidad:" + medicamentoReceta.getCantidadMedicamento()
                                    + "\n" + "Cada " + medicamentoReceta.getIntervaloHora() + " horas por " + medicamentoReceta.getDias() + " d??as");
                        }
                        else if (objSnapshot.child("tipoReceta").getValue().toString().equals("Onc??logo")) {
                            id_medicamento_oncologo.add((objSnapshot.child("idMedicamento").getValue().toString()));
                            oncologo.add("Medicamento: " + medicamentoReceta.getNombreMedicamento() + "\n" + "V??a de administraci??n: "
                                    + medicamentoReceta.getViaAdministracion() + "\n" + "Porci??n: " + medicamentoReceta.getCantidadPorcion()
                                    + medicamentoReceta.getTipoPorcion() + "\n" + "Cantidad:" + medicamentoReceta.getCantidadMedicamento()
                                    + "\n" + "Cada " + medicamentoReceta.getIntervaloHora() + " horas por " + medicamentoReceta.getDias() + " d??as");
                        }
                        else if (objSnapshot.child("tipoReceta").getValue().toString().equals("Neur??logo")) {
                            id_medicamento_neurologo.add((objSnapshot.child("idMedicamento").getValue().toString()));
                            neurologo.add("Medicamento: " + medicamentoReceta.getNombreMedicamento() + "\n" + "V??a de administraci??n: "
                                    + medicamentoReceta.getViaAdministracion() + "\n" + "Porci??n: " + medicamentoReceta.getCantidadPorcion()
                                    + medicamentoReceta.getTipoPorcion() + "\n" + "Cantidad:" + medicamentoReceta.getCantidadMedicamento()
                                    + "\n" + "Cada " + medicamentoReceta.getIntervaloHora() + " horas por " + medicamentoReceta.getDias() + " d??as");
                        }
                        else if (objSnapshot.child("tipoReceta").getValue().toString().equals("Psiquiatr??a")) {
                            id_medicamento_psiquiatria.add((objSnapshot.child("idMedicamento").getValue().toString()));
                            psiquiatria.add("Medicamento: " + medicamentoReceta.getNombreMedicamento() + "\n" + "V??a de administraci??n: "
                                    + medicamentoReceta.getViaAdministracion() + "\n" + "Porci??n: " + medicamentoReceta.getCantidadPorcion()
                                    + medicamentoReceta.getTipoPorcion() + "\n" + "Cantidad:" + medicamentoReceta.getCantidadMedicamento()
                                    + "\n" + "Cada " + medicamentoReceta.getIntervaloHora() + " horas por " + medicamentoReceta.getDias() + " d??as");
                        }
                        else if (objSnapshot.child("tipoReceta").getValue().toString().equals("M??dico familiar")) {
                            id_medicamento_medicoFamiliar.add((objSnapshot.child("idMedicamento").getValue().toString()));
                            medicoFamiliar.add("Medicamento: " + medicamentoReceta.getNombreMedicamento() + "\n" + "V??a de administraci??n: "
                                    + medicamentoReceta.getViaAdministracion() + "\n" + "Porci??n: " + medicamentoReceta.getCantidadPorcion()
                                    + medicamentoReceta.getTipoPorcion() + "\n" + "Cantidad:" + medicamentoReceta.getCantidadMedicamento()
                                    + "\n" + "Cada " + medicamentoReceta.getIntervaloHora() + " horas por " + medicamentoReceta.getDias() + " d??as");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        lista_medicamento.clear();
        id_medicamento_receta.clear();

        // LOS ID DE CDE MEDICAMENTO PARA OBTENER SUS DATOS
        id_medicamento_receta.put("Cardi??logo", id_medicamento_cardiologo);
        id_medicamento_receta.put("Ur??logo", id_medicamento_urologo);
        id_medicamento_receta.put("Dermat??logo", id_medicamento_dermatologo);
        id_medicamento_receta.put("Onc??logo", id_medicamento_oncologo);
        id_medicamento_receta.put("Neur??logo", id_medicamento_neurologo);
        id_medicamento_receta.put("Psiquiatr??a", id_medicamento_psiquiatria);
        id_medicamento_receta.put("M??dico familiar", id_medicamento_medicoFamiliar);

        // TITULOS
        lista_tipo_receta.add("Cardi??logo");
        lista_tipo_receta.add("Ur??logo");
        lista_tipo_receta.add("Dermat??logo");
        lista_tipo_receta.add("Onc??logo");
        lista_tipo_receta.add("Neur??logo");
        lista_tipo_receta.add("Psiquiatr??a");
        lista_tipo_receta.add("M??dico familiar");


        // LA INFORMACION QUE SE MOSTRARA EN EL EXPANDABLELISTVIEW
        lista_medicamento.put(lista_tipo_receta.get(0), cardiologo);
        lista_medicamento.put(lista_tipo_receta.get(1), urologo);
        lista_medicamento.put(lista_tipo_receta.get(2), dermatologo);
        lista_medicamento.put(lista_tipo_receta.get(3), oncologo);
        lista_medicamento.put(lista_tipo_receta.get(4), neurologo);
        lista_medicamento.put(lista_tipo_receta.get(5), psiquiatria);
        lista_medicamento.put(lista_tipo_receta.get(6), medicoFamiliar);

        adaptadorTipoReceta = new AdaptadorTipoReceta(this, lista_tipo_receta, lista_medicamento, id_medicamento_receta);
        listaReceta.setAdapter(adaptadorTipoReceta);

    }
}
