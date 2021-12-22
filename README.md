[![.github/workflows/main.yml](https://github.com/applibgroup/Calligraphy/actions/workflows/main.yml/badge.svg)](https://github.com/applibgroup/Calligraphy/actions/workflows/main.yml)
# Calligraphy

## Introduction
 
###### Custom fonts in Android an OK way.

###### Are you fed up of Custom Views to set fonts? Or traversing the ViewTree to find TextViews? Yeah me too.
## Source
 
###### The code in this repository was inspired from [jbarr21/Calligraphy - v3.0.0](https://github.com/InflationX/Calligraphy). We are very thankful to jbarr21.

## Screenshot

 ![Continuous, Discrete, Custom Java layout](Images/calligraphy.png)
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

## Installation

I) For using Calligraphy module in sample app, include the source code and add the below dependencies in entry/build.gradle to generate hap/support.har.
```
dependencies {
        implementation fileTree(dir: 'libs', include: ['*.jar', '*.har'])
        implementation project(path: ':calligraphy')
        testImplementation 'junit:junit:4.13.1'
}
```
II) For using Calligraphy in separate application using har file, add the har file in the entry/libs folder and add the dependencies in entry/build.gradle file.
```
dependencies {
        implementation fileTree(dir: 'libs', include: ['*.har'])
        testImplementation 'junit:junit:4.13.1'
}
```
III) For using Calligraphy from a remote repository in separate application, add the below dependencies in entry/build.gradle file.
```
dependencies {
        implementation 'dev.applibgroup:calligraphy:1.0.0'
        testImplementation 'junit:junit:4.13.1'
        ohosTestImplementation 'com.huawei.ohos.testkit:runner:1.0.0.200'
}
```
​
## Usage

Set the custom fonts to Components.
Place all your fonts inside resources/rawfile/ folder.
setFont(MainAbility.createFont(context,"Oswald-Stencbab.ttf"));

## Custom font per TextView

<Text
    ohos:id="$+id:text_three"
    ohos:height="match_content"
    ohos:width="match_parent"
    ohos:bottom_margin="16fp"
    ohos:top_margin="16fp"
    ohos:text_size="16vp"
    ohos:text="$string:defined_fontpath_view_theme_attr"/>

With the code below:

```
    Java
    ResourceManager resManager = context.getResourceManager();
    RawFileEntry rawFileEntry = resManager.getRawFileEntry("resources/rawfile/" + name);
	Checkbox checkbox = (Checkbox) component.findComponentById(ResourceTable.Id_check_box);;
    checkbox.setFont(MainAbility.createFont(context,"Oswald-Stencbab.ttf"));
```

What kind of fonts can I add?
--------
* gtw.ttf
* Oswald-Stencbab.ttf
* Roboto-Bold.ttf
* Roboto-ThinItalic.ttf
* RobotoCondensed-Regular.ttf

## Support & extension

Currently there is a limitation to set Toolbar.So Tollbar cannot be set to sample app.

### License
```
Copyright 2013 Christopher Jenkins

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
