# Keyten
This project is a result of hackaton. 
This is simple app to play with other people around the world. There is one button. It serves to take over the animal.
Only one man is able to keep animal to his own at time.

## Configure
To run this app you have to follow some steps.
  1. Create keystore file and put it into **\app\keys** fodler.
  2. Create debug-signing.gradle file and also put it into **\app\keys** folder.
The content should look like this:
```
ext {
    storeFileDebug = 'keys/keyten-debug.keystore'
    storePasswordDebug = 'STORE PASSWORD'
    keyAliasDebug = 'KEY ALIAS'
    keyPasswordDebug = 'KEY PASSWORD'
}
```
where values should be replaced with your credentials

  3. create firebase project, generate google-services.json and put it into the **\app\src\debug** folder.
  4. enablo authentication via Google sign-in method.
  5. That's it. You are able to run Debug build type.
