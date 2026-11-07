# MiPay GPay LSP

[![Build](https://github.com/MasterOfStar/mipay_gpay_lsp/actions/workflows/build.yml/badge.svg)](https://github.com/MasterOfStar/mipay_gpay_lsp/actions)
[![Release](https://img.shields.io/github/v/release/MasterOfStar/mipay_gpay_lsp)](https://github.com/MasterOfStar/mipay_gpay_lsp/releases)
[![License](https://img.shields.io/github/license/MasterOfStar/mipay_gpay_lsp)](LICENSE)

> LSPosed 模块：在小米智能卡（MiPay）刷卡页面注入 Google Pay 快捷按钮，一键跳转 Google Wallet。
> 本项目全部代码工作由Vibe-coding完成

## 🧭 设计理念

> Minimal scope, modern standard, maximum hybrid flavor — seamless and standardized.

- **最小作用域（Minimal scope）**：静态作用域仅 `com.miui.tsmclient`，不 Hook `system_server`，不请求 Root
- **现代标准（Modern standard）**：libxposed API 102 + `META-INF/xposed` 静态声明，不再使用遗留 XposedBridge API
- **最大程度保留混合体验（Maximum hybrid flavor）**：不接管、不替换 MiPay 原有刷卡流程，只在原页面叠加 Google Wallet 入口
- **无缝、标准化（Seamless & standardized）**：直接 `startActivity` 打开 Wallet QUICKDRAW，遵循 Android 标准组件与生命周期

## ✨ 功能特性

- **无缝集成**：Hook 小米智能卡 `DoubleClickActivity`，在刷卡页面右下角显示 Google Pay 药丸按钮
- **Material Design 3**：自适应系统 Monet 主题色，与系统风格完美融合
- **一键跳转**：点击按钮直接打开 Google Wallet 刷卡界面
- **轻量可靠**：仅注入必要代码，不影响 MiPay 原有功能

## 📱 系统要求

| 项目 | 要求 |
|------|------|
| 框架 | [LSPosed](https://github.com/LSPosed/LSPosed)（支持 libxposed API 102） |
| 模块 API | libxposed API 102（静态作用域） |
| Android | 12+ (API 30+) |
| 目标应用 | 小米智能卡 (`com.miui.tsmclient`) |
| 依赖应用 | Google Wallet (`com.google.android.apps.walletnfcrel`) |

## 🚀 安装使用

1. **下载 APK**：从 [Releases](https://github.com/MasterOfStar/mipay_gpay_lsp/releases) 下载最新版本
2. **安装模块**：在 LSPosed Manager 中安装并启用模块
3. **作用域**：模块已静态声明仅作用于 `小米智能卡` (`com.miui.tsmclient`)，无需手动添加 `System Framework`
4. **重启应用**：强制停止并重新打开小米智能卡
5. **开始使用**：刷卡时右下角会出现 Google Pay 按钮，点击即可跳转

## 🖼️ 效果预览

在小米智能卡刷卡页面右下角显示 Google Pay 药丸按钮：

```
┌─────────────────────────────┐
│         Mipay               │
│                             │
│                             │
│                             │
│                    ┌─────┐  │
│                    │ GPay│  │  ← 注入的快捷按钮
│                    └─────┘  │
└─────────────────────────────┘
```

## 💡 灵感来源

<img src="inspiration.jpg" alt="灵感来源" width="400" height="700">

## 🛠️ 自行编译

```bash
# 克隆仓库
git clone https://github.com/MasterOfStar/mipay_gpay_lsp.git
cd mipay_gpay_lsp

# 编译 Release APK
./gradlew assembleRelease

# 输出路径
app/build/outputs/apk/release/app-release.apk
```

## 📋 技术细节

- **模块 API**：libxposed API 102，入口 `META-INF/xposed/java_init.list`，静态作用域 `META-INF/xposed/scope.list`
- **Hook 目标**：`com.miui.tsmclient.ui.quick.DoubleClickActivity`
- **注入方式**：动态添加 FrameLayout + ImageButton
- **SVG 渲染**：使用 [AndroidSVG](https://github.com/BigBadaboom/androidsvg) 渲染 Google Pay Logo
- **主题适配**：读取系统 `system_accent1_100` 色值实现 Monet 动态着色
- **跳转方式**：直接 `startActivity` 打开 Wallet 快捷刷卡页，无需 Root

## 🤝 贡献

欢迎提交 Issue 和 PR！

## 📄 许可证

[MIT License](LICENSE)

---

> 本项目仅供学习交流使用，与小米、Google 无任何关联。
