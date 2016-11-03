# CImageLoaderLib

基本用法：
```java  
  
private CImageLoader loader = null;
private CImageLoaderConfig config = null;

config = new CImageLoaderConfig.Builder()
                .setImageCache(DiskCache.getInstance(MainActivity.this))
                .setLoadPolicy(new ReversePolicy())
                .build();
loader = CImageLoader.getInatance();
loader.init(config);

loader.displayImag(url,imageView);

  
```
CImageLoaderConfig 配置：
```java 

//定义缓存类型(可自定义实现接口)
.setImageCache(ImageCache imageCache)

//定义加载中默认显示图片
.setLoadingImage(int resID)

//定义加载失败默认显示图片
.setFailedImage(int resID)

//定义加载策略(可自定义实现接口)
.setLoadPolicy(LoadPolicy loadPolicy)

//定义线程池线程数目
.setThreadCount(int threadCount)

```
显示方法：
```java

.displayImag(String url, ImageView imageView)

//自定义图片显示配置(图片加载过程显示图)
.displayImag(String url, ImageView imageView,DisplayConfig displayConfig)

//加载完成回调接口
.displayImag(String url, ImageView imageView,OnLoadListener onLoadListener)

.displayImag(String url, ImageView imageView,DisplayConfig displayConfig,OnLoadListener onLoadListener)

```
