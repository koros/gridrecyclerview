# GridRecyclerView
`GridRecyclerView` is a native Android library simplifying the display of data in a grid view. The library offers the flexibility to showcase grids with section headers and varying column counts. It efficiently utilizes the Android RecyclerView, ensuring broad device support and easy extensibility.

### Sample Application Screenshot
<img width="280px" height="450px" src="https://raw.githubusercontent.com/koros/gridrecyclerview/master/docs/gridrecyclerview.gif"><img>

## Usage

Add `jitpack` repository to your Android project, In the `build.gradle` file at the end of repositories add the following line:

``` javascript
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' } // <-- Add this
}
```


Add the `gridrecyclerview` library to your project

``` yaml
implementation 'com.github.koros:gridrecyclerview:1.0.2'
```

Once the library is added, follow these steps to create a grid view:

### 1. Implement the GridRecyclerViewHelper
Begin by creating a helper class that implements `GridRecyclerViewHelper` [https://github.com/koros/gridrecyclerview/blob/master/gridrecyclerview/src/main/java/com/github/koros/gridrecyclerview/GridRecyclerViewHelper.java]. This class manages the creation of headers, the binding of header data, the setup of grid views, and the creation of grid view holders.

``` java

public interface GridRecyclerViewHelper<K> {

    RecyclerView.ViewHolder getHeaderViewHolder(@NonNull ViewGroup parent);

    void onBindHeaderViewHolder(@NonNull RecyclerView.ViewHolder holder, K headerItem);

    ViewGroup getGridView(K key, @NonNull ViewGroup parent);

    GridCellViewHolder getGridViewHolder(K key, @NonNull ViewGroup parent);
}

```


In the code snipet above `K` is whichever data type you chose to represent/hold the headers, The sample code uses POJO object `GridHeader` https://github.com/koros/gridrecyclerview/blob/master/sampleapp/src/main/java/com/github/koros/sampleapp/model/GridHeader.java shown below:

``` java
public class GridHeader {
    private String header;
    private String subHeader;
    private HeaderKey key;

    public GridHeader(String header, HeaderKey key) {
        this.header = header;
        this.key = key;
    }
    // getters and setters
}
```

A complete example of `GridRecyclerViewHelper` can be found in the sample application, https://github.com/koros/gridrecyclerview/blob/master/sampleapp/src/main/java/com/github/koros/sampleapp/grid/SampleGridRecyclerViewHelper.java, a sample snippet is shown below:


 ``` Java
public class SampleGridRecyclerViewHelper implements GridRecyclerViewHelper<GridHeader> {
    @NonNull
    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(@NonNull ViewGroup parent) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.header_view, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, GridHeader headerItem) {
        ((HeaderViewHolder) holder).bind(headerItem);
    }

    @NonNull
    @Override
    public ViewGroup getGridView(GridHeader key, @NonNull ViewGroup parent) {
        switch (key.getKey()) {
            case ACTOR:
                return (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.actor_view, parent, false);
            case GENRE:
                return (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_view, parent, false);
            case MOVIE:
                return (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_view, parent, false);
        }
    }

    @NonNull
    @Override
    public GridCellViewHolder getGridViewHolder(GridHeader key, @NonNull ViewGroup parent) {
        switch (key.getKey()) {
            case ACTOR:
                return new ActorViewHolder(parent);
            case GENRE:
                return new GenreViewHolder(parent);
            case MOVIE:
                return new MovieViewHolder(parent);
        }
    }
}

```

### 2. Implement ViewHolder for each Grid cell
Next, implement the `GridCellViewHolder` interface to bind data to each type of grid cell.

``` Java

public abstract class GridCellViewHolder<T> extends RecyclerView.ViewHolder {
    // Implement the bind method to efficiently bind data to the ViewHolder.
}

```

For instance, create a specialized ViewHolder for actor cells:

``` Java
public class ActorViewHolder extends GridCellViewHolder<Actor> {
    private final Context context;
    private final TextView name;
    private final ImageView image;

    public ActorViewHolder(@NonNull View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        this.name = itemView.findViewById(R.id.name);
        this.image = itemView.findViewById(R.id.image);
    }

    @Override
    public void bind(Actor actor) {
        this.name.setText(actor.getName());
        this.image.setImageDrawable(getDrawableFromName(context, actor.getImage()));
    }
}

```

### 3. Initialize the RecyclerView
In your main activity or fragment, set up the `GridRecyclerViewAdapter` to manage your grid view.
The `GridRecyclerViewAdapter` receives 3 parameters:
- GridRecyclerViewHelper
- Data to display in form of Key -> Value map, where key will represent different sections on the grid and value will be list of data to display. The list of data for each section is wrapped in a GridDescriptor [https://github.com/koros/gridrecyclerview/blob/master/gridrecyclerview/src/main/java/com/github/koros/gridrecyclerview/GridDescriptor.java], which also determines how many colums will be displayed per section 
- Boolean flag which determines whether a header for an empty section should be shown, `false` by default


#### Below is a code excerpt from the sample app demonstrating the implementation of GridRecyclerView in your Android application.
https://github.com/koros/gridrecyclerview/blob/master/sampleapp/src/main/java/com/github/koros/sampleapp/MainActivity.java
``` Java

public class MainActivity extends AppCompatActivity {

    private GridRecyclerViewAdapter gridRecyclerViewAdapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        GridRecyclerViewHelper helper = new SampleGridRecyclerViewHelper();

        Map<GridHeader, GridDescriptor<?>> gridItems = new HashMap<>();

        GridDescriptor<Genre> genres = new GridDescriptor<>(1, getSampleGenres());
        gridItems.put(new GridHeader("Genres", HeaderKey.GENRE), genres);

        GridDescriptor<Movie> movies = new GridDescriptor<>(2, getSampleMovies());
        gridItems.put(new GridHeader("Movies", HeaderKey.MOVIE), movies);

        GridDescriptor<Actor> actors = new GridDescriptor<>(3, getSampleActors());
        gridItems.put(new GridHeader("Actors", HeaderKey.ACTOR), actors);

        gridRecyclerViewAdapter = new GridRecyclerViewAdapter(helper, gridItems, false);
        recyclerView.setAdapter(gridRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

```

