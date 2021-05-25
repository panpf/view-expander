buildscript {
    repositories {
        maven { setUrl("https://mirrors.huaweicloud.com/repository/maven/") }
        mavenCentral()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.2.1")
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.15.1")
    }
}

allprojects {
    repositories {
        maven { setUrl("https://mirrors.huaweicloud.com/repository/maven/") }
        mavenCentral()
        jcenter()
    }
}
