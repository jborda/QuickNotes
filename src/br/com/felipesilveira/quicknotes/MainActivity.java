package br.com.felipesilveira.quicknotes;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;

public class MainActivity extends ListActivity {
	
	private static final String TAG = "QuickNotesMainActivity";
	private Cursor mCursor;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d(TAG, "Criando a MainActivity");
      
        setContentView(R.layout.main);
        
        Intent i = new Intent(this, WelcomeActivity.class);
        startActivity(i);
        
        Button insertButton = (Button)findViewById(R.id.insert_button);
        insertButton.setOnClickListener(mInsertListener);

        // adicionando um 'Hint' ao Editbox.
        EditText editBox = (EditText)findViewById(R.id.edit_box);
        editBox.setHint("Nova nota...");
        
        mCursor = this.getContentResolver().
                  query(QuickNotesProvider.Notes.CONTENT_URI, null, null, null, null);

        ListAdapter adapter = new SimpleCursorAdapter(
        		              // O primeiro parametro eh o context.
        		              this, 
        		              // O segundo, o layout de cada item. 
        		              R.layout.list_item,
        		              // O terceiro parametro eh o cursor que contem os dados
        		              // a serem mostrados
        		              mCursor,
        		              // o quarto parametro eh um array com as colunas do cursor
        		              // que serao mostradas
        		              new String[] {QuickNotesProvider.Notes.TEXT},
        		              // o quinto parametro eh um array (com o mesmo tamanho
        		              // do anterior) com os elementos que receberao
        		              // os dados.
        		              new int[] {R.id.text});
 
        setListAdapter(adapter);
     }
     
     /*
      *  Definindo um OnClickListener para o bot�o "Inserir"
      */
     private OnClickListener mInsertListener = new OnClickListener() {
          public void onClick(View v) {
        	  EditText editBox = (EditText)findViewById(R.id.edit_box);
              addNote(editBox.getText().toString());
              editBox.setText("");
          }
     };
     
     /*
      * M�todo respons�vel por inserir um registro no content provider
      */
	 protected void addNote(String text) {
		 ContentValues values = new ContentValues();
		 values.put(QuickNotesProvider.Notes.TEXT, text);  

		 getContentResolver().insert(
		      QuickNotesProvider.Notes.CONTENT_URI, values);
	 } 	 
}