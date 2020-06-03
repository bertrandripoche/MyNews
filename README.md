<h1>My News</h1>
This app has been realized for a training purpose.<br/>
Its goal is to check New-York Times articles.<br/>

<h1>Screenshots</h1>
<img src="readme_material/screenshot1.png" width="200">
<img src="readme_material/screenshot2.png" width="200">
<img src="readme_material/screenshot3.png" width="200">
<img src="readme_material/screenshot4.png" width="200">

<h1>Motivation</h1>
This app was part of the mandatory app to realize to achieve the training.<br/>

<h1>Features</h1>
Allow to consult New-York Times articles :<br/>
- View : view articles by categories (via a view pager and a navigation drawer)<br/>
- Search : search articles by query terms and category<br/>
- Notification : enable notification by query terms and category<br/>

1 langage available : english

<h1>How to use the app ?</h1>

**Displaying articles** :
- you can swipe the list up and down to see available articles
- you can swipe right and left to change category
- you can access categories via the navigation drawer accesible via the hamburger menu
<br/><img src="readme_material/video1.gif" width="200">

**Searching articles** :
- click the magnifying glass to access the search screen
- enter the criterias (at least a query term and a category)
- hit Search to see the results
<br/><img src="readme_material/video1.gif" width="200">

**Notifications** :
- click the hamburger menu then the notification entry to access the Notifications screen
- Choose at least one query term and one category
- Enable the switch button to enable the notifications (once a day)
<br/><img src="readme_material/video1.gif" width="200">

<h1>Technical considerations</h1>
These libraries have been used to develop this app :<br/>
Butterknife :    implementation 'com.jakewharton:butterknife:10.1.0'<br/>
    annotationProcessor 'com.jakewharton:butterknife-compiler:10.1.0'<br/>
Retrofit :    implementation 'com.squareup.retrofit2:retrofit:2.3.0'<br/>
    implementation 'com.squareup.retrofit2:converter-gson:2.3.0'<br/>
RXJava :    implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'<br/>
    implementation 'io.reactivex.rxjava2:rxjava:2.1.7'<br/>
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'<br/>
Glide :    implementation 'com.github.bumptech.glide:glide:4.9.0'<br/>
    annotationProcessor 'com.github.bumptech.glide:compiler:4.9.0'<br/>
RecyclerView :     implementation 'androidx.recyclerview:recyclerview:1.1.0'

<h1>Credits</h1>
Bertrand Ripoche