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

## development note

2024.10.03
채팅창에서 이미지 선택을 위한 다이얼로그용 갤러리 기능 추가 