# 詳細設計書作成ガイドライン

## 1. 基本方針

### 1.1 目的

- 初学者向けJava課題の詳細設計を明確に文書化する
- 設計手法とドキュメント作成の基礎を学ぶ
- 一貫性のある設計書を作成する

### 1.2 適用範囲

- すべてのJava課題の詳細設計書に適用

## 2. ファイル管理

### 2.1 ディレクトリ構造

```
java-training-design-docs/
├── chapter02/
│   ├── MyName/
│   │   └── MyName-README.md
│   └── [課題名]/
│       └── [課題名]-README.md
└── docs/
    ├── templates/
    │   └── design-doc-template.md
    └── guidelines/
        └── design-doc-guidelines.md
```

### 2.2 命名規則

- 設計書ファイル：`[クラス名]-README.md`
- ディレクトリ：クラス名と同じ名前
- すべてオリジナルのクラス名を維持

## 3. 設計書の作成手順

1. テンプレートファイルをコピー
2. クラス名、機能要件を記入
3. クラス図とクラス定義を作成
4. メソッド設計を記述
5. 処理フローを作成
6. 入出力仕様を定義
7. その他必要事項を記入

## 4. 各セクションの記述方法

### 4.1 機能要件

- プログラムの目的と主な機能を簡潔に記述
- 箇条書きで記載
- 仕様が明確に伝わる表現を使用

### 4.2 クラス設計

#### クラス図

- mermaidの記法に従う
- 必要最小限の情報を記載
- 可読性を重視

#### クラス定義

- 表形式で記述
- 必須項目を漏れなく記載
- 明確な表現を使用

### 4.3 メソッド設計

- メソッド定義は表形式で記述
- 機能説明は箇条書きで具体的に
- オーバーロードがある場合は別々に記述

### 4.4 処理フロー

- mermaidのflowchartを使用
- 主要な処理の流れを記載
- 分岐や繰り返しは適切に表現

### 4.5 入出力設計

- 入力仕様：形式、制約、範囲を明記
- 出力仕様：形式、例を具体的に記載
- エラー時の挙動も記載

## 5. 記述上の注意点

### 5.1 一般的な注意

- 簡潔かつ明確な文章を使用
- 主語と述語を明確に
- 箇条書きを効果的に活用
- 一貫性のある記述を維持

### 5.2 図表の使用

- 表：項目の整理に使用
- 図：処理の流れの視覚化に使用
- mermaid：クラス図、フローチャートに使用

### 5.3 コードブロック

- 出力例やコマンドは適切にコードブロックで囲む
- 言語指定を適切に行う

## 6. レビューのポイント

### 6.1 必須確認項目

- 機能要件の明確さ
- クラス図とクラス定義の整合性
- メソッド定義の正確さ
- 処理フローの妥当性
- 入出力仕様の具体性
- 学習項目の適切な記載

### 6.2 品質確認

- 記述の一貫性
- 表現の分かりやすさ
- 必要十分な情報量
- 形式の統一性

## 7. 改訂履歴管理

- バージョン番号を記録
- 変更内容を記録
- 変更理由を記録