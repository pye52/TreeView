package com.kanade.treeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kanade.treeadapter.Node;
import com.kanade.treeadapter.TreeAdapter;
import com.kanade.treeadapter.TreeItemClickListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.type;
import static android.R.id.list;

public class MainActivity extends AppCompatActivity {
    private List<User> list;
    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String testStr = "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"title\": \"A1\",\n" +
                "        \"pid\": 0\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"title\": \"B1\",\n" +
                "        \"pid\": 1\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 3,\n" +
                "        \"title\": \"C1\",\n" +
                "        \"pid\": 2\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 4,\n" +
                "        \"title\": \"C2\",\n" +
                "        \"pid\": 2\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 5,\n" +
                "        \"title\": \"C3\",\n" +
                "        \"pid\": 2\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 6,\n" +
                "        \"title\": \"B2\",\n" +
                "        \"pid\": 1\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 7,\n" +
                "        \"title\": \"C4\",\n" +
                "        \"pid\": 6\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 8,\n" +
                "        \"title\": \"C5\",\n" +
                "        \"pid\": 6\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 9,\n" +
                "        \"title\": \"D1\",\n" +
                "        \"pid\": 5\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 10,\n" +
                "        \"title\": \"E1\",\n" +
                "        \"pid\": 9\n" +
                "    }\n" +
                "]";

        Type type = new TypeToken<List<User>>(){}.getType();
        list = new Gson().fromJson(testStr, type);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        final TreeAdapter<User> adapter = new TreeAdapter<>(this);

        final List<User> childs = new ArrayList<>();
        childs.add(new User(11, 3, "C11"));
        childs.add(new User(12, 3, "C12"));
        childs.add(new User(13, 3, "C13"));

        adapter.setNodes(list);
        adapter.setListener(new TreeItemClickListener() {
            @Override
            public void OnClick(Node node) {
                if (node.getId() == 3) {
                    adapter.addChildrenById(3, childs);
                }
                Toast.makeText(MainActivity.this, node.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        rv.setAdapter(adapter);
    }
}
