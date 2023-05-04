# PDF_APP
https://user-images.githubusercontent.com/70539485/143813055-07e035ca-99e9-41da-835c-8e2b0ee025b3.mp4






Dependency that we are using for pdf view:
First add and sync the project 
implementation 'com.github.barteksc:android-pdf-viewer:2.8.2'
We are loading the pdf files from the assets folder that is present in our app. The main deception of this is that it will increase the size of our app. If you want to decrease the size of your app, then loads, only those pdf's that are in KB's, and if you want to load bigger size pdf files you can also do that by using a web-view, but it's not stable, I have already tried that thing and sometimes the pdf does not load. The best thing that you can perform is create a fire-base link of your pdf's and show it in the recycler's view
