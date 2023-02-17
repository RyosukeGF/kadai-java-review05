package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.PreparedStatement;


public class Review05 {

    public static void main(String[] args) {
        // TODO 自動生成されたメソッド・スタブ

     // 3. データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement spstmt = null;
        ResultSet rs = null;
      //Statement stmt = null;

        try {
            //1.ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. DBと接続する, DB指定
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "mysryo119"
                );

         // 4. DBとやりとりする窓口（Statementオブジェクト）の作成
            String selectSql = "SELECT *FROM person where id = ?";
            spstmt = con.prepareStatement(selectSql);
            //stmt = con.createStatement(); //conというオブジェクトのcreatestatementクラス実行して代入


            // 5, 6. Select文の実行と結果を格納／代入
            System.out.print("検索キーワードを入力してください > ");
            int input = KeyInId();

         // 入力されたidをPreparedStatementオブジェクトにセット
            spstmt.setInt(1,input);
            //String input = keyIn();
            //String sql = "SELECT * FROM person"; //MySQLでの実行文を渡す＝文字列
            //String sql = "SELECT * FROM person where id = " + input;

            rs = spstmt.executeQuery(); //直接文字列書き込むのと同じ？


            // 7. 結果を表示する
            while( rs.next() ){
                // Name列の値を取得
                String name = rs.getString("Name");
                int age = rs.getInt("age");

                // 取得した値を表示
                System.out.println(name);
                System.out.println(age);
            }


        } //try終了

        catch (ClassNotFoundException e) {
            // TODO 自動生成された catch ブロック
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            System.err.println("DBに異常終了が発生しました。");
            e.printStackTrace();
        }

        finally {

        //8.接続を閉じる
        if( rs != null ){
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                e.printStackTrace();
            }
        }


        if( spstmt != null ){
            try {
                spstmt.close();
            } catch (SQLException e) {
                System.err.println("Statementを閉じるときにエラーが発生しました。");
                e.printStackTrace();
            }
        }

        if( con != null ){
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("データベース切断時にエラーが発生しました。");
                e.printStackTrace();
            }
        }

        } //finally

    }//void main



    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        } catch (IOException e) {

        }
        return line;
    }

    private static int KeyInId() {
        int result = 0;
        try {
            result = Integer.parseInt(keyIn());
        }catch (NumberFormatException e) {
        }
        return result;

    }


}//Class
