# GridRecyclerView
Simplify the creation of a grid view with section headers in your Android app using the `GridRecyclerView` library. This library leverages the Android `RecyclerView` under the hood, providing flexibility and ease of use.

<img width="280px" height="450px" src="https://github.com/koros/gridrecyclerview/blob/master/docs/gridrecyclerview.gif"><img>

## Usage

Add jitpack repository to your Android project, in the `build.gradle` at the end of repositories add the following:

``` javascript
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' } // <-- Add this
}
```


Add the `gridrecyclerview` library to your project

``` yaml
implementation 'com.github.koros:gridrecyclerview:1.0.1'
```

Once the library is added, follow these steps to create a grid view:

### 1. Implement the GridRecyclerViewHelper
Begin by creating a helper class that implements `GridRecyclerViewHelper`. This class manages the creation of headers, the binding of header data, the setup of grid views, and the creation of grid view holders.

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
            case STUDIO:
                return (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.studio_view, parent, false);
            case DIRECTOR:
                return (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.director_view, parent, false);
            default:
                throw new IllegalArgumentException("Unknown Header Key Value");
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
            case STUDIO:
                return new StudioViewHolder(parent);
            case DIRECTOR:
                return new DirectorViewHolder(parent);
            default:
                throw new IllegalArgumentException("Unknown Header Key Value");
        }
    }
}

```

The `GridHeader` class serves as a Plain Old Java Object (POJO) holding information about grid section headers.

### 2. Implement ViewHolder for Each Grid Cell
Next, implement the `GridCellViewHolder` interface to efficiently bind data to each type of grid cell.

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

