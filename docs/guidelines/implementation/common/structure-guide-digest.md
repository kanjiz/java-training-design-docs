# プログラム構造化ガイドライン

## 1. 基本原則

### 1.1 単一責任の原則

- 1つのメソッドは1つの責任のみを持つ
- 複数の処理が混在している場合は分割を検討
- 各メソッドの役割が明確に説明できること
- メソッドの責任範囲を超えた処理は避ける：
  - 検索メソッドは結果を返すだけ（表示処理は呼び出し元で）
  - 変換メソッドは変換結果を返すだけ（次の処理は呼び出し元で）
  - 計算メソッドは計算結果を返すだけ（保存は呼び出し元で）
- できるだけ戻り値のあるメソッドを設計する：
  - void は表示や保存など、出力が目的の場合のみ
  - 処理結果は呼び出し元に返して判断を委ねる

### 1.2 メソッド分割の指針

#### メソッドの適切なサイズ

- 1画面（20-30行程度）で収まる長さにする
- 理由：
  - コードレビュー時に全体を把握しやすい
  - スクロールせずにメソッドの処理を理解できる
  - 長いメソッドは複数の責任を持っている可能性が高い
- 短いメソッドについて：
  - 1行でも意味のある処理はメソッドとして分離してよい
  - 処理の意図が命名により明確になる：  
    if (validateOrderNumber(orderInput)) は  
    if (orderInput != null && orderInput.matches("^ORD-\\d{6}$")) より  
    コードの意図が読みやすい

#### 引数の数

- 3つまでを推奨
- 理由：
  - 4つ以上になると引数の順序を間違えやすい
  - 呼び出し側でのミスが起きやすい
- 引数が多い場合の対策：
  - 関連する引数をまとめたクラスを作る
  - メソッドを分割して引数を減らす

#### 処理内容による分割

- 入力検証は独立したメソッドに
  - 検証ロジックの再利用が可能
  - 仕様変更時の修正箇所が明確
- 計算ロジックは再利用可能な形に
  - ビジネスロジックの分離
  - テストが書きやすい
- 出力処理は別メソッドに分離
  - 表示形式の変更に対応しやすい
  - ビジネスロジックと表示の責任を分離

## 2. 基本的な分割例

### 2.1 良い分割例

```java
// 良い例：責任が明確に分離されている
class OrderProcessor {
    // 検証ロジックを独立したメソッドに分離
    // 1行でも、命名によって処理の意図が明確になる
    boolean validateOrderNumber(String orderInput) {
        return orderInput != null && orderInput.matches("^ORD-\\d{6}$");
    }

    void processOrder(String orderInput) {
        // 検証結果に基づいて処理を制御
        if (!validateOrderNumber(orderInput)) {
            return;
        }
        processValidOrder(orderInput);
    }

    void cancelOrder(String orderInput) {
        // 同じ検証メソッドを再利用
        if (!validateOrderNumber(orderInput)) {
            return;
        }
        processCancelOrder(orderInput);
    }
}
```

### 2.2 避けるべきパターン

```java
// 悪い例：検証ロジックが混在している
class OrderProcessor {
    void processOrder(String orderInput) {
        // 検証ロジックが処理と混ざっている
        if (orderInput != null && orderInput.matches("^ORD-\\d{6}$")) {
            processValidOrder(orderInput);
        }
    }

    void cancelOrder(String orderInput) {
        // 同じ検証ロジックが別メソッドでも登場
        if (orderInput != null && orderInput.matches("^ORD-\\d{6}$")) {
            processCancelOrder(orderInput);
        }
        // 問題点：
        // - 大規模なシステムで同じ検証が複数の場所で行われている可能性
        // - 注文番号の形式が変更になった時、修正が必要な箇所を見つけ出すのが困難
        // - 修正すべき箇所を見落としてバグになるリスク
    }
}
```

## 3. 実践的な例

### 3.1 注文処理の良い例

```java
// 注文処理システム：各メソッドの責任が明確
class OrderProcessor {
    // 検証専用のメソッド
    boolean validateOrder(Order order) {
        return order != null 
            && order.getQuantity() > 0
            && order.getUnitPrice() > 0;
    }

    // 計算専用のメソッド
    int calculateOrderAmount(Order order) {
        return order.getQuantity() * order.getUnitPrice();
    }

    // 表示専用のメソッド
    void displayOrderInfo(Order order, int orderAmount) {
        System.out.println("注文番号: " + order.getNumber());
        System.out.println("合計金額: " + orderAmount + "円");
    }

    // 各メソッドを組み合わせて処理を実行
    void processOrder(Order order) {
        // 検証
        if (!validateOrder(order)) {
            System.out.println("注文が無効です");
            return;
        }

        // 計算
        int orderAmount = calculateOrderAmount(order);
        
        // 表示
        displayOrderInfo(order, orderAmount);
    }
}
```

### 3.2 注文処理の改善が必要な例

```java
class BadOrderProcessor {
    // 改善が必要：全ての処理が1つのメソッドに混在
    void processOrder(Order order) {
        // 検証、計算、表示が混在
        if (order != null && order.getQuantity() > 0 && order.getUnitPrice() > 0) {
            int orderAmount = order.getQuantity() * order.getUnitPrice();
            System.out.println("注文番号: " + order.getNumber());
            System.out.println("合計金額: " + orderAmount + "円");
        } else {
            System.out.println("注文が無効です");
        }
    }
}
```

## 4. レビューのポイント

- メソッドの責任範囲は明確か
- メソッドの入出力は明確か：
  - メソッド名から戻り値の内容が予想できるか
  - 引数がどのように使われるか分かるか
  - 副作用（データベース更新やメール送信など）が明確か
- 再利用可能な形で分割されているか
- テストが書きやすい構造になっているか
