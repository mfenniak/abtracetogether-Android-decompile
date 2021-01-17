Instructions for Decompiling
#

1. Download an APK version; apkpure.com is a recommended source.  Store it in the "apk" subfolder, and git add it.

2. Extract the APK

```
rm -rf tmp
mkdir tmp
cd tmp
unzip ../apk/ABTraceTogether_v1.0.0_apkpure.com.apk
```

3. Convert classes.dex to classes.jar, using the tool dex2jar from dex-tools.  The specific version tested is refered to here.

```
dex-tools-2.1-20171001-lanchon/d2j-dex2jar.sh classes.dex
```

Some errors are expected, but can generally be ignored as long as a `classes-dex2jar.jar` file is generated.

classes2.dex seems to only contain third-party dependencies, so I don't bother converting and decompiling it.

4. Using JD-GUI, decompile the jar file.

- Open the `classes-dex2jar.jar` file from the tmp folder.
- From the File menu, select "Save All Sources"
- Navigate to the tmp folder.
- Hit "Save".
- Verify that a new file, `classes-dex2jar.jar.src.zip` is generated.

5. Extract the decompiled sources:

```
cd ..
rm -rf src
mkdir src
unzip ../tmp/classes-dex2jar.jar.src.zip
```

6. Remove all the third-party sources that aren't relevant.

```
rm -rf android androidx com io
```

7. Commit to a new git release.

```
git add apk src
git commit -m"Decompiled ABTraceTogether 1.0.0"
git tag -a v1.0.0 -m"Decompiled ABTraceTogether 1.0.0"
git push
```
