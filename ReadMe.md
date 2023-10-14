## USAGE

```
dependencies {
	        implementation 'com.github.sarang628:InstagramGallery:6a985cc9f7'
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