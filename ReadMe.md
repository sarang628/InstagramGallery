## USAGE

```
dependencies {
	        implementation 'com.github.sarang628:InstagramGallery:6a985cc9f7'
	        implementation 'com.github.sarang628:MediaContentResolver:54ff69ee3a'
	}
```


```
@InstallIn(SingletonComponent::class)
@Module
class MediaContentResolverModule {
    @Provides
    fun ProvideMediaContentResolver(@ApplicationContext context: Context): MediaContentResolver {
        return MediaContentResolver.newInstance(context = context)
    }
}
```

```
Column {
                GalleryScreen(onNext = {
                    //selected images
                    Log.d("MainActivity", TextUtils.join(",", it))
                }, onClose = {
                    
                })
            }
```