# GridRecyclerView

[![Build](https://github.com/koros/gridrecyclerview/actions/workflows/build.yml/badge.svg)](https://github.com/koros/gridrecyclerview/actions/workflows/build.yml)
[![JitPack](https://jitpack.io/v/koros/gridrecyclerview.svg)](https://jitpack.io/#koros/gridrecyclerview)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

`GridRecyclerView` is an Android library for sectioned grids where each section can have its own header and column count. The library now provides a Compose-first renderer while keeping the legacy RecyclerView adapter API available for existing apps.

<img width="280px" height="450px" src="https://raw.githubusercontent.com/koros/gridrecyclerview/master/docs/gridrecyclerview.gif" alt="GridRecyclerView sample">

## Requirements

- Android Gradle Plugin `9.3.1`
- Gradle `9.5.0`
- Java `17`
- Compile SDK `37`
- Min SDK `23`
- Jetpack Compose BOM `2026.08.00`

## Installation

Add JitPack to dependency resolution:

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

Add the library:

```groovy
dependencies {
    implementation 'com.github.koros:gridrecyclerview:<version>'
}
```

For local development, use the included module:

```groovy
dependencies {
    implementation project(':gridrecyclerview')
}
```

## Compose Usage

Create a map of section headers to `GridDescriptor` values. The descriptor controls the section column count and its item list.

```kotlin
val gridItems = linkedMapOf(
    GridHeader("Genres", HeaderKey.GENRE) to GridDescriptor(1, genres),
    GridHeader("Movies", HeaderKey.MOVIE) to GridDescriptor(2, movies),
    GridHeader("Actors", HeaderKey.ACTOR) to GridDescriptor(3, actors)
)
```

Render it with the Compose `GridRecyclerView` function:

```kotlin
GridRecyclerView(
    gridItems = gridItems,
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    headerContent = { header ->
        Text(text = header.header)
    },
    gridItemContent = { header, item ->
        when (header.key) {
            HeaderKey.GENRE -> GenreCard(item as Genre)
            HeaderKey.MOVIE -> MovieCard(item as Movie)
            HeaderKey.ACTOR -> ActorCard(item as Actor)
        }
    }
)
```

Handle grid cell clicks with `onGridItemClick`:

```kotlin
GridRecyclerView(
    gridItems = gridItems,
    headerContent = { header ->
        Text(text = header.header)
    },
    gridItemContent = { section, item ->
        MovieCard(item as Movie)
    },
    onGridItemClick = { section, item ->
        // section identifies the clicked section, item is the clicked cell data
    }
)
```

Header and subheader clicks remain owned by your custom header UI. In Compose, add `Modifier.clickable` inside `headerContent` to the exact title, subtitle, icon, or action you want to handle. In the legacy RecyclerView API, set those click listeners inside your header `ViewHolder`.

See the Compose sample in [MainActivity.kt](sampleapp/src/main/java/com/github/koros/sampleapp/MainActivity.kt).

## Legacy RecyclerView API

Existing RecyclerView integrations can continue to use:

- `GridRecyclerViewAdapter`
- `GridRecyclerViewHelper`
- `GridCellViewHolder`
- `GridDescriptor`

The legacy API is covered by the checked-in API signature file and remains visible in [api/current.txt](api/current.txt).

Legacy grid cell clicks can be handled with `OnGridItemClickListener`:

```java
GridRecyclerViewAdapter<GridHeader> adapter = new GridRecyclerViewAdapter<>(
    helper,
    gridItems,
    false,
    (section, item) -> {
        // handle clicked grid cell
    }
);
```

## Build And Test

Run the full local verification:

```sh
ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew build
```

Run only the API governance check:

```sh
ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew checkApi
```

If a public API change is intentional, regenerate the baseline:

```sh
ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew :gridrecyclerview:generateApi
```

## CI/CD

GitHub Actions runs on pushes and pull requests. The pipeline:

- installs Java 17 and the Android SDK
- runs `checkApi`, unit tests, lint, and full debug/release builds
- publishes the release AAR to Maven Local as a pipeline validation step
- uploads the generated AAR, sample APKs, and build reports as workflow artifacts

Tag pushes also create GitHub Releases automatically. To release a new version:

```sh
git tag v1.1.0
git push origin v1.1.0
```

The release workflow strips the leading `v`, builds with `RELEASE_VERSION=1.1.0`, creates or updates the matching GitHub Release, and attaches the generated AAR plus sample APKs.

API governance is handled with Metalava signature checks. CI fails when public API changes are not reflected in `api/current.txt`.
