# check_binding.ps1
Write-Host "=== 1. 当前模块是否开启 viewBinding/dataBinding ===" -ForegroundColor Cyan
Select-String -Path "android\build.gradle" -Pattern "viewBinding|dataBinding"

Write-Host "`n=== 2. layout 文件名 ===" -ForegroundColor Cyan
Get-ChildItem -Path "android\src\main\res\layout" -Filter "*activity_about_face_app*" -Recurse

Write-Host "`n=== 3. 生成的 binding 类位置 ===" -ForegroundColor Cyan

# 先编译一次（静默）
& ".\android\gradlew.bat" -p android assembleDebug *>$null
Get-ChildItem -Path "android\build" -Filter "*ActivityAboutFaceAppBinding*" -Recurse

Write-Host "`n=== 4. kotlin 文件 package ===" -ForegroundColor Cyan
Get-Content "android\src\main\java\com\faceAI\demo\AboutFaceAppActivity.kt" -TotalCount 3