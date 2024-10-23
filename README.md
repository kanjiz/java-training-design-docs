# java-training-design-docs

Javaプログラミング演習課題の詳細設計書を管理するリポジトリです。

## リポジトリ構造

```text
java-training-design-docs/
├── README.md                        # このファイル
├── how-to-submit.md                 # 課題の提出方法
├── docs/
│   ├── templates/                   # 設計書のテンプレート
│   │   └── design-doc-template.md
│   └── guidelines/                  # 設計書作成のガイドライン
│       └── design-doc-guidelines.md
├── chapter02/                       # 第2章の課題
│   ├── README.md                    # 第2章の課題一覧
│   ├── SingleNamePrinter-README.md   
│   ├── TwoFruitsPrinter-README.md
│   └── RouteInputPrinter-README.md
├── chapter03/
    └── ...
```

## ディレクトリ・ファイル規則

### 命名規則

1. チャプターディレクトリ
   - 形式：`chapterXX`（XXは2桁の数字）
   - 例：`chapter02`, `chapter11`

2. 設計書ファイル
   - クラス名-README.md
   - 例：`SingleNamePrinter-README.md`

### 構成規則

1. 各チャプターのディレクトリにはREADME.mdと設計書を配置
2. 設計書はチャプターディレクトリ直下に配置

### Markdownの記述規則

1. コードブロックの言語指定
   - 出力例：`text`
   - ディレクトリ構造：`text`
   - Mermaid図：`mermaid`

2. 図表の作成
   - クラス図：Mermaid class diagram
   - フローチャート：Mermaid flowchart
   - 表：Markdownの表形式
