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

## 4. 発展的な話題：Stream APIによる文字列検証

Stream APIを使用すると、複数の文字列に対する検証をより簡潔に記述できます。

よく使用されるメソッド：

- [`String.isBlank()`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/String.html#isBlank())
  - 文字列が空もしくは空白文字のみで構成されているかを検証
  - Java 11以降で使用可能
  - 空白文字には半角・全角スペース、タブ、改行などが含まれる

### 4.1 利点

- 複数の検証条件を連鎖させることができる
- より宣言的なコードが書ける
- コレクション操作と組み合わせやすい

### 4.2 制限事項

- 例外処理が困難
  - filterでは例外をスローできない
  - エラーメッセージを細かく制御できない
- 処理の途中経過を確認しにくい
- パフォーマンスを重視する場合は従来の制御文のほうが適している場合がある

### 4.3 使用例

無効なデータを単純に除外して有効なデータのみを収集する場合、Stream APIは効果的です。
一方、無効なデータに対してエラーメッセージを出力したり、例外を投げたりする必要がある場合は、
従来の制御文を使用するほうが適切です。

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StringValidationPatterns {
  /**
   * 文字列が実質的な内容を持つかを判定します。
   * nullや空白文字列は内容がないとみなされます。
   *
   * @param value 判定対象の文字列
   * @return 内容がある場合はtrue、ない場合はfalse
   */
  public static boolean hasContent(String value) {
    return Objects.nonNull(value) && !value.isBlank();
  }

  /**
   * 内容のある文字列の数を数えます。
   * nullや空白文字列は内容がないとみなされます。
   *
   * @param strings 配列
   * @return 内容のある文字列の数
   * @throws NullPointerException stringsがnullの場合
   */
  public static int countStringsWithContent(String[] strings) {
    Objects.requireNonNull(strings, "配列がnullです");
    int count = 0;
    for (String s : strings) {
      if (hasContent(s)) {
        count++;
      }
    }
    return count;
  }

  /**
   * 拡張for文を使用して内容のある文字列の配列を生成します。
   *
   * @param strings 元の文字列配列
   * @return 内容のある文字列のみを含む配列
   * @throws NullPointerException stringsがnullの場合
   */
  public static String[] createArrayWithContentForEach(String[] strings) {
    Objects.requireNonNull(strings, "配列がnullです");
    String[] result = new String[countStringsWithContent(strings)];
    int pos = 0;
    for (String s : strings) {
      if (hasContent(s)) {
        result[pos++] = s;
      }
    }
    return result;
  }

  /**
   * Stream APIを使用して内容のある文字列の配列を生成します。
   *
   * @param strings 元の文字列配列
   * @return 内容のある文字列のみを含む配列
   * @throws NullPointerException stringsがnullの場合
   */
  public static String[] createArrayWithContentStream(String[] strings) {
    Objects.requireNonNull(strings, "配列がnullです");
    return Arrays.stream(strings)
        .filter(StringValidationPatterns::hasContent)
        .toArray(String[]::new);
  }

  /**
   * 拡張for文を使用して内容のある文字列のリストを生成します。
   *
   * @param strings 元の文字列配列
   * @return 内容のある文字列のみを含むリスト
   * @throws NullPointerException stringsがnullの場合
   */
  public static List<String> createListWithContentForEach(String[] strings) {
    Objects.requireNonNull(strings, "配列がnullです");
    List<String> result = new ArrayList<>();
    for (String s : strings) {
      if (hasContent(s)) {
        result.add(s);
      }
    }
    return result;
  }

  /**
   * Stream APIを使用して内容のある文字列のリストを生成します。
   *
   * @param strings 元の文字列配列
   * @return 内容のある文字列のみを含むリスト
   * @throws NullPointerException stringsがnullの場合
   */
  public static List<String> createListWithContentStream(String[] strings) {
    Objects.requireNonNull(strings, "配列がnullです");
    return Arrays.stream(strings)
        .filter(StringValidationPatterns::hasContent)
        .toList();
  }

  public static void main(String[] args) {
    // サンプルデータ: 各国のあいさつ
    String[] greetings = {
        "Hello", // 英語
        "こんにちは", // 日本語
        null, // null
        "", // 空文字列
        "Hola", // スペイン語
        "Bonjour", // フランス語
        "  ", // 空白文字列
        "Hallo", // ドイツ語
        "Ciao", // イタリア語
        "안녕하세요", // 韓国語
        "你好", // 中国語
        "Привет" // ロシア語
    };

    // 配列での結果を表示
    System.out.println("--- 配列での結果 ---");
    String[] resultForEach = createArrayWithContentForEach(greetings);
    System.out.println("拡張for文: " + Arrays.toString(resultForEach));

    String[] resultStream = createArrayWithContentStream(greetings);
    System.out.println("Stream API: " + Arrays.toString(resultStream));

    // リストでの結果を表示
    System.out.println("\n--- リストでの結果 ---");
    List<String> listResultForEach = createListWithContentForEach(greetings);
    System.out.println("拡張for文: " + listResultForEach);

    List<String> listResultStream = createListWithContentStream(greetings);
    System.out.println("Stream API: " + listResultStream);

    // エラー処理が必要な場合は従来の制御文を使用
    System.out.println("\n--- エラー処理が必要な場合 ---");
    for (String s : greetings) {
      try {
        if (Objects.isNull(s)) {
          throw new IllegalArgumentException("nullは受け付けません");
        }
        if (s.isBlank()) {
          throw new IllegalArgumentException("空白文字列は受け付けません");
        }
        System.out.println("有効な文字列: " + s);
      } catch (IllegalArgumentException e) {
        System.err.println("検証エラー: " + e.getMessage());
      }
    }
  }
}
```
