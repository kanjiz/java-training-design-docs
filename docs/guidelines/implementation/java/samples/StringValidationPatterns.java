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
   * @return 内容のある文字列のみを含む、変更可能なArrayList
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
   * @return 内容のある文字列のみを含む、変更不可なリスト
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
