# 文字列フォーマット実装ガイドライン

## 概要

Java 15以降で導入された`String.formatted()`メソッドと、従来の文字列フォーマット方法の比較および使用方法を解説します。

## 1. String.formatted()のメリット

### 1.1 主な利点

- メソッドチェーンが可能
- より自然な文字列操作の流れ
- コードの可読性が向上
- NullPointerExceptionの心配が不要

### 1.2 従来の方法との比較

```java
public class FormatComparison {
    public static void main(String[] args) {
        String name = "田中一郎";
        int age = 25;

        // 従来の方法1: String.format()
        String message1 = String.format("Name: %s, Age: %d", name, age);

        // 従来の方法2: printf
        System.out.printf("Name: %s, Age: %d%n", name, age);

        // 新しい方法: String.formatted()
        String message2 = "Name: %s, Age: %d".formatted(name, age);

        System.out.println(message1);
        System.out.println(message2);
    }
}
```

## 2. 基本的な使用パターン

### 2.1 数値のフォーマット

```java
public class NumberFormatExample {
    public static void main(String[] args) {
        double price = 1234.5678;
        int quantity = 42;

        // 小数点以下2桁
        String formattedPrice = "価格: %.2f円".formatted(price);
        System.out.println(formattedPrice);  // 価格: 1234.57円

        // 数値の0埋め
        String formattedQuantity = "商品番号: %04d".formatted(quantity);
        System.out.println(formattedQuantity);  // 商品番号: 0042

        // 桁区切り
        String largeNumber = "売上: %,d円".formatted(1234567);
        System.out.println(largeNumber);  // 売上: 1,234,567円
    }
}
```

### 2.2 文字列の位置揃え

```java
public class AlignmentExample {
    public static void main(String[] args) {
        String title = "商品一覧";
        String item1 = "りんご";
        String item2 = "バナナ";

        // 中央揃え（15文字幅）
        System.out.println("%15s".formatted(title));
        // 左揃え（15文字幅）
        System.out.println("%-15s 100円".formatted(item1));
        System.out.println("%-15s 200円".formatted(item2));
    }
}
```

## 3. 実践的な使用例

### 3.1 レシート出力

```java
public class ReceiptPrinter {
    public static void main(String[] args) {
        String storeName = "サンプルストア";
        String date = "2024-03-15";
        String[] items = {"りんご", "バナナ", "オレンジ"};
        int[] prices = {100, 200, 150};

        // ヘッダー出力
        System.out.println("※※※ %s ※※※".formatted(storeName));
        System.out.println("発行日: %s".formatted(date));
        System.out.println("------------------");

        // 商品一覧
        int total = 0;
        for (int i = 0; i < items.length; i++) {
            System.out.println("%-10s %,4d円".formatted(items[i], prices[i]));
            total += prices[i];
        }

        // 合計
        System.out.println("------------------");
        System.out.println("合計: %,6d円".formatted(total));
    }
}
```

## 4. フォーマット指定子の主な例

| 指定子 | 説明 | 例 |
|--------|------|-----|
| %s | 文字列 | `"Hello, %s".formatted("World")` |
| %d | 整数 | `"Count: %d".formatted(42)` |
| %f | 浮動小数点数 | `"Price: %.2f".formatted(12.345)` |
| %n | 改行 | （printfでのみ使用） |
| %x | 16進数 | `"Hex: %x".formatted(255)` |
| %,d | 桁区切り | `"Value: %,d".formatted(1000000)` |
| %tF | 日付(yyyy-MM-dd) | `"Date: %tF".formatted(new Date())` |

## 5. 注意点

### 5.1 バージョン要件

- Java 15以降で使用可能
- それ以前のバージョンでは`String.format()`を使用

### 5.2 実行時の例外

```java
public class FormatExceptionExample {
    public static void main(String[] args) {
        try {
            // フォーマット指定子と引数の型が一致しない
            String invalid = "Number: %d".formatted("not a number");
        } catch (IllegalFormatException e) {
            System.err.println("フォーマットエラー: " + e.getMessage());
        }

        try {
            // 引数の数が足りない
            String incomplete = "Values: %d %d".formatted(42);
        } catch (IllegalFormatException e) {
            System.err.println("引数不足エラー: " + e.getMessage());
        }
    }
}
```

## 6. ベストプラクティス

1. フォーマット文字列は定数として定義することを検討

   ```java
   private static final String PRICE_FORMAT = "価格: %.2f円";
   // 使用時
   String price = PRICE_FORMAT.formatted(1234.56);
   ```

1. 複雑なフォーマットは可読性のために分割

   ```java
   String template = "名前: %-10s\n単価: %,6d円\n個数: %3d個";
   String output = template.formatted(
       itemName,
       unitPrice,
       quantity
   );
   ```

1. ローカライズが必要な場合は`ResourceBundle`と組み合わせる

※文字列フォーマットの詳細な仕様については、Java API documentationの`Formatter`クラスのドキュメントを参照してください。
