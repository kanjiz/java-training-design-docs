# 文字列検証ガイドライン

このガイドラインでは、Javaにおける文字列の基本的な検証パターンと、実装上の推奨事項について説明します。

## 1. null検証

### 1.1 NullPointerExceptionとは

Javaでは、nullのオブジェクトに対してメソッドを呼び出すと、NullPointerExceptionが発生します。
これは実行時例外であり、プログラムの異常終了を引き起こします。

```java
public class NullExceptionExample {
    public static void main(String[] args) {
        String str = null;
        
        // 以下のコードでNullPointerExceptionが発生
        System.out.println(str.length());  // 例外発生！
        System.out.println(str.isEmpty()); // 例外発生！
    }
}
```

### 1.2 Objectsクラスのnull検証

null検証に使用する主なメソッド：

- [`Objects.isNull(Object obj)`](https://docs.oracle.com/javase/jp/21/docs/api/java.base/java/util/Objects.html#isNull(java.lang.Object))
  - オブジェクトがnullかどうかを検証
  - Java 8以降で使用可能

- [`Objects.nonNull(Object obj)`](https://docs.oracle.com/javase/jp/21/docs/api/java.base/java/util/Objects.html#nonNull(java.lang.Object))
  - オブジェクトがnullでないことを検証
  - Java 8以降で使用可能

```java
import java.util.Objects;

public class BasicNullCheck {
    public static void main(String[] args) {
        String nullStr = null;
        String validStr = "Hello";

        // 基本的なnull検証
        System.out.println("nullの検証: " + Objects.isNull(nullStr));     // true
        System.out.println("非nullの検証: " + Objects.nonNull(validStr)); // true
    }
}
```

## 2. 空文字列の検証

### 2.1 空文字列とは

長さが0の文字列を空文字列と呼びます。nullとは異なり、有効なオブジェクトです。

```java
public class EmptyStringExample {
    public static void main(String[] args) {
        String emptyString = "";
        String nonEmptyString = "Hello";
        
        // isEmpty()による検証
        System.out.println("空文字列の検証: " + emptyString.isEmpty());      // true
        System.out.println("非空文字列の検証: " + nonEmptyString.isEmpty()); // false
        
        // 文字列の長さを確認
        System.out.println("空文字列の長さ: " + emptyString.length());       // 0
        System.out.println("非空文字列の長さ: " + nonEmptyString.length()); // 5
    }
}
```

### 2.2 nullと空文字列の組み合わせ

多くの場合、nullと空文字列の両方を検証する必要があります。

```java
import java.util.Objects;

public class StringValidator {
    /**
     * 文字列がnullまたは空文字列かどうかを検証します。
     *
     * @param str 検査対象の文字列
     * @return 文字列がnullまたは空文字列の場合はtrue、それ以外はfalse
     */
    public static boolean isNullOrEmpty(String str) {
        return Objects.isNull(str) || str.isEmpty();
    }
    
    public static void main(String[] args) {
        String nullStr = null;
        String emptyStr = "";
        String validStr = "Hello";
        
        System.out.println("null文字列: " + isNullOrEmpty(nullStr));     // true
        System.out.println("空文字列: " + isNullOrEmpty(emptyStr));      // true
        System.out.println("有効な文字列: " + isNullOrEmpty(validStr));  // false
    }
}
```

## 3. 実践的な使用例

### 3.1 標準入力での検証

標準入力（BufferedReader.readLine()）からの読み込みでは、nullが返されるのはEOF（End of File）に到達した場合のみです。

- Windows: Ctrl+Z + Enter
- Unix系: Ctrl+D

通常の入力操作ではnullは返されないため、一般的な入力処理では過剰なnull検証は不要です。EOFの検出が必要な特別な場合にのみnull検証を行います。

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class InputValidator {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in))) {
            
            System.out.println("文字列を入力してください:");
            String input = reader.readLine();
            
            if (Objects.isNull(input)) {
                System.out.println("EOFが検出されました");
            } else if (input.isEmpty()) {
                System.out.println("エンターキーのみが押されました");
            } else {
                System.out.println("入力された文字列: " + input);
            }
            
        } catch (IOException e) {
            System.err.println("入力エラーが発生しました: " + e.getMessage());
        }
    }
}
```

### 3.2 引数の検証

メソッドの引数やコンストラクタでのフィールド初期化など、オブジェクトの状態を保証する必要がある場合、[`Objects.requireNonNull(T obj, String message)`](https://docs.oracle.com/javase/jp/21/docs/api/java.base/java/util/Objects.html#requireNonNull(T,java.lang.String)) が便利です。

- 引数がnullの場合にNullPointerExceptionをスロー
- カスタムメッセージの指定が可能
- メソッドの事前条件として意図が明確

```java
import java.util.Objects;

public class AdvancedValidation {
    /**
     * 文字列を大文字に変換します。
     * 
     * @param input 処理対象の文字列
     * @return 大文字に変換された文字列
     * @throws NullPointerException 引数がnullの場合
     * @throws IllegalArgumentException 引数が空文字列の場合
     */
    public static String processString(String input) {
        Objects.requireNonNull(input, "入力文字列はnullにできません");
        
        if (input.isEmpty()) {
            throw new IllegalArgumentException("空文字列は受け付けません");
        }
        
        return input.toUpperCase();
    }

    public static void main(String[] args) {
        try {
            String result = processString("hello");
            System.out.println("処理結果: " + result);
            
            processString(null);          // NullPointerExceptionの例
            // processString("");         // IllegalArgumentExceptionの例
        } catch (NullPointerException e) {
            System.err.println("Null検証: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("空文字検証: " + e.getMessage());
        }
    }
}
```
