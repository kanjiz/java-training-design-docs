# コンソール入出力実装ガイドライン

## 概要

Java 18以降の標準入出力処理における推奨実装パターンを解説します。
プラットフォーム非依存で適切な文字セット処理を行うコードの実装方法を示します。

## 1. 標準入力の実装パターン

### 1.1 推奨パターン（Java 18以降）

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

try (BufferedReader inputReader = new BufferedReader(
        new InputStreamReader(System.in,
            Charset.forName(System.getProperty("native.encoding"))))) {
    String input = inputReader.readLine();
    // 入力の処理
} catch (IOException e) {
    e.printStackTrace();
}
```

#### 特徴

- プラットフォームのネイティブ文字セットを自動的に使用
- クロスプラットフォームで正しく動作
- try-with-resourcesによるリソース管理

### 1.2 従来のパターン（非推奨）

```java
BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
// または
Scanner scanner = new Scanner(System.in);
```

#### 非推奨の理由

- Java 18以降ではデフォルトの文字セットはUTF-8
- プラットフォームのネイティブ文字セットが考慮されない
- プラットフォーム依存の問題が発生する可能性

## 2. 実装例

### 2.1 基本的な使い方

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Example {
    public static void main(String[] args) {
        try (BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(System.in,
                    Charset.forName(System.getProperty("native.encoding"))))) {
            System.out.print("入力してください: ");
            String input = inputReader.readLine();
            System.out.println("入力された内容: " + input);
        } catch (IOException e) {
            System.err.println("入力エラーが発生しました: " + e.getMessage());
        }
    }
}
```

### 2.2 繰り返し入力を受け付ける場合

```java
try (BufferedReader inputReader = new BufferedReader(
        new InputStreamReader(System.in,
            Charset.forName(System.getProperty("native.encoding"))))) {
    String line;
    while ((line = inputReader.readLine()) != null) {
        // 入力の処理
        if (line.isEmpty()) break;  // 空行で終了する場合
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

### 2.3 エラー処理

```java
try (BufferedReader inputReader = new BufferedReader(
        new InputStreamReader(System.in,
            Charset.forName(System.getProperty("native.encoding"))))) {
    String input = inputReader.readLine();
    if (input == null) {
        // EOF（Ctrl+Z/Ctrl+D）の処理
        return;
    }
    // 入力の処理
} catch (IOException e) {
    System.err.println("入力エラーが発生しました: " + e.getMessage());
} catch (IllegalArgumentException e) {
    System.err.println("システムエラーが発生しました: " + e.getMessage());
}
```

## 3. テスト時の確認項目

1. 標準的な入力処理
   - 一行の入力と処理
   - 複数行の入力と処理

2. 特殊なケース
   - 空文字列の入力
   - EOF（Ctrl+Z/Ctrl+D）の入力
   - 非常に長い入力

3. エラー処理
   - IOException発生時の動作確認
   - リソースの適切な解放
