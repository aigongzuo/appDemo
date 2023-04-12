adb -s 指定设备

ADB 打开具体页面
    现式启动：adb shell am start -n com.plusme.live/com.kxsimon.money.view.RechargActivity    
    隐式启动： adb shell am start -a action.name -d "custom-scheme://custom-host?itemId=17331" package
    