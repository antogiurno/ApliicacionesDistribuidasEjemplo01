package com.example.ejemplo01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;



public class GameActivity extends AppCompatActivity {

    private GridView gridView;
    private TaTeTi taTeTi;
    private Button[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        // Intent y jugador
        Intent intent = getIntent();
        String player = intent.getStringExtra("player");

        // Configurar botón de resultado
        Button resultButton = findViewById(R.id.result_button);
        if (resultButton == null) {
            Log.e("GameActivity", "No se pudo inflar el botón 'result_button'");
        } else {
            resultButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // tu código aquí
                }
            });
        }


        // Crear una nueva instancia de TaTeTi con el jugador elegido
        taTeTi = new TaTeTi(player);
        buttons = new Button[9];

        // Configurar botones del tablero
        for (int i = 0; i < 9; i++) {
            int id = getResources().getIdentifier("button_" + i, "id", getPackageName());
            buttons[i] = findViewById(id);

            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    int row = (id / 3) + 1;
                    int col = (id % 3) + 1;
                    TaTeTi.Cell cell = taTeTi.getCell(row, col);
                    if (taTeTi.getCurrentPlayer() == TaTeTi.Player.X) {
                        cell.setState(TaTeTi.CellState.X);
                        ((Button) v).setText("X");
                    } else {
                        cell.setState(TaTeTi.CellState.O);
                        ((Button) v).setText("O");
                    }
                    // Verifica si el juego ha terminado
                    TaTeTi.GameState gameState = taTeTi.getGameState();
                    if (gameState != TaTeTi.GameState.IN_PROGRESS) {
                        // El juego ha terminado, muestra el mensaje correspondiente
                        String message = null;
                        if (gameState == TaTeTi.GameState.TIE) {
                            message = "Empate!";
                        } else if (gameState == TaTeTi.GameState.X_WON) {
                            message = "Ganó X!";
                        } else if (gameState == TaTeTi.GameState.O_WON) {
                            message = "Ganó O!";
                        }

                        // Crea el diálogo con el mensaje de resultado
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setView(R.layout.result_dialog);
                        AlertDialog dialog = builder.create();

                        // Configura el mensaje de resultado en el TextView del diálogo
                        TextView resultTextView = dialog.findViewById(R.id.result_textview);
                        resultTextView.setText(message);

                        // Agrega un listener al botón del diálogo para cerrarlo
                        Button resultButton = (Button) dialog.findViewById(R.id.result_button);
                        resultButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        // Muestra el diálogo
                        dialog.show();
                    }
                }
            });
        }

        gridView = findViewById(R.id.gridview);
        gridView.setAdapter(new MyAdapter(this));
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public class MyAdapter extends BaseAdapter {

        private Context context;

        public MyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.grid_item, null);
            }
            TextView textView = view.findViewById(R.id.textView1);
            TaTeTi.Cell cell = taTeTi.getCell((position / 3) + 1, (position % 3) + 1);
            if (cell.getState() == TaTeTi.CellState.X) {
                textView.setText("X");
            } else if (cell.getState() == TaTeTi.CellState.O) {
                textView.setText("O");
            } else {
                textView.setText("");
            }
            return view;
        }}}
