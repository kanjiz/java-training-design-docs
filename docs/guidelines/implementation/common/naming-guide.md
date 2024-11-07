# 命名規則ガイドライン

プログラミング言語に依存しない、命名に関する共通の規則をまとめたガイドラインです。

## 1. 基本原則

### 1.1 文字種の制約

- 名前には英語（アルファベット）のみを使用
- 以下の使用は禁止：
  - 日本語などのマルチバイト文字
  - 日本語のローマ字表記
  - 絵文字

### 1.2 意図の明確さ

- 名前から目的や役割が明確に理解できること
- 略語は広く認知されているもの以外は使用しない
- 曖昧な名前や汎用的すぎる名前を避ける

### 1.3 一貫性

- プロジェクト全体で統一された命名規則を使用
- 同じ概念には同じ動詞を使用（get/fetchの混在を避けるなど）
  - 以下の例のように、似た意味の動詞は一つに統一
    - データ生成：create, make, generate, new から一つを選択
    - データ削除：delete, remove, destroy, erase から一つを選択
    - データ追加：add, insert, append, attach から一つを選択
    - データ更新：update, modify, change, alter から一つを選択
    - データ取得：get, fetch, retrieve, obtain から一つを選択
    - データ表示：show, display, print, render から一つを選択
    - データ検証：validate, verify, check, test から一つを選択
    - データ検索：find, search, lookup, seek から一つを選択
  - ※どの動詞を選ぶかは任意。重要なのは一貫して使用すること

### 1.4 適切な長さ

- 省略しすぎず、冗長すぎない適度な長さ
- スコープが広いほど、より説明的な名前を使用
- 単語数は2〜3語程度を目安に

## 2. 命名パターン

### 2.1 推奨されるパターン

- 処理内容を示す動詞 + 対象を示す名詞
- 複数の単語は意味のある単位で区切る

### 2.2 避けるべきパターン

- 単一文字（ループカウンタを除く）
- 数字による区別（item1, item2）
- 過度な省略
- あいまいな単語（data, info, temp, value）
- データ型を含む名前（intAge, strName, boolIsValid）
  - 型情報はコードから明確
  - 将来の型変更への柔軟性を損なう
  - 可読性を低下させる冗長な情報

## 3. カテゴリー別の命名指針

### 3.1 変数名

- 内容を表す名詞または名詞句
- 一時変数は用途が明確な名前
- 真偽値は質問形または状態を表す形容詞
  - is: 状態を表す（isActive, isValid, isAdult, isLeap）
  - has: 所有を表す（hasChildren, hasCar, hasPermission）
  - can: 能力を表す（canEdit, canDelete）
  - should: 推奨や必要性を表す（shouldUpdate, shouldRefresh）
- 配列やコレクション型は複数形を使用
  - 要素の集合であることを明確に表現
  - 単一の要素と区別しやすい
  - 処理の意図が理解しやすい

### 3.2 関数/メソッド名

- 動作を表す動詞または動詞句で開始
- 戻り値の型や目的が分かる名前
- 副作用がある場合はそれが分かる名前
- 検索条件などの振る舞いの違いは引数ではなく、メソッド名に反映する
  （詳細は structure-guide.md を参照）

### 3.3 クラス/構造体名

- 機能や役割を表す名詞
- 抽象的すぎない、具体的すぎない適度な抽象度
- 責任範囲が明確に分かる名前

## 4. 具体例

### 4.1 良い例

```text
calculateTotal        - 合計を計算する関数
CustomerRepository    - 顧客データを管理するクラス
isValid              - 状態を確認する真偽値
isAdult              - 成人かどうかの状態
isLeap               - 閏年かどうかの状態
hasCar               - 車の所有状態
canEdit              - 編集権限の有無
firstName            - データの内容が明確な変数
customers            - 顧客のコレクション
productList          - 商品のリスト
employeeIds          - 従業員IDの配列
searchCustomerById      - IDによる顧客の検索（1件）
searchCustomersByName   - 名前による顧客の検索（複数の可能性）
searchCustomersByEmail  - メールアドレスによる顧客の検索（複数の可能性）
```

### 4.2 悪い例

```text
calc                 - 処理内容が不明確
MyClass              - 役割が不明確
temp                 - 用途が不明確
data                 - 内容が不明確
value                - 何の値かが不明確
intAge               - データ型を含める必要なし
strName              - データ型を含める必要なし
boolIsValid          - データ型を含める必要なし
searchCustomers(int searchType) - 検索条件を引数で分岐
```

## 5. レビューのポイント

- 名前から目的や役割が即座に理解できるか
- プロジェクトの命名規則に従っているか
- 抽象度は適切か
- 一貫性は保たれているか
- 略語の使用は適切か
