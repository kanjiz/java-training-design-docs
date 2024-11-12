# 文字列検証ガイドライン

このガイドラインでは、Javaにおける文字列の検証パターンと、実装上の推奨事項について説明します。

## 1. 空文字列の検証

### 1.1 isEmpty()メソッドの使用

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EmptyStringExample {
    public static void main(String[] args) {
        // 固定文字列での検証
        String emptyString = "";
        String nonEmptyString = "Hello";
        
        System.out.println("空文字列の検証: " + emptyString.isEmpty());      // true
        System.out.println("非空文字列の検証: " + nonEmptyString.isEmpty()); // false
        
        // 標準入力からの文字列検証
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in))) {
            System.out.println("文字列を入力してください: ");
            String input = reader.readLine();
            
            if (input.isEmpty()) {
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

### 1.2 ベストプラクティス

1. `isEmpty()`の使用を推奨
   - Java SE 6以降で使用可能
   - コードの意図が明確
   - `length() == 0`よりも可読性が高い

2. null安全な実装

```java
public class SafeStringValidator {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
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

## 2. 標準入力とEOF

### 2.1 EOFの検出

標準入力からの読み込みでは、EOFに到達すると`readLine()`は`null`を返します：

- Windows: Ctrl+Z + Enter
- Unix系: Ctrl+D

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EOFDetectionExample {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in))) {
            
            System.out.println("文字列を入力してください（終了はCtrl+Z）:");
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    System.out.println("エンターキーのみが押されました");
                    continue;
                }
                System.out.println("入力された文字列: " + line);
            }
            System.out.println("EOFを検出しました");
            
        } catch (IOException e) {
            System.err.println("入力エラーが発生しました: " + e.getMessage());
        }
    }
}
```

### 2.2 入力パターンの判別

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputPatternExample {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in))) {
            
            System.out.println("文字列を入力してください:");
            String input = reader.readLine();
            
            if (input == null) {
                System.out.println("EOFが検出されました");
            } else if (input.isEmpty()) {
                System.out.println("エンターキーのみが押されました");
            } else {
                System.out.println("通常の入力: " + input);
            }
            
        } catch (IOException e) {
            System.err.println("入力エラーが発生しました: " + e.getMessage());
        }
    }
}
```

## 3. メソッド引数の検証

```java
public class StringArgumentValidator {
    public static void processString(String input) {
        // nullチェックを最初に行う
        if (input == null) {
            throw new IllegalArgumentException("入力がnullです");
        }
        
        // 空文字列のチェック
        if (input.isEmpty()) {
            System.out.println("空文字列が入力されました");
            return;
        }
        
        System.out.println("処理する文字列: " + input);
    }
    
    public static void main(String[] args) {
        try {
            processString(null);         // 例外発生
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        
        processString("");              // 空文字列のメッセージ
        processString("Hello World");   // 通常の処理
    }
}
```

## 4. よくある間違いと注意点

1. nullチェックの順序を間違える

```java
// 間違い - NullPointerExceptionの可能性
if (str.isEmpty() || str == null)

// 正しい
if (str == null || str.isEmpty())
```

2. 無駄なnullチェック

```java
// 冗長 - readLine()がnullを返すのはEOFの時のみ
String input = reader.readLine();
if (input != null && !input.isEmpty()) {
    // 処理
}

// シンプル - EOFを意識的に扱う必要がある場合のみnullチェック
String input = reader.readLine();
if (!input.isEmpty()) {
    // 処理
}
```
