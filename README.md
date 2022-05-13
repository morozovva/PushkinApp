# PushkinApp
Итоговый проект по "Технологии разработки программных приложений"

Необходимо написать мобильное приложение для посетителей музеев с информацией о музее и его выставках. 
С помощью qr-кодов посетители могут прочитать или прослушать информацию об экспонатах выставки.

Основныен зависимости проекта включают в себя зависимости, необходимые для обеспечения стандартного функционала любого приложения на Android. 
Так же добавлены 2 зависимости, позволяющие пользователю пользоваться картой или камерой со считывателем QR-кода.
dependencies {
    implementation 'com.github.yuriy-budiyev:code-scanner:2.1.2'
    implementation 'com.yandex.android:maps.mobile:4.0.0-lite'
    
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    implementation 'androidx.navigation:navigation-fragment:2.4.2'
    implementation 'androidx.navigation:navigation-ui:2.4.2'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'com.google.android.gms:play-services-maps:18.0.2'
    implementation('androidx.lifecycle:lifecycle-extensions:2.2.0')
    implementation('androidx.recyclerview:recyclerview:1.2.1')
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
}

