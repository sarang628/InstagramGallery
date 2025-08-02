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

2025.08.02
구글 정책으로 사진 접근을 위한 권한 추가 시 앱 업데이트 불가
권한 없이 사진 접근 할 수 있도록 작업 진행