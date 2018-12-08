# SP_DrawerLayout

This is a lightweight drawer layout. If you want to reduce your app-size by avoiding to import support libs required for drawer-layout and also want to overcome some of the common issues such as one described at https://stackoverflow.com/questions/18029874/drawerlayout-getting-stuck-on-swipe/53629344#53629344.

# Advantages
1. Adding to your layout file as simple as adding a relative layout.
2. Easily make drawer on right edge or left edge simply by setting an attribute in xml layout.
3. Complete freedom to design your drawer with headers, footers, lists, scrolls, etc.
4. Choose the overlay that appears behind the drawer when drawer is pulled open.
5. Completely open source

Please vote the anser here if this helps you - https://stackoverflow.com/a/53629344/9640177

# How to use
This drawer layout is very simple yo use. Before using, you need to add this to your project. You can do that either by coping the lib files or by using graddle.

## Setting up dependencies
In your project level graddle file add maven { url 'https://jitpack.io' } under allprojects.

    allprojects {
      repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' } //Add this line
      }
    }
    
In your app level graddle add this dependency 

    dependencies {
       implementation 'com.github.mayank1513:SP_DrawerLayout:master-SNAPSHOT'
       
You are ready to go. In case you want to customize the library even further, you can copy this repository to your local machine and edit.

## Using SP_DrawerLayout
Now you need to add custom xmlns to the xml layout file where you want to add this drawer layout. For example in your activity xml

    <?xml version="1.0" encoding="utf-8"?>
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    
      xmlns:custom="http://schemas.android.com/apk/res-auto"
    
      android:orientation="vertical"
      android:layout_width="match_parent"
      android:layout_height="match_parent">
      
Now you can define your layout

    <com.mayank.drawerlayout.Drawer 
        android:id="@+id/drawer"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/colorPrimary"
        
        custom:drawerBackground="@color/colorAccent"</b>
        custom:drawerWidth="250dp"
        custom:onRightEdge="false">
        
    </com.mayank.drawerlayout.Drawer>
    
If you want to Customize the background you can do it in your java file.
     
     drawer = findViewById(R.id.drawer);
     drawer.setShadowingBackground(findViewById(R.id.dBackground));
     
# Methods available
1. expand() to expand the drawer programatically say when button is touched or title bar is touched.
2. collapse() to collapse the drawer programatically, for example, after an oprion is clicked from inside the drawer.
3. isCollapsed() to check if the drawer is collapsed.
4. disableDrawer() to disable drawer in case you want the drawer to not expand on swipe from edge while displaying some items.
5. setShadowingBackground(View view) to set your custom background - replace the dark semitransperent view that appears behind drawer and overlays your other views.

For best experience add drawer layout as the last child of you layout.
  
