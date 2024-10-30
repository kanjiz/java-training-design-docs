# programming-design-docs

プログラミング演習課題の詳細設計書を管理するリポジトリです。

## 共通参照事項

1. 各課題の実装前に、それぞれの詳細設計書を参照してください
2. 提出方法は how-to-submit.md を参照してください

## ドキュメントの種類

### 1. 設計書

- 各プログラムの詳細設計を記述
- チャプターごとにディレクトリを分割
- 命名規則：`クラス名-README.md`

### 2. 設計ガイドライン

- 設計書作成の標準とルールを提供
- `docs/guidelines/design-doc-guidelines.md`

### 3. 実装ガイドライン

- 言語別実装の標準パターンとベストプラクティス
- `docs/guidelines/implementation/`配下に各言語のガイドラインを配置
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

## ディレクトリ構造

```text
programming-design-docs/
├── README.md                   # このファイル
├── how-to-submit.md           # 課題の提出方法
├── docs/
│   ├── templates/             # 設計書のテンプレート
│   │   └── design-doc-template.md
│   └── guidelines/           # 各種ガイドライン
│       ├── design-doc-guidelines.md
│       └── implementation/   # 言語別実装ガイドライン
├── chapter02/               # 第2章の課題
├── chapter03/              # 第3章の課題
└── ...                     # 以降のチャプター
```
