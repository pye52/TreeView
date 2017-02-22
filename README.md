# TreeView
RecyclerView用多层级TreeView，如果层级比较少推荐使用[Brvah](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki/%E5%88%86%E7%BB%84%E7%9A%84%E4%BC%B8%E7%BC%A9%E6%A0%8F)。直接上图

![image](https://github.com/pye52/TreeView/blob/master/treeviewGif.gif)

适用Json结构

```json
[
    {
        "id": 1,
        "title": "A1",
        "level": 1,
        "pid": 0
    },
    {
        "id": 2,
        "title": "B1",
        "level": 2,
        "pid": 1
    },
    {
        "id": 3,
        "title": "C1",
        "level": 3,
        "pid": 2
    },
    {
        "id": 4,
        "title": "C2",
        "level": 3,
        "pid": 2
    },
    {
        "id": 5,
        "title": "C3",
        "level": 3,
        "pid": 2
    },
    {
        "id": 6,
        "title": "B2",
        "level": 2,
        "pid": 1
    },
    {
        "id": 7,
        "title": "C4",
        "level": 3,
        "pid": 6
    },
    {
        "id": 8,
        "title": "C5",
        "level": 3,
        "pid": 6
    },
    {
        "id": 9,
        "title": "D1",
        "level": 4,
        "pid": 5
    },
    {
        "id": 10,
        "title": "E1",
        "level": 5,
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
// 若不在初始化时指定数据，则会等到执行setNodes时才会有数据
adapter.setNodes(list);
recyclerview.setAdapter(adapter);
```

