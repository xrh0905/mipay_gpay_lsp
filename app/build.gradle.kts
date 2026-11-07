import java.util.Properties

plugins {
    id("com.android.application")
}

val localKeystoreProperties = Properties().apply {
    val localFile = file("keystore/keystore.properties")
    if (localFile.exists()) localFile.inputStream().use { load(it) }
}

val keystorePath = System.getenv("KEYSTORE_FILE")
    ?: localKeystoreProperties.getProperty("storeFile")
    ?: "keystore/mipay.jks"
val keystorePassword = System.getenv("KEYSTORE_PASS")
    ?: localKeystoreProperties.getProperty("storePassword").orEmpty()
val keyAliasName = System.getenv("KEYSTORE_ALIAS")
    ?: localKeystoreProperties.getProperty("keyAlias")
    ?: "mipaygpay"
val signingKeyPassword = System.getenv("KEY_PASS")
    ?.takeUnless { it.isNullOrBlank() }
    ?: localKeystoreProperties.getProperty("keyPassword")
    ?: keystorePassword
val hasSigningConfig = keystorePassword.isNotEmpty() && file(keystorePath).exists()

android {
    namespace = "com.mipay.gpay.lsp"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.mipay.gpay.lsp"
        minSdk = 30
        targetSdk = 37
        versionCode = 4
        versionName = "1.3"
    }

    signingConfigs {
        if (hasSigningConfig) {
            create("mipay") {
                storeFile = file(keystorePath)
                storePassword = keystorePassword
                keyAlias = keyAliasName
                keyPassword = signingKeyPassword
            }
        }
    }

    buildTypes {
        debug {
            if (hasSigningConfig) signingConfig = signingConfigs.getByName("mipay")
        }
        release {
            if (hasSigningConfig) signingConfig = signingConfigs.getByName("mipay")
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packaging {
        resources {
            merges += "META-INF/xposed/*"
        }
    }
}

dependencies {
    implementation("com.caverock:androidsvg-aar:1.4")
    compileOnly("io.github.libxposed:api:102.0.0")
}
