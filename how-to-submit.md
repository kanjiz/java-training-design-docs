# 課題の提出方法

## 1. 課題の確認

1. 各チャプターのREADME.mdで課題一覧を確認
2. 実装するクラスの詳細設計書を参照
   - クラスの機能要件を理解
   - 入出力仕様を確認
   - 処理フローを確認

## 2. 環境準備

1. mainメソッドを含むクラス名と同名のフォルダを作成

2. 開発環境の設定

   ```ini
   # .editorconfig
   root = true

   [*]
   charset = utf-8
   end_of_line = lf
   indent_style = space
   indent_size = 4
   trim_trailing_whitespace = true
   insert_final_newline = true
   ```

   主な設定項目：
   - 文字コード：UTF-8
   - 改行コード：LF
   - インデント：半角スペース4個

3. よくある問題と対処方法
   - 文字化けが発生する場合
     - ファイルの文字コードがUTF-8であることを確認
     - エディタの文字コード設定を確認
   - インデントが崩れる場合
     - エディタの設定で「空白の表示」をオンにして確認
     - タブと空白が混在していないか確認

## 3. 実装

1. 詳細設計書に基づいて実装
2. クラス名、メソッド定義は設計書の通りに実装
3. 処理フローに従ってコードを記述

## 4. 動作確認

1. コンパイルエラーがないことを確認
2. 設計書の出力例と同じ結果が得られることを確認
3. エラー処理が必要な場合は動作確認

## 5. 提出

フォルダ構成例：

```text
chapter02/
├── SingleNamePrinter/          # mainメソッドを含むクラス
│   └── SingleNamePrinter.java
├── TwoFruitsPrinter/          # mainメソッドを含むクラス
│   └── TwoFruitsPrinter.java
└── ComplexCalculator/         # 複数クラスがある場合の例
    ├── ComplexCalculator.java  # mainメソッドを含むクラス
    ├── Calculator.java         # 追加のクラス
    └── Helper.java            # 追加のクラス
```

## 注意事項

- フォルダ名はmainメソッドを含むクラス名と一致させる
- 追加のクラスファイルは同じフォルダ内に配置する
- 提出前に不要なファイル（.classファイル等）が含まれていないことを確認
