# java-training-design-docs

Javaプログラミング演習課題の詳細設計書を管理するリポジトリです。

## リポジトリ構造

```text
java-training-design-docs/
├── README.md                           # このファイル
├── how-to-submit.md                    # 課題の提出方法
├── docs/
│   ├── templates/                      # 設計書のテンプレート
│   │   └── design-doc-template.md
│   ├── guidelines/                     # ガイドライン
│   │   ├── design-doc-guidelines.md    # 設計書作成のガイドライン
│   │   └── implementation/            # 実装ガイドライン
│   │       └── java/                  # Java実装ガイドライン
│   │           ├── README.md          # 実装ガイドライン一覧
│   │           ├── console-io-guide.md # コンソール入出力ガイド
│   │           ├── enhanced-for-loop-guide.md  # 拡張for文ガイド
│   │           └── string-formatted-guide.md   # 文字列フォーマットガイド
├── chapter02/                          # 第2章の課題
│   ├── README.md                       # 第2章の課題一覧
│   ├── SingleNamePrinter-README.md    
│   ├── TwoFruitsPrinter-README.md
│   └── RouteInputPrinter-README.md
└── chapter03/
    └── ...
```

## ドキュメントの種類

### 1. 設計書

- 各プログラムの詳細設計を記述
- チャプターごとにディレクトリを分割
- 命名規則：`クラス名-README.md`

### 2. 設計ガイドライン

- 設計書作成の標準とルールを提供
- `docs/guidelines/design-doc-guidelines.md`

### 3. 実装ガイドライン

- Java実装の標準パターンとベストプラクティス
- `docs/guidelines/implementation/java/`配下に配置
- 実装時の参照ドキュメントとして機能

## ディレクトリ・ファイル規則

### 1. 命名規則

- チャプターディレクトリ：`chapterXX`（XXは2桁の数字）
- 設計書：`クラス名-README.md`
- 実装ガイドライン：`機能名-guide.md`

### 2. 構成規則

- 各チャプターのディレクトリにはREADME.mdを配置
- 設計書はチャプターディレクトリ直下に配置
- 実装ガイドラインは対応する技術カテゴリのディレクトリに配置

### 3. Markdownの記述規則

- コードブロックの言語指定
  - 出力例：`text`
  - ディレクトリ構造：`text`
  - Mermaid図：`mermaid`
- 図表の作成
  - クラス図：Mermaid class diagram
  - フローチャート：Mermaid flowchart
  - シーケンス図：Mermaid sequenceDiagram
  - 表：Markdownの表形式
