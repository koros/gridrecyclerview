# GridRecyclerView

[![Build](https://github.com/koros/gridrecyclerview/actions/workflows/build.yml/badge.svg)](https://github.com/koros/gridrecyclerview/actions/workflows/build.yml)
[![JitPack](https://jitpack.io/v/koros/gridrecyclerview.svg)](https://jitpack.io/#koros/gridrecyclerview)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

`GridRecyclerView` is an Android library for sectioned grids where each section can have its own header, item type, and column count. It is Compose-first for new apps and still includes the original RecyclerView adapter API for existing View-based integrations.

Use it when a screen needs sections such as:

- one-column categories
- two-column products or movies
- three-column people, tags, or compact cards
- optional headers for empty sections
- custom click handling for cells, headers, subtitles, icons, or actions

<img width="280px" height="450px" src="https://raw.githubusercontent.com/koros/gridrecyclerview/master/docs/gridrecyclerview.gif" alt="GridRecyclerView sample">

## Contents

- [Requirements](#requirements)
- [Installation](#installation)
- [Quick Start With Compose](#quick-start-with-compose)
- [Step By Step Compose Integration](#step-by-step-compose-integration)
- [Click Handling](#click-handling)
- [Empty Sections](#empty-sections)
- [Legacy RecyclerView API](#legacy-recyclerview-api)
- [API Reference](#api-reference)
- [Sample App](#sample-app)
- [Build, Test, And API Governance](#build-test-and-api-governance)
- [CI/CD And Releases](#cicd-and-releases)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

## Requirements

- Android Gradle Plugin `9.3.1`
- Gradle `9.5.0`
- Java `17` for normal Android compilation
- Compile SDK `37`
- Min SDK `23`
- Kotlin `2.2.10`
- Jetpack Compose BOM `2026.08.00`

The published artifact includes the Compose renderer and the legacy RecyclerView adapter. New integrations should use the Compose API unless they are deliberately staying on View-based screens.

## Installation

### 1. Add JitPack

In your root `settings.gradle`:

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = 'https://jitpack.io' }
    }
}
```

Kotlin DSL:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```

### 2. Add The Dependency

Use the latest version shown by the JitPack badge or the GitHub Releases page.

Groovy:

```groovy
dependencies {
    implementation 'com.github.koros:gridrecyclerview:<latest-version>'
}
```

Kotlin DSL:

```kotlin
dependencies {
    implementation("com.github.koros:gridrecyclerview:<latest-version>")
}
```

For local development inside this repository:

```groovy
dependencies {
    implementation project(':gridrecyclerview')
}
```

### 3. Enable Compose In Your App

If your app is not already using Compose, enable it in the consuming app module:

```groovy
android {
    buildFeatures {
        compose true
    }
}
```

Add your app's preferred Compose Material/UI dependencies as usual. The library exposes layout primitives and does not force a visual design system on your item cards.

## Quick Start With Compose

Create section data with `GridDescriptor`, then render it with `GridRecyclerView`.

```kotlin
data class SectionHeader(
    val title: String,
    val subtitle: String? = null,
    val type: SectionType
)

enum class SectionType {
    GENRE,
    MOVIE,
    ACTOR
}

val gridItems = linkedMapOf(
    SectionHeader("Genres", "Browse by category", SectionType.GENRE) to GridDescriptor(1, genres),
    SectionHeader("Movies", "Popular picks", SectionType.MOVIE) to GridDescriptor(2, movies),
    SectionHeader("Actors", "Featured people", SectionType.ACTOR) to GridDescriptor(3, actors)
)

GridRecyclerView(
    gridItems = gridItems,
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    headerContent = { header ->
        Column {
            Text(text = header.title)
            header.subtitle?.let { Text(text = it) }
        }
    },
    gridItemContent = { header, item ->
        when (header.type) {
            SectionType.GENRE -> GenreCard(item as Genre)
            SectionType.MOVIE -> MovieCard(item as Movie)
            SectionType.ACTOR -> ActorCard(item as Actor)
        }
    }
)
```

The map order is the display order. Use `linkedMapOf(...)` when section order matters.

## Step By Step Compose Integration

### Step 1. Model Your Sections

The map key can be any type. A small data class works well because it can hold the visible header text and a stable type key.

```kotlin
data class GridSection(
    val title: String,
    val subtitle: String? = null,
    val type: GridSectionType
)

enum class GridSectionType {
    PRODUCTS,
    BRANDS,
    CATEGORIES
}
```

### Step 2. Create `GridDescriptor` Values

Each descriptor needs:

- `numberOfColumns`: must be greater than `0`
- `items`: must be a non-null list

```kotlin
val sections: Map<GridSection, GridDescriptor<*>> = linkedMapOf(
    GridSection("Categories", "Shop by interest", GridSectionType.CATEGORIES) to
        GridDescriptor(1, categories),
    GridSection("Products", "Trending now", GridSectionType.PRODUCTS) to
        GridDescriptor(2, products),
    GridSection("Brands", "Popular stores", GridSectionType.BRANDS) to
        GridDescriptor(3, brands)
)
```

### Step 3. Render The Grid

`GridRecyclerView` is backed by a `LazyColumn`. Headers and grid rows are flattened internally, so you only provide section data and composables.

```kotlin
@Composable
fun ShopGridScreen(
    sections: Map<GridSection, GridDescriptor<*>>
) {
    GridRecyclerView(
        gridItems = sections,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        headerContent = { section ->
            SectionHeader(section)
        },
        gridItemContent = { section, item ->
            SectionItem(section, item)
        }
    )
}
```

### Step 4. Render Typed Items

Because a sectioned grid can contain different item types, `gridItemContent` receives `Any?`. Use the section key to cast safely.

```kotlin
@Composable
private fun SectionItem(
    section: GridSection,
    item: Any?
) {
    when (section.type) {
        GridSectionType.CATEGORIES -> CategoryRow(item as Category)
        GridSectionType.PRODUCTS -> ProductCard(item as Product)
        GridSectionType.BRANDS -> BrandCard(item as Brand)
    }
}
```

For a more defensive production UI, check the type before rendering:

```kotlin
when (section.type) {
    GridSectionType.PRODUCTS -> {
        val product = item as? Product
        if (product != null) {
            ProductCard(product)
        }
    }
    else -> Unit
}
```

### Step 5. Update Data Normally

Pass a new map or updated descriptors from your state holder. Compose will recalculate the internal row metadata when `gridItems` changes.

```kotlin
val sections by viewModel.sections.collectAsStateWithLifecycle()

GridRecyclerView(
    gridItems = sections,
    headerContent = { SectionHeader(it) },
    gridItemContent = { section, item -> SectionItem(section, item) }
)
```

## Click Handling

The library deliberately lets your UI own click behavior. This keeps the event code close to the typed model and avoids special cases for headers, subtitles, and nested controls.

### Cell Clicks

Add `Modifier.clickable` to the composable you render for the cell:

```kotlin
GridRecyclerView(
    gridItems = sections,
    headerContent = { section ->
        SectionHeader(section)
    },
    gridItemContent = { section, item ->
        when (section.type) {
            GridSectionType.PRODUCTS -> {
                val product = item as Product
                ProductCard(
                    product = product,
                    modifier = Modifier.clickable {
                        onProductClick(section, product)
                    }
                )
            }
            GridSectionType.BRANDS -> {
                val brand = item as Brand
                BrandCard(
                    brand = brand,
                    modifier = Modifier.clickable {
                        onBrandClick(section, brand)
                    }
                )
            }
            GridSectionType.CATEGORIES -> {
                val category = item as Category
                CategoryRow(
                    category = category,
                    modifier = Modifier.clickable {
                        onCategoryClick(section, category)
                    }
                )
            }
        }
    }
)
```

### Header And Subheader Clicks

Headers are rendered by your `headerContent`, so clicks work the same way:

```kotlin
headerContent = { section ->
    Column {
        Text(
            text = section.title,
            modifier = Modifier.clickable { onHeaderClick(section) }
        )

        section.subtitle?.let { subtitle ->
            Text(
                text = subtitle,
                modifier = Modifier.clickable { onSubHeaderClick(section) }
            )
        }
    }
}
```

This also handles edge cases such as only the subheader being clickable, a trailing action button being clickable, or the whole header row being clickable.

## Empty Sections

By default, empty sections are hidden completely. Set `showHeadersForEmptySections = true` when you want to show a header even when the item list is empty.

```kotlin
GridRecyclerView(
    gridItems = sections,
    showHeadersForEmptySections = true,
    headerContent = { section ->
        SectionHeader(section)
    },
    gridItemContent = { section, item ->
        SectionItem(section, item)
    }
)
```

This is useful for loading states, "no results" states, or screens where every section heading must remain visible.

## Legacy RecyclerView API

Existing View-based screens can keep using:

- `GridRecyclerViewAdapter`
- `GridRecyclerViewHelper`
- `GridCellViewHolder`
- `GridDescriptor`

### 1. Create Section Data

```java
Map<SectionHeader, GridDescriptor<?>> sections = new LinkedHashMap<>();
sections.put(new SectionHeader("Movies", SectionType.MOVIE), new GridDescriptor<>(2, movies));
sections.put(new SectionHeader("Actors", SectionType.ACTOR), new GridDescriptor<>(3, actors));
```

### 2. Implement `GridRecyclerViewHelper`

```java
GridRecyclerViewHelper<SectionHeader> helper = new GridRecyclerViewHelper<SectionHeader>() {
    @NonNull
    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.section_header, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(@NonNull RecyclerView.ViewHolder holder, SectionHeader header) {
        ((HeaderViewHolder) holder).bind(header);
    }

    @NonNull
    @Override
    public ViewGroup getGridView(SectionHeader key, @NonNull ViewGroup parent) {
        LinearLayout row = new LinearLayout(parent.getContext());
        row.setOrientation(LinearLayout.HORIZONTAL);
        return row;
    }

    @NonNull
    @Override
    public GridCellViewHolder getGridViewHolder(SectionHeader key, @NonNull ViewGroup parent) {
        if (key.getType() == SectionType.MOVIE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_cell, parent, false);
            return new MovieViewHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.actor_cell, parent, false);
        return new ActorViewHolder(view);
    }
};
```

### 3. Attach The Adapter

```java
GridRecyclerViewAdapter<SectionHeader> adapter =
        new GridRecyclerViewAdapter<>(helper, sections);

recyclerView.setAdapter(adapter);
```

To update the grid:

```java
adapter.setGridItems(updatedSections);
adapter.setShowHeadersForEmptySections(true);
```

For legacy clicks, set listeners inside your own `ViewHolder` implementations. That gives each holder direct access to its bound view and item.

## API Reference

### `GridDescriptor<T>`

Describes one section:

- `numberOfColumns`: positive column count for that section
- `items`: non-null list of section items

Invalid column counts throw `IllegalArgumentException`. Null item lists throw `NullPointerException`.

### `GridRecyclerView`

Compose renderer for sectioned grids.

Important parameters:

- `gridItems`: ordered map of section keys to descriptors
- `showHeadersForEmptySections`: renders header-only rows for empty sections when true
- `contentPadding`: padding around the internal `LazyColumn`
- `verticalArrangement`: spacing between headers and grid rows
- `horizontalArrangement`: spacing between cells in each row
- `headerContent`: composable for section headers
- `gridItemContent`: composable for each populated grid cell

### `GridRecyclerViewAdapter`

RecyclerView adapter for View-based screens. It flattens sections into header rows and grid rows, then delegates view creation/binding to `GridRecyclerViewHelper`.

### `GridUtils`

Utility helpers for legacy integrations:

- `getItem(...)`: retrieve an item from a section
- `getItem(..., Class<T>)`: retrieve and type-check an item
- `createSublist(...)`: create a bounds-clamped row slice

## Sample App

The sample app shows:

- one-column, two-column, and three-column sections
- Compose item cards
- header and subheader rendering
- item click handling inside the cell composable
- drawable fallback behavior

Main sample file: [MainActivity.kt](sampleapp/src/main/java/com/github/koros/sampleapp/MainActivity.kt)

Run it locally:

```sh
ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew :sampleapp:installDebug
```

Or build the APK:

```sh
ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew :sampleapp:assembleDebug
```

## Build, Test, And API Governance

Run the full local verification:

```sh
ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew build
```

Run the API governance check:

```sh
ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew checkApi
```

If a public API change is intentional, regenerate the baseline:

```sh
ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew :gridrecyclerview:generateApi
```

Then review and commit [api/current.txt](api/current.txt).

API governance is handled with Metalava signature checks. CI fails when public API changes are not reflected in `api/current.txt`.

## CI/CD And Releases

GitHub Actions runs on pushes and pull requests. The build pipeline:

- installs Java and the Android SDK
- runs `checkApi`, unit tests, lint, and debug/release builds
- publishes the release AAR to Maven Local as a pipeline validation step
- uploads the generated AAR, sample APKs, and build reports as workflow artifacts

Tag pushes create GitHub Releases automatically. To publish a release:

```sh
git tag v1.1.0
git push origin v1.1.0
```

The release workflow strips the leading `v`, builds with `RELEASE_VERSION=1.1.0`, creates or updates the matching GitHub Release, and attaches the generated AAR plus sample APKs.

Consumers using JitPack can depend on the matching tag version:

```groovy
implementation 'com.github.koros:gridrecyclerview:1.1.0'
```

## Troubleshooting

### JitPack Cannot Resolve The Dependency

Check that:

- `maven { url = 'https://jitpack.io' }` is present in `dependencyResolutionManagement`
- the version matches a published Git tag or release
- the dependency coordinate is `com.github.koros:gridrecyclerview:<version>`

### The Section Order Looks Wrong

Use an insertion-ordered map such as `linkedMapOf(...)` in Kotlin or `LinkedHashMap` in Java. A plain hash map does not guarantee display order.

### A Section Crashes With An Invalid Column Count

`GridDescriptor` requires `numberOfColumns > 0`.

```kotlin
GridDescriptor(numberOfColumns = 2, items = products)
```

### A Cast Fails In `gridItemContent`

Use the section key to choose the correct item type. If items can be mixed or loaded dynamically, prefer safe casts:

```kotlin
val product = item as? Product
if (product != null) {
    ProductCard(product)
}
```

### Empty Sections Do Not Show Headers

Set `showHeadersForEmptySections = true`.

### `checkApi` Fails After A Public API Change

Run:

```sh
ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew :gridrecyclerview:generateApi
```

Review `api/current.txt`. Commit it only when the API change is intentional.

## Contributing

Keep changes small and focused:

- update tests for behavior changes
- update README examples for API changes
- run `./gradlew build`
- run `./gradlew checkApi` when public APIs change
- keep `api/current.txt` in sync with intentional public API changes

## License

GridRecyclerView is available under the [MIT License](LICENSE).
