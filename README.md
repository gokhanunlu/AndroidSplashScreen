# SplashScreen Uygulaması

<p align="center">
  <img src="https://github.com/gokhanunlu/AndroidSplashScreen/blob/master/app/src/main/res/drawable/gif.gif" width="300" height="600">
</p>


Bu proje, kullanıcıya uygulamanın başlangıcında bir dizi tanıtım ekranı gösteren bir Android uygulamasıdır.

### setupSystemBar

`setupSystemBar` fonksiyonu, sistem çubuğunun rengini beyaza ayarlar ve sistem çubuğu simgelerini koyu renkte yapar.

```java
private void setupSystemBar() {
    Tools.setSystemBarColor(this, android.R.color.white);
    Tools.setSystemBarLight(this);
}
```

### bottomProgressDots

`bottomProgressDots` fonksiyonu, alttaki ilerleme noktalarını yönetir. Bu fonksiyon, geçerli ekranın konumuna bağlı olarak ilerleme noktalarının görünümünü günceller.

```java
private void bottomProgressDots(int current_index) {
    LinearLayout dotsLayout = findViewById(R.id.layoutDots);
    ImageView[] dots = new ImageView[max_step];

    dotsLayout.removeAllViews();
    for (int i = 0; i < dots.length; i++) {
        dots[i] = new ImageView(this);
        int height = 8;
        int width = i == current_index ? (height * 8) : (height * 4);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width, height));
        params.setMargins(2, 10, 2, 10);
        dots[i].setLayoutParams(params);
        dots[i].setImageResource(R.drawable.shape_rectangle);
        dots[i].setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);
        dotsLayout.addView(dots[i]);
    }

    dots[current_index].setImageResource(R.drawable.shape_rectangle);
    dots[current_index].setColorFilter(getResources().getColor(R.color.orange_700), PorterDuff.Mode.SRC_IN);
}
```

### changeViewPagerScroller

`changeViewPagerScroller` fonksiyonu, ViewPager'ın kaydırma hızını değiştirir. Bu, kullanıcının ekranlar arasında daha düzgün bir şekilde geçiş yapabilmesini sağlar.

```java
private void changeViewPagerScroller() {
    try {
        Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
        scrollerField.setAccessible(true);
        FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext(), new LinearInterpolator());
        scrollerField.set(viewPager, scroller);
    } catch (NoSuchFieldException | IllegalAccessException e) {
        e.printStackTrace();
    }
}
```

### animateColorChange

`animateColorChange` fonksiyonu, bir ImageView'ın renk filtresini animasyonlu bir şekilde değiştirir. Bu, animasyonların renklerini dinamik bir şekilde değiştirmek için kullanılır.

```java
private void animateColorChange(ImageView imageView, int colorTo) {
    int colorFrom = currentColor;
    ValueAnimator colorAnim = ValueAnimator.ofArgb(colorFrom, colorTo);
    colorAnim.setDuration(250);
    colorAnim.setEvaluator(new ArgbEvaluator());
    colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            int color = (int) animation.getAnimatedValue();
            imageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    });
    colorAnim.start();
    currentColor = colorTo;
}
```

### animateImageViews

`animateImageViews` fonksiyonu, belirli ImageView'ların animasyonlarını yönetir. Bu fonksiyon, ImageView'ların X ve Y koordinatlarını, döndürme değerlerini ve renklerini animasyonlu bir şekilde değiştirir.

```java
private void animateImageViews(int position) {
    path4.animate().translationX(translationXValues[position]).setDuration(250).start();
    path7.animate().translationY(translationYValues[position]).setDuration(250).start();
    path9.animate().translationY(translationYValues[position]).setDuration(250).start();
    path10.animate().translationY(-translationYValues[position]).rotation(rotationValues[position]).setDuration(250).start();

    int colorTo = getResources().getColor(colorArray[position]);
    animateColorChange(path4, colorTo);
    animateColorChange(path7, colorTo);
    animateColorChange(path9, colorTo);
    animateColorChange(path10, colorTo);
}
```
 

### generateCustomList

`generateCustomList` fonksiyonu, tanıtım ekranlarının listesini oluşturur. Her bir ekran, bir başlık, bir açıklama ve bir resim içerir.

```java
private void generateCustomList() {
    items.add(new DtoIntro(R.drawable.p_1,
            "Lorem ipsum dolor sit amet",
            "Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));

    items.add(new DtoIntro(R.drawable.p_2,
            "Ut enim ad minim veniam",
            "Quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."));
   
    items.add(new DtoIntro(R.drawable.p_3,
            "Ut enim ad minim veniam",
            "Quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."));

    items.add(new DtoIntro(R.drawable.illustration_orange_04,
            "Ut enim ad minim veniam",
            "Quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."));

    items.add(new DtoIntro(R.drawable.illustration_orange_04,
            "Ut enim ad minim veniam",
            "Quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."));
}
