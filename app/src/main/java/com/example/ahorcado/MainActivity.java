package com.example.ahorcado;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText etInput;
    private Button btnCheck, btnPista, btnReiniciar;
    private TextView palabraOculta, categoriaSeleccionada;
    private ImageView imageViewAhorcado;

    private String palabraCorrecta;
    private char[] palabraMostrada;
    private int errores = 0;
    private final int maxErrores = 6;
    private final int[] imagenesAhorcado = {
            R.drawable.ahorcado_01,
            R.drawable.ahorcado_02,
            R.drawable.ahorcado_03,
            R.drawable.ahorcado_04,
            R.drawable.ahorcado_05,
            R.drawable.ahorcado_06,
            R.drawable.ahorcado_07
    };

    // Mapa de palabras y sus pistas
    private HashMap<String, String> palabrasYPistas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        etInput = findViewById(R.id.etInput);
        btnCheck = findViewById(R.id.btnCheck);
        btnPista = findViewById(R.id.btnPista);
        btnReiniciar = findViewById(R.id.btnReiniciar);
        palabraOculta = findViewById(R.id.palabraOculta);
        imageViewAhorcado = findViewById(R.id.imageViewAhorcado);
        categoriaSeleccionada = findViewById(R.id.categoria_seleccionada);

        // Inicializar mapa de palabras y pistas
        palabrasYPistas = new HashMap<>();
        palabrasYPistas.put("PROGRAMACION", "Actividad de escribir código para crear software.");
        palabrasYPistas.put("ALGORITMO", "Conjunto de instrucciones para resolver un problema.");
        palabrasYPistas.put("BASEDEDATOS", "Colección organizada de datos.");
        palabrasYPistas.put("REDES", "Conjunto de dispositivos interconectados.");
        palabrasYPistas.put("SEGURIDAD", "Protección contra accesos no autorizados.");
        palabrasYPistas.put("INTERNET", "Red global de comunicación.");
        palabrasYPistas.put("SOFTWARE", "Conjunto de programas y aplicaciones.");
        palabrasYPistas.put("HARDWARE", "Componentes físicos de una computadora.");

        iniciarJuego();

        // Listener del botón de pista
        btnPista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarPista();
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarLetra();
            }
        });

        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarJuego();
            }
        });
    }

    private void iniciarJuego() {
        errores = 0;
        Random random = new Random();
        Object[] palabras = palabrasYPistas.keySet().toArray();
        palabraCorrecta = (String) palabras[random.nextInt(palabras.length)];
        palabraMostrada = new char[palabraCorrecta.length()];
        for (int i = 0; i < palabraMostrada.length; i++) {
            palabraMostrada[i] = '_';
        }
        actualizarPalabraOculta();
        imageViewAhorcado.setImageResource(imagenesAhorcado[0]);
        categoriaSeleccionada.setText("Tecnologías de la Información");
    }

    private void mostrarPista() {
        String pista = palabrasYPistas.get(palabraCorrecta);
        categoriaSeleccionada.setText(pista);
    }

    private void comprobarLetra() {
        String input = etInput.getText().toString().toUpperCase();
        etInput.setText(""); // Limpiar el campo de texto

        if (input.length() == 1) {
            char letraIngresada = input.charAt(0);
            boolean acierto = false;

            for (int i = 0; i < palabraCorrecta.length(); i++) {
                if (palabraCorrecta.charAt(i) == letraIngresada) {
                    palabraMostrada[i] = letraIngresada; // Reemplazar guión por la letra correcta
                    acierto = true;
                }
            }

            if (acierto) {
                actualizarPalabraOculta(); // Actualizar el TextView con la nueva palabra
                if (String.valueOf(palabraMostrada).equals(palabraCorrecta)) {
                    palabraOculta.setText("¡Correcto! La palabra es " + palabraCorrecta);
                }
            } else {
                errores++;
                if (errores < maxErrores) {
                    imageViewAhorcado.setImageResource(imagenesAhorcado[errores]);
                } else {
                    imageViewAhorcado.setImageResource(imagenesAhorcado[maxErrores]);
                    palabraOculta.setText("¡Has perdido! La palabra era " + palabraCorrecta);
                }
            }
        }
    }

    private void actualizarPalabraOculta() {
        palabraOculta.setText(String.valueOf(palabraMostrada).replace("", " ").trim());
    }

    private void reiniciarJuego() {
        iniciarJuego();
    }
}
