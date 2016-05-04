package danielstaal.pset3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
/**
 * Created by Daniel Staal on 4/25/2016.
 *
 * The only activity in this program. Uses a arrayList and listView the show a to-do List
 * On restore the arraylist is recovered using a sql database
 */
public class MainActivity extends AppCompatActivity {

    ArrayList<String> listItems = new ArrayList<String>();
    ListView lv;
    EditText et;
    ArrayAdapter<String> adapter;
    DBhelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DBhelper(this);
        listItems = database.read();

        setListview();

        setupLongClickListener();
    }

    /*
     * setting up the longClickListener to remove an item from the database
     */
    private void setupLongClickListener(){
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v,
                                           int pos, long id) {
                // delete item from sql
                database.delete(listItems.get(pos));
                // remove item from listItems
                listItems.remove(pos);
                adapter.notifyDataSetChanged();

                return true;
            }
        });
    }

    /*
     * function to initially set the listView
     */
    private void setListview(){
        et = (EditText)findViewById(R.id.newItem);
        lv = (ListView)findViewById(R.id.itemlist);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        lv.setAdapter(adapter);
        lv.setTextFilterEnabled(true);
    }

    /*
     * add item to the database list of items
     */
    public void addItem(View v){
        // add item to listItems
        String item = et.getText().toString();
        listItems.add(item);
        et.setText("");
        adapter.notifyDataSetChanged();

        // add item to sql
        database.create(item);
    }
}