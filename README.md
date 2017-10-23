# TreeView
RecyclerView用多层级TreeView，如果层级比较少推荐使用[Brvah](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/%E5%88%86%E7%BB%84%E7%9A%84%E4%BC%B8%E7%BC%A9%E6%A0%8F)。直接上图

![image](https://github.com/pye52/TreeView/blob/master/treeviewGif.gif)

引入：

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
	compile 'com.github.pye52:TreeView:0.2.3'
}
```

Json例子结构

```json
[
    {
        "id": 1,
        "title": "A1",
        "pid": 0
    },
    {
        "id": 2,
        "title": "B1",
        "pid": 1
    },
    {
        "id": 3,
        "title": "C1",
        "pid": 2
    },
    {
        "id": 4,
        "title": "C2",
        "pid": 2
    },
    {
        "id": 5,
        "title": "C3",
        "pid": 2
    },
    {
        "id": 6,
        "title": "B2",
        "pid": 1
    },
    {
        "id": 7,
        "title": "C4",
        "pid": 6
    },
    {
        "id": 8,
        "title": "C5",
        "pid": 6
    },
    {
        "id": 9,
        "title": "D1",
        "pid": 5
    },
    {
        "id": 10,
        "title": "E1",
        "pid": 9
    }
]
```

使用：

```java
public class User implements RvTree{
  ...
}

Type type = new TypeToken<List<User>>(){}.getType();
List<User> list = new Gson().fromJson(testStr, type);
// TreeAdapter<User> adapter = new TreeAdapter<>(this, list);
TreeAdapter<User> adapter = new TreeAdapter<>(this);
// 若不在初始化时指定数据，则需要手动调用notifyDataSetChanged来刷新列表
adapter.setNodes(list);
adapter.notifyDataSetChanged();

recyclerview.setAdapter(adapter);

 final List<User> C1Childs = new ArrayList<>();
 C1Childs.add(new User(11, 3, "C11"));
 C1Childs.add(new User(12, 3, "C12"));
 C1Childs.add(new User(13, 3, "C13"));
 adapter.setListener(new TreeItemClickListener() {
     @Override
     public void OnClick(Node node) {
         if (node.getId() == 3) {
             // 向id为3的节点下面添加数据并展开
             adapter.addChildrenById(3, C1Childs);
         }
         Toast.makeText(MainActivity.this, node.getName(), Toast.LENGTH_SHORT).show();
     }
 });
```

