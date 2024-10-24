# 拡張for文実装ガイドライン

## 概要

配列やコレクションの要素を処理する際の拡張for文（for-each文）の使用方法と、
従来のfor文と比較した際の利点について解説します。

## 1. 拡張for文を積極的に使うべき理由

### 1.1 コードの可読性向上

- 意図が明確：「配列の各要素に対して処理を行う」ということが一目で分かる
- 余分な記述が少ない：インデックス変数や配列長の記述が不要
- バグの可能性が低減：配列の境界値の誤りや範囲外アクセスを防止

### 1.2 保守性の向上

- インデックス管理が自動化されるため、ループの制御に関するバグが減少
- 配列からListなど他のコレクション型に変更する際の修正が最小限
- コードレビューが容易になる

### 1.3 パフォーマンスへの影響なし

- コンパイラが最適化を行うため、従来のfor文と実行速度は同等
- 内部的にイテレータが使用され、効率的な実装が保証される

## 2. 拡張for文を使用すべき場面

### 2.1 使用を推奨する場面

- 配列やコレクションの全要素を順番に処理する場合
- 各要素に対して同じ処理を適用する場合
- 要素の値を参照するだけの処理
- 要素の順序は重要だが、位置（インデックス）は不要な場合

```java
public class ScoreCalculator {
    public static void main(String[] args) {
        int[] scores = {85, 90, 78, 88, 92};
        int sum = 0;
        for (int score : scores) {
            sum += score;
        }
        System.out.println("合計点: " + sum);
    }
}
```

### 2.2 特に効果的な使用パターン

- 配列の要素をすべて表示
- 特定条件を満たす要素の検索
- 統計値（合計、平均など）の計算
- 配列の内容を別の配列やリストにコピー

## 3. 従来のfor文を使用すべき場面

以下の場合は、従来のfor文の使用を検討してください：

### 3.1 インデックスが必要な処理

- 要素の位置を使用した計算が必要
- インデックスと値の両方を表示する必要がある
- 複数の配列を同期して処理する

### 3.2 特殊な走査が必要な場合

- 逆順での処理
- 特定の間隔で要素を処理（1つおき、など）
- 配列の一部分のみを処理

### 3.3 要素の更新が必要な場合

- 配列の要素を別の値で上書き
- インデックスを使った要素間の値の交換
- 隣接要素を参照する必要がある処理

## 4. コード比較例

### 4.1 合計値の計算

```java
public class SumComparison {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5};

        // 従来のfor文
        int sum1 = 0;
        for (int i = 0; i < numbers.length; i++) {
            sum1 += numbers[i];
        }
        System.out.println("従来のfor文による合計: " + sum1);

        // 拡張for文
        int sum2 = 0;
        for (int num : numbers) {
            sum2 += num;
        }
        System.out.println("拡張for文による合計: " + sum2);
    }
}
```

### 4.2 条件を満たす要素の検索

```java
public class ScoreChecker {
    public static void main(String[] args) {
        int[] scores = {85, 90, 78, 88, 92};

        // 従来のfor文
        System.out.println("従来のfor文による出力:");
        for (int i = 0; i < scores.length; i++) {
            if (scores[i] >= 90) {
                System.out.println("Index " + i + ": " + scores[i]);
            }
        }

        // 拡張for文
        System.out.println();  // 空行を出力
        System.out.println("拡張for文による出力:");
        for (int score : scores) {
            if (score >= 90) {
                System.out.println(score);
            }
        }
    }
}
```

## 5. ベストプラクティス

1. デフォルトとして拡張for文を検討
1. インデックスが必要な場合のみ従来のfor文を使用
1. コレクションの型に依存しない書き方を心がける
1. パフォーマンスを理由に従来のfor文を選択することは避ける

## 6. 発展的な話題：Stream APIによる反復処理

配列の処理には、Java 8以降で導入されたStream APIを使用するより簡潔な方法もあります。
以下は学習の次のステップとして知っておくと良い発展的な内容です。

### 6.1 配列の合計値計算の例

```java
import java.util.Arrays;

public class ArraySumExample {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5};

        // 拡張for文による方法
        int sum1 = 0;
        for (int num : numbers) {
            sum1 += num;
        }
        System.out.println("拡張for文による合計: " + sum1);

        // Stream APIによる方法
        int sum2 = Arrays.stream(numbers).sum();
        System.out.println("Stream APIによる合計: " + sum2);

        // その他の集計処理の例
        double average = Arrays.stream(numbers).average().orElse(0.0);
        int max = Arrays.stream(numbers).max().orElse(0);
        int min = Arrays.stream(numbers).min().orElse(0);

        System.out.println("平均値: " + average);
        System.out.println("最大値: " + max);
        System.out.println("最小値: " + min);
    }
}
```

### 6.2 Stream APIのメリット

- さらに簡潔なコード記述が可能
- 集計処理（合計、平均、最大値など）が1行で書ける
- 並列処理への対応が容易
- メソッドチェーンによる処理の組み合わせが可能

### 6.3 注意点

- Java 8以降で使用可能
- 単純な処理では従来の方法の方が可読性が高い場合もある
- パフォーマンスを重視する場合は適切な使用方法の理解が必要
- `Optional`型の扱いについての理解が必要（average()の戻り値など）

※Stream APIの詳細な使用方法は、基本的な配列操作とループの理解を深めた後に学ぶことをお勧めします。より複雑な処理や並列処理が必要になった際に、改めて学習を検討してください。
